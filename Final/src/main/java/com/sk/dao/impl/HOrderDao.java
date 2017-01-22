package com.sk.dao.impl;

import com.sk.dao.OrderDao;
import com.sk.model.Board;
import com.sk.model.Employee;
import com.sk.model.Order;
import com.sk.model.OrderStatus;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
@Repository("orderDao")
public class HOrderDao implements OrderDao {

    private SessionFactory sf;

    @Resource(name = "sessionFactory")
    public void setSf(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    @Transactional(readOnly = true)
    public Order findById(Integer id) {
        return sf.getCurrentSession().get(Order.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findByOrderStatus(OrderStatus status) {
        return sf.getCurrentSession()
                .createQuery("select o from Order o where o.status.description =:status ", Order.class)
                .setParameter("status", status.getDescription())
                .getResultList();

    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return sf.getCurrentSession()
                .createQuery("select o from Order o", Order.class).getResultList();
    }

    @Override
    public Integer insert(Order order) {
        return (Integer) sf.getCurrentSession().save(order);
    }

    @Override
    public void update(Order order) {
        sf.getCurrentSession().update(order);
    }

    @Override
    public void delete(Order order) {
        sf.getCurrentSession().delete(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findByCriteria(LocalDateTime from, LocalDateTime to, int waiterId, int boardId) {
        CriteriaBuilder crBld = sf.getCriteriaBuilder();
        CriteriaQuery<Order> query = crBld.createQuery(Order.class);
        Root<Order> order = query.from(Order.class);

        List<Predicate> predicates = new ArrayList<>();

        if (from != null) {
            predicates.add(crBld.greaterThanOrEqualTo(order.get("orderDate"), from));
        }

        if (to != null) {
            predicates.add(crBld.lessThanOrEqualTo(order.get("orderDate"), to));
        }

        if (waiterId > 0) {
            predicates.add(crBld.equal(order.join("waiterEmployee").get("id"), waiterId));
        }

        if (boardId > 0) {
            predicates.add(crBld.equal(order.join("board").get("id"), boardId));
        }

        query.select(order)
                .where(predicates.toArray(new Predicate[]{}));

        return sf.getCurrentSession().createQuery(query).getResultList();
    }

}
