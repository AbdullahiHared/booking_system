package org.example.model;

import java.util.Date;

public class Booking {
    private int id;
    private Customer customer;

    // Constructors
    public Booking(int id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }

    public Booking() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
