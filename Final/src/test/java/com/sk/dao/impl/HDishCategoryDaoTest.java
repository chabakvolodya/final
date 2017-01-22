package com.sk.dao.impl;

import com.sk.dao.DishCategoryDao;
import com.sk.model.DishCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class HDishCategoryDaoTest extends BaseDatabaseTest {


    @Resource(name = "dishCategoryDao")
    private DishCategoryDao dishCategoryDao;

    @Test
    public void findAll() {
        List<DishCategory> foundCategories = dishCategoryDao.findAll();
        long expectedListSize = 5;

        Assert.assertEquals(expectedListSize, foundCategories.size());
    }

}