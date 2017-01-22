package com.sk.model;

import org.joda.money.Money;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Waiter extends Employee {

    @OneToMany(mappedBy = "waiterEmployee", fetch = FetchType.EAGER)
    private Set<Order> orders;

    public Waiter() {
        super();
    }

    public Waiter(int id, Position position, String firstName, String lastName, LocalDate birthDay, String phoneNumber, Money salary, boolean isDeleted, Set<Order> orders) {
        super(id, position, firstName, lastName, birthDay, phoneNumber, salary, isDeleted);
        this.orders = orders;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Waiter)) return false;
        if (!super.equals(o)) return false;

        Waiter waiter = (Waiter) o;

        return getOrders() != null ? getOrders().equals(waiter.getOrders()) : waiter.getOrders() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Waiter{" +
                "orders=" + orders +
                "} " + super.toString();
    }
}
