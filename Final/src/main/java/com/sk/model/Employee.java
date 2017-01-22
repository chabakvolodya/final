package com.sk.model;

import com.sk.constraint.MinJodaMoney;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.money.Money;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "position_id")
    @NotNull(message = "Can not be empty")
    private Position position;

    @Column(name = "first_name")
    @NotEmpty(message = "Name can not be empty")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last Name can not be empty")
    private String lastName;

    @Column(name = "birth_day")
    @NotNull(message = "Wrong date")
    private LocalDate birthDay;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "USD")})
    @Column(name = "salary")
    @MinJodaMoney(value = 10, message = "Should be 10 or more")
    private Money salary;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "photo")
    private byte[] photo;

    public Employee() {
    }

    public Employee(int id, Position position, String firstName, String lastName, LocalDate birthDay, String phoneNumber, Money salary, boolean isDeleted) {
        this.id = id;
        this.position = position;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Money getSalary() {
        return salary;
    }

    public void setSalary(Money salary) {
        this.salary = salary;
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
        return "Employee{" +
                "id=" + id +
                ", position=" + position +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", salary=" + salary +
                ", isDeleted=" + isDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (isDeleted() != employee.isDeleted()) return false;
        if (getPosition() != null ? !getPosition().equals(employee.getPosition()) : employee.getPosition() != null)
            return false;
        if (getFirstName() != null ? !getFirstName().equals(employee.getFirstName()) : employee.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(employee.getLastName()) : employee.getLastName() != null)
            return false;
        if (getBirthDay() != null ? !getBirthDay().equals(employee.getBirthDay()) : employee.getBirthDay() != null)
            return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(employee.getPhoneNumber()) : employee.getPhoneNumber() != null)
            return false;
        return getSalary() != null ? getSalary().equals(employee.getSalary()) : employee.getSalary() == null;

    }

    @Override
    public int hashCode() {
        int result = getPosition() != null ? getPosition().hashCode() : 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getBirthDay() != null ? getBirthDay().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getSalary() != null ? getSalary().hashCode() : 0);
        result = 31 * result + (isDeleted() ? 1 : 0);
        return result;
    }
}
