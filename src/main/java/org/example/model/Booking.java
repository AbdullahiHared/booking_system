package org.example.model;

public class Booking {
    private int id;
    private Customer customer;
    private String seat;

    // Constructors
    public Booking(int customerId, String seatNumber) {
        this.setCustomerId(customerId); // Use setCustomerId to set the customer ID
        this.seat = seatNumber; // This line causes the error
    }

    public Booking() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeat() {
        return seat;
    }


    public void setSeat(String seat) {
        this.seat = seat;
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