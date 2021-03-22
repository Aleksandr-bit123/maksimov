package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.GoalEntity;
import com.diploma.maksimov.db.entity.GoodTurnoverEntity;
import com.diploma.maksimov.db.entity.OrderEntity;
import com.diploma.maksimov.db.entity.PointEntity;
import com.diploma.maksimov.db.repository.GoalRepository;
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
public class GoalServise implements IGoalService {
    private final GoalRepository goalRepository;
    private final PointRepository pointRepository;
    private final OrderRepository orderRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GoalServise(GoalRepository goalRepository, PointRepository pointRepository, OrderRepository orderRepository) {
        this.goalRepository = goalRepository;
        this.pointRepository = pointRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public long create(Goal goal) {
        GoalEntity goalEntity = objectMapper.convertValue(goal, GoalEntity.class);
        Optional<PointEntity> optionalPointEntity = pointRepository.findById(goal.getPointId());
        if (optionalPointEntity.isPresent()) {
            PointEntity pointEntity = optionalPointEntity.get();
            if (pointEntity.getPointType() == Point.PointType.client) {
                List<OrderEntity> orderEntityList = orderRepository.findAllByDateAndClientIdandStatusIsNull(goalEntity.getDeliveryDate(), goalEntity.getPointId());
                List<GoodTurnoverEntity> goodTurnoverEntityList;
                if (goalEntity.getGoodTurnoverList() == null) {
                    goodTurnoverEntityList = new LinkedList<>();
                } else {
                    goodTurnoverEntityList = goalEntity.getGoodTurnoverList();
                }
                GoalEntity finalGoalEntity = goalEntity;
                orderEntityList.forEach(orderEntity -> {

                    goodTurnoverEntityList.add(new GoodTurnoverEntity(null, finalGoalEntity.getId(), orderEntity.getGoodId(), orderEntity.getQuantity(), GoodTurnover.PaymentMethod.cash, "", (byte) 100, true, orderEntity.getId()));
                    orderEntity.setStatus(Goal.Status.waiting);
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

        return objectMapper.convertValue(all, new TypeReference<List<Goal>>() {
        });
    }

    @Override
    public Goal read(long id) {
        Optional<GoalEntity> goalEntityOptional = goalRepository.findById(id);
        if (goalEntityOptional.isPresent()) {
            GoalEntity goalEntity = goalEntityOptional.get();
            return objectMapper.convertValue(goalEntity, Goal.class);
        }
        return null;
    }

    @Override
    public boolean update(Goal goal, long id) {
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
                        if (pointEntity.getPointType() == Point.PointType.contragent) {
                            List<GoodTurnover> goalGoodTurnoverList = goal.getGoodTurnoverList();
                            List<GoodTurnoverEntity> goalGoodTurnoverListTemp = new LinkedList<>();
                            goal.setId(null);
                            Long newGoalId;
                            //скарируем список оборота товара на уже существующие связи с целью-контрагент
                            goalGoodTurnoverList.forEach(goodTurnover -> {
                                if (goodTurnover.getLinkPoint() != null &&
                                        goodTurnover.getLinkPoint().equals(pointEntity.getId())) {
                                    goal.setId(goodTurnover.getLinkGoal());
                                }
                                if (goodTurnover.getLinkPoint() == null && goodTurnover.getLink() == null && goodTurnover.getLinkGoal() == null) {
                                    goodTurnover.setId(null);
                                    goodTurnover.setGoalId(null);
                                    goodTurnover.setTurnover(false);
                                    goodTurnover.setPaymentMethod(GoodTurnover.PaymentMethod.paidFor);
                                    goalGoodTurnoverListTemp.add(objectMapper.convertValue(goodTurnover,GoodTurnoverEntity.class));
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

                            List<GoodTurnoverEntity> finalGoodTurnoverContragentEntityList = goodTurnoverContragentEntityList;
                            goodTurnoverClientEntityList.forEach(goodTurnoverClientEntity -> finalGoodTurnoverContragentEntityList.forEach(goodTurnoverContragentEntity -> {
                                if (goodTurnoverClientEntity.getOrderId().equals(goodTurnoverContragentEntity.getOrderId())) {
                                    goodTurnoverClientEntity.setLink(goodTurnoverContragentEntity.getId());
                                    if (goalContragentEntityOptional.isPresent()){
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

                        if (pointEntity.getPointType() == Point.PointType.client && pointEntityOfExistGoal.getId().equals(pointEntity.getId())) {
                            create(goal);
                            return true;
                        }
                    }
                }
            }
            goalRepository.save(objectMapper.convertValue(goal, GoalEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        Optional<GoalEntity> goalEntityOptional = goalRepository.findById(id);
        if (goalEntityOptional.isPresent()) {
            goalRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Goal> readAllByDate(LocalDate date) {
        Iterable<GoalEntity> all = goalRepository.findAllByDate(date);

        return objectMapper.convertValue(all, new TypeReference<List<Goal>>() {
        });
    }
}
