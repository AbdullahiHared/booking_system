package org.example.model;

import java.time.LocalDate;

public class Seat {
    private int seatNumber;
    private boolean isReserved;
    private String passengerName;
    private LocalDate passengerBirthDate;
    private LocalDate bookingDate;
    private double price;

    // Constructor

    // Constructor
    public Seat(int seatNumber, double price) {
        this.seatNumber = seatNumber;
        this.isReserved = false;
        this.passengerName = null;
        this.passengerBirthDate = null;
        this.bookingDate = null;
        this.price = price;
    }


}
