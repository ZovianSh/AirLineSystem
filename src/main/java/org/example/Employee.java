package org.example;

import lombok.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Child class 2
@Setter
@Getter
@ToString
public class Employee extends Person {
    private String employeeId;
    private String password;
    private String workPosition;
    private double discount;
    private static final String CREDENTIALS_FILE = "C:/Users/dzovi/Desktop/credentials.txt";
    private static final Map<String, String> credentials = new HashMap<>();

    //Constructor
    public Employee(String firstName, String lastName, int age, String email, String employeeId, String password, String workPosition, double discount) {
        super(firstName, lastName, age, email);
        this.employeeId = employeeId;
        this.password = password;
        this.workPosition = workPosition;
        this.discount = discount;
    }

    static {
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    credentials.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading credentials file: " + e.getMessage());
        }
    }

    //method for authentication
    static boolean authenticateEmployee(String employeeId, String employeePassword) {
        String storedPassword = credentials.get(employeeId);
        return storedPassword != null && storedPassword.equals(employeePassword);
    }

    public static void employeeMenu(Scanner scanner) {
        //create a temporary Employee instance for demonstration
        Employee employee = new Employee("Jane", "Doe", 30, "jane.doe@example.com", "E123", "password", "Manager", 0.1);

        while (true) {
            System.out.println("Employee options:");
            System.out.println("1. View Flights");
            System.out.println("2. Search Flights");
            System.out.println("3. Book Flight");
            System.out.println("4. View Your Booked Flight(s) Details");
            System.out.println("5. Update Details for Your Booked flight(s)");
            System.out.println("6. Cancel booking(s)");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    employee.viewFlights();
                    break;
                case 2:
                    System.out.print("Enter departure destination: ");
                    String departureDestination = scanner.next();
                    System.out.print("Enter arrival destination: ");
                    String arrivalDestination = scanner.next();
                    employee.searchFlights(departureDestination, arrivalDestination, scanner);
                    break;
                case 3:
                    employee.bookFlight(scanner);
                    break;
                case 4:
                    employee.viewBookingDetails(scanner);
                    break;
                case 5:
                    employee.updateBookingDetails(scanner);
                    break;
                case 6:
                    employee.cancelBooking(scanner);
                    break;
                case 7:
                    System.out.println("Exiting Employee Menu...");
                    Main.mainMenu(scanner);
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to display employee details
    @Override
    public String toString() {
        return "Employee: " + super.toString() + ", Position: " + workPosition;
    }
}


