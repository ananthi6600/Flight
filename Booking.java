package com.flight;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Booking {
    Scanner sc = new Scanner(System.in);
    public void selectFlight() throws SQLException, ClassNotFoundException {
        int currentFlightBookingNumber = 0;
        System.out.println("Enter Flight number 101 or 102");
        String s = sc.next();
        boolean checkValid = Validation.checkValidationNumber(s);
        if (checkValid) {
            currentFlightBookingNumber = Integer.parseInt(s);
            System.out.println(currentFlightBookingNumber);
        } else {
            System.out.println("Enter valid Flight Number");
            selectFlight();
        }

        flightDetails(currentFlightBookingNumber);
        selectTypeOfSeat(currentFlightBookingNumber);


    }


    public void selectTypeOfSeat(int currentFlightBookingNumber) throws SQLException, ClassNotFoundException {

        System.out.println("************Select Type of Seat***********");
        System.out.println("General\n* Cost of business seat =2000.00\n* Cost of economy seat =1000.00\n* Cost of meal =200.00\n* Citizen =10% discount");
        System.out.println("Takkal\n* Cost of business seat  =3000.00\n* Cost of economy seat =2000.00\n* Cost of meal =200.00\n* Citizen =10% discount");
        System.out.println();
        System.out.println("Enter your Option- General Business --> press '1'\n" +
                "                   General Economy  --> press '2'\n" +
                "                   Takkal Business  --> press '3'\n" +
                "                   Takkal Economy   --> press '4'\n");
        String select = sc.next();
        String bo=new Validation().checkSeatsRequiredNumber();

        int noOfSeatsRequired;
        while(true) {
            if (bo != null) {
                noOfSeatsRequired = Integer.parseInt(bo);
                break;
            } else {
                System.out.println("Please Enter Number [ 1 to 10] ......!");
                bo = new Validation().checkSeatsRequiredNumber();
            }
        }
        int generalBus=0,generalEco=0,takkalBus=0,takkalEco=0;
        java.lang.String typeOfseat="";
        double cost = 0.00;
        if (select.equals("1")) {
            checkGeneralBusiness(currentFlightBookingNumber, noOfSeatsRequired);
            generalBus=noOfSeatsRequired;
            cost=2000.00;
            typeOfseat="general";
        } else if (select.equals("2")) {
            checkGeneralEconomy(currentFlightBookingNumber, noOfSeatsRequired);
            cost=1000.00;
            generalEco=noOfSeatsRequired;
            typeOfseat="general";
        } else if (select.equals("3")) {
            checkTakkalBusiness(currentFlightBookingNumber, noOfSeatsRequired);
            cost=3000.00;
            takkalBus=noOfSeatsRequired;
            typeOfseat="takkal";
        } else if (select.equals("4")) {
            checkTakkalEconomy(currentFlightBookingNumber, noOfSeatsRequired);
            cost=2000.00;
            takkalEco=noOfSeatsRequired;
            typeOfseat="takkal";
        } else {
            System.out.println("Enter a valid Option");
            selectTypeOfSeat(currentFlightBookingNumber);
        }

        setValues(currentFlightBookingNumber,cost,generalBus,generalEco,takkalBus,takkalEco,noOfSeatsRequired,typeOfseat);


    }

    public void flightDetails(int currentFlightBookingNumber) throws SQLException, ClassNotFoundException {
        Statement statement = MysqlConnection.getMysql().createStatement();
        String sql = "select *from flight";
        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("FlightNumber    |    generalBusinessSeats    |    generalEconomySeats   |    takkalBusinessSeats   |     takkalEconomySeats   |   TotalNoofSeats    |       arraival    |        distination");
        while (resultSet.next()) {
            if (resultSet.getInt("flightNumber") == currentFlightBookingNumber) {
                System.out.println(resultSet.getInt("flightNumber") + "                         " + resultSet.getInt("generalBusinessSeats") + "                          " + resultSet.getInt("generalEconomySeats") + "                         " + resultSet.getInt("takkalBusinessSeats") + "                               " + resultSet.getInt("takkalEconomySeats") + "                        " + resultSet.getInt("availableNoofSeats")+"                   "+resultSet.getString("arraival")+"              "+resultSet.getString("destination"));
            }
        }
        MysqlConnection.conClose();

    }

    private void setValues(int currentFlightBookingNumber, double cost, int generalBus, int generalEco, int takkalBus, int takkalEco, int noOfSeatsRequired, java.lang.String typeOfSeat)  throws SQLException, ClassNotFoundException  {
        boolean b=false,c=false;
        double totalAmount=noOfSeatsRequired*cost;
        System.out.println("If you want to meal enter 'YES' otherwise enter 'NO'");
        String meals=sc.next().toUpperCase();
        if(meals.equals("YES")){
            b=true;
            totalAmount=totalAmount+(noOfSeatsRequired*200);
        }
        System.out.println("If you are a senior citizen enter 'YES' otherwise enter 'NO'");
        String citizen=sc.next().toUpperCase();
        if(citizen.equals("YES")){
            c=true;
            totalAmount=totalAmount-(totalAmount/10);
        }

        BookingFlightDetails ticket=new BookingFlightDetails();

        ticket.setCitizen(c);
        ticket.setFlightNum(currentFlightBookingNumber);
        ticket.setMeals(b);
        ticket.setGeneralBusinessSeat(generalBus);
        ticket.setGeneralEconomySeat(generalEco);
        ticket.setTakkalBusinessSeat(takkalBus);
        ticket.setTakkalEconomySeat(takkalEco);
        ticket.setTotalAmount(totalAmount);
        ticket.setTypeOfSeat(typeOfSeat);
        new  InsertBookedTicketDetails().insertValues(ticket);
    }


    public void checkGeneralBusiness(int currentFlightBookingNumber, int noOfSeatsRequired) throws SQLException, ClassNotFoundException {
        Statement statement = MysqlConnection.getMysql().createStatement();
        String sql = "select *from flight";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            if (currentFlightBookingNumber == resultSet.getInt("flightNumber")) {
                if (resultSet.getInt("generalBusinessSeats") == 0) {
                    System.out.println("Sorry ...! in general business seat section no seats available");
                    continueBoooking(currentFlightBookingNumber);
                } else if (noOfSeatsRequired > resultSet.getInt("generalBusinessSeats")) {
                    System.out.println("Sorry....! Your required no of seats , more than available seats ");
                    continueBoooking(currentFlightBookingNumber);
                } else {
                    int p = resultSet.getInt("generalBusinessSeats") - noOfSeatsRequired;
                        noOfFlightsRemoveFromIndivdualCounts(p, currentFlightBookingNumber);
                    int p1 = resultSet.getInt("availableNoofSeats") - noOfSeatsRequired;
                        noOfFlightsRemoveFromTotalCounts(p1, currentFlightBookingNumber);
                    break;
                }
            }
        }
        MysqlConnection.conClose();
    }



    public void checkTakkalBusiness(int currentFlightBookingNumber, int noOfSeatsRequired) throws SQLException, ClassNotFoundException {
        Statement statement = MysqlConnection.getMysql().createStatement();
        String sql = "select *from flight";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            if (currentFlightBookingNumber == resultSet.getInt("flightNumber")) {
                if (resultSet.getInt("takkalBusinessSeats") == 0) {
                    System.out.println("Sorry ...! in general business seat section no seats available");
                    continueBoooking(currentFlightBookingNumber);
                } else if (noOfSeatsRequired > resultSet.getInt("takkalBusinessSeats")) {
                    System.out.println("Sorry....! Your required no of seats , more than available seats ");
                    continueBoooking(currentFlightBookingNumber);
                } else {
                    int p = resultSet.getInt("generalBusinessSeats") - noOfSeatsRequired;
                    noOfFlightsRemoveFromIndivdualCounts(p, currentFlightBookingNumber);
                    int p1 = resultSet.getInt("availableNoofSeats") - noOfSeatsRequired;
                    noOfFlightsRemoveFromTotalCounts(p1, currentFlightBookingNumber);
                    break;
                }
                break;
            }
        }
        MysqlConnection.conClose();
    }

    public void checkTakkalEconomy(int currentFlightBookingNumber, int noOfSeatsRequired) throws SQLException, ClassNotFoundException {
        Statement statement = MysqlConnection.getMysql().createStatement();
        String sql = "select *from flight";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            if (currentFlightBookingNumber == resultSet.getInt("flightNumber")) {
                if (resultSet.getInt("takkalEconomySeats") == 0) {
                    System.out.println("Sorry ...! in general business seat section no seats available");
                    continueBoooking(currentFlightBookingNumber);
                } else if (noOfSeatsRequired > resultSet.getInt("takkalEconomySeats")) {
                    System.out.println("Sorry....! Your required no of seats , more than available seats ");
                    continueBoooking(currentFlightBookingNumber);
                } else {
                    int p = resultSet.getInt("generalBusinessSeats") - noOfSeatsRequired;
                    noOfFlightsRemoveFromIndivdualCounts(p, currentFlightBookingNumber);
                    int p1 = resultSet.getInt("availableNoofSeats") - noOfSeatsRequired;
                    noOfFlightsRemoveFromTotalCounts(p1, currentFlightBookingNumber);
                    break;

                }
                break;
            }
        }
        MysqlConnection.conClose();
    }

    void checkGeneralEconomy(int currentFlightBookingNumber ,int noOfSeatsRequired )throws SQLException, ClassNotFoundException {
        Statement statement = MysqlConnection.getMysql().createStatement();
        String sql = "select *from flight";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            if (currentFlightBookingNumber == resultSet.getInt("flightNumber")) {
                if (resultSet.getInt("generalEconomySeats") == 0) {
                    System.out.println("Sorry ...! in general business seat section no seats available");
                    continueBoooking(currentFlightBookingNumber);
                } else if (noOfSeatsRequired > resultSet.getInt("generalEconomySeats")) {
                    System.out.println("Sorry....! Your required no of seats , more than available seats ");
                    continueBoooking(currentFlightBookingNumber);
                } else {
                    int p = resultSet.getInt("generalBusinessSeats") - noOfSeatsRequired;
                    noOfFlightsRemoveFromIndivdualCounts(p, currentFlightBookingNumber);
                    int p1 = resultSet.getInt("availableNoofSeats") - noOfSeatsRequired;
                    noOfFlightsRemoveFromTotalCounts(p1, currentFlightBookingNumber);
                    break;


                }
            }
        }
        MysqlConnection.conClose();
    }

    private void noOfFlightsRemoveFromTotalCounts(int p1, int currentFlightBookingNumber) throws SQLException, java.lang.ClassNotFoundException {
        Statement statement2 = MysqlConnection.getMysql().createStatement();
        String sql2 = "update flight "
                + "set availableNoofSeats=" + p1 + " "
                + "where flightNumber=" + currentFlightBookingNumber + " ";
        statement2.executeUpdate(sql2);
        MysqlConnection.conClose();
    }

    private void noOfFlightsRemoveFromIndivdualCounts(int p, int currentFlightBookingNumber) throws SQLException, java.lang.ClassNotFoundException {
        Statement statement1 = MysqlConnection.getMysql().createStatement();
        String sql1 = "update flight "
                + "set generalBusinessSeats=" + p + " "
                + "where flightNumber=" + currentFlightBookingNumber + " ";
        statement1.executeUpdate(sql1);
        MysqlConnection.conClose();
    }
    public void continueBoooking ( int currentFlightBookingNumber) throws SQLException, ClassNotFoundException {
                    System.out.println("if you want continue booking press 1 else press any button to exit booking");
                    String string = sc.next();
                    if (string.equals("1")) {
                        selectTypeOfSeat(currentFlightBookingNumber);
                        new ViewOptions().printOptions();
                    } else {
                        new ViewOptions().printOptions();
                    }
                     MysqlConnection.conClose();
    }
}