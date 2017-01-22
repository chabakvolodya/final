package com.sk.dao.impl;

import com.sk.dao.PositionDao;
import com.sk.model.Position;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
public class HPositionDaoTest extends BaseDatabaseTest {

    @Resource(name = "positionDao")
    private PositionDao positionDao;

    @Test
    public void findAll() {
        List<Position> foundPositions = positionDao.findAll();
        long expectedListSize = 4;

        Assert.assertEquals(expectedListSize, foundPositions.size());
    }

}