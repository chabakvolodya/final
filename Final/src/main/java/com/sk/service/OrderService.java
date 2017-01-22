package com.sk.service;

import com.sk.model.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    Order insert(Order order);

    void update(Order order);

    void remove(Order order);

    List<Order> findAll();

    Order findById(int id);

    List<Order> findAllOpen();

    List<Order> findAllClose();

    void close(Order order);

    public List<Order> findByCriteria(LocalDateTime from, LocalDateTime to, int waiterId, int boardId);

}
