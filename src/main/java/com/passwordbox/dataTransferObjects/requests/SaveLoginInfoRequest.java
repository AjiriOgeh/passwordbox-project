package com.passwordbox.dataTransferObjects.requests;

import lombok.Data;

@Data
public class SaveLoginInfoRequest {
    private String username;
    private String title;
    private String website;
    private String loginId;
    private String password;
}
