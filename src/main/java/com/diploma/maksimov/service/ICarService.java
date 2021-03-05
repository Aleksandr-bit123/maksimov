package com.diploma.maksimov.service;

import com.diploma.maksimov.dto.Car;

import java.util.List;

public interface ICarService {
        /**
         * Создает новую машину
         * @param car - машина для создания
         * Если машина с таким id уже есть, то не добавляет в список машин
         */
        long create(Car car);

        /**
         * Возвращает список всех имеющихся машин
         * @return список машин
         */
        List<Car> readAll();

        /**
         * Возвращает машину по ее ID
         * @param id - ID машины
         * @return - объект машины с заданным ID
         */
        Car read(long id);

        /**
         * Обновляет машину с заданным ID,
         * в соответствии с переданной машиной
         * @param car - машина в соответсвии с которой нужно обновить данные
         * @param id - id машины которую нужно обновить
         * @return - true если данные были обновлены, иначе false
         */
        boolean update(Car car, long id);

        /**
         * Удаляет машину с заданным ID
         * @param id - id машины, которую нужно удалить
         * @return - true если машина была удалена, иначе false
         */
        boolean delete(long id);
}
