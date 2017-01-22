package com.sk.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "waiter_employee_id")
    private Employee waiterEmployee;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_detail",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "dish_id")})
    private List<Dish> dishes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_status_id")
    private OrderStatus status;

    public Order() {
    }

    public Order(int id, Employee waiterEmployee, LocalDateTime orderDate, Board board, List<Dish> dishes, OrderStatus status) {
        this.id = id;
        this.waiterEmployee = waiterEmployee;
        this.orderDate = orderDate;
        this.board = board;
        this.dishes = dishes;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getWaiterEmployee() {
        return waiterEmployee;
    }

    public void setWaiterEmployee(Employee waiterEmployee) {
        this.waiterEmployee = waiterEmployee;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", waiterEmployeeId=" + waiterEmployee.getId() +
                ", orderDate=" + orderDate +
                ", board=" + board +
                ", dishes=" + dishes +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (getWaiterEmployee() != null ? !getWaiterEmployee().equals(order.getWaiterEmployee()) : order.getWaiterEmployee() != null)
            return false;
        if (getOrderDate() != null ? !getOrderDate().equals(order.getOrderDate()) : order.getOrderDate() != null)
            return false;
        if (getBoard() != null ? !getBoard().equals(order.getBoard()) : order.getBoard() != null) return false;
        if (getDishes() != null ? !getDishes().equals(order.getDishes()) : order.getDishes() != null) return false;
        return getStatus() != null ? getStatus().equals(order.getStatus()) : order.getStatus() == null;

    }

    @Override
    public int hashCode() {
        int result = getWaiterEmployee() != null ? getWaiterEmployee().hashCode() : 0;
        result = 31 * result + (getOrderDate() != null ? getOrderDate().hashCode() : 0);
        result = 31 * result + (getBoard() != null ? getBoard().hashCode() : 0);
        result = 31 * result + (getDishes() != null ? getDishes().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        return result;
    }
}
