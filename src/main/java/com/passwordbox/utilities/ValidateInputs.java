package com.passwordbox.utilities;

import com.passwordbox.dataTransferObjects.requests.SaveCreditCardRequest;
import com.passwordbox.dataTransferObjects.requests.SaveLoginInfoRequest;
import com.passwordbox.exceptions.NullFieldException;

import java.net.HttpURLConnection;
import java.net.URL;

import static com.passwordbox.utilities.CreditCardValidator.isCreditCardInvalid;

public class ValidateInputs {

    public static void validateLoginID(String loginID) {
        if (loginID == null) throw new NullFieldException("Login ID cannot be null. Please enter a valid login ID");
    }

    public static void validateWebsite(String website){
        if (!doesWebsiteExist(website))throw new IllegalArgumentException(String.format("%s does not exist. Please enter a valid website", website));
    }

    public static boolean doesWebsiteExist(String website) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(website);
            connection =  (HttpURLConnection) url.openConnection();
            connection.setRequestMethod ("HEAD");
            connection.connect();
            return true;
        }
        catch (Exception error) {
            return false;
        }
    }

    public static void validateCreditCardFields(SaveCreditCardRequest saveCreditCardRequest) {
        if (saveCreditCardRequest.getCardNumber() == null) throw new NullFieldException("Card number field cannot be null. Please enter a valid card number.");
        if (saveCreditCardRequest.getCvv() == null) throw new NullFieldException("CVV field cannot be null. Please enter a valid cvv.");
        if (saveCreditCardRequest.getPin() == null) throw new NullFieldException("PIN field cannot be null. Please enter a valid PIN.");
        if (saveCreditCardRequest.getExpiryMonth() == null) throw new NullFieldException("Expiry month cannot be null. Please enter a valid numeric month value.");
        if (saveCreditCardRequest.getExpiryYear() == null) throw new NullFieldException("Expiry year cannot be null. Please enter a valid numeric year value.");
    }

    public static void validateCreditCardNumber(String cardNumber) {
        if (cardNumber!= null && !cardNumber.matches("\\d{13,19}")) throw new IllegalArgumentException("Please enter a valid card number.");
        if (cardNumber != null && isCreditCardInvalid(cardNumber)) throw new IllegalArgumentException("Please enter a valid card number.");
    }

    public static void validateCreditCardPin(String creditCardPin) {
        if (creditCardPin!= null && !creditCardPin.matches("\\d{3,15}")) throw new IllegalArgumentException("Please enter a valid PIN.");
    }

    public static void validateCreditCardCVV(String cvv) {
        if (cvv != null && !cvv.matches("\\d{3,7}")) throw new IllegalArgumentException("Please enter a valid cvv.");
    }

    public static void validatePassportNumberField(String passportNumber) {
        if (passportNumber == null) throw new NullFieldException("Passport number field cannot be null. Please enter a valid passport number.");
        if (passportNumber.isEmpty()) throw new IllegalArgumentException("Passport number field cannot be empty. Please enter a valid passport number.");
    }

}