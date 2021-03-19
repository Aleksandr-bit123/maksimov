package com.diploma.maksimov.service;

import com.diploma.maksimov.db.entity.GoalEntity;
import com.diploma.maksimov.db.entity.OrderEntity;
import com.diploma.maksimov.db.repository.OrderRepository;
import com.diploma.maksimov.dto.Goal;
import com.diploma.maksimov.dto.Order;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    @PostConstruct
    public void init(){

    }

    @Override
    public long create(Order order) {
        OrderEntity orderEntity = objectMapper.convertValue(order, OrderEntity.class);
        orderRepository.save(orderEntity);
        return orderEntity.getId();
    }

    @Override
    public List<Order> readAll() {
        Iterable<OrderEntity> all = orderRepository.findAll();
        return objectMapper.convertValue(all, new TypeReference<List<Order>>() {
        });
    }

    @Override
    public Order read(long id) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
        if (orderEntityOptional.isPresent()) {
            OrderEntity orderEntity = orderEntityOptional.get();
            return objectMapper.convertValue(orderEntity, Order.class);
        }
        return null;
    }

    @Override
    public boolean update(Order order, long id) {
        if (orderRepository.findById(id).isPresent()) {
            orderRepository.save(objectMapper.convertValue(order, OrderEntity.class));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
        if (orderEntityOptional.isPresent()) {
            orderRepository.deleteById(id);

            return true;
        }
        return false;
    }

    public List<Order> readAllByDate(LocalDate date) {
        Iterable<OrderEntity> all = orderRepository.findAllByDate(date);

        return objectMapper.convertValue(all, new TypeReference<List<Order>>() {
        });
    }
}
