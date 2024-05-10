package com.passwordbox.dataTransferObjects.requests;

import com.passwordbox.data.models.CreditCardType;
import lombok.Data;

@Data
public class EditCreditCardRequest {
    private String username;
    private String title;
    private String editedTitle;
    private String editedCardNumber;
    private String editedCVV;
    private String editedPin;
    private String editedExpiryMonth;
    private String editedExpiryYear;
}
