package com.sk.service;

import com.sk.model.Employee;

import java.util.List;


public interface EmployeeService {

    Employee add(Employee employee);

    void remove(Employee employee);

    List<Employee> findByLastName(String lastName);

    List<Employee> findAll();

    List<Employee> findByPositionDescription(String positionDescription);

    Employee findById(int id);

    void update(Employee employee);
}
