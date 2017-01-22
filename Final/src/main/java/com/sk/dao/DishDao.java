package com.sk.dao;

import com.sk.model.Dish;

import java.util.List;

public interface DishDao extends GeneralDao<Dish, Integer> {
    List<Dish> findByName(String name);

    List<Dish> findAllActive();
}
