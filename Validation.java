package com.flight;
import java.util.*;
public class Validation {
Scanner sc=new Scanner(System.in);
    public static boolean checkValidationNumber(String s) {

        if (s.equals("101") || s.equals("102"))
            return true;
        else
            return false;
    }

    public String checkSeatsRequiredNumber() {
        System.out.println("Enter no of seats required");
        String noOfSeatsReq = sc.next();
        if (noOfSeatsReq.equals("1") || noOfSeatsReq.equals("2") || noOfSeatsReq.equals("3") || noOfSeatsReq.equals("4") || noOfSeatsReq.equals("5") || noOfSeatsReq.equals("6")||noOfSeatsReq.equals("7")||noOfSeatsReq.equals("8")||noOfSeatsReq.equals("9")||noOfSeatsReq.equals("10"))
            return noOfSeatsReq;
         else
             return null;


    }
}
