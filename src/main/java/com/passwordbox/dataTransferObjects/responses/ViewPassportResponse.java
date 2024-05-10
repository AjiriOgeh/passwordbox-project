package com.passwordbox.dataTransferObjects.responses;

import lombok.Data;

@Data
public class ViewPassportResponse {
    private String id;
    private String title;
    private String surname;
    private String givenNames;
    private String passportNumber;
    private String issueDate;
    private String expiryDate;
}
