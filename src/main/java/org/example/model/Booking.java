package org.example.model;
import java.util.Date;

public class Booking {
    private int id;
    private int passengerId;
    private String passengerName;
    private Date bookingDate;
    private String bookingTime;

    // Constructors
    public Booking(int id, int passengerId,String passengerName, Date bookingDate, String bookingTime) {
        this.passengerName = passengerName;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.passengerId = passengerId;
        this.id = id;
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }
}
