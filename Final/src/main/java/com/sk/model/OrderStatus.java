package com.sk.model;


import javax.persistence.*;

@Entity
@Table(name = "order_status")
@NamedQueries({
        @NamedQuery(name = "OrderStatus.findAll", query = "select os from OrderStatus os"),
        @NamedQuery(name = "OrderStatus.findByDescription",
                query = "select os from OrderStatus os where os.description = :description"),
})
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "status_description")
    private String description;

    public OrderStatus() {
    }

    public OrderStatus(int id, String description) {
        this.id = id;
        this.description = description;
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

    @Override
    public String toString() {
        return "OrderStatus{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderStatus)) return false;

        OrderStatus that = (OrderStatus) o;

        return getDescription() != null ? getDescription().equals(that.getDescription()) : that.getDescription() == null;

    }

    @Override
    public int hashCode() {
        return getDescription() != null ? getDescription().hashCode() : 0;
    }
}
