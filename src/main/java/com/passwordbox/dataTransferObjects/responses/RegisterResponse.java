package com.passwordbox.dataTransferObjects.responses;

import lombok.Data;

@Data
public class RegisterResponse {
    private String id;
    private String dateOfRegistration;
    private String username;

}
