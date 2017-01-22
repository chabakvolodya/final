//Here I have used only Named query

package com.sk.dao.impl;

import com.sk.dao.OrderStatusDao;
import com.sk.model.OrderStatus;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
@Repository("orderStatusDao")
public class HOrderStatusDao implements OrderStatusDao {

    @Resource(name = "sessionFactory")
    private SessionFactory sf;

    @Override
    @Transactional(readOnly = true)
    public List<OrderStatus> findAll() {
        return sf.getCurrentSession()
                .createNamedQuery("OrderStatus.findAll", OrderStatus.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderStatus findByDescription(String osDescription) {
        return sf.getCurrentSession()
                .createNamedQuery("OrderStatus.findByDescription", OrderStatus.class)
                .setParameter("description", osDescription)
                .getSingleResult();
    }

}
