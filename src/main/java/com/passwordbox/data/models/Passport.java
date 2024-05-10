package com.passwordbox.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Passports")
public class Passport {
    @Id
    private String id;
    private String title;
    private String surname;
    private String givenNames;
    private String passportNumber;
    private String issueDate;
    private String expiryDate;
}
