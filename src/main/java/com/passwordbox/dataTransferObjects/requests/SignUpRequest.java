package com.passwordbox.dataTransferObjects.requests;

import lombok.Data;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String masterPassword;
}
