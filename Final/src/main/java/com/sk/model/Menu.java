package com.sk.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu")
@NamedQueries({
        @NamedQuery(name = "Menu.findById", query = "select m from Menu m where m.id=:id"),
        @NamedQuery(name = "Menu.findByName", query = "select m from Menu m where m.description=:description"),
        @NamedQuery(name = "Menu.findAll", query = "select m from Menu m"),
})
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "menu_description")
    @NotEmpty(message = "Please enter new menu name")
    @Size(min = 3, message = "Min length of name is 3 symbols.")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "menu_detail", joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> dishes = new ArrayList<>();

    public Menu() {
    }

    public Menu(int id, String description, List<Dish> dishes) {
        this.id = id;
        this.description = description;
        this.dishes = dishes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dishes=" + dishes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;

        Menu menu = (Menu) o;

        if (getDescription() != null ? !getDescription().equals(menu.getDescription()) : menu.getDescription() != null)
            return false;
        return getDishes() != null ? getDishes().equals(menu.getDishes()) : menu.getDishes() == null;

    }

    @Override
    public int hashCode() {
        int result = getDescription() != null ? getDescription().hashCode() : 0;
        result = 31 * result + (getDishes() != null ? getDishes().hashCode() : 0);
        return result;
    }
}
