package com.passwordbox.utilities;

import com.passwordbox.data.models.*;
import com.passwordbox.dataTransferObjects.requests.*;
import com.passwordbox.dataTransferObjects.responses.*;

import java.time.format.DateTimeFormatter;

import static com.passwordbox.utilities.CreditCardDateValidation.areEditedExpiryMonthAndYearFilled;
import static com.passwordbox.utilities.CreditCardValidator.determineCreditCardType;

public class Mappers {
    public static User registerRequestMap(SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername().toLowerCase());
        user.setMasterPassword(signUpRequest.getMasterPassword());
        return user;
    }

    public static RegisterResponse registerResponseMap(User user) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(user.getId());
        registerResponse.setUsername(user.getUsername());
        registerResponse.setDateOfRegistration(user.getDateOfRegistration().format(DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss")));
        return registerResponse;
    }

    public static LogoutResponse logoutResponseMap(User user) {
        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setId(user.getId());
        logoutResponse.setUsername(user.getUsername());
        return logoutResponse;
    }

    public static LoginResponse loginResponseMap(User user) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(user.getId());
        loginResponse.setUsername(user.getUsername());
        return loginResponse;
    }

    public static LoginInfo saveNewLoginInfoRequestMap(SaveLoginInfoRequest saveLoginInfoRequest) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setTitle(saveLoginInfoRequest.getTitle().toLowerCase());
        loginInfo.setWebsite(saveLoginInfoRequest.getWebsite());
        loginInfo.setLoginId(saveLoginInfoRequest.getLoginId());
        loginInfo.setPassword(DataCypher.encryptData(saveLoginInfoRequest.getPassword()));
        return loginInfo;
    }

    public static SaveLoginInfoResponse saveNewLoginInfoResponseMap(LoginInfo loginInfo){
        SaveLoginInfoResponse saveLoginInfoResponse = new SaveLoginInfoResponse();
        saveLoginInfoResponse.setId(loginInfo.getId());
        saveLoginInfoResponse.setTitle(loginInfo.getTitle().toLowerCase());
        return saveLoginInfoResponse;
    }

    public static LoginInfo editLoginInfoRequestMap(EditLoginInfoRequest editLoginInfoRequest, LoginInfo loginInfo) {
        if (editLoginInfoRequest.getEditedTitle() != null) loginInfo.setTitle(editLoginInfoRequest.getEditedTitle().toLowerCase());
        if (editLoginInfoRequest.getEditedLoginId() != null) loginInfo.setLoginId(editLoginInfoRequest.getEditedLoginId());
        if (editLoginInfoRequest.getEditedWebsite() != null) loginInfo.setWebsite(editLoginInfoRequest.getEditedWebsite());
        if (editLoginInfoRequest.getEditedPassword() != null) loginInfo.setPassword(DataCypher.encryptData(editLoginInfoRequest.getEditedPassword()));
        return loginInfo;
    }

    public static EditLoginInfoResponse editLoginInfoResponseMap(LoginInfo loginInfo){
        EditLoginInfoResponse editLoginInfoResponse = new EditLoginInfoResponse();
        editLoginInfoResponse.setId(loginInfo.getId());
        editLoginInfoResponse.setTitle(loginInfo.getTitle());
        return editLoginInfoResponse;
    }

    public static ViewLoginInfoResponse viewLoginInfoResponseMap(LoginInfo loginInfo){
        ViewLoginInfoResponse viewLoginInfoResponse = new ViewLoginInfoResponse();
        viewLoginInfoResponse.setId(loginInfo.getId());
        viewLoginInfoResponse.setTitle(loginInfo.getTitle());
        viewLoginInfoResponse.setWebsite(loginInfo.getWebsite());
        viewLoginInfoResponse.setLoginId(loginInfo.getLoginId());
        viewLoginInfoResponse.setPassword(DataCypher.decryptData(loginInfo.getPassword()));
        return viewLoginInfoResponse;
    }

    public static DeleteLoginInfoResponse deleteLoginInfoResponseMap(LoginInfo loginInfo) {
        DeleteLoginInfoResponse deleteLoginInfoResponse = new DeleteLoginInfoResponse();
        deleteLoginInfoResponse.setId(loginInfo.getId());
        deleteLoginInfoResponse.setTitle(loginInfo.getTitle());
        return deleteLoginInfoResponse;
    }

    public static GeneratePasswordResponse generatePasswordResponseMap(String password) {
        GeneratePasswordResponse generatePasswordResponse = new GeneratePasswordResponse();
        generatePasswordResponse.setPassword(password);
        generatePasswordResponse.setLength(password.length());
        return generatePasswordResponse;
    }

    public static CreditCard saveCreditCardRequestMap(SaveCreditCardRequest saveCreditCardRequest) {
        CreditCard creditCard = new CreditCard();
        creditCard.setTitle(saveCreditCardRequest.getTitle().toLowerCase());
        creditCard.setCardNumber(DataCypher.encryptData(saveCreditCardRequest.getCardNumber()));
        creditCard.setCvv(DataCypher.encryptData(saveCreditCardRequest.getCvv()));
        creditCard.setPin(DataCypher.encryptData(saveCreditCardRequest.getPin()));
        creditCard.setCreditCardType(determineCreditCardType(saveCreditCardRequest.getCardNumber()));
        creditCard.setExpiryDate(getExpiryDate(saveCreditCardRequest.getExpiryMonth(), saveCreditCardRequest.getExpiryYear()));
        return creditCard;
    }

    private static String getExpiryDate(String month, String year) {
        return month + "/" + year;
    }

    public static SaveCreditCardResponse saveCreditCardResponseMap(CreditCard creditCard) {
        SaveCreditCardResponse saveCreditCardResponse = new SaveCreditCardResponse();
        saveCreditCardResponse.setId(creditCard.getId());
        saveCreditCardResponse.setTitle(creditCard.getTitle());
        return saveCreditCardResponse;
    }

    public static CreditCard editCreditCardRequestMap(EditCreditCardRequest editCreditCardRequest, CreditCard creditCard) {
        if (editCreditCardRequest.getEditedTitle() != null) creditCard.setTitle(editCreditCardRequest.getEditedTitle().toLowerCase());
        if (editCreditCardRequest.getEditedCardNumber() != null) creditCard.setCardNumber(DataCypher.encryptData(editCreditCardRequest.getEditedCardNumber()));
        if (editCreditCardRequest.getEditedCVV() != null) creditCard.setCvv(DataCypher.encryptData(editCreditCardRequest.getEditedCVV()));
        if (editCreditCardRequest.getEditedPin() != null) creditCard.setPin(DataCypher.encryptData(editCreditCardRequest.getEditedPin()));
        if (editCreditCardRequest.getEditedCardNumber() != null) creditCard.setCreditCardType(determineCreditCardType(editCreditCardRequest.getEditedCardNumber()));
        if (areEditedExpiryMonthAndYearFilled(editCreditCardRequest)) creditCard.setExpiryDate(getExpiryDate(editCreditCardRequest.getEditedExpiryMonth(), editCreditCardRequest.getEditedExpiryYear()));
        return creditCard;
    }

    public static EditCreditCardResponse editCreditCardResponseMap(CreditCard creditCard) {
        EditCreditCardResponse editCreditCardResponse = new EditCreditCardResponse();
        editCreditCardResponse.setId(creditCard.getId());
        editCreditCardResponse.setTitle(creditCard.getTitle());
        return editCreditCardResponse;
    }

    public static ViewCreditCardResponse viewCreditCardResponseMap(CreditCard creditCard) {
        ViewCreditCardResponse viewCreditCardResponse = new ViewCreditCardResponse();
        viewCreditCardResponse.setId(creditCard.getId());
        viewCreditCardResponse.setTitle(creditCard.getTitle());
        viewCreditCardResponse.setCardNumber(DataCypher.decryptData(creditCard.getCardNumber()));
        viewCreditCardResponse.setCvv(DataCypher.decryptData(creditCard.getCvv()));
        viewCreditCardResponse.setPin(DataCypher.decryptData(creditCard.getPin()));
        viewCreditCardResponse.setCreditCardType(creditCard.getCreditCardType());
        viewCreditCardResponse.setExpiryDate(creditCard.getExpiryDate());
        return viewCreditCardResponse;
    }

    public static DeleteCreditCardResponse deleteCreditCardResponseMap(CreditCard creditCard) {
        DeleteCreditCardResponse deleteCreditCardResponse = new DeleteCreditCardResponse();
        deleteCreditCardResponse.setId(creditCard.getId());
        deleteCreditCardResponse.setTitle(creditCard.getTitle());
        return deleteCreditCardResponse;
    }

    public static Passport savePassportRequestMap(SavePassportRequest savePassportRequest) {
        Passport passport = new Passport();
        passport.setTitle(savePassportRequest.getTitle().toLowerCase());
        passport.setSurname(savePassportRequest.getSurname());
        passport.setGivenNames(savePassportRequest.getGivenNames());
        passport.setPassportNumber(DataCypher.encryptData(savePassportRequest.getPassportNumber()));
        passport.setIssueDate(savePassportRequest.getIssueDate());
        passport.setExpiryDate(savePassportRequest.getExpiryDate());
        return passport;
    }

    public static SavePassportResponse savePassportResponseMap(Passport passport) {
        SavePassportResponse savePassportResponse = new SavePassportResponse();
        savePassportResponse.setId(passport.getId());
        savePassportResponse.setTitle(passport.getTitle());
        return savePassportResponse;
    }

    public static Passport editPassportRequestMap(EditPassportRequest editPassportRequest, Passport passport) {
        if (editPassportRequest.getEditedTitle() != null) passport.setTitle(editPassportRequest.getEditedTitle().toLowerCase());
        if (editPassportRequest.getEditSurname() != null) passport.setSurname(editPassportRequest.getEditSurname());
        if (editPassportRequest.getEditedGivenNames() != null) passport.setGivenNames(editPassportRequest.getEditedGivenNames());
        if (editPassportRequest.getEditedPassportNumber() != null) passport.setPassportNumber(DataCypher.encryptData(editPassportRequest.getEditedPassportNumber()));
        if (editPassportRequest.getEditedIssueDate() != null) passport.setIssueDate(editPassportRequest.getEditedIssueDate());
        if (editPassportRequest.getEditedExpiryDate() != null) passport.setExpiryDate(editPassportRequest.getEditedExpiryDate());
        return passport;
    }

    public static EditPassportResponse editPassportResponseMap(Passport passport) {
        EditPassportResponse editPassportResponse = new EditPassportResponse();
        editPassportResponse.setId(passport.getId());
        editPassportResponse.setTitle(passport.getTitle());
        return editPassportResponse;
    }

    public static ViewPassportResponse viewPassportResponseMap(Passport passport) {
        ViewPassportResponse viewPassportResponse = new ViewPassportResponse();
        viewPassportResponse.setId(passport.getId());
        viewPassportResponse.setTitle(passport.getTitle());
        viewPassportResponse.setSurname(passport.getSurname());
        viewPassportResponse.setGivenNames(passport.getGivenNames());
        viewPassportResponse.setPassportNumber(DataCypher.decryptData(passport.getPassportNumber()));
        viewPassportResponse.setIssueDate(passport.getIssueDate());
        viewPassportResponse.setExpiryDate(passport.getExpiryDate());
        return viewPassportResponse;
    }

    public static DeletePassportResponse deletePassportResponseMap(Passport passport) {
        DeletePassportResponse deletePassportResponse = new DeletePassportResponse();
        deletePassportResponse.setId(passport.getId());
        deletePassportResponse.setTitle(passport.getTitle());
        return deletePassportResponse;
    }

}
