package com.dot.ai.configs;

import java.time.Year;

public class Utility {

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public static double calculateTransactionFee(double amount) {
        double txnFee = amount * 0.05;
        if (txnFee > 100) {
            return 100;
        } else return txnFee;
    }

    public static double commissionFee(double txnFee) {
        return txnFee * 0.02;
    }

    public static String generateAccountNumber() {
        Year thisYear = Year.now();

        int minNumber = 100000;
        int maxNumber = 999999;

//  Generate a 6 digit number from between our min and max numbers

        int randomNumber = (int) Math.floor(Math.random() * (maxNumber - minNumber + 1) + minNumber);

//  convert the year and the rsndom number generated to a string and concatenate together to get the account number
        String year = String.valueOf(thisYear);
        String randomNumberString = String.valueOf(randomNumber);

//        Append both year and randomNumberString  values together using the stringbuilder

        StringBuilder accountNumberBuilder = new StringBuilder();
        return accountNumberBuilder.append(thisYear).append(randomNumberString).toString();
    }
}
