package com.passwordbox.utilities;

import com.passwordbox.data.models.CreditCardType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CreditCardValidator {
    private  String creditCardNumber;
    private static long[] creditCardNumbersArray;
    private static long doubleNumbersTotal;
    private static long oddPlaceNumbersTotal;

    public void setCreditCardNumber(String creditCardNumber){
        this.creditCardNumber = creditCardNumber;
    }

    public void setCreditCardNumberArray() {
        long cardNumber = Long.parseLong(creditCardNumber);
        creditCardNumbersArray = new long[creditCardNumber.length()];

        for (int count = creditCardNumber.length() - 1; count >= 0 ; count--) {
            creditCardNumbersArray[count] = cardNumber % 10;
            cardNumber /= 10;
        }
    }

    public void getDoubleDigitsTotal() {
        for (int count = creditCardNumbersArray.length - 2; count >= 0; count -= 2) {
            if ((creditCardNumbersArray[count] * 2) > 9) {
                long splitDigit1 = (creditCardNumbersArray[count] * 2) % 10;
                long splitDigit2 = (creditCardNumbersArray[count] * 2) / 10;
                doubleNumbersTotal += splitDigit1 + splitDigit2;
            }
            else {
                doubleNumbersTotal += creditCardNumbersArray[count] * 2;
            }
        }
    }

    public void getOddPlaceNumbersTotal() {
        for (int count = creditCardNumbersArray.length - 1; count >= 0; count -=2) {
            oddPlaceNumbersTotal += creditCardNumbersArray[count];
        }
    }

    public static boolean getValidityStatus() {
        long total = oddPlaceNumbersTotal + doubleNumbersTotal;
        return total % 10 != 0;
    }

    public static CreditCardType determineCreditCardType(String creditCardNumber){
        if (creditCardNumber.charAt(0) =='4') return CreditCardType.VISA;
        else if (creditCardNumber.charAt(0) == '5') return CreditCardType.MASTERCARD;
        else if (creditCardNumber.charAt(0) == '6') return CreditCardType.DISCOVER;
        else if (creditCardNumber.charAt(0) == '3' && creditCardNumber.charAt(0) == '4') return CreditCardType.AMERICAN_EXPRESS;
        else if (creditCardNumber.charAt(0) == '3' && creditCardNumber.charAt(0) == '7') return CreditCardType.AMERICAN_EXPRESS;
        else return CreditCardType.OTHER;
    }

	public static boolean isCreditCardInvalid(String creditCardNumber) {
        CreditCardValidator creditCardValidator = new CreditCardValidator();
		creditCardValidator.setCreditCardNumber(creditCardNumber);
		creditCardValidator.setCreditCardNumberArray();
		creditCardValidator.getDoubleDigitsTotal();
		creditCardValidator.getOddPlaceNumbersTotal();
        return getValidityStatus();
	}

}