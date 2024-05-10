package com.passwordbox.dataTransferObjects.responses;

import lombok.Data;

@Data
public class ViewLoginInfoResponse {
    private String id;
    private String title;
    private String loginId;
    private String website;
    private String password;

}
