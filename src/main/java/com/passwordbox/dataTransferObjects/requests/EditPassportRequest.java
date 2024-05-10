package com.passwordbox.dataTransferObjects.requests;

import lombok.Data;

@Data
public class EditPassportRequest {
    private String username;
    private String title;
    private String editedTitle;
    private String editSurname;
    private String editedGivenNames;
    private String editedPassportNumber;
    private String editedIssueDate;
    private String editedExpiryDate;
}
