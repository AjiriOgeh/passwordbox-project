package com.passwordbox.dataTransferObjects.requests;

import lombok.Data;

@Data
public class EditLoginInfoRequest {
    private String username;
    private String title;
    private String editedTitle;
    private String editedWebsite;
    private String editedLoginId;
    private String editedPassword;
}
