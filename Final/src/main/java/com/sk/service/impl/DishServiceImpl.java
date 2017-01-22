package com.sk.service.impl;

import com.sk.dao.DishDao;
import com.sk.model.Dish;
import com.sk.service.DishService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
public class DishServiceImpl implements DishService {

    @Resource(name = "dishDao")
    private DishDao dishDao;

    @Override
    public Dish add(Dish dish) {
        int newId = dishDao.insert(dish);
        dish.setId(newId);
        return dish;
    }

    @Override
    public void remove(Dish dish) {
        dishDao.delete(dish);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dish> findByName(String dishName) {
        return dishDao.findByName(dishName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dish> findAll() {
        return dishDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dish> findAllActive() {
        return dishDao.findAllActive();
    }

    @Override
    @Transactional(readOnly = true)
    public Dish findById(int id) {
        return dishDao.findById(id);
    }

    @Override
    public void update(Dish dish) {

        if (dish.getPhoto() == null){
            dish.setPhoto(findById(dish.getId()).getPhoto());
        }

        dishDao.update(dish);
    }
}
