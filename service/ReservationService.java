package service;

import model.customer.Customer;
import model.reservation.Reservation;
import model.room.IRoom;

import java.util.*;
import java.util.stream.Collectors;

/*
 * Copyright (c) 2023. Taras Bahnyuk
 * All rights reserved.
 */
public class ReservationService {

    private static final ReservationService SINGLETON = new ReservationService();


    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Map<String, Collection<Reservation>> reservations = new HashMap<>();

    private ReservationService() {
    }

    public static ReservationService getSingleton() {
        return SINGLETON;
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

        Collection<Reservation> customerReservations = getCustomersReservation(customer);

        if (customerReservations == null) {
            customerReservations = new LinkedList<>();
        }

        customerReservations.add(reservation);
        reservations.put(customer.getEmail(), customerReservations);

        return reservation;
    }


    public Collection<IRoom> findRooms(Date checkIn, Date checkOut) {
        Collection<Reservation> allReservations = getAllReservations();
        Collection<IRoom> occupiedRooms = new LinkedList<>();

        for (Reservation reservation : allReservations) {
            if (checkIn.before(reservation.getCheckOutDate()) && checkOut.after(reservation.getCheckInDate())) {
                occupiedRooms.add(reservation.getRoom());
            }
        }
        return rooms.values().stream().filter(room -> occupiedRooms.stream().noneMatch(notAvailableRoom -> notAvailableRoom.equals(room))).collect(Collectors.toList());
    }


    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public Collection<Reservation> getAllReservations() {
        Collection<Reservation> allReservations = new LinkedList<>();

        for (Collection<Reservation> reservations : reservations.values()) {
            allReservations.addAll(reservations);
        }
        return allReservations;
    }

}
