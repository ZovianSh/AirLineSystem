package org.example;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.example.Passenger.passengerMenu;


@Getter
@Setter
@ToString
public abstract class Person {
    //getter and setter
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private Flight bookedFlight;
    protected List<Booking> bookings; //protected to allow access in subclasses

    private static final String FLIGHTS_FILE = "C://Users//dzovi//Desktop//Flights.txt";
    private static final String BOOKINGS_FILE = "C://Users//dzovi//Desktop//bookedFlights.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    // Constructor
    public Person(String firstName, String lastName, int age, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.bookings = new ArrayList<>();
    }

    public void viewFlights() {
        System.out.println("Viewing all available flights...");
        List<Flight> flights = readFlightsFromFile();

        if (flights.isEmpty()) {
            System.out.println("No flights available.");
        } else {
            System.out.println("All flights:");
            for (Flight flight : flights) {
                System.out.println(flight);
            }
        }
    }

    private List<Flight> readFlightsFromFile() {
        List<Flight> flights = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("C://Users//dzovi//Desktop//Flights.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 9) {
                    String flightNumber = parts[0];
                    String departureDestination = parts[1];
                    String arrivalDestination = parts[2];
                    Date departureDate = Flight.DATE_FORMAT.parse(parts[3]);
                    Date arrivalDate = Flight.DATE_FORMAT.parse(parts[4]);
                    String departureTime = parts[5];
                    String arrivalTime = parts[6];
                    int availableSeats = Integer.parseInt(parts[7]);
                    double baseTicketPrice = Double.parseDouble(parts[8]);

                    flights.add(new Flight(flightNumber, departureDestination, arrivalDestination, departureDate, arrivalDate, departureTime, arrivalTime, availableSeats, baseTicketPrice));
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("An error occurred while reading flights from file: " + e.getMessage());
        }
        return flights;
    }


        // Display the flight manager menu again
//        System.out.println("Going back to Flight Manager Menu...");
//        managerMenu(scanner);

    public void searchFlights(String departureDestination, String arrivalDestination, Scanner scanner){
        System.out.println("Searching flights...");
        List<Flight> flights = readFlightsFromFile();
        List<Flight> matchingFlights = new ArrayList<>();

        for (Flight flight : flights) {
            if (flight.getDepartureDestination().equalsIgnoreCase(departureDestination) &&
                    flight.getArrivalDestination().equalsIgnoreCase(arrivalDestination)) {
                matchingFlights.add(flight);
            }
        }

        if (matchingFlights.isEmpty()) {
            System.out.println("No matching flights found.");
            System.out.println("1. Try Again");
            System.out.println("2. Back to Employee Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.print("Enter departure destination: ");
                String newDepartureDestination = scanner.next();
                System.out.print("Enter arrival destination: ");
                String newArrivalDestination = scanner.next();
                searchFlights(newDepartureDestination, newArrivalDestination, scanner);
            } else if (choice == 2) {
                return;
            } else {
                System.out.println("Invalid choice. Returning to Employee Menu.");
                return;
            }
        } else {
            System.out.println("Matching Flights:");
            for (Flight flight : matchingFlights) {
                System.out.println(flight);
            }
        }
    }

    public void bookFlight(Scanner scanner){
        System.out.println("Booking a flight...");

        while (true) {
            System.out.print("Enter departure destination: ");
            String departureDestination = scanner.next();
            System.out.print("Enter arrival destination: ");
            String arrivalDestination = scanner.next();

            List<Flight> flights = readFlightsFromFile2();
            List<Flight> matchingFlights = new ArrayList<>();

            for (Flight flight : flights) {
                if (flight.getDepartureDestination().equalsIgnoreCase(departureDestination) &&
                        flight.getArrivalDestination().equalsIgnoreCase(arrivalDestination)) {
                    matchingFlights.add(flight);
                }
            }

            if (matchingFlights.isEmpty()) {
                System.out.println("No matching flights found.");
                System.out.println("1. Try Again");
                System.out.println("2. Back to Menu");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1) {
                    continue;
                } else {
                    if (this instanceof Employee) {
                        ((Employee) this).employeeMenu(scanner);
                    } else {
                        passengerMenu(scanner);
                    }
                    return;
                }
            } else {
                System.out.println("Matching Flights:");
                for (int i = 0; i < matchingFlights.size(); i++) {
                    System.out.println((i + 1) + ". " + matchingFlights.get(i));
                }

                System.out.print("Choose a flight by number: ");
                int flightChoice = scanner.nextInt();
                scanner.nextLine();
                while (flightChoice < 1 || flightChoice > matchingFlights.size()) {
                    System.out.print("Invalid choice. Choose a flight by number: ");
                    flightChoice = scanner.nextInt();
                }
                Flight chosenFlight = matchingFlights.get(flightChoice - 1);

                FlightClass flightClass = null;
                while (flightClass == null) {
                    System.out.print("Choose a flight class (ECONOMY, BUSINESS, FIRST CLASS): ");
                    String flightClassInput = scanner.nextLine().trim().toUpperCase().replace(" ", "_");
                    try {
                        flightClass = FlightClass.valueOf(flightClassInput);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid flight class. Please enter again (ECONOMY, BUSINESS, FIRST CLASS): ");
                    }
                }

                System.out.print("Enter your first name: ");
                String firstName = scanner.next();
                System.out.print("Enter your last name: ");
                String lastName = scanner.next();
                System.out.print("Enter your age: ");
                int age = scanner.nextInt();
                while (age < 0) {
                    System.out.print("Invalid age. Enter your age: ");
                    age = scanner.nextInt();
                }
                System.out.print("Enter your email: ");
                String email = scanner.next();

                double basePrice = chosenFlight.getBaseTicketPrice();
                double discount = 0.0;

                if (age < 3) {
                    discount = 1.0;
                } else if (age < 10) {
                    discount = 0.5;
                } else if (age >= 60) {
                    discount = 0.35;
                } else if (isStudent(age)) {
                    discount = 0.20;
                }

                double classMultiplier = 1.0;
                if (flightClass == FlightClass.BUSINESS) {
                    classMultiplier = 1.25;
                } else if (flightClass == FlightClass.FIRST_CLASS) {
                    classMultiplier = 1.5;
                }

                double finalPrice = basePrice * classMultiplier * (1 - discount);

                if (this instanceof Employee) {
                    Employee employee = (Employee) this;
                    System.out.print("Enter your years of service: ");
                    int yearsOfService = scanner.nextInt();
                    while (yearsOfService < 0) {
                        System.out.print("Invalid years of service. Enter your years of service: ");
                        yearsOfService = scanner.nextInt();
                    }
                    double employeeDiscount = yearsOfService * 0.02;
                    finalPrice = finalPrice * (1 - employeeDiscount);
                }

                System.out.println("Ticket Price: " + finalPrice);
                System.out.print("Choose payment method (VISA, MASTERCARD, GIFTCARD, PAYPAL): ");
                String paymentMethod = scanner.next().toUpperCase();
                while (!paymentMethod.equals("VISA") &&
                        !paymentMethod.equals("MASTERCARD") &&
                        !paymentMethod.equals("GIFTCARD") &&
                        !paymentMethod.equals("PAYPAL")) {
                    System.out.print("Invalid choice. Choose payment method (VISA, MASTERCARD, GIFTCARD, PAYPAL): ");
                    paymentMethod = scanner.next().toUpperCase();
                }
                scanner.nextLine();  // consume the newline character
                System.out.print("Enter card number (0000 0000 0000 0000): ");
                String cardNumber = scanner.nextLine().trim();
                while (!cardNumber.matches("\\d{4} \\d{4} \\d{4} \\d{4}")) {
                    System.out.print("Invalid card number format. Enter again (0000 0000 0000 0000): ");
                    cardNumber = scanner.nextLine().trim();
                }
                System.out.print("Enter card PIN: ");
                String pin = scanner.next();

                System.out.println("All done! Your ticket(s) and receipt will be sent to your email.");


                // Save booking information
                saveBooking(firstName, lastName, age, email, cardNumber, pin, flightClass, chosenFlight);

                if (this instanceof Employee) {
                    ((Employee) this).employeeMenu(scanner);
                } else {
                    passengerMenu(scanner);
                }
                return;
            }
        }
    }

    private void saveBooking(String firstName, String lastName, int age, String email, String cardNumber, String pin,
                             FlightClass flightClass, Flight chosenFlight) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKINGS_FILE, true))) {
            writer.write(firstName + "," + lastName + "," + age + "," + email + "," + cardNumber.substring(0, 4)
                    + " **** **** ****," + pin.charAt(0) + "*** ," + flightClass + ", [" + chosenFlight.getFlightNumber() + ","
                    + chosenFlight.getDepartureDestination() + "," + chosenFlight.getArrivalDestination() + ","
                    + DATE_FORMAT.format(chosenFlight.getDepartureDate()) + "," + DATE_FORMAT.format(chosenFlight.getArrivalDate()) + ","
                    + chosenFlight.getDepartureTime() + "," + chosenFlight.getArrivalTime() + "]");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the booking: " + e.getMessage());
        }
    }

    private List<Flight> readFlightsFromFile2() {
        List<Flight> flights = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FLIGHTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                flights.add(new Flight(parts[0], parts[1], parts[2], DATE_FORMAT.parse(parts[3]), DATE_FORMAT.parse(parts[4]),
                        parts[5], parts[6], Integer.parseInt(parts[7]), Double.parseDouble(parts[8])));
            }
        } catch (IOException | ParseException e) {
            System.out.println("An error occurred while reading flights from file: " + e.getMessage());
        }
        return flights;
    }

    private boolean isStudent(int age) {
        // Implement student status check if needed
        return age >= 10 && age <= 25; // Example: assume students are aged 18-25
    }

    //
    public void viewBookingDetails(Scanner scanner){
        while (true) {
            System.out.println("Viewing booked flights...");
            System.out.print("Enter your first name: ");
            String firstName = scanner.next();
            System.out.print("Enter your last name: ");
            String lastName = scanner.next();
            System.out.print("Enter your email: ");
            String email = scanner.next();

            List<String> matchingBookings = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 7 &&
                            parts[0].equalsIgnoreCase(firstName) &&
                            parts[1].equalsIgnoreCase(lastName) &&
                            parts[3].equalsIgnoreCase(email)) {
                        matchingBookings.add(line);
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading booking details: " + e.getMessage());
            }

            if (matchingBookings.isEmpty()) {
                System.out.println("No matching bookings found.");
            } else {
                System.out.println("Matching Bookings:");
                for (String booking : matchingBookings) {
                    System.out.println(booking);
                }
            }

            System.out.println("Do you want to view something else?");
            System.out.println("1. Yes");
            System.out.println("2. No, back to menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            if (choice != 1) {
                break;
            }
        }

        if (this instanceof Employee) {
            ((Employee) this).employeeMenu(scanner);
        } else {
            passengerMenu(scanner);
        }
    }

    public void updateBookingDetails(Scanner scanner) {
        System.out.println("Updating booking details...");
        System.out.print("Enter your first name: ");
        String firstName = scanner.next();
        System.out.print("Enter your last name: ");
        String lastName = scanner.next();
        System.out.print("Enter your email: ");
        String email = scanner.next();

        List<String> bookings = new ArrayList<>();
        List<String> allBookings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                allBookings.add(line);
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(firstName) &&
                        parts[1].equalsIgnoreCase(lastName) &&
                        parts[3].equalsIgnoreCase(email)) {
                    bookings.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading booking details: " + e.getMessage());
        }

        if (bookings.isEmpty()) {
            System.out.println("No matching bookings found.");
            return;
        }

        System.out.println("Matching Bookings:");
        for (int i = 0; i < bookings.size(); i++) {
            System.out.println((i + 1) + ". " + bookings.get(i));
        }

        System.out.print("Choose a booking to update by number: ");
        int bookingChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (bookingChoice < 1 || bookingChoice > bookings.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        String chosenBooking = bookings.get(bookingChoice - 1);
        String[] bookingParts = chosenBooking.split(",");

        System.out.println("Choose the detail to update:");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Email");
        System.out.println("4. Payment Method (Card Number and PIN)");
        System.out.println("5. Flight Class");
        System.out.println("6. Flight");
        System.out.print("Enter your choice: ");
        int updateChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (updateChoice) {
            case 1:
                System.out.print("Enter new first name: ");
                bookingParts[0] = scanner.next();
                break;
            case 2:
                System.out.print("Enter new last name: ");
                bookingParts[1] = scanner.next();
                break;
            case 3:
                System.out.print("Enter new email: ");
                bookingParts[3] = scanner.next();
                break;
            case 4:
                System.out.print("Enter new card number (0000 0000 0000 0000): ");
                String cardNumber = scanner.nextLine().trim();
                while (!cardNumber.matches("\\d{4} \\d{4} \\d{4} \\d{4}")) {
                    System.out.print("Invalid card number format. Enter again (0000 0000 0000 0000): ");
                    cardNumber = scanner.nextLine().trim();
                }
                System.out.print("Enter new card PIN: ");
                String pin = scanner.next();
                bookingParts[4] = cardNumber.substring(0, 4) + "**** **** ****";
                break;
            case 5:
                System.out.print("Choose a new flight class (ECONOMY, BUSINESS, FIRST_CLASS): ");
                bookingParts[5] = scanner.next().toUpperCase();
                break;
            case 6:
                List<Flight> flights = readFlightsFromFile();
                List<Flight> matchingFlights = new ArrayList<>();

                System.out.print("Enter new departure destination: ");
                String newDepartureDestination = scanner.next();
                System.out.print("Enter new arrival destination: ");
                String newArrivalDestination = scanner.next();

                for (Flight flight : flights) {
                    if (flight.getDepartureDestination().equalsIgnoreCase(newDepartureDestination) &&
                            flight.getArrivalDestination().equalsIgnoreCase(newArrivalDestination)) {
                        matchingFlights.add(flight);
                    }
                }

                if (matchingFlights.isEmpty()) {
                    System.out.println("No matching flights found.");
                    return;
                } else {
                    System.out.println("Matching Flights:");
                    for (int i = 0; i < matchingFlights.size(); i++) {
                        System.out.println((i + 1) + ". " + matchingFlights.get(i));
                    }

                    System.out.print("Choose a flight by number: ");
                    int flightChoice = scanner.nextInt();
                    Flight chosenFlight = matchingFlights.get(flightChoice - 1);

                    bookingParts[7] = chosenFlight.getFlightNumber();
                    bookingParts[8] = chosenFlight.getDepartureDestination();
                    bookingParts[9] = chosenFlight.getArrivalDestination();
                    bookingParts[10] = DATE_FORMAT.format(chosenFlight.getDepartureDate());
                    bookingParts[11] = DATE_FORMAT.format(chosenFlight.getArrivalDate());
                    bookingParts[12] = chosenFlight.getDepartureTime();
                    bookingParts[13] = chosenFlight.getArrivalTime();
                }
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        // Update the booking in the list
        allBookings.set(allBookings.indexOf(chosenBooking), String.join(",", bookingParts));

        // Write the updated bookings back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKINGS_FILE))) {
            for (String booking : allBookings) {
                writer.write(booking);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while updating the booking details: " + e.getMessage());
        }

        System.out.println("Booking details updated successfully.");
    }

    public void cancelBooking(Scanner scanner) {
        System.out.println("Canceling booking...");

        System.out.print("Enter your first name: ");
        String firstName = scanner.next();
        System.out.print("Enter your last name: ");
        String lastName = scanner.next();
        System.out.print("Enter your email: ");
        String email = scanner.next();

        List<String> bookings = readBookingsFromFile();
        List<String> updatedBookings = new ArrayList<>();

        boolean bookingFound = false;

        for (String booking : bookings) {
            String[] parts = booking.split(",");
            if (parts[0].equalsIgnoreCase(firstName) &&
                    parts[1].equalsIgnoreCase(lastName) &&
                    parts[3].equalsIgnoreCase(email)) {
                bookingFound = true;
                System.out.println("Booking found:");
                System.out.println("Flight: " + parts[7]);
                System.out.println("From: " + parts[8]);
                System.out.println("To: " + parts[9]);
                System.out.println("Flight Class: " + parts[6]);

                System.out.print("Are you sure you want to cancel this booking? (yes/no): ");
                String confirmation = scanner.next().toLowerCase();
                if (confirmation.equals("yes")) {
                    System.out.println("Booking canceled successfully.");
                } else {
                    System.out.println("Booking cancellation aborted.");
                    updatedBookings.add(booking); // Keep the booking if not canceled
                }
            } else {
                updatedBookings.add(booking); // Keep other bookings
            }
        }

        if (!bookingFound) {
            System.out.println("No bookings found for the provided details.");
        }

        // Update the bookings file with the remaining bookings
        writeBookingsToFile(updatedBookings);
    }

    private List<String> readBookingsFromFile() {
        List<String> bookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("C://Users//dzovi//Desktop//bookedFlights.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bookings.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the bookings: " + e.getMessage());
        }
        return bookings;
    }

    private void writeBookingsToFile(List<String> bookings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C://Users//dzovi//Desktop//bookedFlights.txt"))) {
            for (String booking : bookings) {
                writer.write(booking);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing the bookings: " + e.getMessage());
        }
    }

}
