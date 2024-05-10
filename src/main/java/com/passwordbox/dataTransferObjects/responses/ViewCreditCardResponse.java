package com.passwordbox.dataTransferObjects.responses;

import com.passwordbox.data.models.CreditCardType;
import lombok.Data;

@Data
public class ViewCreditCardResponse {
    private String id;
    private String title;
    private String cardNumber;
    private String cvv;
    private String pin;
    private CreditCardType creditCardType;
    private String expiryDate;
}
