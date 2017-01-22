package com.sk.dao;

import com.sk.model.Order;
import com.sk.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao extends GeneralDao<Order, Integer> {

    List<Order> findByOrderStatus(OrderStatus status);

    public List<Order> findByCriteria(LocalDateTime from, LocalDateTime to, int waiterId, int boardId);
}
