package api;

import model.customer.Customer;
import model.room.IRoom;
import service.CustomerService;
import service.ReservationService;
import ui.PrintMenu;

import java.util.Collection;
import java.util.List;

/*
 * Copyright (c) 2023. Taras Bahnyuk
 * All rights reserved.
 */
public class AdminResource {

    private static final AdminResource SINGLETON = new AdminResource();

    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getSingleton();

    private AdminResource() {}

    public static AdminResource getInstance() {
        return SINGLETON;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {
        rooms.forEach(reservationService::addRoom);
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        PrintMenu.printAllReservations();
    }
}