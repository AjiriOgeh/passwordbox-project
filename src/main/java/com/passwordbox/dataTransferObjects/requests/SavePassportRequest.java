package com.passwordbox.dataTransferObjects.requests;

import lombok.Data;

@Data
public class SavePassportRequest {
    private String username;
    private String title;
    private String surname;
    private String givenNames;
    private String passportNumber;
    private String issueDate;
    private String expiryDate;
}
