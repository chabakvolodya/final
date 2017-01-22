package com.sk.dao.impl;

import com.sk.dao.IngredientDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
public class HIngredientDaoTest extends BaseDatabaseTest {


    @Resource(name = "ingredientDao")
    IngredientDao ingredientDao;


    @Test
    public void findAll() {
        int expectedListSize = 19;

        Assert.assertEquals(expectedListSize, ingredientDao.findAll().size());
    }

}