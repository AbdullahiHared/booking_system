package org.example.model;

public class Customer {
    private int customerId;
    private String firstName;
    private String lastName;
    private int birthDate;

    public Customer(int customerId, String firstName, String lastName, int birthDate) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}
