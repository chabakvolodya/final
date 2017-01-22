package com.sk.dao.impl;

import com.sk.dao.EmployeeDao;
import com.sk.model.Employee;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
@Repository("employeeDao")
public class HEmployeeDao implements EmployeeDao {

    @Resource(name = "sessionFactory")
    private SessionFactory sf;

    @Override
    @Transactional(readOnly = true)
    public Employee findById(Integer id) {
        return sf.getCurrentSession()
                .createQuery("select e from Employee e where e.id =:id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findByName(String lastName) {
        return sf.getCurrentSession()
                .createQuery("select e from Employee e where e.lastName =:lastName", Employee.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findByPositionDescription(String positionDescription) {
        return sf.getCurrentSession()
                .createQuery("select e from Employee e where e.position.description =:positionDescription", Employee.class)
                .setParameter("positionDescription", positionDescription)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return sf.getCurrentSession()
                .createQuery("select e from Employee e", Employee.class)
                .getResultList();
    }

    @Override
    public Integer insert(Employee employee) {
        return (Integer) sf.getCurrentSession().save(employee);
    }

    @Override
    public void update(Employee employee) {
        sf.getCurrentSession().merge(employee);
    }

    @Override
    public void delete(Employee employee) {
        employee.setDeleted(true);
        sf.getCurrentSession().update(employee);
    }

}
