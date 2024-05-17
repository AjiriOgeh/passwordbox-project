package com.passwordbox.dataTransferObjects.requests;

import lombok.Data;

@Data
public class GeneratePasswordRequest {
    private String uppercaseCharactersChoice;
    private String lowercaseCharactersChoice;
    private String numericCharactersChoice;
    private String specialCharactersChoice;
    private String length;
}
