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

}
