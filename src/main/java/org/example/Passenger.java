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

//    @Override
//    public void viewFlights() {
//        System.out.println("Viewing available flights...");
//        // Logic to display all available flights
//        // You may read from a file or a database here and display the list of flights
//    }

}
