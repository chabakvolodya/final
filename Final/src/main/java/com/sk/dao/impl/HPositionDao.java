package com.sk.dao.impl;

import com.sk.dao.PositionDao;
import com.sk.model.Position;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
@Repository("positionDao")
public class HPositionDao implements PositionDao {

    @Resource(name = "sessionFactory")
    private SessionFactory sf;


    @Override
    @Transactional(readOnly = true)
    public List<Position> findAll() {
        return sf.getCurrentSession()
                .createQuery("select p from Position p", Position.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Position findById(int id) {
        return sf.getCurrentSession()
                .createQuery("select p from Position p where p.id=:id", Position.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
