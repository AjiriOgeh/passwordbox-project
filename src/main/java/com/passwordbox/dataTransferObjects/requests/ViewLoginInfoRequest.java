package com.passwordbox.dataTransferObjects.requests;

import lombok.Data;

@Data
public class ViewLoginInfoRequest {
    private String username;
    private String title;
}
