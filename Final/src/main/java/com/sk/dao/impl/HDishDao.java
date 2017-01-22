//Here I have used only Criteria API

package com.sk.dao.impl;

import com.sk.dao.DishDao;
import com.sk.model.Dish;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
@Repository("dishDao")
public class HDishDao implements DishDao {

    private SessionFactory sf;

    @Resource(name = "sessionFactory")
    public void setSf(SessionFactory sessionFactory) {
        this.sf = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public Dish findById(Integer id) {
        CriteriaBuilder cb = sf.getCriteriaBuilder();
        CriteriaQuery<Dish> query = cb.createQuery(Dish.class);
        Root<Dish> dish = query.from(Dish.class);
        query.select(dish).where(cb.equal(dish.get("id"), id));

        return sf.getCurrentSession().createQuery(query).uniqueResult();
    }


    @Override
    @Transactional(readOnly = true)
    public List<Dish> findByName(String name) {
        CriteriaBuilder cb = sf.getCriteriaBuilder();
        CriteriaQuery<Dish> query = cb.createQuery(Dish.class);
        Root<Dish> dish = query.from(Dish.class);
        query.select(dish).where(cb.like(dish.get("name"), "%" + name + "%"));

        return sf.getCurrentSession().createQuery(query).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dish> findAll() {
        CriteriaBuilder cb = sf.getCriteriaBuilder();
        CriteriaQuery<Dish> query = cb.createQuery(Dish.class);
        Root<Dish> dish = query.from(Dish.class);
        query.select(dish);

        return sf.getCurrentSession().createQuery(query).list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dish> findAllActive() {
        CriteriaBuilder cb = sf.getCriteriaBuilder();
        CriteriaQuery<Dish> query = cb.createQuery(Dish.class);
        Root<Dish> dish = query.from(Dish.class);
        query.select(dish).where(cb.equal(dish.get("isDeleted"), false));
        return sf.getCurrentSession().createQuery(query).list();
    }


    @Override
    public Integer insert(Dish dish) {
        return (Integer) sf.getCurrentSession().save(dish);
    }

    @Override
    public void update(Dish dish) {
        sf.getCurrentSession().merge(dish);
    }

    @Override
    public void delete(Dish dish) {
        dish.setDeleted(true);
        sf.getCurrentSession().update(dish);
    }
}





























