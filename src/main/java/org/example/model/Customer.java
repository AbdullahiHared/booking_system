package org.example.model;

import java.sql.Date;
import java.time.LocalDate;

public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String name;
    private int customerSeat;
    public Customer(int customerId, String firstName, String lastName, LocalDate birthDate, int customerSeat) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = Date.valueOf(birthDate);
    }

    // Getter and Setter for customerId
    public int getId() {
        return customerId;
    }

    public int getCustomerSeat() {
        return this.customerSeat;
    }

    public void setId(int customerId) {
        this.customerId = customerId;
    }

    // Getter and Setter for firstName

    public String getName () {
        name = firstName + " " + this.lastName;
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    // Getter and Setter for birthDate
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = Date.valueOf(birthDate);
    }
}
