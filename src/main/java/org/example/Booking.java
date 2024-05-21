package org.example;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
public class Booking {
    private Flight flight;
    private String flightClass;
    private double price;

    private Passenger passenger;
    private Date bookingDate;
    private boolean confirmed;
    private List<Booking> bookings;
    private String filename;


    // constructor
    public Booking(Flight flight, String flightClass, double price) {
        this.flight = flight;
        this.flightClass = flightClass;
        this.price = price;

        this.passenger = passenger;
        this.bookingDate = bookingDate;
        this.confirmed = false; // Initially, the booking is not confirmed
        this.filename = filename;
        this.bookings = new ArrayList<>();
    }

    public Booking(Passenger passenger, Flight selectedFlight, Date bookingDate) {
        this.passenger = passenger;
        this.bookingDate = bookingDate;
    }
}
