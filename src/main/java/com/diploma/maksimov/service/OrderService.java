package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.GoalEntity;
import com.diploma.maksimov.db.entity.GoodTurnoverEntity;
import com.diploma.maksimov.db.entity.OrderEntity;
import com.diploma.maksimov.db.repository.GoalRepository;
import com.diploma.maksimov.db.repository.GoodTurnoverRepository;
import com.diploma.maksimov.db.repository.OrderRepository;
import com.diploma.maksimov.dto.Goal;
import com.diploma.maksimov.dto.Order;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements CrudService<Order,Long,Long> {
    private final OrderRepository orderRepository;
    private final GoodTurnoverRepository goodTurnoverRepository;
    private final GoalRepository goalRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderService(OrderRepository orderRepository, GoodTurnoverRepository goodTurnoverRepository, GoalRepository goalRepository) {
        this.orderRepository = orderRepository;
        this.goodTurnoverRepository = goodTurnoverRepository;
        this.goalRepository = goalRepository;
    }

    @Override
    public Long create(Order order) {
        if (order.getId() == null || !orderRepository.existsById(order.getId())) {
            OrderEntity orderEntity = objectMapper.convertValue(order, OrderEntity.class);
            orderEntity = orderRepository.save(orderEntity);
            return orderEntity.getId();
        }
        return order.getId();
    }

    @Override
    public List<Order> readAll() {
        Iterable<OrderEntity> all = orderRepository.findAll();
        return objectMapper.convertValue(all, new TypeReference<>() {
        });
    }

    @Override
    public Order read(Long id) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
        if (orderEntityOptional.isPresent()) {
            OrderEntity orderEntity = orderEntityOptional.get();
            return objectMapper.convertValue(orderEntity, Order.class);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean update(Order order, Long id) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
        if (orderEntityOptional.isPresent()) {
            OrderEntity orderEntity = objectMapper.convertValue(order, OrderEntity.class);
            orderEntity.setStatus(orderEntityOptional.get().getStatus());
            orderEntity.setGoodTurnoverList(orderEntityOptional.get().getGoodTurnoverList());
            orderRepository.save(orderEntity);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
            if (orderEntityOptional.isPresent() && orderEntityOptional.get().getStatus() != Goal.Status.canceled && orderEntityOptional.get().getStatus() != Goal.Status.doing) {
                List<GoodTurnoverEntity> allByOrderId = goodTurnoverRepository.findAllByOrderId(id);
                orderRepository.deleteById(id);
                allByOrderId.forEach(goodTurnoverEntity -> {
                    Optional<GoalEntity> goalEntityOptional = goalRepository.findById(goodTurnoverEntity.getGoalId());
                    if (goalEntityOptional.isPresent() && goalEntityOptional.get().getGoodTurnoverList().isEmpty()) {
                            goalRepository.deleteById(goalEntityOptional.get().getId());
                    }
                });
                return true;
            }
        return false;
    }

    public List<Order> readAllByDate(LocalDate date) {
        Iterable<OrderEntity> all = orderRepository.findAllByDate(date);
        return objectMapper.convertValue(all, new TypeReference<>() {
        });
    }
}
