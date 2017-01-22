package com.sk.model;

import org.joda.money.Money;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Cook extends Employee {
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_detail",
            joinColumns = {@JoinColumn(name = "cook_employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "dish_id")})
    private Set<Dish> preparedDishes;


    public Cook() {
        super();
    }

    public Cook(int id, Position position, String firstName, String lastName, LocalDate birthDay,
                String phoneNumber, Money salary, boolean isDeleted, Set<Dish> preparedDishes) {
        super(id, position, firstName, lastName, birthDay, phoneNumber, salary, isDeleted);
        this.preparedDishes = preparedDishes;
    }

    public Set<Dish> getPreparedDishes() {
        return preparedDishes;
    }

    public void setPreparedDishes(Set<Dish> preparedDishes) {
        this.preparedDishes = preparedDishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cook)) return false;
        if (!super.equals(o)) return false;

        Cook cook = (Cook) o;

        return preparedDishes != null ? preparedDishes.equals(cook.preparedDishes) : cook.preparedDishes == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (preparedDishes != null ? preparedDishes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cook{" +
                "preparedDishes=" + preparedDishes +
                "} " + super.toString();
    }
}
