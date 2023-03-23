package com.flight;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class CancelTickets {
    Scanner sc = new Scanner(System.in);

    public void checkBookingid() throws SQLException, ClassNotFoundException {
        System.out.println("********************CANCELLATION**************");
        System.out.println("Enter booking id");
        int bookId = sc.nextInt(), noOfSeats = 0, bId;
        double amount=0.00;
        boolean check = false;
        Statement statement = MysqlConnection.getMysql().createStatement();
        String sql = "select *from booking";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            if (resultSet.getInt("bookingid") == bookId) {
                bId = resultSet.getInt("bookingid");

                if (resultSet.getInt("genbusinessSeats") != 0)
                    noOfSeats = resultSet.getInt("genbusinessSeats");
                else if (resultSet.getInt("geneconomySeats") != 0)
                    noOfSeats = resultSet.getInt("geneconomySeats");
                else if (resultSet.getInt("takkalbusinessSeats") != 0)
                    noOfSeats = resultSet.getInt("takkalbusinessSeats");
                else if (resultSet.getInt("takkaleconomySeats")!=0)
                    noOfSeats = resultSet.getInt("takkaleconomySeats");


                if (resultSet.getString("typeOfSeat").equals("general"))
                    amount = resultSet.getDouble("totalAmount") - (noOfSeats * 200);
                else if (resultSet.getString("typeOfSeat").equals("takkal"))
                    amount = resultSet.getDouble("totalAmount") - (noOfSeats * 1000);
                changeCancellationStatusInBookingDetails(bId);
                insertCancelInformation(bId,noOfSeats,amount);

                check = true;
                break;
            }
        }
        if (check!=true) {
            System.out.println("Please Enter Valid bookingId");
            checkBookingid();
        }
        MysqlConnection.conClose();
    }

    private void changeCancellationStatusInBookingDetails(int bId) throws SQLException, ClassNotFoundException {

        Statement statement1 = MysqlConnection.getMysql().createStatement();
        String sql1 = "update booking "
                +" set ticketCancelStatus= 1 "
                +" where flightNumber="+ bId+" ";
        MysqlConnection.conClose();

    }




    private void insertCancelInformation(int bId, int noOfSeats, double amount) throws SQLException, ClassNotFoundException  {
      Cancel cancel=  new Cancel();
      cancel.setBookingId(bId);
      cancel.setNoOfSeats(noOfSeats);
      cancel.setRefund(amount);

      InsertBookedTicketDetails insert= new InsertBookedTicketDetails();
      insert.insertCancelTicket(cancel);
    }
}
