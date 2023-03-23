package com.flight;

import java.sql.SQLException;
import java.util.Scanner;

public class ViewOptions {
    Scanner sc = new Scanner(System.in);

    public void printOptions() throws SQLException, ClassNotFoundException {
        do {
            System.out.println("Enter Your Option");
            System.out.println("1. Booking Tickets\n2. Cancel Booking\n3. View Flight Information\n4. Exit");
            String option = sc.next();

            if (option.equals("1")) {
                new Booking().selectFlight();
            } else if (option.equals("2")) {
                System.out.println("if you want to cancel your tickets,\nGeneral -- > cancellation fees of INR 200 PER seat\nTakkal -- > cancellation fees of INR 200 PER seat");
                new CancelTickets().checkBookingid();
            } else if(option.equals("3")){
                InsertBookedTicketDetails.viewFlightDetails();
            }else if (option.equals("4")) {
                System.out.println("Exit Successful");
                System.exit(0);
            } else {
                System.out.println("Enter a valid Option");
            }
        }while(true);
    }
}