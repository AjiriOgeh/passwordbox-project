package com.passwordbox.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("CreditCards")
public class CreditCard {
    @Id
    private String id;
    private String title;
    private String cardNumber;
    private String cvv;
    private String pin;
    private CreditCardType creditCardType;
    private String expiryDate;
}
