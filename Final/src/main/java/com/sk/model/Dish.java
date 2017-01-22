package com.sk.model;

import com.sk.constraint.MinJodaMoney;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.money.Money;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Dish name can not be empty.")
    @Size(min = 2, max = 30, message = "Name should be min 2, max 30 symbols")
    @Column(name = "dish_name")
    private String name;

    @NotNull(message = "Category can not be empty")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_category_id")
    private DishCategory category;

    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "USD")})
    @Column(name = "dish_price")
    @MinJodaMoney(value = 0, message = "Price should be zero or greater")
    private Money price;

    @Column(name = "dish_weight")
    @Max(value = 3000, message = "Max weight 3000")
    @Min(value = 0, message = "Min weight 0")
    private int weight;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "dish_ingredient", joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients = new ArrayList<>();

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "photo")
    private byte[] photo;

    public Dish() {
    }

    public Dish(int id, String name, DishCategory category, Money price, int weight, List<Ingredient> ingredients, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.weight = weight;
        this.ingredients = ingredients;
        this.isDeleted = isDeleted;
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

    public DishCategory getCategory() {
        return category;
    }

    public void setCategory(DishCategory category) {
        this.category = category;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", weight=" + weight +
                ", ingredients=" + ingredients +
                ", isDeleted=" + isDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;

        Dish dish = (Dish) o;

        if (getWeight() != dish.getWeight()) return false;
        if (isDeleted() != dish.isDeleted()) return false;
        if (getName() != null ? !getName().equals(dish.getName()) : dish.getName() != null) return false;
        if (getCategory() != null ? !getCategory().equals(dish.getCategory()) : dish.getCategory() != null)
            return false;
        return getPrice() != null ? getPrice().equals(dish.getPrice()) : dish.getPrice() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + getWeight();
        result = 31 * result + (getIngredients() != null ? getIngredients().hashCode() : 0);
        result = 31 * result + (isDeleted() ? 1 : 0);
        return result;
    }
}
