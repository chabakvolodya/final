package com.sk.dao;

import com.sk.model.OrderStatus;

import java.util.List;

public interface OrderStatusDao {
    List<OrderStatus> findAll();

    OrderStatus findByDescription(String osDescription);
}
