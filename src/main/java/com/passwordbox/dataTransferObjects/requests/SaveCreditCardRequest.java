package com.passwordbox.dataTransferObjects.requests;

import lombok.Data;

@Data
public class SaveCreditCardRequest {
    private String username;
    private String title;
    private String cardNumber;
    private String cvv;
    private String pin;
    private String expiryMonth;
    private String expiryYear;
}
