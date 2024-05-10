package com.passwordbox.dataTransferObjects.requests;

import lombok.Data;

@Data
public class DeleteLoginInfoRequest {
    private String username;
    private String title;
    private String masterPassword;
}
