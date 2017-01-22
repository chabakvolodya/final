package com.sk.dao.impl;

import com.sk.dao.DishDao;
import com.sk.model.Dish;
import com.sk.model.DishCategory;
import com.sk.model.Ingredient;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.object.HasToString.hasToString;

@RunWith(SpringJUnit4ClassRunner.class)
public class HDishDaoTest extends BaseDatabaseTest {

    @Resource(name = "dishDao")
    private DishDao dishDao;
    @Resource(name = "txManager")
    private HibernateTransactionManager txManager;

    @Test
    public void findById() {
        Dish expectedDish = new Dish(1, "tomato soup",
                new DishCategory(1, "starters"),
                Money.of(CurrencyUnit.USD, 2),
                300,
                new ArrayList<>(2),
                false);

        expectedDish.getIngredients().add(new Ingredient(1, "tomato", 41, false));
        expectedDish.getIngredients().add(new Ingredient(3, "salt", 43, false));

        Dish dish = dishDao.findById(1);


        Assert.assertThat(dish, hasToString(String.valueOf(expectedDish)));
    }

    @Test
    public void findByName() {
        Dish expectedDish = new Dish(1, "tomato soup",
                new DishCategory(1, "starters"),
                Money.of(CurrencyUnit.USD, 2),
                300,
                new ArrayList<>(2),
                false);
        expectedDish.getIngredients().add(new Ingredient(1, "tomato", 41, false));
        expectedDish.getIngredients().add(new Ingredient(3, "salt", 43, false));

        int expectedListSize = 1;


        List<Dish> dishList = dishDao.findByName("tomato soup");

        Assert.assertEquals(expectedListSize, dishList.size());
        Assert.assertThat(dishList.get(0), hasToString(String.valueOf(expectedDish)));
    }

    @Test
    public void findAll() {
        int expectedListSize = 15;
        List<Dish> dishList = dishDao.findAll();

        Assert.assertEquals(expectedListSize, dishList.size());

    }

    @Test
    public void insert() {
        Dish dishToInsert = new Dish(1, "hot tomato soup",
                new DishCategory(1, "starters"),
                Money.of(CurrencyUnit.USD, 200),
                500,
                new ArrayList<>(3),
                false);
        dishToInsert.getIngredients().add(new Ingredient(1, "tomato", 41, false));
        dishToInsert.getIngredients().add(new Ingredient(3, "salt", 43, false));
        dishToInsert.getIngredients().add(new Ingredient(5, "potato", 61, false));

        int expectedNewId = 16;

        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        Integer newId = dishDao.insert(dishToInsert);
        txManager.commit(transaction);

        dishToInsert.setId(newId);

        Assert.assertEquals(Long.valueOf(expectedNewId), Long.valueOf(newId));
        Assert.assertThat(dishToInsert,
                hasToString(String.valueOf(dishDao.findById(newId))));
    }

    @Test
    public void update() {
        Dish dishToUpdate = new Dish(1, "hot tomato soup",
                new DishCategory(1, "starters"),
                Money.of(CurrencyUnit.USD, 200),
                500,
                new ArrayList<>(3),
                true);
        dishToUpdate.getIngredients().add(new Ingredient(1, "tomato", 41, false));
        dishToUpdate.getIngredients().add(new Ingredient(5, "potato", 61, false));

        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        dishDao.update(dishToUpdate);
        txManager.commit(transaction);

        Assert.assertThat(dishToUpdate,
                hasToString(String.valueOf(dishDao.findById(dishToUpdate.getId()))));
    }

    @Test
    public void delete() {
        Dish expectedDish = new Dish(1, "tomato soup",
                new DishCategory(1, "starters"),
                Money.of(CurrencyUnit.USD, 2),
                300,
                new ArrayList<>(2),
                true);

        expectedDish.getIngredients().add(new Ingredient(1, "tomato", 41, false));
        expectedDish.getIngredients().add(new Ingredient(3, "salt", 43, false));

        Dish dishToDelete = new Dish();
        dishToDelete.setId(1);

        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));
        dishDao.delete(expectedDish);
        txManager.commit(transaction);

        Assert.assertThat(expectedDish,
                hasToString(String.valueOf(dishDao.findById(dishToDelete.getId()))));

    }

}