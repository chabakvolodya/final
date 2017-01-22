package com.sk.dao.impl;

import com.sk.dao.IngredientDao;
import com.sk.model.Ingredient;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
@Repository("ingredientDao")
public class HIngredientDao implements IngredientDao {


    @Resource(name = "sessionFactory")
    private SessionFactory sf;

    @Override
    public Ingredient findById(Integer id) {
        return sf.getCurrentSession()
                .createNamedQuery("Ingredient.findById", Ingredient.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ingredient> findAll() {
        return sf.getCurrentSession()
                .createNamedQuery("Ingredient.findAll", Ingredient.class)
                .getResultList();
    }

    @Override
    public List<Ingredient> findByName(String name) {
        return sf.getCurrentSession()
                .createNamedQuery("Ingredient.findByName", Ingredient.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public Integer insert(Ingredient ingredient) {
        return (Integer) sf.getCurrentSession().save(ingredient);
    }

    @Override
    public void update(Ingredient ingredient) {
        sf.getCurrentSession().update(ingredient);
    }


    @Override
    public void delete(Ingredient ingredient) {
        ingredient.setIsDelete(true);
        sf.getCurrentSession().update(ingredient);
    }
}
