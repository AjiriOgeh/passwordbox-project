package com.passwordbox.utilities;

import com.passwordbox.dataTransferObjects.requests.EditPassportRequest;
import com.passwordbox.exceptions.InvalidPassportDateException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class PassportDatesValidation {
    private static DateTimeFormatter format = DateTimeFormatter.ofPattern("d/M/yyyy");

    public static boolean isDateValid(String date) {

        try {
            LocalDate.parse(date, format);
            return true;
        }
        catch (Exception error) {
            return false;
        }
    }

    public static void validatePassportDates(String issueDate, String expiryDate) {
        if (!isDateValid(issueDate)) throw new InvalidPassportDateException("Please enter a valid passport issue date with format - d/m/yyyy");
        if (!isDateValid(expiryDate)) throw new InvalidPassportDateException("Please enter a valid passport expiry date with format - d/m/yyyy");
        if (!hasIssueDateOccurred(issueDate)) throw new InvalidPassportDateException("Issue date is invalid. Please enter a valid date.");
        if (!isDifferenceBetweenYearsValid(issueDate, expiryDate)) throw new InvalidPassportDateException("Issue or Expiry date is invalid. Please enter a valid date.");
    }

    public static boolean isDifferenceBetweenYearsValid(String passportIssueDate, String passportExpiryDate) {
        LocalDate issueDate = LocalDate.parse(passportIssueDate, format);
        LocalDate expiryDate = LocalDate.parse(passportExpiryDate, format);

        Period periodBetweenDates = Period.between(issueDate, expiryDate);
        System.out.println(periodBetweenDates.getYears());
        return periodBetweenDates.getYears() >= 4 && periodBetweenDates.getYears() <= 10;
    }

    public static boolean hasIssueDateOccurred(String passportIssueDate){
        LocalDate issueDate = LocalDate.parse(passportIssueDate, format);
        LocalDate presentDate = LocalDate.now();

        long daysInBetween = ChronoUnit.DAYS.between(issueDate, presentDate);

        if (daysInBetween > 0) return true;
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isDifferenceBetweenYearsValid("5/4/2022", "4/4/2028"));

    }

    public static void validateEditedIssueAndExpiryDateFields(String issueDate, String expiryDate) {
        if (issueDate == null && expiryDate !=null) throw new InvalidPassportDateException("Please enter a valid input for both the issue and expiry date.");
        if (issueDate != null && expiryDate ==null) throw new InvalidPassportDateException("Please enter a valid input for both the issue and expiry date.");

    }

    public static boolean areEditedIssueAndExpiryDateFilled(EditPassportRequest editPassportRequest) {
        if (editPassportRequest.getEditedIssueDate() != null && editPassportRequest.getEditedExpiryDate() != null) return true;
        return false;
    }

    public static void validateEditPassportDatesInputs(EditPassportRequest editPassportRequest) {
        validateEditedIssueAndExpiryDateFields(editPassportRequest.getEditedIssueDate(), editPassportRequest.getEditedExpiryDate());
        validatePassportDates(editPassportRequest.getEditedIssueDate(), editPassportRequest.getEditedExpiryDate());
    }




}
