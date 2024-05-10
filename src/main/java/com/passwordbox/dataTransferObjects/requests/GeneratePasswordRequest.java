package com.passwordbox.dataTransferObjects.requests;

import lombok.Data;

@Data
public class GeneratePasswordRequest {
    private boolean upperCaseCharacters;
    private boolean lowerCaseCharacters;
    private boolean numericCharacters;
    private boolean specialCharacters;
    private String length;
}
