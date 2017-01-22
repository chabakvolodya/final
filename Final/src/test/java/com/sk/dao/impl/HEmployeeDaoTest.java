package com.sk.dao.impl;

import com.sk.dao.EmployeeDao;
import com.sk.model.Employee;
import com.sk.model.Order;
import com.sk.model.Position;
import com.sk.model.Waiter;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.object.HasToString.hasToString;


@RunWith(SpringJUnit4ClassRunner.class)
public class HEmployeeDaoTest extends BaseDatabaseTest {

    @Resource(name = "employeeDao")
    private EmployeeDao employeeDao;

    @Resource(name = "txManager")
    private HibernateTransactionManager txManager;

    @Test
    public void findById() {
        Employee expectedEmployee = new Employee(1,
                new Position(1, "waiter"),
                "Ivan",
                "Ivanov",
                LocalDate.of(1990, 1, 1),
                "+380501111111",
                Money.of(CurrencyUnit.USD, 2000),
                false);

        Employee employee = employeeDao.findById(1);
        System.out.println(employee);

        Assert.assertTrue(employee instanceof Waiter);
        Assert.assertEquals(expectedEmployee.getId(), employee.getId());
        Assert.assertEquals(expectedEmployee.getPosition(), employee.getPosition());
    }

    @Test
    public void findByName() {
        Employee expectedEmployee = new Employee(1,
                new Position(1, "waiter"),
                "Ivan",
                "Ivanov",
                LocalDate.of(1990, 1, 1),
                "+380501111111",
                Money.of(CurrencyUnit.USD, 2000),
                false);

        int expectedListSize = 1;

        List<Employee> employeeList = employeeDao.findByName("Ivanov");

        Assert.assertEquals(expectedListSize, employeeList.size());
        Assert.assertEquals(expectedEmployee.getFirstName(), employeeList.get(0).getFirstName());
        Assert.assertEquals(expectedEmployee.getLastName(), employeeList.get(0).getLastName());
    }

    @Test
    public void findAll() {
        int expectedListSize = 6;
        List<Employee> employeeList = employeeDao.findAll();

        Assert.assertEquals(expectedListSize, employeeList.size());
    }

    @Test
    public void findByPositionDescription() {
        int expectedListSize = 2;
        String positionDescription = "waiter";
        List<Employee> employeeList = employeeDao.findByPositionDescription(positionDescription);

        Assert.assertEquals(expectedListSize, employeeList.size());
    }

    @Test
    public void insert() {
        Employee employeeToInsert = new Employee(0,
                new Position(1, "waiter"),
                "Vasya",
                "Vasilkov",
                LocalDate.of(1980, 1, 1),
                "+38050123123",
                Money.of(CurrencyUnit.USD, 3000),
                false);

        int expectedId = 7;

        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition());
        Integer newId = employeeDao.insert(employeeToInsert);
        txManager.commit(transaction);

        employeeToInsert.setId(newId);

        Assert.assertEquals(Long.valueOf(expectedId), Long.valueOf(newId));
        Assert.assertThat(employeeToInsert,
                hasToString(String.valueOf(employeeDao.findById(newId))));
    }

    @Test
    public void update() {
        Waiter employeeToUpdate = new Waiter(1,
                new Position(2, "cook"),
                "Dmitrii",
                "Dmitriev",
                LocalDate.of(1970, 1, 1),
                "+380501111",
                Money.of(CurrencyUnit.USD, 3000),
                true, null);

        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition());
        employeeDao.update(employeeToUpdate);
        txManager.commit(transaction);

        System.out.println(employeeDao.findById(employeeToUpdate.getId()));

        Assert.assertTrue(employeeDao.findById(employeeToUpdate.getId()).equals(employeeToUpdate));
    }

    @Test
    public void delete() {
        Employee expectedEmployee = new Waiter(1,
                new Position(1, "waiter"),
                "Ivan",
                "Ivanov",
                LocalDate.of(1990, 1, 1),
                "+380501111111",
                Money.of(CurrencyUnit.USD, 2000),
                true,
                new HashSet<Order>());

        Employee employeeToDelete = employeeDao.findById(1);

        TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition());
        employeeDao.delete(employeeToDelete);
        txManager.commit(transaction);


        Assert.assertEquals(expectedEmployee.isDeleted(), employeeDao.findById(employeeToDelete.getId()).isDeleted());
    }

}