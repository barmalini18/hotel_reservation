import api.HotelResource;
import model.customer.Customer;
import model.reservation.Reservation;
import model.room.IRoom;
import ui.PrintMenu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

/*
 * Copyright (c) 2023. Taras Bahnyuk
 * All rights reserved.
 */
public class MainMenu {
    static final HotelResource hotelResource = HotelResource.getSingleton();
    private static final int RECOMMENDED_ROOMS_DEFAULT_PLUS_DAYS = 7;

    public static void mainMenu() { //pretty

        PrintMenu.printUserMenu();
        //  String inputString;
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNext()) {
                    var inputString = scanner.next();

                    switch (inputString.charAt(0)) {
                        case '1' -> findRoom();
                        case '2' -> seeMyReservation();
                        case '3' -> createAccount();
                        case '4' -> AdminMenu.adminMenu();
                        case '5' -> exitApp();
                        default -> {
                        }
                    }
                }

            } catch (StringIndexOutOfBoundsException ex) {
                /* ignore and continue */
            }
        }
    }

    private static void findRoom() { //pretty

        //Let's get user's email first
        String email = enterEmail();

        if (hotelResource.getCustomer(email) == null) {
            System.out.println("User doesn't exist, you'll have to create account first");
            mainMenu();
        } else {
            System.out.println("Welcome, " + (hotelResource.getCustomer(email).getFirstName()));
        }

        System.out.println("Check-In Date mm/dd/yy example 02/01/23");
        Date checkIn = getInputDate();

        System.out.println("Check-Out Date:");
        Date checkOut = getInputDate();
        if (!checkIn.before(checkOut)) {
            System.out.println("Check in date must be before check out");
            mainMenu();
        }

        Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);

        if (availableRooms.isEmpty()) {
            System.out.println("No rooms found on your dates");
            // Let's find a room on alternative dates (+7 days)
            checkIn = addDaysToDate(checkIn, RECOMMENDED_ROOMS_DEFAULT_PLUS_DAYS);
            checkOut = addDaysToDate(checkOut, RECOMMENDED_ROOMS_DEFAULT_PLUS_DAYS);

            availableRooms = hotelResource.findARoom(checkIn, checkOut);
            if (availableRooms.isEmpty()) {
                System.out.println("And nothing's found on alternative dates, sorry.");
                mainMenu();

            } else {
                System.out.println("But on dates: \n" + checkIn + " to " + checkOut +
                        "\nthose rooms available: ");
                printRooms(availableRooms);
            }

        } else {
            printRooms(availableRooms);
        }

        System.out.println("Reserve room? y/n: ");
        Scanner scanner = new Scanner(System.in);
        String wantsToReserve = scanner.nextLine();
        if (wantsToReserve.charAt(0) == 'y') {
            reserveRoom(email, checkIn, checkOut, availableRooms);
        }
        PrintMenu.printUserMenu(); // reservation interrupted, go back to main menu

    }

    private static void reserveRoom(String email, Date checkInDate, Date checkOutDate, Collection<IRoom> rooms) { //pretty
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter room number:");

        final String roomNumber = scanner.nextLine();

        if (rooms.stream().anyMatch(room -> room.getRoomNumber().equals(roomNumber))) {
            final IRoom room = hotelResource.getRoom(roomNumber);

            final Reservation reservation = hotelResource.bookARoom(email, room, checkInDate, checkOutDate);
            System.out.println("Reservation created successfully!");
            System.out.println(reservation);
        } else {
            System.out.println("Wrong room number, let's try agan.");
            reserveRoom(email, checkInDate, checkOutDate, rooms);
        }
    }

    private static void printRooms(final Collection<IRoom> rooms) { // pretty
        try {
            System.out.println("Found " + rooms.size() + " rooms");
            rooms.forEach(System.out::println);
        } catch (Exception e) {
            // ignore errors
        }

    }

    private static void seeMyReservation() {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your email");
        String email = enterEmail();
        printReservations(hotelResource.getCustomersReservations(email));
        PrintMenu.printUserMenu();
    }


    private static void printReservations(final Collection<Reservation> reservations) { //pretty
        if(reservations != null) {
            System.out.println("Found " + reservations.size() + " reservations");
            reservations.forEach(reservation -> System.out.println("\n" + reservation));
        }else {
            System.out.println("No reservations found");
        }

    }

    private static void createAccount() { // pretty
        final Scanner scanner = new Scanner(System.in);
        String email = enterEmail();

        System.out.println("First name: ");
        String firstName = scanner.nextLine();
        System.out.println("Last name: ");
        String lastName = scanner.nextLine();

        try {
            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");

            PrintMenu.printUserMenu();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            createAccount();
        }

    }

    public static Date addDaysToDate(Date date, int days) { //pretty
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    private static Date getInputDate() { //pretty
        final Scanner scanner = new Scanner(System.in);
        final String DEFAULT_DATE_FORMAT = "MM/dd/yy";

        while (true) {
            try {
                return new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(scanner.nextLine());

            } catch (ParseException ex) {
                System.out.println("Wrong date format, try gain");
                //  repeat until passes
            }
        }
    }

    private static String enterEmail(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter email: ");
        String email = scanner.nextLine();

        while (!Customer.isValidEmail(email)){
            System.out.println("email format should be 'name@domain.com'");
            email = scanner.nextLine();
        }
        return  email;
    }

    private static void exitApp() {
        System.out.println("Exiting app");
        System.exit(0);
    }


}