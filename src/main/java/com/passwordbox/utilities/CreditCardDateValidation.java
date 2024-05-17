package com.passwordbox.utilities;

import com.passwordbox.dataTransferObjects.requests.EditCreditCardRequest;
import com.passwordbox.exceptions.DateMonthException;

import java.time.LocalDate;

public class CreditCardDateValidation {

    public static void validateExpiryDate(String month, String year) {
        validateMonth(month);
        validateYear(year, month);
    }

    public static void validateMonth(String month) {
        if (!month.matches("\\d+")) throw new DateMonthException("Please enter a valid numeric character month input.");
        if (!doesMonthExist(month)) throw new DateMonthException("Please enter a valid corresponding month number.");
    }

    public static boolean doesMonthExist(String month) {
        if (Integer.parseInt(month) >= 1 && Integer.parseInt(month) <= 12) return true;
        return false;
    }

    public static void validateYear(String year, String month) {
        int currentYear = LocalDate.now().getYear();
        if (!year.matches("\\d+")) throw new DateMonthException("Please enter a valid numeric character year input.");
        if (isYearInThePast(year, currentYear)) throw new DateMonthException("CreditCard has expired. Please enter a valid creditcard.");
        if (currentYear == Integer.parseInt(year)) {
            if(isMonthInThePast(month)) throw new DateMonthException("CreditCard has expired. Please enter a valid creditcard.");
        }
        if (hasYearExceedExpirationLimit(year, currentYear)) throw new DateMonthException("Please enter the valid year on your creditcard.");
    }

    public static boolean isYearInThePast(String year, int currentYear) {
        if (Integer.parseInt(year) < currentYear) return true;
        return false;
    }

    public static boolean isMonthInThePast(String month) {
        int currentMonth = LocalDate.now().getMonthValue();
        if (Integer.parseInt(month) < currentMonth) return true;
        return false;
    }

    public static boolean hasYearExceedExpirationLimit(String year, int currentYear){
        if (Integer.parseInt(year) >= (currentYear + 10)) return true;
        return false;
    }

    public static void validateEditedExpiryMonthAndYearFields(String month, String year){
        if (month == null && year !=null) throw new DateMonthException("Please enter a valid input for both month and year.");
        if (month != null && year == null) throw new DateMonthException("Please enter a valid input for both month and year.");
    }

    public static boolean areEditedExpiryMonthAndYearFieldsWithValues(EditCreditCardRequest editCreditCardRequest) {
        if (editCreditCardRequest.getEditedExpiryMonth() != null && editCreditCardRequest.getEditedExpiryYear() != null) return true;
        return false;
    }

}