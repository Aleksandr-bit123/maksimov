package com.diploma.maksimov.service;

import com.diploma.maksimov.dto.Order;

import java.util.List;

public interface IOrderService {
    long create(Order order);
    List<Order> readAll();
    Order read(long id);
    boolean update(Order order, long id);
    boolean delete(long id);
}
