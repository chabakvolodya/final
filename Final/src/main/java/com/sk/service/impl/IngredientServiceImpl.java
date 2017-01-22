package com.sk.service.impl;

import com.sk.dao.IngredientDao;
import com.sk.model.Ingredient;
import com.sk.service.IngredientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {

    @Resource(name = "ingredientDao")
    private IngredientDao ingredientDao;

    @Override
    @Transactional(readOnly = true)
    public List<Ingredient> findAll() {
        return ingredientDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Ingredient findById(int id) {
        return ingredientDao.findById(id);
    }

    @Override
    public List<Ingredient> findByName(String name) {
        return ingredientDao.findByName(name);
    }

    @Override
    public void deleteById(int id) {
        Ingredient ingredient = ingredientDao.findById(id);
        ingredient.setIsDelete(true);
        ingredientDao.delete(ingredient);
    }

    @Override
    public void activateById(int id) {
        Ingredient ingredient = ingredientDao.findById(id);
        ingredient.setIsDelete(false);
        ingredientDao.update(ingredient);
    }

    @Override
    public void update(Ingredient ingredient) {
        ingredientDao.update(ingredient);
    }

    @Override
    public Ingredient insert(Ingredient ingredient) {
        int newId = ingredientDao.insert(ingredient);
        ingredient.setId(newId);
        return ingredient;
    }
}
