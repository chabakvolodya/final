package com.sk.dao.impl;

import com.sk.dao.DishCategoryDao;
import com.sk.model.DishCategory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
@Repository("dishCategoryDao")
public class HDishCategoryDao implements DishCategoryDao {

    @Resource(name = "sessionFactory")
    private SessionFactory sf;

    @Override
    @Transactional(readOnly = true)
    public List<DishCategory> findAll() {
        return sf.getCurrentSession()
                .createQuery("select dc from DishCategory dc", DishCategory.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public DishCategory findById(int id) {
        return sf.getCurrentSession()
                .createQuery("select dc from DishCategory dc where dc.id=:id", DishCategory.class)
                .setParameter("id", id)
                .getSingleResult();
    }


}
