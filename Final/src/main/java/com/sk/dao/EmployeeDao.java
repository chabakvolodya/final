package com.sk.dao;

import com.sk.model.Employee;

import java.util.List;

public interface EmployeeDao extends GeneralDao<Employee, Integer> {
    List<Employee> findByName(String name);

    List<Employee> findByPositionDescription(String positionDescription);

}
