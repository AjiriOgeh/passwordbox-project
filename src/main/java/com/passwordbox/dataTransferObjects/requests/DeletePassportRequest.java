package com.passwordbox.dataTransferObjects.requests;

import lombok.Data;

@Data
public class DeletePassportRequest {
    private String username;
    private String title;
    private String masterPassword;
}
