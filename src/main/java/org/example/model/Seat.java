package org.example.model;

import javax.sound.sampled.FloatControl;
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

    // Method to return seat details
    public String getSeatDetails() {
        return "Seat Number: " + seatNumber +
                ", Is Reserved: " + isReserved +
                ", Passenger Name: " + (passengerName != null ? passengerName : "N/A") +
                ", Passenger Birthdate: " + (passengerBirthDate != null ? passengerBirthDate : "N/A") +
                ", Booking Date: " + (bookingDate != null ? bookingDate : "N/A") +
                ", Price: " + price;
    }

    // Method to reserve seat
    public void reserveSeat(String passengerName, LocalDate passengerBirthDate, LocalDate bookingDate, double price) {
        if (isReserved) {
            System.out.println("Seat is already reserved.");
        } else {
            isReserved = true;
            this.passengerName = passengerName;
            this.passengerBirthDate = passengerBirthDate;
            this.bookingDate = bookingDate;
            this.price = price;
            System.out.println("Seat reserved successfully");
        }
    }

    // Method to cancel reservation
    public void cancelReservation() {
        if (isReserved) {
            isReserved = false;
            passengerName = null;
            passengerBirthDate = null;
            bookingDate = null;
            price = 0;
            System.out.println("Reservation canceled successfully.");
        } else {
            System.out.println("Seat is not reserved");
        }
     }

    // Getters and setters
    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public LocalDate getPassengerBirthDate() {
        return passengerBirthDate;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
