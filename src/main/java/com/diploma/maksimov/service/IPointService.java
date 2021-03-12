package com.diploma.maksimov.service;

import com.diploma.maksimov.dto.Point;

import java.util.List;

public interface IPointService {
    long create(Point point);
    List<Point> readAll();
    Point read(long id);
    boolean update(Point point, long id);
    boolean delete(long id);
}
