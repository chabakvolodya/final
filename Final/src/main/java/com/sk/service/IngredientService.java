package com.sk.service;

import com.sk.model.Ingredient;

import java.util.List;

public interface IngredientService {

    List<Ingredient> findAll();

    List<Ingredient> findByName(String name);

    Ingredient findById(int id);

    void deleteById(int id);

    void activateById(int id);

    void update(Ingredient ingredient);

    Ingredient insert(Ingredient ingredient);

}
