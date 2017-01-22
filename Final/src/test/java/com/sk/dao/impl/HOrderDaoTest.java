package com.sk.dao.impl;

import com.sk.dao.OrderDao;
import com.sk.model.Board;
import com.sk.model.Employee;
import com.sk.model.Order;
import com.sk.model.OrderStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.object.HasToString.hasToString;


@RunWith(SpringJUnit4ClassRunner.class)
public class HOrderDaoTest extends BaseDatabaseTest {

    @Resource(name = "orderDao")
    private OrderDao orderDao;
    @Resource(name = "txManager")
    private HibernateTransactionManager txManager;


    @Test
    public void findById() {
        long expectedOrderId = 1;
        long expectedWaiterId = 1;

        Order order = orderDao.findById((int) expectedOrderId);
        Assert.assertEquals(expectedOrderId, order.getId());
        Assert.assertEquals(expectedWaiterId, order.getWaiterEmployee().getId());
    }

    @Test
    public void findByOrderStatus() {
        long expectedClosedOrders = 5;
        long expectedOpenedOrders = 0;

        OrderStatus orderStatusClose = new OrderStatus(2, "close");
        OrderStatus orderStatusOpen = new OrderStatus(1, "open");
        List<Order> ordersInStatusClose = orderDao.findByOrderStatus(orderStatusClose);
        List<Order> ordersInStatusOpen = orderDao.findByOrderStatus(orderStatusOpen);

        Assert.assertEquals(expectedClosedOrders, ordersInStatusClose.size());
        Assert.assertEquals(expectedOpenedOrders, ordersInStatusOpen.size());
    }

    @Test
    public void findAll() {
        long expectedListSize = 5;

        List<Order> orders = orderDao.findAll();

        Assert.assertEquals(expectedListSize, orders.size());

    }

    @Test
    public void insert() {
        Order orderToInsert = new Order();
        Employee waiter = new Employee();
        waiter.setId(1);
        Board board = new Board();
        board.setId(1);
        board.setDescription("Table_01");
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(1);
        orderStatus.setDescription("open");


        orderToInsert.setWaiterEmployee(waiter);
        orderToInsert.setOrderDate(LocalDateTime.now());
        orderToInsert.setBoard(board);
        orderToInsert.setStatus(orderStatus);
        orderToInsert.setDishes(null);

        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition());
        Integer newId = orderDao.insert(orderToInsert);
        txManager.commit(transaction);
        orderToInsert.setId(newId);

        Order insertedOrder = orderDao.findById(newId);

        Assert.assertEquals(orderToInsert.getId(), insertedOrder.getId());
        Assert.assertEquals(orderToInsert.getWaiterEmployee().getId(), insertedOrder.getWaiterEmployee().getId());
        Assert.assertEquals(orderToInsert.getOrderDate(), insertedOrder.getOrderDate());
        Assert.assertEquals(orderToInsert.getBoard().getId(), insertedOrder.getBoard().getId());
        Assert.assertEquals(orderToInsert.getStatus().getId(), insertedOrder.getStatus().getId());
        Assert.assertNull(insertedOrder.getDishes());
    }

    @Test
    public void update() {
        Order orderForUpdate = orderDao.findById(1);

        orderForUpdate.setDishes(new ArrayList<>());
        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition());
        orderDao.update(orderForUpdate);
        txManager.commit(transaction);

        Assert.assertThat(orderDao.findById(1), hasToString(orderForUpdate.toString()));

    }

    @Test
    public void delete() {
        int orderId = 1;
        int expectedOrdersAfterDelete = 4;

        Order orderForDelete = orderDao.findById(orderId);
        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition());
        orderDao.delete(orderForDelete);
        txManager.commit(transaction);

        Assert.assertEquals(expectedOrdersAfterDelete, orderDao.findAll().size());
    }

}