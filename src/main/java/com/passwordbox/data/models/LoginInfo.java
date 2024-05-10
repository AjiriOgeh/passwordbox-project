package com.passwordbox.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("LoginInfos")
public class LoginInfo {
    @Id
    private String id;
    private String title;
    private String website;
    private String loginId;
    private String password;
}
