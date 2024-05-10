package com.passwordbox.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Data
public class Vault {
    @DBRef
    private List<LoginInfo> loginInfos = new ArrayList<>();
    @DBRef
    private List<CreditCard> creditCards = new ArrayList<>();
    @DBRef
    private List<Passport> passports = new ArrayList<>();
 }