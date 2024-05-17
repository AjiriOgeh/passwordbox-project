package com.passwordbox.dataTransferObjects.responses;

import lombok.Data;

@Data
public class SavePassportResponse {
    private String id;
    private String title;
    private String number; // please delete it
}
