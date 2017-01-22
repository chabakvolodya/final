package com.sk.dao.impl;

import com.sk.dao.BoardDao;
import com.sk.model.Board;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
@Repository("boardDao")
public class HBoardDao implements BoardDao {

    private SessionFactory sessionFactory;

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Board> findAll() {

        return sessionFactory.getCurrentSession()
                .createQuery("select b from Board b", Board.class).list();
    }


}
