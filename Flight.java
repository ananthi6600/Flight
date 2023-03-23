package com.flight;

import java.sql.SQLException;

public class Flight {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("\n^^^^^^^^^^^^^^^^^^Welcome to AirLine Reservation Online Booking^^^^^^^^^^^^^^^^^\n");
        new ViewOptions().printOptions();
    }
}
