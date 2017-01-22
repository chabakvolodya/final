package com.sk.dao.impl;

import com.sk.dao.OrderStatusDao;
import com.sk.model.OrderStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.hamcrest.object.HasToString.hasToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class HOrderStatusDaoTest extends BaseDatabaseTest {

    @Resource(name = "orderStatusDao")
    private OrderStatusDao orderStatusDao;

    @Test
    public void findAll() {
        long expectedListSize = 2;
        List<OrderStatus> statusList = orderStatusDao.findAll();

        assertEquals(expectedListSize, statusList.size());
    }

    @Test
    public void findByDescription() {
        OrderStatus expected = new OrderStatus(1, "open");

        OrderStatus actual = orderStatusDao.findByDescription("open");

        assertThat(expected, hasToString(actual.toString()));
    }

}