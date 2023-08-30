package model.customer;

import java.util.regex.Pattern;

/*
 * Copyright (c) 2023. Taras Bahnyuk
 * All rights reserved.
 */
public class Customer {

    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(final String firstName, final String lastName, final String email) {
        try {
            isValidEmail(email);
        } catch (Exception e) {
            System.out.println("Email should be in format 'address@domain.com'");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;


    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public static boolean isValidEmail(String email) {
        final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        // email pattern
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }


    @Override
    public String toString() {
        return "First Name: " + this.firstName + " Last Name: " + this.lastName + " Email: " + this.email;
    }
}
