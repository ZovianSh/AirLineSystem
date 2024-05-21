package org.example;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FlightManager {
    static ArrayList<Flight> flights;

    private static final String CREDENTIALS_FILE = "C:/Users/dzovi/Desktop/credentials.txt";
    private static final Map<String, String> credentials = new HashMap<>();
    private static final String FLIGHTS_FILE = "C://Users//dzovi//Desktop//Flights.txt";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final Scanner scanner = new Scanner(System.in);

    // Constructor
    public FlightManager() {
        flights = new ArrayList<>();
    }

    //load credential for authentication(id and password)
    static void loadCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    credentials.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //the authentication
    static boolean authenticate(String userId, String password) {
        String storedPassword = credentials.get(userId);
        return storedPassword != null && storedPassword.equals(password);
    }

    //menu
    static void managerMenu(Scanner scanner) {
        System.out.println("Flight Manager Menu:");
        System.out.println("1. Add Flight");
        System.out.println("2. Remove Flight");
        System.out.println("3. Update Flight Details");
        System.out.println("4. Search Flights");
        System.out.println("5. display all flights");
        System.out.println("6. Exit");

        while (true) {
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice >= 1 && choice <= 6) {
                switch (choice) {
                    case 1:
                        addFlight();
                        break;
                    case 2:
                        removeFlight();
                        break;
                    case 3:
                        updateFlight();
                        break;
                    case 4:
                        searchFlightByNumber();
                        break;
                    case 5:
                        displayAllFlights();

                    case 6:
                        System.out.println("Exiting Flight Manager Menu.");
                        Main.mainMenu(scanner);
                        return;
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //add a flight to the list
    private static void addFlight() {
        Scanner scanner1 = new Scanner(System.in);

        System.out.println("Adding a new flight...");

        while (true) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FLIGHTS_FILE, true))) {
                String flightNumber;
                while (true) {
                    System.out.print("Enter flight number: ");
                    flightNumber = scanner1.next();

                    if (isNumeric(flightNumber)) {
                        break;
                    } else {
                        System.out.println("Invalid input. Flight number must be numbers only. Please try again.");
                    }
                }

                String departureDestination;
                String arrivalDestination;
                while (true) {
                    System.out.print("Enter departure destination: ");
                    departureDestination = scanner1.next();
                    System.out.print("Enter arrival destination: ");
                    arrivalDestination = scanner1.next();

                    if (!isNumeric(departureDestination) && !isNumeric(arrivalDestination)) {
                        if (!departureDestination.equals(arrivalDestination)) {
                            break;
                        } else {
                            System.out.println("Departure and arrival destinations can't be the same. Please try again.");
                        }
                    } else {
                        System.out.println("Invalid input. Destinations cannot be numbers. Please try again.");
                    }
                }

                Date departureDate;
                Date arrivalDate;
                while (true) {
                    System.out.print("Enter departure date(YYYY-MM-DD): ");
                    String departureDateInput = scanner1.next();
                    System.out.print("Enter arrival date(YYYY-MM-DD): ");
                    String arrivalDateInput = scanner1.next();
                    try {
                        departureDate = DATE_FORMAT.parse(departureDateInput);
                        arrivalDate = DATE_FORMAT.parse(arrivalDateInput);

                        if (!isValidDate(departureDateInput) || !isValidDate(arrivalDateInput)) {
                            System.out.println("Invalid date. Please ensure the date follows the calendar rules.");
                            continue;
                        }
                        if (departureDate.before(new Date()) || arrivalDate.before(new Date())) {
                            System.out.println("Expired date. Choose another one.");
                            continue;
                        }
                        if (arrivalDate.before(departureDate)) {
                            System.out.println("Arrival date can't be before departure date. Please try again.");
                            continue;
                        }
                        break;
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD or YYYY/MM/DD format.");
                    }
                }

                String departureTime;
                String arrivalTime;
                while (true) {
                    System.out.print("Enter departure time (HH:MM): ");
                    departureTime = scanner1.next();
                    System.out.print("Enter arrival time (HH:MM): ");
                    arrivalTime = scanner1.next();

                    if (!isValidTime(departureTime) || !isValidTime(arrivalTime)) {
                        System.out.println("Invalid time. Please try again!");
                        continue;
                    }

                    if (departureDate.equals(arrivalDate)) {
                        String[] depTimeParts = departureTime.split(":");
                        String[] arrTimeParts = arrivalTime.split(":");
                        int depHour = Integer.parseInt(depTimeParts[0]);
                        int depMinute = Integer.parseInt(depTimeParts[1]);
                        int arrHour = Integer.parseInt(arrTimeParts[0]);
                        int arrMinute = Integer.parseInt(arrTimeParts[1]);

                        if (arrHour < depHour || (arrHour == depHour && arrMinute <= depMinute)) {
                            System.out.println("Arrival time must be after departure time on the same day. Please try again.");
                            continue;
                        }
                    }
                    break;
                }

                int availableSeats;
                while (true) {
                    System.out.print("Enter available seats: ");
                    if (scanner1.hasNextInt()) {
                        availableSeats = scanner1.nextInt();
                        break;
                    } else {
                        System.out.println("Invalid input. Available seats must be in numbers. Please try again!");
                        scanner1.next(); // Clear the invalid input
                    }
                }

                double baseTicketPrice;
                while (true) {
                    System.out.print("Enter base ticket price: ");
                    if (scanner1.hasNextDouble()) {
                        baseTicketPrice = scanner1.nextDouble();
                        break;
                    } else {
                        System.out.println("Invalid input. Ticket price must be a number. Please try again.");
                        scanner1.next(); // Clear the invalid input
                    }
                }

//                FlightClass flightClass;
//                while (true) {
//                    System.out.print("Enter flight class (ECONOMY, BUSINESS, FIRST_CLASS): ");
//                    try {
//                        flightClass = FlightClass.valueOf(scanner1.next().toUpperCase());
//                        break;
//                    } catch (IllegalArgumentException e) {
//                        System.out.println("Invalid flight class. Please enter one of the following: ECONOMY, BUSINESS, FIRST_CLASS.");
//                    }
//                }


                // Create a new Flight object
                Flight flight = new Flight(flightNumber, departureDestination, arrivalDestination, departureDate, arrivalDate, departureTime, arrivalTime, availableSeats, baseTicketPrice);

                writer.write(flight.toString());
                writer.newLine();

                System.out.println("Flight added successfully.");
                break;
            } catch (IOException e) {
                System.out.println("An error occurred while adding the flight: " + e.getMessage());
            }
        }
    }

    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidTime(String time) {
        if (time == null || time.length() != 5 || time.charAt(2) != ':') {
            return false;
        }
        String[] parts = time.split(":");
        if (parts.length != 2) {
            return false;
        }
        try {
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static boolean isValidDate(String date) {
        String[] parts = date.split("-");
        if (parts.length != 3) {
            return false;
        }
        try {
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            if (month < 1 || month > 12) {
                return false;
            }
            if (day < 1 || day > 31) {
                return false;
            }
            // Check for the correct number of days in each month
            if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
                return false;
            }
            if (month == 2) {
                if (isLeapYear(year)) {
                    if (day > 29) {
                        return false;
                    }
                } else {
                    if (day > 28) {
                        return false;
                    }
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 100 != 0) {
            return true;
        } else if (year % 400 != 0) {
            return false;
        } else {
            return true;
        }
    }

//            double totalTicketPrice = calculateTicketPrice(ticketPrice, flightClass);
//
//            writer.println(flightNumber + "," + destination + "," + date + "," + time + "," + availableSeats + "," +
//                    totalTicketPrice + "," + flightClass);
//            System.out.println("Flight added successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//
//        flights.add(flight);
//    }

//    private static double calculateTicketPrice(double baseTicketPrice, String flightClass) {
//        double discountedPrice = baseTicketPrice;
//        Scanner scanner2 = new Scanner(System.in);
//
//        System.out.print("Enter passenger's age: ");
//        int age = scanner.nextInt();
//
//        // Apply age discounts
//        if (age < 5) {
//            discountedPrice *= 0.5; // 50% discount for children under 5
//        } else if (age < 10 || age > 65) {
//            discountedPrice *= 0.8; // 20% discount for passengers aged under 10 or over 65
//        }
//
//        // Apply class-specific discounts
//        switch (flightClass) {
//            case "business":
//                discountedPrice *= 1.5; // 50% price increase for business class
//                break;
//            case "first_class":
//                discountedPrice *= 2; // Double price for first class
//                break;
//            // No special discount for economy class
//            default:
//                break;
//        }
//
//        return discountedPrice;
//    }


    //remove a flight from the list
    public static void removeFlight() {
        Scanner scanner = new Scanner(System.in);

        List<Flight> flights = readFlightsFromFile();

        while (true) {
            System.out.println("Searching the flight to remove...");
            String departureDestination;
            String arrivalDestination;

            System.out.print("Enter departure destination: ");
            departureDestination = scanner.next();
            System.out.print("Enter arrival destination: ");
            arrivalDestination = scanner.next();

            List<Flight> foundFlights = searchFlights(flights, departureDestination, arrivalDestination);

            if (foundFlights.isEmpty()) {
                System.out.println("No flights found for the provided destinations. Please try again.");
//            removeFlight(); // Allow the user to try again
//            return;
                continue;
            }

            System.out.println("Found flights:");
            for (int i = 0; i < foundFlights.size(); i++) {
                System.out.println((i + 1) + ". " + foundFlights.get(i));
            }

            System.out.print("Enter the number of the flight to remove: ");
            int selectedFlightIndex = scanner.nextInt();
            if (selectedFlightIndex < 1 || selectedFlightIndex > foundFlights.size()) {
                System.out.println("Invalid selection. Please try again.");
                //removeFlight(); // Allow the user to try again
                //return;
                continue;
            }

            Flight selectedFlight = foundFlights.get(selectedFlightIndex - 1);
            flights.remove(selectedFlight);

            // Write the updated list of flights back to the file
            writeFlightsToFile(flights);

            System.out.println("Flight removed successfully.");
            //break; //exiting loop after successful removal
            System.out.print("Do you want to remove another flight? (yes/no): ");
            String anotherRemoval = scanner.next();
            if (!anotherRemoval.equalsIgnoreCase("yes")) {
                managerMenu(scanner);
                return;
            }
        }
    }

    private static List<Flight> readFlightsFromFile() {
        List<Flight> flights = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FLIGHTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                flights.add(new Flight(parts[0], parts[1], parts[2], DATE_FORMAT.parse(parts[3]), DATE_FORMAT.parse(parts[4]), parts[5], parts[6], Integer.parseInt(parts[7]), Double.parseDouble(parts[8])));
            }
        } catch (IOException | ParseException e) {
            System.out.println("An error occurred while reading flights from file: " + e.getMessage());
        }
        return flights;
    }

    private static void writeFlightsToFile(List<Flight> flights) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FLIGHTS_FILE))) {
            for (Flight flight : flights) {
//                writer.write(flight.toString());
//                writer.newLine();
                writer.write(String.join(",",
                        flight.getFlightNumber(),
                        flight.getDepartureDestination(),
                        flight.getArrivalDestination(),
                        DATE_FORMAT.format(flight.getDepartureDate()),
                        DATE_FORMAT.format(flight.getArrivalDate()),
                        flight.getDepartureTime(),
                        flight.getArrivalTime(),
                        String.valueOf(flight.getAvailableSeats()),
                        String.valueOf(flight.getBaseTicketPrice())));
                        //flight.getFlightClass().name()
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing flights to file: " + e.getMessage());
        }
    }

    private static List<Flight> searchFlights(List<Flight> flights, String departureDestination, String arrivalDestination) {
        List<Flight> foundFlights = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getDepartureDestination().equalsIgnoreCase(departureDestination) && flight.getArrivalDestination().equalsIgnoreCase(arrivalDestination)) {
                foundFlights.add(flight);
            }
        }
        return foundFlights;
    }


    //get all flights
    public ArrayList<Flight> getAllFlights() {
        return flights;
    }

    // Method to update an existing flight
    public static void updateFlight() {
        Scanner scanner = new Scanner(System.in);

        List<Flight> flights = readFlightsFromFile();

        System.out.println("Updating flight details...");
        System.out.print("Enter the flight number of the flight you want to update: ");
        String flightNumber = scanner.next();

        Flight flightToUpdate = null;
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                flightToUpdate = flight;
                break;
            }
        }

        if (flightToUpdate == null) {
            System.out.println("No flight found with the provided flight number. Please try again.");
            return;
        }

        System.out.println("Flight found: " + flightToUpdate);

        boolean isUpdated = false;

        while (true) {
            System.out.println("What detail would you like to update?");
            System.out.println("1. Departure Destination");
            System.out.println("2. Arrival Destination");
            System.out.println("3. Departure Date");
            System.out.println("4. Arrival Date");
            System.out.println("5. Departure Time");
            System.out.println("6. Arrival Time");
            System.out.println("7. Available Seats");
            System.out.println("8. Base Ticket Price");
            //System.out.println("9. Flight Class");
            System.out.println("9. Exit");
            System.out.print("Enter the number of your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    while (true) {
                        System.out.print("Enter new departure destination: ");
                        String newDepartureDestination = scanner.next();
                        if (!isNumeric(newDepartureDestination)) {
                            flightToUpdate.setDepartureDestination(newDepartureDestination);
                            isUpdated = true;
                            break;
                        } else {
                            System.out.println("Invalid input. Destinations cannot be numbers. Please try again.");
                        }
                    }
                    break;
                case 2:
                    while (true) {
                        System.out.print("Enter new arrival destination: ");
                        String newArrivalDestination = scanner.next();
                        if (!isNumeric(newArrivalDestination)) {
                            flightToUpdate.setArrivalDestination(newArrivalDestination);
                            isUpdated = true;
                            break;
                        } else {
                            System.out.println("Invalid input. Destinations cannot be numbers. Please try again.");
                        }
                    }
                    break;
                case 3:
                    while (true) {
                        System.out.print("Enter new departure date (YYYY-MM-DD): ");
                        String newDepartureDateInput = scanner.next();
                        try {
                            Date newDepartureDate = DATE_FORMAT.parse(newDepartureDateInput);
                            flightToUpdate.setDepartureDate(newDepartureDate);
                            isUpdated = true;
                            break;
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                        }
                    }
                    break;
                case 4:
                    while (true) {
                        System.out.print("Enter new arrival date (YYYY-MM-DD): ");
                        String newArrivalDateInput = scanner.next();
                        try {
                            Date newArrivalDate = DATE_FORMAT.parse(newArrivalDateInput);
                            flightToUpdate.setArrivalDate(newArrivalDate);
                            isUpdated = true;
                            break;
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                        }
                    }
                    break;
                case 5:
                    while (true) {
                        System.out.print("Enter new departure time (HH:MM): ");
                        String newDepartureTime = scanner.next();
                        if (isValidTime(newDepartureTime)) {
                            flightToUpdate.setDepartureTime(newDepartureTime);
                            isUpdated = true;
                            break;
                        } else {
                            System.out.println("Invalid time format. Please enter the time in HH:MM format.");
                        }
                    }
                    break;
                case 6:
                    while (true) {
                        System.out.print("Enter new arrival time (HH:MM): ");
                        String newArrivalTime = scanner.next();
                        if (isValidTime(newArrivalTime)) {
                            flightToUpdate.setArrivalTime(newArrivalTime);
                            isUpdated = true;
                            break;
                        } else {
                            System.out.println("Invalid time format. Please enter the time in HH:MM format.");
                        }
                    }
                    break;
                case 7:
                    while (true) {
                        System.out.print("Enter new available seats: ");
                        if (scanner.hasNextInt()) {
                            int newAvailableSeats = scanner.nextInt();
                            flightToUpdate.setAvailableSeats(newAvailableSeats);
                            isUpdated = true;
                            break;
                        } else {
                            System.out.println("Invalid input. Available seats must be in numbers. Please try again!");
                            scanner.next(); // Clear the invalid input
                        }
                    }
                    break;
                case 8:
                    while (true) {
                        System.out.print("Enter new base ticket price: ");
                        if (scanner.hasNextDouble()) {
                            double newBaseTicketPrice = scanner.nextDouble();
                            flightToUpdate.setBaseTicketPrice(newBaseTicketPrice);
                            isUpdated = true;
                            break;
                        } else {
                            System.out.println("Invalid input. Ticket price must be a number. Please try again.");
                            scanner.next(); // Clear the invalid input
                        }
                    }
                    break;
                case 9:
//                    while (true) {
//                        System.out.print("Enter new flight class (ECONOMY, BUSINESS, FIRST_CLASS): ");
//                        try {
//                            FlightClass newFlightClass = FlightClass.valueOf(scanner.next().toUpperCase());
//                            flightToUpdate.setFlightClass(newFlightClass);
//                            isUpdated = true;
//                            break;
//                        } catch (IllegalArgumentException e) {
//                            System.out.println("Invalid flight class. Please enter one of the following: ECONOMY, BUSINESS, FIRST_CLASS.");
//                        }
//                    }
//                    break;
//                case 10:
                    System.out.println("Exiting update process...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (isUpdated) {
                if (flightToUpdate.getDepartureDate().equals(flightToUpdate.getArrivalDate())
                        && flightToUpdate.getDepartureTime().equals(flightToUpdate.getArrivalTime())) {
                    System.out.println("Error: Departure time and arrival time cannot be the same if the departure and arrival dates are the same. Please update the times.");
                    continue;
                }

                // Confirm and save the change
                isUpdated = true;
                System.out.print("Do you want to update anything else? (yes/no): ");
                String anotherUpdate = scanner.next();
                if (!anotherUpdate.equalsIgnoreCase("yes")) {
                    writeFlightsToFile(flights);
                    System.out.println("Flight updated successfully.");
                    System.out.println("Updated flight details: " + flightToUpdate);
                    System.out.println("Confirmed!");
                    System.out.println("returning to flight manager menu...");
                    managerMenu(scanner);
                    return;
                } else {
                    System.out.println("Current flight details: " + flightToUpdate);
                }
            }
        }
    }

    //to search flight
    public static void searchFlightByNumber() {
        Scanner scanner = new Scanner(System.in);

        while(true){
            List<Flight> flights = readFlightsFromFile();

            System.out.print("Enter the flight number to search: ");
            String flightNumber = scanner.next();

            Flight foundFlight = null;
            for (Flight flight : flights) {
                if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                    foundFlight = flight;
                    break;
                }
            }

            if (foundFlight != null) {
                System.out.println("Flight found: " + foundFlight);
            } else {
                System.out.println("No flight found with the provided flight number.");
            }
            System.out.print("Would you like to search for another flight? (yes/no): ");
            String anotherSearch = scanner.next();

            if (!anotherSearch.equalsIgnoreCase("yes")) {
                break;
            }
        }

        // Display the flight manager menu again
        System.out.println("Going back to Flight Manager Menu...");
        managerMenu(scanner);
    }

    public static void displayAllFlights() {
        List<Flight> flights = readFlightsFromFile();

        if (flights.isEmpty()) {
            System.out.println("No flights available.");
        } else {
            System.out.println("All flights:");
            for (Flight flight : flights) {
                System.out.println(flight);
            }
        }

        // Display the flight manager menu again
        System.out.println("Going back to Flight Manager Menu...");
        managerMenu(scanner);
    }

}



