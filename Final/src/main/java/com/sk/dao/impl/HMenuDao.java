package com.sk.dao.impl;

import com.sk.dao.MenuDao;
import com.sk.model.Menu;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
@Repository("menuDao")
public class HMenuDao implements MenuDao {

    @Resource(name = "sessionFactory")
    private SessionFactory sf;

    @Override
    @Transactional(readOnly = true)
    public Menu findById(Integer id) {
        return sf.getCurrentSession()
                .createNamedQuery("Menu.findById", Menu.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findByName(String name) {
        return sf.getCurrentSession()
                .createNamedQuery("Menu.findByName", Menu.class)
                .setParameter("description", name)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return sf.getCurrentSession()
                .createNamedQuery("Menu.findAll", Menu.class)
                .getResultList();
    }

    @Override
    public Integer insert(Menu menu) {
        return (Integer) sf.getCurrentSession().save(menu);
    }

    @Override
    public void update(Menu menu) {
        sf.getCurrentSession().update(menu);
    }

    @Override
    public void delete(Menu menu) {
        sf.getCurrentSession().delete(menu);
    }

}
