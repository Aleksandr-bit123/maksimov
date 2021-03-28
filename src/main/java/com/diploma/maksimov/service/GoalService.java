package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.GoalEntity;
import com.diploma.maksimov.db.entity.GoodTurnoverEntity;
import com.diploma.maksimov.db.entity.OrderEntity;
import com.diploma.maksimov.db.entity.PointEntity;
import com.diploma.maksimov.db.repository.GoalRepository;
import com.diploma.maksimov.db.repository.GoodTurnoverRepository;
import com.diploma.maksimov.db.repository.OrderRepository;
import com.diploma.maksimov.db.repository.PointRepository;
import com.diploma.maksimov.dto.Goal;
import com.diploma.maksimov.dto.GoodTurnover;
import com.diploma.maksimov.dto.Point;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class GoalService implements CrudService<Goal,Long,Long> {
    private final GoalRepository goalRepository;
    private final PointRepository pointRepository;
    private final OrderRepository orderRepository;
    private final GoodTurnoverRepository goodTurnoverRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GoalService(GoalRepository goalRepository, PointRepository pointRepository, OrderRepository orderRepository, GoodTurnoverRepository goodTurnoverRepository) {
        this.goalRepository = goalRepository;
        this.pointRepository = pointRepository;
        this.orderRepository = orderRepository;
        this.goodTurnoverRepository = goodTurnoverRepository;
    }

    @Override
    public Long create(Goal goal) {
        GoalEntity goalEntity = objectMapper.convertValue(goal, GoalEntity.class);
        Optional<PointEntity> optionalPointEntity = pointRepository.findById(goal.getPointId());
        if (optionalPointEntity.isPresent()) {
            PointEntity pointEntity = optionalPointEntity.get();
            if (pointEntity.getPointType() == Point.PointType.client) {
                List<OrderEntity> orderEntityList = orderRepository.findAllByDateAndClientIdAndStatusIsNull(goalEntity.getDeliveryDate(), goalEntity.getPointId());
                List<GoodTurnoverEntity> goodTurnoverEntityList;
                if (goalEntity.getGoodTurnoverList() == null) {
                    goodTurnoverEntityList = new LinkedList<>();
                } else {
                    goodTurnoverEntityList = goalEntity.getGoodTurnoverList();
                }
                GoalEntity finalGoalEntity = goalEntity;
                orderEntityList.forEach(orderEntity -> {
                    if (orderEntity.getStatus() == null) {
                        goodTurnoverEntityList.add(new GoodTurnoverEntity(null, finalGoalEntity.getId(), orderEntity.getGoodId(), orderEntity.getQuantity(), GoodTurnover.PaymentMethod.cash, "", (byte) 100, true, orderEntity.getId(),null,null,null));
                        orderEntity.setStatus(Goal.Status.waiting);
                    }
                });
                finalGoalEntity.setGoodTurnoverList(goodTurnoverEntityList);
                goalEntity = goalRepository.save(finalGoalEntity);
                return goalEntity.getId();
            }
        }
        goalEntity = goalRepository.save(goalEntity);
        return goalEntity.getId();
    }

    @Override
    public List<Goal> readAll() {
        Iterable<GoalEntity> all = goalRepository.findAll();

        return objectMapper.convertValue(all, new TypeReference<>() {
        });
    }

    @Override
    public Goal read(Long id) {
        Optional<GoalEntity> goalEntityOptional = goalRepository.findById(id);
        if (goalEntityOptional.isPresent()) {
            GoalEntity goalEntity = goalEntityOptional.get();
            return objectMapper.convertValue(goalEntity, Goal.class);
        }
        return null;
    }

    @Override
    public boolean update(Goal goal, Long id) {
        Optional<GoalEntity> goalEntityOptional = goalRepository.findById(id);
        if (goalEntityOptional.isPresent()) {
            Optional<PointEntity> optionalPointEntityOfExistGoal = pointRepository.findById(goalEntityOptional.get().getPointId());
            if (optionalPointEntityOfExistGoal.isPresent()) {
                PointEntity pointEntityOfExistGoal = optionalPointEntityOfExistGoal.get();
                if (pointEntityOfExistGoal.getPointType() == Point.PointType.client) {
                    Optional<PointEntity> optionalPointEntity = pointRepository.findById(goal.getPointId());
                    if (optionalPointEntity.isPresent()) {
                        PointEntity pointEntity = optionalPointEntity.get();
                        //назначаем контрагента для текущей точки
                        if (pointEntity.getPointType() == Point.PointType.contragent && goal.getDriverId().equals(goalEntityOptional.get().getDriverId())) {
                            List<GoodTurnover> goalGoodTurnoverList = goal.getGoodTurnoverList();
                            List<GoodTurnoverEntity> goalGoodTurnoverListTemp = new LinkedList<>();
                            goal.setId(null);
                            Long newGoalId;
                            //сканируем список оборота товара на уже существующие связи с целью-контрагент
                            goalGoodTurnoverList.forEach(goodTurnover -> {
                                if (goodTurnover.getLinkPoint() != null &&
                                        goodTurnover.getLinkPoint().equals(pointEntity.getId())) {
                                    goal.setId(goodTurnover.getLinkGoal());
                                }
                                if ((goodTurnover.getLinkPoint() == null || goodTurnover.getLinkPoint().equals(pointEntity.getId())) && goodTurnover.getLink() == null /*&& goodTurnover.getLinkGoal() == null*/) {
                                    goodTurnover.setId(null);
                                    goodTurnover.setGoalId(null);
                                    goodTurnover.setTurnover(false);
                                    goodTurnover.setPaymentMethod(GoodTurnover.PaymentMethod.paidFor);
                                    goalGoodTurnoverListTemp.add(objectMapper.convertValue(goodTurnover, GoodTurnoverEntity.class));
                                }
                            });

                            newGoalId = goal.getId();

                            if (goal.getId() == null) {
                                goal.setGoodTurnoverList(new LinkedList<>());
                                newGoalId = create(goal);
                            }

                            Optional<GoalEntity> goalContragentEntityOptional = goalRepository.findById(newGoalId);
                            Long finalNewGoalId = newGoalId;
                            goalContragentEntityOptional.ifPresent(goalEntity -> {
                                goalEntity.getGoodTurnoverList().addAll(goalGoodTurnoverListTemp);
                                goalEntity.getGoodTurnoverList().forEach(goodTurnoverEntity -> goodTurnoverEntity.setGoalId(finalNewGoalId));
                            });
                            goalContragentEntityOptional.ifPresent(goalRepository::save);

                            List<GoodTurnoverEntity> goodTurnoverClientEntityList = goalEntityOptional.get().getGoodTurnoverList();

                            List<GoodTurnoverEntity> goodTurnoverContragentEntityList = new LinkedList<>();
                            if (goalContragentEntityOptional.isPresent()) {
                                goodTurnoverContragentEntityList = goalContragentEntityOptional.get().getGoodTurnoverList();
                            }
                            //установка связей между целью-клиентом и целью-заказом
                            List<GoodTurnoverEntity> finalGoodTurnoverContragentEntityList = goodTurnoverContragentEntityList;
                            goodTurnoverClientEntityList.forEach(goodTurnoverClientEntity -> finalGoodTurnoverContragentEntityList.forEach(goodTurnoverContragentEntity -> {
                                if (goodTurnoverClientEntity.getOrderId().equals(goodTurnoverContragentEntity.getOrderId())) {
                                    goodTurnoverClientEntity.setLink(goodTurnoverContragentEntity.getId());
                                    if (goalContragentEntityOptional.isPresent()) {
                                        goodTurnoverClientEntity.setLinkPoint(goalContragentEntityOptional.get().getPointId());
                                        goodTurnoverClientEntity.setLinkGoal(goalContragentEntityOptional.get().getId());
                                    }
                                    goodTurnoverContragentEntity.setLink(goodTurnoverClientEntity.getId());
                                    goodTurnoverContragentEntity.setLinkPoint(goalEntityOptional.get().getPointId());
                                    goodTurnoverContragentEntity.setLinkGoal(goalEntityOptional.get().getId());
                                }
                            }));

                            goalContragentEntityOptional.ifPresent(goalRepository::save);
                            goalEntityOptional.ifPresent(goalRepository::save);
                            return true;
                        }

                        if (pointEntity.getPointType() == Point.PointType.client &&
                                pointEntityOfExistGoal.getId().equals(pointEntity.getId()) &&
                                goal.getDriverId().equals(goalEntityOptional.get().getDriverId())) {
                            create(goal);
                            return true;
                        }
                    }
                }

                if (pointEntityOfExistGoal.getPointType() == Point.PointType.contragent) {
                    Optional<PointEntity> optionalPointEntity = pointRepository.findById(goal.getPointId());
                    if (optionalPointEntity.isPresent()) {
                        PointEntity pointEntity = optionalPointEntity.get();
                        //назначаем контрагента для текущей точки
                        if (pointEntity.getPointType() == Point.PointType.contragent &&
                                pointEntityOfExistGoal.getId().equals(pointEntity.getId()) &&
                                goal.getDriverId().equals(goalEntityOptional.get().getDriverId())) {
                            create(goal);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        Optional<GoalEntity> goalEntityOptional = goalRepository.findById(id);
        if (goalEntityOptional.isPresent()) {
            Optional<PointEntity> optionalPointEntityOfExistGoal = pointRepository.findById(goalEntityOptional.get().getPointId());
            if (optionalPointEntityOfExistGoal.isPresent()) {
                PointEntity pointEntityOfExistGoal = optionalPointEntityOfExistGoal.get();
                if (pointEntityOfExistGoal.getPointType() == Point.PointType.contragent) {

                    List<GoodTurnoverEntity> goodTurnoverEntityList = goalEntityOptional.get().getGoodTurnoverList();
                    goodTurnoverEntityList.forEach(goodTurnoverEntity -> {

                        Optional<GoodTurnoverEntity> goodTurnoverEntityOptional = goodTurnoverRepository.findById(goodTurnoverEntity.getLink());
                        goodTurnoverEntityOptional.ifPresent(goodTurnoverEntity1 -> {
                            goodTurnoverEntity1.setLinkGoal(null);
                            goodTurnoverEntity1.setLinkPoint(null);
                            goodTurnoverEntity1.setLink(null);
                        });
                    });

                    goalRepository.deleteById(id);
                    return true;

                }
                if (pointEntityOfExistGoal.getPointType() == Point.PointType.client) {
                    List<Long> contragentIdForDeleteList = new LinkedList<>();
                    goalEntityOptional.get().getGoodTurnoverList().forEach(goodTurnoverEntity -> {
                        Optional<OrderEntity> orderEntity = orderRepository.findById(goodTurnoverEntity.getOrderId());
                        orderEntity.ifPresent(orderEntity1 -> orderEntity1.setStatus(null));
                        orderEntity.ifPresent(orderRepository::save);
                        goodTurnoverRepository.deleteById(goodTurnoverEntity.getLink());
                        contragentIdForDeleteList.add(goodTurnoverEntity.getLinkGoal());

                    });

                    while (contragentIdForDeleteList.iterator().hasNext()) {
                        Long currentId = contragentIdForDeleteList.iterator().next();
                        goalRepository.deleteById(currentId);
                    }

                    goalRepository.deleteById(id);
                    return true;
                }
            }
        }
        return false;
    }

    public List<Goal> readAllByDate(LocalDate date) {
        Iterable<GoalEntity> all = goalRepository.findAllByDate(date);

        return objectMapper.convertValue(all, new TypeReference<>() {
        });
    }

    public List<Goal> readAllByDateAndDriver(LocalDate date, Long driverId) {
        Iterable<GoalEntity> all = goalRepository.findAllByDateAndDriverId(date, driverId);

        return objectMapper.convertValue(all, new TypeReference<>() {
        });
    }
}
