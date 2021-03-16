package com.diploma.maksimov.service;

import java.util.List;

public interface CrudService<T, I> {
    void create(T object);
    List<T> readAll();
    T read(I id);
    boolean update(T object, I id);
    boolean delete(I id);
}
