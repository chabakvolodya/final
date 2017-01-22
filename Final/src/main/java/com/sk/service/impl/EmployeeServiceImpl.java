package com.sk.service.impl;

import com.sk.dao.EmployeeDao;
import com.sk.model.Cook;
import com.sk.model.Employee;
import com.sk.model.Waiter;
import com.sk.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final String WAITER = "waiter";
    private static final String COOK = "cook";

    @Resource(name = "employeeDao")
    private EmployeeDao employeeDao;

    @Override
    public Employee findById(int id) {
        return employeeDao.findById(id);
    }


    @Override
    public Employee add(Employee employee) {

        switch (employee.getPosition().getDescription()) {
            case WAITER:
                Waiter waiter = Mapper.waiterFrom(employee);
                waiter.setId(employeeDao.insert(waiter));
                return waiter;

            case COOK:
                Cook cook = Mapper.cookFrom(employee);
                cook.setId(employeeDao.insert(cook));
                return cook;

            default:
                employee.setId(employeeDao.insert(employee));
                return employee;
        }
    }

    @Override
    public void remove(Employee employee) {
        employeeDao.delete(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findByLastName(String lastName) {
        return employeeDao.findByName(lastName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findByPositionDescription(String positionDescription) {
        return employeeDao.findByPositionDescription(positionDescription);
    }

    @Override
    public void update(Employee employee) {
        Employee empToMerge = employeeDao.findById(employee.getId());
        empToMerge.setFirstName(employee.getFirstName());
        empToMerge.setLastName(employee.getLastName());
        empToMerge.setPosition(employee.getPosition());
        empToMerge.setBirthDay(employee.getBirthDay());
        empToMerge.setPhoneNumber(employee.getPhoneNumber());
        empToMerge.setSalary(employee.getSalary());

        employeeDao.update(empToMerge);
    }

    private void employeeToWaiter(Employee employee, Waiter waiter) {
        waiter.setFirstName(employee.getFirstName());
        waiter.setLastName(employee.getLastName());
    }

    private static final class Mapper {
        static Waiter waiterFrom(Employee employee) {
            Waiter waiter = new Waiter();
            waiter.setFirstName(employee.getFirstName());
            waiter.setLastName(employee.getLastName());
            waiter.setPosition(employee.getPosition());
            waiter.setBirthDay(employee.getBirthDay());
            waiter.setPhoneNumber(employee.getPhoneNumber());
            waiter.setSalary(employee.getSalary());
            return waiter;
        }

        static Cook cookFrom(Employee employee) {
            Cook cook = new Cook();
            cook.setFirstName(employee.getFirstName());
            cook.setLastName(employee.getLastName());
            cook.setPosition(employee.getPosition());
            cook.setBirthDay(employee.getBirthDay());
            cook.setPhoneNumber(employee.getPhoneNumber());
            cook.setSalary(employee.getSalary());
            return cook;
        }
    }
}
