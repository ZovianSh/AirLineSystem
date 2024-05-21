package org.example;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@ToString
public class Flight {
    private String flightNumber;
    private String departureDestination;
    private String arrivalDestination;
    private Date departureDate;
    private Date arrivalDate;
    private String departureTime;
    private String arrivalTime;
    private int availableSeats;
    private double baseTicketPrice;
    private static final int MAX_SEATS = 200;

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


    // Constructor
    public Flight(String flightNumber, String departureDestination, String arrivalDestination, Date departureDate, Date arrivalDate, String departureTime, String arrivalTime, int availableSeats, double baseTicketPrice) {
        this.flightNumber = flightNumber;
        this.departureDestination = departureDestination;
        this.arrivalDestination = arrivalDestination;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
        this.baseTicketPrice = baseTicketPrice;
        if (availableSeats > MAX_SEATS) {
            this.availableSeats = MAX_SEATS;
        }
    }

    @Override
    public String toString() {
        return flightNumber + "," + departureDestination + "," + arrivalDestination + "," + DATE_FORMAT.format(departureDate) + "," + DATE_FORMAT.format(arrivalDate) + "," + departureTime + "," + arrivalTime + "," + availableSeats + "," + baseTicketPrice;
    }
}
