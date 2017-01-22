package com.sk.service;

import com.sk.model.DishCategory;

import java.util.List;

public interface DishCategoryService {

    List<DishCategory> findAll();

    DishCategory findById(int id);

}
