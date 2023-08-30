package ui;
/*
 * Copyright (c) 2023. Taras Bahnyuk
 * All rights reserved.
 */

import model.reservation.Reservation;
import service.ReservationService;

import java.util.Collection;

public class PrintMenu {
    public static void printUserMenu() {
   //     ClearScreen.clearScreen();
   //     System.out.println("Main menu:");
        System.out.println(ConsoleColors.BLUE + "1)" + ConsoleColors.RESET + "Find and Reserve" +
                ConsoleColors.BLUE + " 2)" + ConsoleColors.RESET + "My Reservations" +
                ConsoleColors.BLUE + " 3)" + ConsoleColors.RESET + "Create Account" +
                ConsoleColors.GREEN_BRIGHT + " 4)" + ConsoleColors.RESET + "Admin Menu " +
                ConsoleColors.RED + " 5)" + ConsoleColors.RESET + "Exit Application");
    }

    public static void printAdminMenu() {
    //    ClearScreen.clearScreen();
    //    System.out.println("Admin menu:");
        System.out.println(ConsoleColors.GREEN_BRIGHT + "1)" + ConsoleColors.RESET + "Customers " +
                ConsoleColors.GREEN_BRIGHT + " 2)" + ConsoleColors.RESET + "Rooms " +
                ConsoleColors.GREEN_BRIGHT + " 3)" + ConsoleColors.RESET + "Reservations " +
                ConsoleColors.GREEN_BRIGHT + " 4)" + ConsoleColors.RESET + "Add Room " +
                ConsoleColors.BLUE + " 5)" + ConsoleColors.RESET + "Main Menu" +
                ConsoleColors.GREEN_BRIGHT + " 6)" + ConsoleColors.RESET + "Populate with data");
    }
    public static void printOtherMenu() {
        ClearScreen.clearScreen();
        System.out.println("Printing OTHER menu");
    }

    public static void printAllReservations() {
        final Collection<Reservation> reservations = ReservationService.getSingleton().getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation + "\n");
            }
        }
    }

}

