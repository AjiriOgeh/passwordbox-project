package com.passwordbox.controllers;

import com.passwordbox.data.repositories.CreditCardRepository;
import com.passwordbox.data.repositories.LoginInfoRepository;
import com.passwordbox.data.repositories.PassportRepository;
import com.passwordbox.data.repositories.UserRepository;
import com.passwordbox.dataTransferObjects.requests.*;
import com.passwordbox.dataTransferObjects.responses.DeletePassportResponse;
import com.passwordbox.dataTransferObjects.responses.SavePassportResponse;
import com.passwordbox.dataTransferObjects.responses.ViewPassportResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginInfoRepository loginInfoRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private PassportRepository passportRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();

        loginInfoRepository.deleteAll();

        creditCardRepository.deleteAll();

        passportRepository.deleteAll();

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("jack123");
        signUpRequest.setMasterPassword("Password123.");
        userController.signUp(signUpRequest);

        SaveLoginInfoRequest saveLoginInfoRequest = new SaveLoginInfoRequest();
        saveLoginInfoRequest.setUsername("jack123");
        saveLoginInfoRequest.setTitle("gmail login");
        saveLoginInfoRequest.setWebsite("www.gmail.com");
        saveLoginInfoRequest.setLoginId("jack123@gmail.com");
        saveLoginInfoRequest.setPassword("password.");
        userController.saveNewLoginInfo(saveLoginInfoRequest);

        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("gtb savings card");
        saveCreditCardRequest.setCardNumber("5328930010000034");
        saveCreditCardRequest.setCvv("567");
        saveCreditCardRequest.setPin("1234");
        saveCreditCardRequest.setExpiryMonth("1");
        saveCreditCardRequest.setExpiryYear("2025");
        userController.saveCreditCard(saveCreditCardRequest);

        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle("my british passport");
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("B12345678");
        savePassportRequest.setIssueDate("05/05/2020");
        savePassportRequest.setExpiryDate("04/05/2030");
        userController.savePassport(savePassportRequest);
    }

    @Test
    public void userSignUpTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("jim456");
        signUpRequest.setMasterPassword("Password123.");

        var response = userController.signUp(signUpRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void userSignUp_UsernameIsNullTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername(null);
        signUpRequest.setMasterPassword("Password123.");

        var response = userController.signUp(signUpRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSignUp_UsernameIsEmptyTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("");
        signUpRequest.setMasterPassword("Password123.");

        var response = userController.signUp(signUpRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSignUp_UsernameContains_SpaceCharacterTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("jim 456");
        signUpRequest.setMasterPassword("Password123.");

        var response = userController.signUp(signUpRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSignUp_PasswordFieldIsNullTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("jim456");
        signUpRequest.setMasterPassword(null);

        var response = userController.signUp(signUpRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSignUp_PasswordFieldIsEmptyTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("jim456");
        signUpRequest.setMasterPassword("");

        var response = userController.signUp(signUpRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userLogsOutTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");

        var response = userController.logout(logoutRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void nonExistentUser_LogsOutTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jim456");

        var response = userController.logout(logoutRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void userLogsInTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");

        var response = userController.logout(logoutRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("jack123");
        loginRequest.setPassword("Password123.");

        response = userController.login(loginRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void nonExistentUser_LogsInTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");

        var response = userController.logout(logoutRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("jim456");
        loginRequest.setPassword("Password123.");

        response = userController.login(loginRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userLogsIn_PasswordIsInvalidTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");

        var response = userController.logout(logoutRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("jack123");
        loginRequest.setPassword("word.");

        response = userController.login(loginRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesLoginInfoTest() {
        SaveLoginInfoRequest saveLoginInfoRequest = new SaveLoginInfoRequest();
        saveLoginInfoRequest.setUsername("jack123");
        saveLoginInfoRequest.setTitle("yahoo login");
        saveLoginInfoRequest.setWebsite("www.yahoo.com");
        saveLoginInfoRequest.setLoginId("jack123@yahoo.com");
        saveLoginInfoRequest.setPassword("password.");

        var response = userController.saveNewLoginInfo(saveLoginInfoRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void nonExistentUser_SavesLoginInfoTest() {
        SaveLoginInfoRequest saveLoginInfoRequest = new SaveLoginInfoRequest();
        saveLoginInfoRequest.setUsername("jim456");
        saveLoginInfoRequest.setTitle("yahoo login");
        saveLoginInfoRequest.setWebsite("www.yahoo.com");
        saveLoginInfoRequest.setLoginId("jack123@yahoo.com");
        saveLoginInfoRequest.setPassword("password.");

        var response = userController.saveNewLoginInfo(saveLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userLogsOut_SavesLoginInfoTest(){
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");

        var response = userController.logout(logoutRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        SaveLoginInfoRequest saveLoginInfoRequest = new SaveLoginInfoRequest();
        saveLoginInfoRequest.setUsername("jack123");
        saveLoginInfoRequest.setTitle("yahoo login");
        saveLoginInfoRequest.setWebsite("www.yahoo.com");
        saveLoginInfoRequest.setLoginId("jack123@yahoo.com");
        saveLoginInfoRequest.setPassword("password.");

        response = userController.saveNewLoginInfo(saveLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesLoginInfo_TitleIsNullTest() {
        SaveLoginInfoRequest saveLoginInfoRequest = new SaveLoginInfoRequest();
        saveLoginInfoRequest.setUsername("jack123");
        saveLoginInfoRequest.setTitle(null);
        saveLoginInfoRequest.setWebsite("www.gmail.com");
        saveLoginInfoRequest.setLoginId("jack123@gmail.com");
        saveLoginInfoRequest.setPassword("password.");

        var response = userController.saveNewLoginInfo(saveLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesLoginInfo_TitleIsEmptyTest() {
        SaveLoginInfoRequest saveLoginInfoRequest = new SaveLoginInfoRequest();
        saveLoginInfoRequest.setUsername("jack123");
        saveLoginInfoRequest.setTitle(null);
        saveLoginInfoRequest.setWebsite("www.gmail.com");
        saveLoginInfoRequest.setLoginId("jack123@gmail.com");
        saveLoginInfoRequest.setPassword("password.");

        var response = userController.saveNewLoginInfo(saveLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesLoginInfo_TitleExists_InUsersListTest() {
        SaveLoginInfoRequest saveLoginInfoRequest = new SaveLoginInfoRequest();
        saveLoginInfoRequest.setUsername("jack123");
        saveLoginInfoRequest.setTitle("gmail login");
        saveLoginInfoRequest.setWebsite("www.gmail.com");
        saveLoginInfoRequest.setLoginId("jack123@gmail.com");
        saveLoginInfoRequest.setPassword("password.");

        var response = userController.saveNewLoginInfo(saveLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userEditsLoginInfoTest() {
        EditLoginInfoRequest editLoginInfoRequest = new EditLoginInfoRequest();
        editLoginInfoRequest.setUsername("jack123");
        editLoginInfoRequest.setTitle("gmail login");
        editLoginInfoRequest.setEditedTitle("yahoo login");
        editLoginInfoRequest.setEditedWebsite("www.yahoo.com");
        editLoginInfoRequest.setEditedLoginId("jack123@yahoo.com");
        editLoginInfoRequest.setEditedPassword("word.");

        var response = userController.editLoginInfo(editLoginInfoRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void nonExistentUser_EditsLoginInfoTest() {
        EditLoginInfoRequest editLoginInfoRequest = new EditLoginInfoRequest();
        editLoginInfoRequest.setUsername("jim456");
        editLoginInfoRequest.setTitle("gmail login");
        editLoginInfoRequest.setEditedTitle("yahoo login");
        editLoginInfoRequest.setEditedWebsite("www.yahoo.com");
        editLoginInfoRequest.setEditedLoginId("jack123@yahoo.com");
        editLoginInfoRequest.setEditedPassword("word.");

        var response = userController.editLoginInfo(editLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userLogsOut_EditsLoginInfoTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");

        var response = userController.logout(logoutRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        EditLoginInfoRequest editLoginInfoRequest = new EditLoginInfoRequest();
        editLoginInfoRequest.setUsername("jack123");
        editLoginInfoRequest.setTitle("gmail login");
        editLoginInfoRequest.setEditedTitle("yahoo login");
        editLoginInfoRequest.setEditedWebsite("www.yahoo.com");
        editLoginInfoRequest.setEditedLoginId("jack123@yahoo.com");
        editLoginInfoRequest.setEditedPassword("word.");

        response = userController.editLoginInfo(editLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userEdits_NonExistentLoginInfoTest() {
        EditLoginInfoRequest editLoginInfoRequest = new EditLoginInfoRequest();
        editLoginInfoRequest.setUsername("jack123");
        editLoginInfoRequest.setTitle("hotmail login");
        editLoginInfoRequest.setEditedTitle("yahoo login");
        editLoginInfoRequest.setEditedWebsite("www.yahoo.com");
        editLoginInfoRequest.setEditedLoginId("jack123@yahoo.com");
        editLoginInfoRequest.setEditedPassword("word.");

        var response = userController.editLoginInfo(editLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userEditsLoginInfo_EditedTitleIsInvalidTest() {
        EditLoginInfoRequest editLoginInfoRequest = new EditLoginInfoRequest();
        editLoginInfoRequest.setUsername("jack123");
        editLoginInfoRequest.setTitle("gmail login");
        editLoginInfoRequest.setEditedTitle("");
        editLoginInfoRequest.setEditedWebsite("www.yahoo.com");
        editLoginInfoRequest.setEditedLoginId("jack123@yahoo.com");
        editLoginInfoRequest.setEditedPassword("word.");

        var response = userController.editLoginInfo(editLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userViewsLoginInfoTest(){
        ViewLoginInfoRequest viewLoginInfoRequest = new ViewLoginInfoRequest();
        viewLoginInfoRequest.setUsername("jack123");
        viewLoginInfoRequest.setTitle("gmail login");

        var response = userController.viewLoginInfo(viewLoginInfoRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void nonExistentUser_ViewsLoginInfoTest(){
        ViewLoginInfoRequest viewLoginInfoRequest = new ViewLoginInfoRequest();
        viewLoginInfoRequest.setUsername("jim456");
        viewLoginInfoRequest.setTitle("gmail login");

        var response = userController.viewLoginInfo(viewLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userViews_NonExistentLoginInfoTest(){
        ViewLoginInfoRequest viewLoginInfoRequest = new ViewLoginInfoRequest();
        viewLoginInfoRequest.setUsername("jack123");
        viewLoginInfoRequest.setTitle("yahoo login");

        var response = userController.viewLoginInfo(viewLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userLogsOut_ViewsLoginInfoTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");

        var response = userController.logout(logoutRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ViewLoginInfoRequest viewLoginInfoRequest = new ViewLoginInfoRequest();
        viewLoginInfoRequest.setUsername("jack123");
        viewLoginInfoRequest.setTitle("gmail login");

        response = userController.viewLoginInfo(viewLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userDeletesLoginInfoTest(){
        DeleteLoginInfoRequest deleteLoginInfoRequest = new DeleteLoginInfoRequest();
        deleteLoginInfoRequest.setUsername("jack123");
        deleteLoginInfoRequest.setTitle("gmail login");
        deleteLoginInfoRequest.setMasterPassword("Password123.");

        var response = userController.deleteLoginInfo(deleteLoginInfoRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void nonExistentUser_DeletesLoginInfoTest(){
        DeleteLoginInfoRequest deleteLoginInfoRequest = new DeleteLoginInfoRequest();
        deleteLoginInfoRequest.setUsername("jim456");
        deleteLoginInfoRequest.setTitle("gmail login");
        deleteLoginInfoRequest.setMasterPassword("Password123.");

        var response = userController.deleteLoginInfo(deleteLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userLogsOut_DeletesLoginInfoTest(){
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");

        var response = userController.logout(logoutRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        DeleteLoginInfoRequest deleteLoginInfoRequest = new DeleteLoginInfoRequest();
        deleteLoginInfoRequest.setUsername("jack123");
        deleteLoginInfoRequest.setTitle("gmail login");
        deleteLoginInfoRequest.setMasterPassword("Password123.");

        response = userController.deleteLoginInfo(deleteLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userDeletes_NonExistentLoginInfoTest(){
        DeleteLoginInfoRequest deleteLoginInfoRequest = new DeleteLoginInfoRequest();
        deleteLoginInfoRequest.setUsername("jack123");
        deleteLoginInfoRequest.setTitle("yahoo login");
        deleteLoginInfoRequest.setMasterPassword("Password123.");

        var response = userController.deleteLoginInfo(deleteLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userDeletesLoginInfo_WithInvalidPasswordTest(){
        DeleteLoginInfoRequest deleteLoginInfoRequest = new DeleteLoginInfoRequest();
        deleteLoginInfoRequest.setUsername("jack123");
        deleteLoginInfoRequest.setTitle("yahoo login");
        deleteLoginInfoRequest.setMasterPassword("InvalidPassword123.");

        var response = userController.deleteLoginInfo(deleteLoginInfoRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     *
     * the user generates password should be fileld yp
     */
    @Test
    public void userGeneratesPasswordTest() {
        GeneratePasswordRequest generatePasswordRequest = new GeneratePasswordRequest();
        generatePasswordRequest.setLength("16");

        var response = userController.generatePassword(generatePasswordRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void userGeneratesPassword_LengthIsNotANumberTest() {
        GeneratePasswordRequest generatePasswordRequest = new GeneratePasswordRequest();
        generatePasswordRequest.setLength("A");

        var response = userController.generatePassword(generatePasswordRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesCreditCardTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("zenith savings card");
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        var response = userController.saveCreditCard(saveCreditCardRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void nonExistentUser_SavesCreditCardTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jim456");
        saveCreditCardRequest.setTitle("zenith savings card");
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        var response = userController.saveCreditCard(saveCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userLogsOut_SavesCreditCardTest(){
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");

        var response = userController.logout(logoutRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("zenith savings card");
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        response = userController.saveCreditCard(saveCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesCreditCard_TitleIsNullTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle(null);
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        var response = userController.saveCreditCard(saveCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesCreditCard_TitleIsEmptyTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("zenith savings card");
        saveCreditCardRequest.setCardNumber("");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        var response = userController.saveCreditCard(saveCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesCreditCard_TitleExists_InUsersListTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("gtb savings card");
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        var response = userController.saveCreditCard(saveCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesCreditCard_CreditCardNumber_IsInvalidTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("zenith savings card");
        saveCreditCardRequest.setCardNumber("1234567890");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        var response = userController.saveCreditCard(saveCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesCreditCard_CreditCardCVV_IsInvalidTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("zenith savings card");
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("ABC");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        var response = userController.saveCreditCard(saveCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesCreditCard_CreditCardPin_IsInvalidTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("zenith savings card");
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("ABCD");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        var response = userController.saveCreditCard(saveCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesCreditCard_ExpiryDateIsInvalidTest(){
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("zenith savings card");
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("13");
        saveCreditCardRequest.setExpiryYear("2026");

        var response = userController.saveCreditCard(saveCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userEditsCreditCardTest() {
        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jack123");
        editCreditCardRequest.setTitle("gtb savings card");
        editCreditCardRequest.setEditedTitle("gtb current card");
        editCreditCardRequest.setEditedCardNumber("5328930010502658");
        editCreditCardRequest.setEditedCVV("456");
        editCreditCardRequest.setEditedPin("6543");
        editCreditCardRequest.setEditedExpiryMonth("3");
        editCreditCardRequest.setEditedExpiryYear("2027");

        var response = userController.editCreditCard(editCreditCardRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void nonExistentUser_EditsCreditCardTest() {
        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jim456");
        editCreditCardRequest.setTitle("gtb savings card");
        editCreditCardRequest.setEditedTitle("gtb savings card");
        editCreditCardRequest.setEditedCardNumber("5328930010502658");
        editCreditCardRequest.setEditedCVV("456");
        editCreditCardRequest.setEditedPin("6543");
        editCreditCardRequest.setEditedExpiryMonth("3");
        editCreditCardRequest.setEditedExpiryYear("2027");

        var response = userController.editCreditCard(editCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userEdits_NonExistentCreditCardTest() {
        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jack123");
        editCreditCardRequest.setTitle("zenith savings card");
        editCreditCardRequest.setEditedTitle("gtb savings card");
        editCreditCardRequest.setEditedCardNumber("5328930010502658");
        editCreditCardRequest.setEditedCVV("456");
        editCreditCardRequest.setEditedPin("6543");
        editCreditCardRequest.setEditedExpiryMonth("3");
        editCreditCardRequest.setEditedExpiryYear("2027");

        var response = userController.editCreditCard(editCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userLogsOut_EditsCreditCardTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");

        var response = userController.logout(logoutRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jack123");
        editCreditCardRequest.setTitle("gtb savings card");
        editCreditCardRequest.setEditedTitle("gtb current card");
        editCreditCardRequest.setEditedCardNumber("5328930010502658");
        editCreditCardRequest.setEditedCVV("456");
        editCreditCardRequest.setEditedPin("6543");
        editCreditCardRequest.setEditedExpiryMonth("3");
        editCreditCardRequest.setEditedExpiryYear("2027");

        response = userController.editCreditCard(editCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userEditsCreditCard_EditedTitleIsInvalidTest() {
        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jack123");
        editCreditCardRequest.setTitle("gtb savings card");
        editCreditCardRequest.setEditedTitle("");
        editCreditCardRequest.setEditedCardNumber("5328930010502658");
        editCreditCardRequest.setEditedCVV("456");
        editCreditCardRequest.setEditedPin("6543");
        editCreditCardRequest.setEditedExpiryMonth("3");
        editCreditCardRequest.setEditedExpiryYear("2027");

        var response = userController.editCreditCard(editCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userEditsCreditCard_CreditCardNumberIsInvalidTest() {
        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jack123");
        editCreditCardRequest.setTitle("gtb savings card");
        editCreditCardRequest.setEditedTitle("gtb current card");
        editCreditCardRequest.setEditedCardNumber("1234567890");
        editCreditCardRequest.setEditedCVV("456");
        editCreditCardRequest.setEditedPin("6543");
        editCreditCardRequest.setEditedExpiryMonth("3");
        editCreditCardRequest.setEditedExpiryYear("2027");

        var response = userController.editCreditCard(editCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userEditsCreditCard_CVVIsInvalidTest() {
        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jack123");
        editCreditCardRequest.setTitle("gtb savings card");
        editCreditCardRequest.setEditedTitle("gtb current card");
        editCreditCardRequest.setEditedCardNumber("5328930010502658");
        editCreditCardRequest.setEditedCVV("ABC");
        editCreditCardRequest.setEditedPin("6543");
        editCreditCardRequest.setEditedExpiryMonth("3");
        editCreditCardRequest.setEditedExpiryYear("2027");

        var response = userController.editCreditCard(editCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userEditsCreditCard_PinIsInvalidTest() {
        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jack123");
        editCreditCardRequest.setTitle("gtb savings card");
        editCreditCardRequest.setEditedTitle("gtb current card");
        editCreditCardRequest.setEditedCardNumber("5328930010502658");
        editCreditCardRequest.setEditedCVV("456");
        editCreditCardRequest.setEditedPin("ABCD");
        editCreditCardRequest.setEditedExpiryMonth("3");
        editCreditCardRequest.setEditedExpiryYear("2027");

        var response = userController.editCreditCard(editCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userEditsCreditCard_ExpiryMonthIsInvalidTest() {
        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jack123");
        editCreditCardRequest.setTitle("gtb savings card");
        editCreditCardRequest.setEditedTitle("gtb current card");
        editCreditCardRequest.setEditedCardNumber("5328930010502658");
        editCreditCardRequest.setEditedCVV("456");
        editCreditCardRequest.setEditedPin("6543");
        editCreditCardRequest.setEditedExpiryMonth("2");
        editCreditCardRequest.setEditedExpiryYear("2023");

        var response = userController.editCreditCard(editCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userViewsCreditCardTest() {
        ViewCreditCardRequest viewCreditCardRequest = new ViewCreditCardRequest();
        viewCreditCardRequest.setUsername("jack123");
        viewCreditCardRequest.setTitle("gtb savings card");

        var response = userController.viewCreditCard(viewCreditCardRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void nonExistentUser_ViewsCreditCardTest() {
        ViewCreditCardRequest viewCreditCardRequest = new ViewCreditCardRequest();
        viewCreditCardRequest.setUsername("jim456");
        viewCreditCardRequest.setTitle("gtb savings card");

        var response = userController.viewCreditCard(viewCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userViews_NonExistentCreditCardTest() {
        ViewCreditCardRequest viewCreditCardRequest = new ViewCreditCardRequest();
        viewCreditCardRequest.setUsername("jack123");
        viewCreditCardRequest.setTitle("zenith savings card");

        var response = userController.viewCreditCard(viewCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userDeletesCreditCardTest() {
        DeleteCreditCardRequest deleteCreditCardRequest = new DeleteCreditCardRequest();
        deleteCreditCardRequest.setUsername("jack123");
        deleteCreditCardRequest.setTitle("gtb savings card");
        deleteCreditCardRequest.setMasterPassword("Password123.");

        var response = userController.deleteCreditCard(deleteCreditCardRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void nonExistentUser_DeletesCreditCardTest() {
        DeleteCreditCardRequest deleteCreditCardRequest = new DeleteCreditCardRequest();
        deleteCreditCardRequest.setUsername("jim456");
        deleteCreditCardRequest.setTitle("gtb savings card");
        deleteCreditCardRequest.setMasterPassword("Password123.");

        var response = userController.deleteCreditCard(deleteCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userDeletesCreditCard_MasterPasswordIsInvalidTest() {
        DeleteCreditCardRequest deleteCreditCardRequest = new DeleteCreditCardRequest();
        deleteCreditCardRequest.setUsername("jack123");
        deleteCreditCardRequest.setTitle("gtb savings card");
        deleteCreditCardRequest.setMasterPassword("invalid password");

        var response = userController.deleteCreditCard(deleteCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userDeletes_NonExistentCreditCardTest() {
        DeleteCreditCardRequest deleteCreditCardRequest = new DeleteCreditCardRequest();
        deleteCreditCardRequest.setUsername("jack123");
        deleteCreditCardRequest.setTitle("zenith savings card");
        deleteCreditCardRequest.setMasterPassword("Password123.");

        var response = userController.deleteCreditCard(deleteCreditCardRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesPassportTest() {
        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle("my american passport");
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("10/10/2020");
        savePassportRequest.setExpiryDate("09/10/2025");

        var response = userController.savePassport(savePassportRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void nonExistentUser_SavesPassportTest() {
        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jim456");
        savePassportRequest.setTitle("my american passport");
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("10/10/2020");
        savePassportRequest.setExpiryDate("09/10/2025");

        var response = userController.savePassport(savePassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userLogsOut_SavesPassportTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");

        var response = userController.logout(logoutRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle("my american passport");
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("10/10/2020");
        savePassportRequest.setExpiryDate("09/10/2025");

        response = userController.savePassport(savePassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesPassport_TitleIsNullTest() {
        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle(null);
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("10/10/2020");
        savePassportRequest.setExpiryDate("09/10/2025");

        var response = userController.savePassport(savePassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesPassport_TitleIsEmptyTest() {
        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle("");
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("10/10/2020");
        savePassportRequest.setExpiryDate("09/10/2025");

        var response = userController.savePassport(savePassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesPassport_TitleExistsTest() {
        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle("my british passport");
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("10/10/2020");
        savePassportRequest.setExpiryDate("09/10/2025");

        var response = userController.savePassport(savePassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesPassport_IssueDateIsInvalidTest() {
        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle("my british passport");
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("10/10/2020uyuy");
        savePassportRequest.setExpiryDate("09/10/2025887");

        var response = userController.savePassport(savePassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userSavesPassport_ExpiryDateIsInvalidTest() {
        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle("my british passport");
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("10/10/2020");
        savePassportRequest.setExpiryDate("09/10/2025");

        var response = userController.savePassport(savePassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userEditsPassportTest() {
        EditPassportRequest editPassportRequest = new EditPassportRequest();
        editPassportRequest.setUsername("jack123");
        editPassportRequest.setTitle("my british passport");
        editPassportRequest.setEditedTitle("my canadian passport");
        editPassportRequest.setEditedPassportNumber("C1357911");
        editPassportRequest.setEditedIssueDate("07/06/2020");
        editPassportRequest.setEditedExpiryDate("06/07/2030");

        var response = userController.editPassport(editPassportRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void nonExistentUser_EditsPassportTest() {
        EditPassportRequest editPassportRequest = new EditPassportRequest();
        editPassportRequest.setUsername("jim456");
        editPassportRequest.setTitle("my british passport");
        editPassportRequest.setEditedTitle("my canadian passport");
        editPassportRequest.setEditedPassportNumber("C1357911");
        editPassportRequest.setEditedIssueDate("07/06/2020");
        editPassportRequest.setEditedExpiryDate("06/07/2030");

        var response = userController.editPassport(editPassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userLogsOut_EditsPassportTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");

        var response = userController.logout(logoutRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        EditPassportRequest editPassportRequest = new EditPassportRequest();
        editPassportRequest.setUsername("jack123");
        editPassportRequest.setTitle("my british passport");
        editPassportRequest.setEditedTitle("my canadian passport");
        editPassportRequest.setEditedPassportNumber("C1357911");
        editPassportRequest.setEditedIssueDate("07/06/2020");
        editPassportRequest.setEditedExpiryDate("06/07/2030");

        response = userController.editPassport(editPassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userEditsPassport_EditedTitleIsInvalidTest() {
        EditPassportRequest editPassportRequest = new EditPassportRequest();
        editPassportRequest.setUsername("jack123");
        editPassportRequest.setTitle("my british passport");
        editPassportRequest.setEditedTitle("");
        editPassportRequest.setEditedPassportNumber("C1357911");
        editPassportRequest.setEditedIssueDate("07/06/2020");
        editPassportRequest.setEditedExpiryDate("06/07/2030");

        var response = userController.editPassport(editPassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userEditsPassport_EditedIssueDateIsInvalidTest() {
        EditPassportRequest editPassportRequest = new EditPassportRequest();
        editPassportRequest.setUsername("jack123");
        editPassportRequest.setTitle("my british passport");
        editPassportRequest.setEditedTitle("my canadian passport");
        editPassportRequest.setEditedPassportNumber("C1357911");
        editPassportRequest.setEditedIssueDate("07/06rerrre/2020");
        editPassportRequest.setEditedExpiryDate("06/07fdfdf/2030");

        var response = userController.editPassport(editPassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userEditsPassport_EditedExpiryDateIsInvalidTest() {
        EditPassportRequest editPassportRequest = new EditPassportRequest();
        editPassportRequest.setUsername("jack123");
        editPassportRequest.setTitle("my british passport");
        editPassportRequest.setEditedTitle("my canadian passport");
        editPassportRequest.setEditedPassportNumber("C1357911");
        editPassportRequest.setEditedIssueDate("07/06rerrre/2020");
        editPassportRequest.setEditedExpiryDate("06/07fdfdf/2030");

        var response = userController.editPassport(editPassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userViewsPassportTest() {
        ViewPassportRequest viewPassportRequest = new ViewPassportRequest();
        viewPassportRequest.setUsername("jack123");
        viewPassportRequest.setTitle("my british passport");

        var response = userController.viewPassport(viewPassportRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void nonExistentUser_ViewsPassportTest() {
        ViewPassportRequest viewPassportRequest = new ViewPassportRequest();
        viewPassportRequest.setUsername("jim456");
        viewPassportRequest.setTitle("my british passport");

        var response = userController.viewPassport(viewPassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userViews_NonExistentPassportTest() {
        ViewPassportRequest viewPassportRequest = new ViewPassportRequest();
        viewPassportRequest.setUsername("jack123");
        viewPassportRequest.setTitle("my canadian passport");

        var response = userController.viewPassport(viewPassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userLogsOut_ViewsPassportTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");

        var response = userController.logout(logoutRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ViewPassportRequest viewPassportRequest = new ViewPassportRequest();
        viewPassportRequest.setUsername("jack123");
        viewPassportRequest.setTitle("my british passport");

        response = userController.viewPassport(viewPassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void userDeletesPassportTest() {
        DeletePassportRequest deletePassportRequest = new DeletePassportRequest();
        deletePassportRequest.setTitle("my british passport");
        deletePassportRequest.setUsername("jack123");
        deletePassportRequest.setMasterPassword("Password123.");

        var response = userController.deletePassport(deletePassportRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void nonExistentUser_DeletesPassportTest() {
        DeletePassportRequest deletePassportRequest = new DeletePassportRequest();
        deletePassportRequest.setTitle("my british passport");
        deletePassportRequest.setUsername("");
        deletePassportRequest.setMasterPassword("Password123.");

        var response = userController.deletePassport(deletePassportRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


}