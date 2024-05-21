package org.example;

import java.util.*;

import static org.example.FlightManager.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadCredentials();
        mainMenu(scanner);
    }
        static void mainMenu(Scanner scanner){
            System.out.println("Welcome to the Airline Reservation System!");
            System.out.println("1. Are you an employee booking a flight? \n2. Are you a passenger booking a flight? \n3. Are you a flight manager?");

            int userType = 0;
            while (userType < 1 || userType > 3) {

                if (scanner.hasNextInt()) {
                    userType = scanner.nextInt();
                    if (userType < 1 || userType > 3) {
                        System.out.println("Invalid option. Please enter a number between 1 and 3.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    scanner.next(); // Clear the invalid input
                }
            }

            switch (userType) {
                case 1:
                    // Employee login
                    boolean authenticated = false;
                    while (!authenticated) {
                        System.out.println("Please enter your login ID:");
                        String employeeId = scanner.next();
                        System.out.println("Please enter your password:");
                        String employeePassword = scanner.next();

                        if (Employee.authenticateEmployee(employeeId, employeePassword)) {
                            System.out.println("Authentication successful! Welcome, " + employeeId);
                            authenticated = true;
                            // Employee menu
                            Employee.employeeMenu(scanner);
                        } else {
                            System.out.println("Invalid login credentials. Try again!");
                        }
                    }
                    break;
                case 2:
                    // Passenger menu
                    Passenger.passengerMenu(scanner);
                    break;
                case 3:
                    // Flight manager login
                    boolean authenticated1 = false;
                    while (!authenticated1) {
                        System.out.println("Please enter your login ID:");
                        String userId = scanner.next();
                        System.out.println("Please enter your password:");
                        String password = scanner.next();

                        if (authenticate(userId, password)) {
                            System.out.println("Authentication successful! Welcome, " + userId);
                            authenticated = true;
                            // Flight manager menu
                            managerMenu(scanner);
                        } else {
                            System.out.println("Invalid login credentials. Try again!");
                        }
                    }
                    break;
                default:
                    // This default case is unreachable because of the validation loop
                    System.out.println("Invalid option.Try again!");
            }
        }
}