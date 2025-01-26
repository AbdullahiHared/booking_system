package org.example.model;

public class Booking {
    private int id;
    private Customer customer;
    private int seat;

    // Constructors
    public Booking(int id, int seat, Customer customer) {
        this.id = id;
        this.seat = seat;
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

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getCustomerId() {
        return customer != null ? customer.getId() : -1; // Return -1 if customer is null
    }

    public void setCustomerId(int customerId) {
        if (this.customer == null) {
            this.customer = new Customer();
        }
        this.customer.setId(customerId);
    }
}