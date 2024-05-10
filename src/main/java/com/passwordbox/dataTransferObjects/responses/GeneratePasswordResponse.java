package com.passwordbox.dataTransferObjects.responses;

import lombok.Data;

@Data
public class GeneratePasswordResponse {
    private String password;
    private int length;
}
