package com.flight;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertBookedTicketDetails {

    public static void viewFlightDetails() throws  SQLException,ClassNotFoundException {
        Statement statement = MysqlConnection.getMysql().createStatement();
        String sql = "select *from flight";
        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("FlightNumber    |    generalBusinessSeats    |    generalEconomySeats   |    takkalBusinessSeats   |     takkalEconomySeats   |   TotalNoofSeats    |       arraival    |        distination");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        while (resultSet.next()) {
                System.out.println(resultSet.getInt("flightNumber") + "                         " + resultSet.getInt("generalBusinessSeats") + "                          " + resultSet.getInt("generalEconomySeats") + "                         " + resultSet.getInt("takkalBusinessSeats") + "                               " + resultSet.getInt("takkalEconomySeats") + "                        " + resultSet.getInt("availableNoofSeats")+"                   "+resultSet.getString("arraival")+"              "+resultSet.getString("destination"));
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
        MysqlConnection.conClose();

    }

    public void insertValues(BookingFlightDetails ticket)  throws SQLException, ClassNotFoundException {
        Statement statement = MysqlConnection.getMysql().createStatement();
        int meal=0,citizen=0,cancelStatus=0;
        if(ticket.isMeals())
            meal=1;
        if(ticket.isCitizen())
            citizen=1;

        String sql = "insert into booking(flightNumber,genbusinessSeats,geneconomySeats,takkalbusinessSeats,takkaleconomySeats,typeOfSeat,meals,citizen,totalAmount,ticketCancelStatus) "
                + "values('"+ ticket.getFlightNum() +"','"+ ticket.getGeneralBusinessSeat() +"','"+ ticket.getGeneralEconomySeat() +"','"+ ticket.getTakkalBusinessSeat()+"','" + ticket.getTakkalEconomySeat() +"','"+ticket.getTypeOfSeat()+"','"+ meal +"','"+ citizen +"','"+ ticket.getTotalAmount() +"','"+cancelStatus+"')";
       statement.executeUpdate(sql);
       int id=findBookingId();
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("***Flight ticket booking process is Successful***\n    Your Booking ID is"+" "+id);
        System.out.println("Total Amount :"+ticket.getTotalAmount()+"  Rupees");
        System.out.println("--------------------------------------------------------------------------------------");
        MysqlConnection.conClose();

    }

    private int findBookingId()   throws SQLException, ClassNotFoundException {
        int id=0;
        Statement statement = MysqlConnection.getMysql().createStatement();
        String sql = "select *from booking";
        ResultSet resultSet = statement.executeQuery(sql);
        while (true) {
            if(resultSet.next()==false){
                break;
            }
            id=resultSet.getInt("bookingid");
        }
        MysqlConnection.conClose();
        return id;
    }

    public void insertCancelTicket(Cancel cancel) throws SQLException, ClassNotFoundException {
        Statement statement = MysqlConnection.getMysql().createStatement();
        String sql="insert into cancel(bookingid,noOfSeats,refundAmount) "
                +"values('"+cancel.getBookingId()+"','"+cancel.getNoOfSeats()+"','"+cancel.getRefund()+"')";
        statement.executeUpdate(sql);
        System.out.println("--------------------------------------------------");
        System.out.println("**Your ticket is cancelled.......!***");
        System.out.println("Refund amount : "+cancel.getRefund()+"  Rupees");
        System.out.println("---------------------------------------------------");
        MysqlConnection.conClose();
    }
}
