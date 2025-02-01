package org.example.model;

import java.time.LocalDate;

public class Booking {
    private int id;
    private Customer customer;
    private String seat;
    private LocalDate bookingDate;
    private TicketType ticketType;

    // Constructors
    public Booking(int customerId, String seatNumber, TicketType ticketType) {
        this.setCustomerId(customerId);
        this.seat = seatNumber;
        this.ticketType = ticketType;
    }

    public Booking() {}

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

    public int getCustomerId() {
        return customer != null ? customer.getId() : -1; // Return -1 if customer is null
    }

    public void setCustomerId(int customerId) {
        if (this.customer == null) {
            this.customer = new Customer();
        }
        this.customer.setId(customerId);
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
}