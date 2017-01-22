package com.sk.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

@Entity
@Table(name = "ingredient")
@NamedQueries({
        @NamedQuery(name = "Ingredient.findAll", query = "select i from Ingredient i"),
        @NamedQuery(name = "Ingredient.findById", query = "select i from Ingredient i where i.id =:id"),
        @NamedQuery(name = "Ingredient.findByName", query = "select i from Ingredient i where upper(i.name) like (upper(:name)) "),
})
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "ingredient_name")
    @NotEmpty(message = "Name can not be empty.")
    private String name;

    @Column(name = "ingredient_quantity")
    @Min(value = 0, message = "Should be greater than zero.")
    @Digits(message = "Should be number.", integer = 4, fraction = 0)
    private int quantity;

    @Column(name = "is_deleted")
    private boolean isDelete;

    public Ingredient() {
    }

    public Ingredient(int id, String name, int quantity, boolean isDelete) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.isDelete = isDelete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean delete) {
        isDelete = delete;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", isDeleted=" + isDelete +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;

        Ingredient that = (Ingredient) o;

        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
