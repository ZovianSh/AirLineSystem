package org.example;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Scanner;

//child class 1
@Getter
@Setter
@ToString
public class Passenger extends Person {
    //constructor
    public Passenger(String firstName, String lastName, int age, String email) {
        super(firstName, lastName, age, email);
        //this.bookings = new ArrayList<>();
    }

    //passenger menu
    public static void passengerMenu(Scanner scanner) {
        Passenger passenger = new Passenger("John", "Doe", 30, "john.doe@example.com");

        // Passenger menu options
        while (true){
            System.out.println("Passenger options:");
            System.out.println("1. View Flights");
            System.out.println("2. Search Flights");
            System.out.println("3. Book a Flight");
            System.out.println("4. View Your Booked Flight(s) Details");
            System.out.println("5. Update Details for Your Booked flight(s)");
            System.out.println("6. Cancel booking(s)");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    passenger.viewFlights();
                    break;
                case 2:
                    System.out.print("Enter departure destination: ");
                    String departureDestination = scanner.next();
                    System.out.print("Enter arrival destination: ");
                    String arrivalDestination = scanner.next();
                    passenger.searchFlights(departureDestination, arrivalDestination, scanner);
                    break;
                case 3:
                    passenger.bookFlight(scanner);
                    break;
                case 4:
                    passenger.viewBookingDetails(scanner);
                    break;
                case 5:
                    passenger.updateBookingDetails(scanner);
                    break;
                case 6:
                    passenger.cancelBooking(scanner);
                    break;
                case 7:
                    System.out.println("Exiting Passenger Menu.");
                    Main.mainMenu(scanner);
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    @Override
    public void viewFlights() {
        System.out.println("Viewing available flights...");
        // Logic to display all available flights
        // You may read from a file or a database here and display the list of flights
    }

//    @Override
//    public void searchFlights() {
//
//    }

//    @Override
//    public void bookFlight(Scanner scanner) {
//        System.out.println("Booking a flight...");
//        // Logic to book a flight
//        // You may prompt the user for flight details and save the booking
//    }
//    @Override
//    public void viewBookingDetails(Scanner scanner) {
//
//    }

//    @Override
//    public void cancelBooking(Scanner scanner) {
//        System.out.println("Cancelling a booking...");
//        // Logic to cancel a booking
//        // You may prompt the user for booking details and remove the booking
//    }



    //other methods
//    public void displayPassengerDetails() {
//        System.out.println("Passenger Name: " + getLastName(), getFirstName());
//        System.out.println("Passenger Age: " + age);
//        System.out.println("Contact Details: " + contactDetails);
//    }

    //method to provide contact details
    public void provideContactDetails(String phoneNumber, String email) {
        // Implement logic to update contact details
    }
    //method to make a booking
//    public void makeBooking(FlightManager flightManager, String destination, Date date, int passengers) {
//        List<Flight> availableFlights = flightManager.searchFlights(/*destination, date, passengers*/flights, departureDestination, arrivalDestination);
//        if (!availableFlights.isEmpty()) {
//            // Assume we choose the first available flight for simplicity
//            Flight selectedFlight = availableFlights.get(0);
//            Booking booking = new Booking(this, selectedFlight, new Date());
//            bookings.add(booking);
//            System.out.println("Booking successful!");
//        } else {
//            System.out.println("No available flights for the specified criteria.");
//        }
//    }

    public void updateContactDetails(String email) {

        //this.email = email;
    }

    public void updateBooking(Flight newFlight, Booking booking) {
        //this.bookedFlight = newFlight;
    }




//
//    @Override
//    public String toString() {
//        return "Passenger: " + getFirstName() + " " + lastName + ", Age: " + age + ", Contact: " + phoneNumber + ", " + email;
//    }

    // Calculate ticket price based on flight class percentage of base price
//    double ticketPrice = calculatePrice(baseTicketPrice, flightClass);
//
//    // Function to calculate price based on flight class percentage of base price
//    private static double calculatePrice(double basePrice, FlightClass flightClass) {
//        switch (flightClass) {
//            case ECONOMY:
//                return basePrice * 0.8; // 80% of base price for economy class
//            case BUSINESS:
//                return basePrice * 1.2; // 120% of base price for business class
//            case FIRST_CLASS:
//                return basePrice * 1.5; // 150% of base price for first class
//            default:
//                return basePrice; // Default to base price if unknown class
//        }
//    }
}
