package com.sk.dao;

import com.sk.model.Ingredient;

import java.util.List;

public interface IngredientDao extends GeneralDao<Ingredient, Integer> {

    List<Ingredient> findByName(String name);

}
