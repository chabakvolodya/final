package com.sk.dao;

import com.sk.model.DishCategory;

import java.util.List;

public interface DishCategoryDao {
    List<DishCategory> findAll();

    DishCategory findById(int id);
}
