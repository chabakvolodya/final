package com.sk.dao.impl;

import com.sk.dao.MenuDao;
import com.sk.model.Dish;
import com.sk.model.DishCategory;
import com.sk.model.Menu;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
public class HMenuDaoTest extends BaseDatabaseTest {

    @Resource(name = "menuDao")
    private MenuDao menuDao;

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "txManager")
    private HibernateTransactionManager txManager;

    @Test
    public void findById() {
        int expectedId = 2;
        String expectedDescription = "morning";
        int expectedDishesListSize = 4;
        Menu menu = menuDao.findById(2);

        Assert.assertEquals(expectedId, menu.getId());
        Assert.assertEquals(expectedDescription, menu.getDescription());
        Assert.assertEquals(expectedDishesListSize, menu.getDishes().size());
    }

    @Test
    public void findByName() {
        int expectedId = 2;
        String expectedDescription = "morning";
        int expectedDishesListSize = 4;
        int expectedResultListSize = 1;
        List<Menu> menus = menuDao.findByName("morning");

        Assert.assertEquals(expectedResultListSize, menus.size());
        Assert.assertEquals(expectedId, menus.get(0).getId());
        Assert.assertEquals(expectedDescription, menus.get(0).getDescription());
        Assert.assertEquals(expectedDishesListSize, menus.get(0).getDishes().size());
    }

    @Test
    public void findAll() {
        int expectedListSize = 2;
        List<Menu> dishList = menuDao.findAll();

        Assert.assertEquals(expectedListSize, dishList.size());

    }

    @Test
    public void insert() {
        int expectedId = 3;
        String expectedDescription = "to insert";

        Menu menuToInsert = getExpectedMenu();
        menuToInsert.setDescription(expectedDescription);

        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition());
        int newId = menuDao.insert(menuToInsert);
        txManager.commit(transaction);

        menuToInsert.setId(newId);

        Menu actualMenu = menuDao.findById(newId);

        Assert.assertEquals(expectedId, actualMenu.getId());
        Assert.assertEquals(expectedDescription, actualMenu.getDescription());
        Assert.assertEquals(menuToInsert.getDishes().size(), actualMenu.getDishes().size());

    }

    @Test
    public void update() {
        int existingId = 2;
        Menu menuToUpdate;

        TransactionStatus transactionFind = txManager.getTransaction(new DefaultTransactionDefinition());
        menuToUpdate = menuDao.findById(existingId);
        txManager.commit(transactionFind);

        menuToUpdate.setDescription("updated description");

        List<Dish> dishes = new ArrayList<>();
        dishes.addAll(menuToUpdate.getDishes());
        dishes.remove(0);
        menuToUpdate.setDishes(dishes);


        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition());
        menuDao.update(menuToUpdate);
        txManager.commit(transaction);

        Assert.assertEquals(menuToUpdate.getDescription(), menuDao.findById(existingId).getDescription());
        Assert.assertEquals(menuToUpdate.getDishes().size(), menuDao.findById(existingId).getDishes().size());
    }

    @Test
    public void delete() {
        Menu menuToDelete;
        int menuToDeleteId = 1;
        int expectedRowsAfterDelTableMenu = 1;

        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition());
        menuToDelete = menuDao.findById(menuToDeleteId);
        menuDao.delete(menuToDelete);
        menuDao.findAll();
        txManager.commit(transaction);

        long actualRowsInTableMenu = JdbcTestUtils.countRowsInTable(jdbcTemplate, "menu");
        Assert.assertEquals(expectedRowsAfterDelTableMenu, actualRowsInTableMenu);
    }

    private Menu getExpectedMenu() {
        Dish dishOneForMenu = new Dish(8, "tea", new DishCategory(3, null),
                Money.of(CurrencyUnit.USD, 0.90D), 100, null, false);
        Dish dishTwoForMenu = new Dish(9, "coffee", new DishCategory(3, null),
                Money.of(CurrencyUnit.USD, 0.90D), 50, null, false);
        Dish dishThreeForMenu = new Dish(10, "cheese & tomato sandwich", new DishCategory(4, null),
                Money.of(CurrencyUnit.USD, 3.25D), 350, null, false);
        Dish dishFourForMenu = new Dish(11, "chicken sandwich", new DishCategory(4, null),
                Money.of(CurrencyUnit.USD, 3.50D), 350, null, false);

        List<Dish> dishList = Arrays.asList(dishOneForMenu, dishTwoForMenu, dishThreeForMenu,
                dishFourForMenu);

        Menu expectedMenu = new Menu();
        expectedMenu.setDescription("morning");
        expectedMenu.setDishes(dishList);

        return expectedMenu;
    }

}