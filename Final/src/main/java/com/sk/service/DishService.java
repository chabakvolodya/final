package com.sk.service;

import com.sk.model.Dish;

import java.util.List;

public interface DishService {
    Dish add(Dish dish);

    void remove(Dish dish);

    List<Dish> findByName(String dishName);

    List<Dish> findAll();

    List<Dish> findAllActive();

    Dish findById(int id);

    void update(Dish dish);
}
