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
    //protected FlightClass flightClass;
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
        //this.flightClass = flightClass;
        if (availableSeats > MAX_SEATS) {
            this.availableSeats = MAX_SEATS;
        }
    }


    // Method to book a flight with passenger details
    public void bookFlight(Passenger passenger) {
        if (availableSeats > 0) {
            // Book the flight for the passenger
            System.out.println("Booking confirmed for " + passenger.getFirstName() + passenger.getLastName() + " on flight to " + arrivalDestination + "from" + departureDestination);
            // Reduce available seats
            availableSeats--;
        } else {
            System.out.println("Sorry, no available seats on this flight.");
        }
    }

    // Method to book a seat on the flight
    protected void bookSeat() {
        // Implementation to book a seat
    }

    //we need it to be with the age and the flightclass
    // Method for calculating the ticket price with the chosen experience
//    public double calculateTicketPrice(String experience) {
//        // Calculation logic based on flight class and passenger age
//        // Example: Adjust ticket price based on flight class and passenger age
//        double totalTicketPrice = baseTicketPrice; //check this
//        double experiencePrice = 0.0;
//
//        if (experience.equalsIgnoreCase("vip")) {
//            experiencePrice = 500.0; // Example price for VIP experience
//        } else if (experience.equalsIgnoreCase("special premium")) {
//            experiencePrice = 200.0; // Example price for special premium experience
//        } else if (experience.equalsIgnoreCase("upgrade")) {
//            experiencePrice = 100.0; // Example price for upgrade experience
//        } else if (experience.equalsIgnoreCase("none")) {
//            // No additional cost for no specific experience chosen
//            return totalTicketPrice;
//        } else {
//            System.out.println("Invalid experience choice.");
//            return -1; // Return -1 to indicate invalid choice
//        }
//
////        ticketPrice += experiencePrice;
////        return ticketPrice;
//    }

    // Method for the customer to choose their preferred experience
    public void chooseExperience(String experience) {
        if (experience.equalsIgnoreCase("vip")) {
            // Provide VIP experience
        } else if (experience.equalsIgnoreCase("special premium")) {
            // Provide special premium experience
        } else if (experience.equalsIgnoreCase("upgrade")) {
            // Provide upgrade experience
        } else if (experience.equalsIgnoreCase("none")) {
            // No specific experience chosen
        } else {
            System.out.println("Invalid experience choice.");
        }
    }

    public boolean isSeatAvailable() {
        // Logic to check if seats are available on the flight
        return availableSeats > 0;
    }

    public static int getMaxSeats(){
        return MAX_SEATS;
    }

    @Override
    public String toString() {
        return flightNumber + "," + departureDestination + "," + arrivalDestination + "," + DATE_FORMAT.format(departureDate) + "," + DATE_FORMAT.format(arrivalDate) + "," + departureTime + "," + arrivalTime + "," + availableSeats + "," + baseTicketPrice;
    }

    public String getFlightDetails() {
        return "Flight to " + arrivalDestination + " from " + departureDestination + " on " + departureDate + " at " + departureTime + "to" + arrivalDate + " at " + arrivalTime + " - " + availableSeats + " seats available";
    }


}
