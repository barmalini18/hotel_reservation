import api.AdminResource;
import model.customer.Customer;
import model.room.IRoom;
import model.room.Room;
import model.room.enums.RoomType;
import ui.PrintMenu;

import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

/*
 * Copyright (c) 2023. Taras Bahnyuk
 * All rights reserved.
 */
public class AdminMenu {

    private static final AdminResource adminResource = AdminResource.getInstance();

    public static void adminMenu() {
        PrintMenu.printAdminMenu();
        String inputString;
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNext()) {
                    inputString = scanner.next();
                    switch (inputString.charAt(0)) {
                        case '1' -> displayAllCustomers();
                        case '2' -> displayAllRooms();
                        case '3' -> displayAllReservations();
                        case '4' -> addRoom();
                        case '5' -> MainMenu.mainMenu();
                        case '6' -> populateWithTestData();
                        default -> {
                        }
                    }
                }

            } catch (StringIndexOutOfBoundsException ex) {
                /* ignore and continue; */
            }
        }
    }


    private static void addRoom() {
        String roomNumber = "";
        double roomPrice = 0.0;
        RoomType roomType = null;
        System.out.println("room number:");
        final Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {

            try {
                switch (i) {

                    case 0 -> {
                        roomNumber = scanner.nextLine();
                        System.out.println("price per night");
                    }
                    case 1 -> {
                        roomPrice = scanner.nextDouble();
                        System.out.println("room type: 1, 2, 3, 4");
                    }
                    case 2 -> roomType = RoomType.valueOfLabel(scanner.nextLine());
                }
            } catch (Exception e) {
                // Exception. Yell at user
                // and repeat
                i--;
            }
        }
        final Room room = new Room(roomNumber, roomPrice, roomType);

        adminResource.addRoom(Collections.singletonList(room));
        System.out.println("Room added successfully!");
        PrintMenu.printAdminMenu();
    }

    private static void displayAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        try {
            System.out.println("Found " + rooms.size() + " rooms");
            adminResource.getAllRooms().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Rooms not found.");
        }
        PrintMenu.printAdminMenu();
    }

    private static void displayAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();

        try {
            System.out.println("Found " + customers.size() + " customers");
            adminResource.getAllCustomers().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Customers not found.");
        }
        PrintMenu.printAdminMenu();
    }
    private static void displayAllReservations() {
        adminResource.displayAllReservations();
        PrintMenu.printAdminMenu();
    }

    private static void populateWithTestData() {
        Room room = new Room("100", 50.0, RoomType.SINGLE);
        adminResource.addRoom(Collections.singletonList(room));
        room = new Room("110", 75.0, RoomType.DOUBLE);
        adminResource.addRoom(Collections.singletonList(room));
        room = new Room("120", 90.0, RoomType.SEAVIEW);
        adminResource.addRoom(Collections.singletonList(room));
        room = new Room("130", 80.0, RoomType.MOUNTAINVIEW);
        adminResource.addRoom(Collections.singletonList(room));
        MainMenu.hotelResource.createACustomer("first1@last1.com","FirstName1", "LastName1");
        MainMenu.hotelResource.createACustomer("first2@last2.com","FirstName2", "LastName2");
        MainMenu.hotelResource.createACustomer("first3@last3.com","FirstName3", "LastName3");
        MainMenu.hotelResource.createACustomer("first4@last4.com","FirstName4", "LastName4");

        PrintMenu.printAdminMenu();
    }

}