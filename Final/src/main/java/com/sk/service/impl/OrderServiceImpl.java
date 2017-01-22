package com.sk.service.impl;

import com.sk.dao.OrderDao;
import com.sk.dao.OrderStatusDao;
import com.sk.model.Order;
import com.sk.model.OrderStatus;
import com.sk.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Resource(name = "orderDao")
    private OrderDao orderDao;
    @Resource(name = "orderStatusDao")
    private OrderStatusDao orderStatusDao;

    @Override
    public Order insert(Order order) {
        order.setId(orderDao.insert(order));
        return order;
    }

    @Override
    public void update(Order order) {

    }

    @Override
    public void remove(Order order) {
        if (order.getStatus().getDescription().equals("open")) {
            orderDao.delete(order);
        }
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public List<Order> findAllOpen() {
        OrderStatus orderStatusOpen = new OrderStatus();
        orderStatusOpen.setDescription("open");
        return orderDao.findByOrderStatus(orderStatusOpen);
    }

    @Override
    public List<Order> findAllClose() {
        OrderStatus orderStatusClose = new OrderStatus();
        orderStatusClose.setDescription("close");
        return orderDao.findByOrderStatus(orderStatusClose);
    }

    @Override
    public void close(Order order) {
        if (order.getStatus().getDescription().equals("open")) {
            order.setStatus(orderStatusDao.findByDescription("close"));
            orderDao.update(order);
        }
    }

    @Override
    public Order findById(int id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findByCriteria(LocalDateTime from, LocalDateTime to, int waiterId, int boardId) {
        return orderDao.findByCriteria(from, to, waiterId, boardId);
    }
}
