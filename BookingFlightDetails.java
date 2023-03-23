package com.flight;

public class BookingFlightDetails {

    private int flightNum;
    private int generalBusinessSeat;
    private int generalEconomySeat;
    private int takkalBusinessSeat;
    private int takkalEconomySeat;
    private boolean meals;
    private boolean citizen;
    private double totalAmount;
    private java.lang.String typeOfSeat;

    public java.lang.String getTypeOfSeat() {
        return typeOfSeat;
    }

    public void setTypeOfSeat(java.lang.String typeOfSeat) {
        this.typeOfSeat = typeOfSeat;
    }

    public void setMeals(boolean meals) {
        this.meals = meals;
    }
    public boolean isMeals() {
        return meals;
    }
    public int getGeneralBusinessSeat() {
        return generalBusinessSeat;
    }

    public void setGeneralBusinessSeat(int generalBusinessSeat) {
        this.generalBusinessSeat = generalBusinessSeat;
    }

    public int getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(int flightNum) {
        this.flightNum = flightNum;
    }

    public boolean isCitizen() {
        return citizen;
    }

    public void setCitizen(boolean citizen) {
        this.citizen = citizen;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }



    public int getTakkalEconomySeat() {
        return takkalEconomySeat;
    }

    public void setTakkalEconomySeat(int takkalEconomySeat) {
        this.takkalEconomySeat = takkalEconomySeat;
    }

    public int getTakkalBusinessSeat() {
        return takkalBusinessSeat;
    }

    public void setTakkalBusinessSeat(int takkalBusinessSeat) {
        this.takkalBusinessSeat = takkalBusinessSeat;
    }



    public int getGeneralEconomySeat() {
        return generalEconomySeat;
    }

    public void setGeneralEconomySeat(int generalEconomySeat) {
        this.generalEconomySeat = generalEconomySeat;
    }
}
