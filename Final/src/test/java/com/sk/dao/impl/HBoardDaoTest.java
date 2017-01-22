package com.sk.dao.impl;

import com.sk.dao.BoardDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
public class HBoardDaoTest extends BaseDatabaseTest {

    @Resource(name = "boardDao")
    private BoardDao boardDao;


    @Test
    public void findByAll() {
        int expectedListSize = 4;

        Assert.assertEquals(expectedListSize, boardDao.findAll().size());
    }

}