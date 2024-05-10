package com.passwordbox.services;

import com.passwordbox.data.models.User;
import com.passwordbox.data.repositories.*;
import com.passwordbox.dataTransferObjects.requests.*;
import com.passwordbox.dataTransferObjects.responses.*;
import com.passwordbox.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplementationTest {

    @Autowired
    private UserService userService;

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
        userService.signUp(signUpRequest);

        SaveLoginInfoRequest saveLoginInfoRequest = new SaveLoginInfoRequest();
        saveLoginInfoRequest.setUsername("jack123");
        saveLoginInfoRequest.setTitle("gmail login");
        saveLoginInfoRequest.setWebsite("www.gmail.com");
        saveLoginInfoRequest.setLoginId("jack123@gmail.com");
        saveLoginInfoRequest.setPassword("password.");
        userService.saveLoginInfo(saveLoginInfoRequest);

        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("gtb savings card");
        saveCreditCardRequest.setCardNumber("5328930010000034");
        saveCreditCardRequest.setCvv("567");
        saveCreditCardRequest.setPin("1234");
        saveCreditCardRequest.setExpiryMonth("1");
        saveCreditCardRequest.setExpiryYear("2025");
        userService.saveCreditCard(saveCreditCardRequest);

        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle("my british passport");
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("B12345678");
        savePassportRequest.setIssueDate("05/05/2020");
        savePassportRequest.setExpiryDate("04/05/2025");
        userService.savePassport(savePassportRequest);
    }

    @Test
    public void userSignsUp_UserIsSavedTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("jim456");
        signUpRequest.setMasterPassword("Password123.");
        RegisterResponse registerResponse = userService.signUp(signUpRequest);

        assertEquals(2, userRepository.count());
        assertEquals("jim456", registerResponse.getUsername());
    }

    @Test
    public void multipleUsersSignUp_UsersAreSavedTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("jim456");
        signUpRequest.setMasterPassword("Password123.");
        RegisterResponse jimRegisterResponse = userService.signUp(signUpRequest);

        signUpRequest.setUsername("james789");
        signUpRequest.setMasterPassword("Password123.");
        RegisterResponse jamesRegisterResponse = userService.signUp(signUpRequest);

        assertEquals(3, userRepository.count());
        assertEquals("jim456", jimRegisterResponse.getUsername());
        assertEquals("james789", jamesRegisterResponse.getUsername());
    }

    @Test
    public void userSignsUp_UsernameFieldIsNull_ThrowsExceptionTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername(null);
        signUpRequest.setMasterPassword("Password123.");

        assertThrows(IllegalArgumentException.class, ()->userService.signUp(signUpRequest));
    }

    @Test
    public void userSignsUp_UsernameFieldIsEmpty_ThrowsExceptionTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("");
        signUpRequest.setMasterPassword("Password123.");

        assertThrows(IllegalArgumentException.class, ()->userService.signUp(signUpRequest));
    }

    @Test
    public void userSignsUp_UsernameField_ContainsSpaceCharacter_ThrowsExceptionTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("jim 456");
        signUpRequest.setMasterPassword("Password123.");

        assertThrows(IllegalArgumentException.class, ()->userService.signUp(signUpRequest));
    }

    @Test
    public void userSignsUp_UsernameExists_ThrowsExceptionTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("jack123");
        signUpRequest.setMasterPassword("Password123.");

        assertThrows(UsernameExistsException.class, ()->userService.signUp(signUpRequest));
    }

    @Test
    public void userSignsUp_MasterPasswordFieldIsNull_ThrowsExceptionTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("jim456");
        signUpRequest.setMasterPassword(null);

        assertThrows(IllegalArgumentException.class, ()->userService.signUp(signUpRequest));
    }

    @Test
    public void userSignsUp_MasterPasswordFieldIsEmpty_ThrowsExceptionTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("jim456");
        signUpRequest.setMasterPassword("");

        assertThrows(IllegalArgumentException.class, ()->userService.signUp(signUpRequest));
    }

    @Test
    public void userSignsUp_MasterPasswordLengthIs_LessThan10Characters_ThrowsExceptionTest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("jim456");
        signUpRequest.setMasterPassword("Word123.");

        assertThrows(IllegalArgumentException.class, ()->userService.signUp(signUpRequest));
    }

    @Test
    public void uerLogsOutTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());
    }

    @Test
    public void nonExistentUserLogsOut_ThrowsExceptionTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jim456");

        assertThrows(UserNotFoundException.class, ()->userService.logout(logoutRequest));
    }

    @Test
    public void userLogsOut_UserLogsInTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("jack123");
        loginRequest.setPassword("Password123.");
        LoginResponse loginResponse = userService.login(loginRequest);

        jackSafeBox = userRepository.findByUsername("jack123");

        assertFalse(jackSafeBox.isLocked());
        assertEquals("jack123", loginResponse.getUsername());
    }

    @Test
    public void nonExistentUserLogsIn_ThrowsExceptionTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("jim456");
        loginRequest.setPassword("Password123.");

        assertThrows(UserNotFoundException.class, ()->userService.login(loginRequest));
    }

    @Test
    public void userLogsInWith_IncorrectMasterPassword_ThrowsExceptionTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("jack123");
        loginRequest.setPassword("invalid password");

        assertThrows(InvalidPasswordException.class, ()->userService.login(loginRequest));
    }

    @Test
    public void userSavesLoginInfoTest() {
        SaveLoginInfoRequest saveLoginInfoRequest = new SaveLoginInfoRequest();
        saveLoginInfoRequest.setUsername("jack123");
        saveLoginInfoRequest.setTitle("yahoo login");
        saveLoginInfoRequest.setWebsite("www.yahoo.com");
        saveLoginInfoRequest.setLoginId("jack123@yahoo.com");
        saveLoginInfoRequest.setPassword("password.");
        SaveLoginInfoResponse saveLoginInfoResponse = userService.saveLoginInfo(saveLoginInfoRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertEquals(2, loginInfoRepository.count());
        assertEquals("jack123@yahoo.com", loginInfoRepository.findAll().get(1).getLoginId());
        assertEquals(2, jackSafeBox.getVault().getLoginInfos().size());
        assertEquals("jack123@yahoo.com", jackSafeBox.getVault().getLoginInfos().get(1).getLoginId());
        assertEquals("yahoo login", saveLoginInfoResponse.getTitle());
    }

    @Test
    public void nonExistentUser_SavesLoginInfo_ThrowsExceptionTest() {
        SaveLoginInfoRequest saveLoginInfoRequest = new SaveLoginInfoRequest();
        saveLoginInfoRequest.setUsername("jim456");
        saveLoginInfoRequest.setTitle("yahoo com");
        saveLoginInfoRequest.setWebsite("www.yahoo.com");
        saveLoginInfoRequest.setLoginId("jim456@yahoo.com");

        assertThrows(UserNotFoundException.class, ()->userService.saveLoginInfo(saveLoginInfoRequest));
    }

    @Test
    public void userLogsOut_SavesLoginInfo_ThrowsExceptionTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());

        SaveLoginInfoRequest saveLoginInfoRequest = new SaveLoginInfoRequest();
        saveLoginInfoRequest.setUsername("jack123");
        saveLoginInfoRequest.setTitle("yahoo login");
        saveLoginInfoRequest.setWebsite("www.yahoo.com");
        saveLoginInfoRequest.setLoginId("jack123@yahoo.com");
        saveLoginInfoRequest.setPassword("password.");

        assertThrows(ProfileLockStateException.class, ()->userService.saveLoginInfo(saveLoginInfoRequest));
    }

    @Test
    public void userSavesLoginInfo_TitleFieldIsNull_ThrowsExceptionTest() {
        SaveLoginInfoRequest saveLoginInfoRequest = new SaveLoginInfoRequest();
        saveLoginInfoRequest.setUsername("jack123");
        saveLoginInfoRequest.setTitle(null);
        saveLoginInfoRequest.setWebsite("www.yahoo.com");
        saveLoginInfoRequest.setLoginId("jack123@yahoo.com");
        saveLoginInfoRequest.setPassword("password.");

        assertThrows(IllegalArgumentException.class, ()->userService.saveLoginInfo(saveLoginInfoRequest));
    }

    @Test
    public void userSavesLoginInfo_TitleFieldIsEmpty_ThrowsExceptionTest() {
        SaveLoginInfoRequest saveLoginInfoRequest = new SaveLoginInfoRequest();
        saveLoginInfoRequest.setUsername("jack123");
        saveLoginInfoRequest.setTitle("");
        saveLoginInfoRequest.setWebsite("www.yahoo.com");
        saveLoginInfoRequest.setLoginId("jack123@yahoo.com");
        saveLoginInfoRequest.setPassword("password.");

        assertThrows(IllegalArgumentException.class, ()->userService.saveLoginInfo(saveLoginInfoRequest));
    }

    @Test
    public void userSavesLoginInfo_TitleExistsInUserList_ThrowsExceptionTest() {
        SaveLoginInfoRequest saveLoginInfoRequest = new SaveLoginInfoRequest();
        saveLoginInfoRequest.setUsername("jack123");
        saveLoginInfoRequest.setTitle("gmail login");
        saveLoginInfoRequest.setWebsite("www.gmail.com");
        saveLoginInfoRequest.setLoginId("jack123@gmail.com");
        saveLoginInfoRequest.setPassword("password.");

        assertThrows(IllegalArgumentException.class, ()->userService.saveLoginInfo(saveLoginInfoRequest));
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
        EditLoginInfoResponse editLoginInfoResponse = userService.editLoginInfo(editLoginInfoRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertEquals(1, loginInfoRepository.count());
        assertEquals("jack123@yahoo.com", loginInfoRepository.findAll().getFirst().getLoginId());
        assertEquals(1, jackSafeBox.getVault().getLoginInfos().size());
        assertEquals("jack123@yahoo.com", jackSafeBox.getVault().getLoginInfos().getFirst().getLoginId());
        assertEquals("yahoo login", editLoginInfoResponse.getTitle());
    }

    @Test
    public void nonExistentUser_EditsLoginInfo_ThrowsExceptionTest() {
        EditLoginInfoRequest editLoginInfoRequest = new EditLoginInfoRequest();
        editLoginInfoRequest.setUsername("jim456");
        editLoginInfoRequest.setTitle("gmail login");
        editLoginInfoRequest.setEditedTitle("yahoo login");
        editLoginInfoRequest.setEditedWebsite("www.yahoo.com");
        editLoginInfoRequest.setEditedLoginId("jim456@yahoo.com");
        editLoginInfoRequest.setEditedPassword("word.");

        assertThrows(UserNotFoundException.class, ()->userService.editLoginInfo(editLoginInfoRequest));
    }

    @Test
    public void userLogsOut_EditsLoginInfo_ThrowExceptionTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());

        EditLoginInfoRequest editLoginInfoRequest = new EditLoginInfoRequest();
        editLoginInfoRequest.setUsername("jack123");
        editLoginInfoRequest.setTitle("gmail login");
        editLoginInfoRequest.setEditedTitle("yahoo login");
        editLoginInfoRequest.setEditedWebsite("www.yahoo.com");
        editLoginInfoRequest.setEditedLoginId("jack123@yahoo.com");
        editLoginInfoRequest.setEditedPassword("word.");

        assertThrows(ProfileLockStateException.class, ()->userService.editLoginInfo(editLoginInfoRequest));
    }

    @Test
    public void userEdits_NonExistentLoginInfo_ThrowExceptionTest() {
        EditLoginInfoRequest editLoginInfoRequest = new EditLoginInfoRequest();
        editLoginInfoRequest.setUsername("jack123");
        editLoginInfoRequest.setTitle("hotmail login");
        editLoginInfoRequest.setEditedTitle("yahoo mail Login");
        editLoginInfoRequest.setEditedWebsite("www.yahoo.com");
        editLoginInfoRequest.setEditedLoginId("jack123@yahoo.com");
        editLoginInfoRequest.setEditedPassword("word.");

        assertThrows(LoginInfoNotFoundException.class, ()->userService.editLoginInfo(editLoginInfoRequest));
    }

    @Test
    public void userEditsLoginInfo_TitleIsInvalid_ThrowsExceptionTest() {
        EditLoginInfoRequest editLoginInfoRequest = new EditLoginInfoRequest();
        editLoginInfoRequest.setUsername("jack123");
        editLoginInfoRequest.setTitle("gmail login");
        editLoginInfoRequest.setEditedTitle("");
        editLoginInfoRequest.setEditedWebsite("www.yahoo.com");
        editLoginInfoRequest.setEditedLoginId("jack123@yahoo.com");
        editLoginInfoRequest.setEditedPassword("word.");

        assertThrows(IllegalArgumentException.class, ()->userService.editLoginInfo(editLoginInfoRequest));
    }

    @Test
    public void userViewsLoginInfoTest() {
        ViewLoginInfoRequest viewLoginInfoRequest = new ViewLoginInfoRequest();
        viewLoginInfoRequest.setUsername("jack123");
        viewLoginInfoRequest.setTitle("gmail login");
        ViewLoginInfoResponse viewLoginInfoResponse = userService.viewLoginInfo(viewLoginInfoRequest);

        assertEquals("jack123@gmail.com", viewLoginInfoResponse.getLoginId());
        assertEquals("www.gmail.com", viewLoginInfoResponse.getWebsite());
    }

    @Test
    public void nonExistentUser_ViewsLoginInfo_ThrowsExceptionTest() {
        ViewLoginInfoRequest viewLoginInfoRequest = new ViewLoginInfoRequest();
        viewLoginInfoRequest.setUsername("jim456");
        viewLoginInfoRequest.setTitle("gmail login");

        assertThrows(UserNotFoundException.class, ()->userService.viewLoginInfo(viewLoginInfoRequest));
    }

    @Test
    public void userLogsOut_UserViewsLoginInfo_ThrowsExceptionTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());

        ViewLoginInfoRequest viewLoginInfoRequest = new ViewLoginInfoRequest();
        viewLoginInfoRequest.setUsername("jack123");
        viewLoginInfoRequest.setTitle("gmail login");

        assertThrows(ProfileLockStateException.class, ()->userService.viewLoginInfo(viewLoginInfoRequest));
    }

    @Test
    public void userViews_NonExistentLoginInfo_ThrowsExceptionTest() {
        ViewLoginInfoRequest viewLoginInfoRequest = new ViewLoginInfoRequest();
        viewLoginInfoRequest.setUsername("jack123");
        viewLoginInfoRequest.setTitle("yahoo login");

        assertThrows(LoginInfoNotFoundException.class, ()->userService.viewLoginInfo(viewLoginInfoRequest));
    }

    @Test
    public void userDeletesLoginInfoTest() {
        DeleteLoginInfoRequest deleteLoginInfoRequest = new DeleteLoginInfoRequest();
        deleteLoginInfoRequest.setUsername("jack123");
        deleteLoginInfoRequest.setTitle("gmail login");
        deleteLoginInfoRequest.setMasterPassword("Password123.");
        DeleteLoginInfoResponse deleteLoginInfoResponse = userService.deleteLoginInfo(deleteLoginInfoRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertEquals(0, loginInfoRepository.count());
        assertEquals(0, jackSafeBox.getVault().getLoginInfos().size());
        assertEquals("gmail login", deleteLoginInfoResponse.getTitle());
    }

    @Test
    public void nonExistentUser_DeletesLoginInfo_ThrowExceptionTest() {
        DeleteLoginInfoRequest deleteLoginInfoRequest = new DeleteLoginInfoRequest();
        deleteLoginInfoRequest.setUsername("jim456");
        deleteLoginInfoRequest.setTitle("gmail login");
        deleteLoginInfoRequest.setMasterPassword("Password123.");

        assertThrows(UserNotFoundException.class, ()->userService.deleteLoginInfo(deleteLoginInfoRequest));
    }

    @Test
    public void userLogsOut_DeletesLoginInfo_ThrowExceptionTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());

        DeleteLoginInfoRequest deleteLoginInfoRequest = new DeleteLoginInfoRequest();
        deleteLoginInfoRequest.setUsername("jack123");
        deleteLoginInfoRequest.setTitle("gmail login");
        deleteLoginInfoRequest.setMasterPassword("Password123.");

        assertThrows(ProfileLockStateException.class, ()->userService.deleteLoginInfo(deleteLoginInfoRequest));
    }

    @Test
    public void userDeletesLoginInfo_MasterPasswordIsInvalid_ThrowExceptionTest() {
        DeleteLoginInfoRequest deleteLoginInfoRequest = new DeleteLoginInfoRequest();
        deleteLoginInfoRequest.setUsername("jack123");
        deleteLoginInfoRequest.setTitle("gmail login");
        deleteLoginInfoRequest.setMasterPassword("invalid password");

        assertThrows(InvalidPasswordException.class, ()->userService.deleteLoginInfo(deleteLoginInfoRequest));
    }

    @Test
    public void userDeletes_NonExistentLoginInfo_ThrowExceptionTest() {
        DeleteLoginInfoRequest deleteLoginInfoRequest = new DeleteLoginInfoRequest();
        deleteLoginInfoRequest.setUsername("jack123");
        deleteLoginInfoRequest.setTitle("yahoo login");
        deleteLoginInfoRequest.setMasterPassword("Password123.");

        assertThrows(LoginInfoNotFoundException.class, ()->userService.deleteLoginInfo(deleteLoginInfoRequest));
    }

    @Test
    public void userGeneratesPasswordTest() {
        GeneratePasswordRequest generatePasswordRequest = new GeneratePasswordRequest();
        generatePasswordRequest.setUpperCaseCharacters(false);
        generatePasswordRequest.setSpecialCharacters(true);
        generatePasswordRequest.setNumericCharacters(false);
        generatePasswordRequest.setLowerCaseCharacters(true);
        generatePasswordRequest.setLength("12");
        GeneratePasswordResponse generatePasswordResponse = userService.generatePassword(generatePasswordRequest);

        assertEquals(12, generatePasswordResponse.getLength());
    }

    @Test
    public void userGeneratesPasswordTwice_PasswordsAreNotTheSameTest() {
        GeneratePasswordRequest generatePasswordRequest = new GeneratePasswordRequest();
        generatePasswordRequest.setUpperCaseCharacters(false);
        generatePasswordRequest.setSpecialCharacters(true);
        generatePasswordRequest.setNumericCharacters(false);
        generatePasswordRequest.setLowerCaseCharacters(true);
        generatePasswordRequest.setLength("12");
        GeneratePasswordResponse firstGeneratePasswordResponse = userService.generatePassword(generatePasswordRequest);
        GeneratePasswordResponse secondGeneratePasswordResponse = userService.generatePassword(generatePasswordRequest);

        assertNotEquals(firstGeneratePasswordResponse.getPassword(), secondGeneratePasswordResponse.getPassword());
    }

    @Test
    public void userGeneratesPassword_LengthIsNotANumber_ThrowsExceptionTest() {
        GeneratePasswordRequest generatePasswordRequest = new GeneratePasswordRequest();
        generatePasswordRequest.setUpperCaseCharacters(false);
        generatePasswordRequest.setSpecialCharacters(true);
        generatePasswordRequest.setNumericCharacters(false);
        generatePasswordRequest.setLowerCaseCharacters(true);
        generatePasswordRequest.setLength("A");

        assertThrows(InvalidPasscodeLengthException.class, ()->userService.generatePassword(generatePasswordRequest));
    }

    @Test
    public void userGeneratesPassword_NumberIsNegative_ThrowsExceptionTest() {
        GeneratePasswordRequest generatePasswordRequest = new GeneratePasswordRequest();
        generatePasswordRequest.setUpperCaseCharacters(false);
        generatePasswordRequest.setSpecialCharacters(true);
        generatePasswordRequest.setNumericCharacters(false);
        generatePasswordRequest.setLowerCaseCharacters(true);
        generatePasswordRequest.setLength("-1");

        assertThrows(InvalidPasscodeLengthException.class, ()->userService.generatePassword(generatePasswordRequest));
    }

    @Test
    public void userGeneratesPassword_NumberIsGreaterThan30_ThrowsException() {
        GeneratePasswordRequest generatePasswordRequest = new GeneratePasswordRequest();
        generatePasswordRequest.setUpperCaseCharacters(false);
        generatePasswordRequest.setSpecialCharacters(true);
        generatePasswordRequest.setNumericCharacters(false);
        generatePasswordRequest.setLowerCaseCharacters(true);
        generatePasswordRequest.setLength("41");

        assertThrows(InvalidPasscodeLengthException.class, ()->userService.generatePassword(generatePasswordRequest));
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
        SaveCreditCardResponse saveCreditCardResponse = userService.saveCreditCard(saveCreditCardRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");
        System.out.println(jackSafeBox.getVault().getCreditCards().get(1));

        assertEquals(2, creditCardRepository.count());
        assertEquals(2, jackSafeBox.getVault().getCreditCards().size());
        assertEquals("zenith savings card", jackSafeBox.getVault().getCreditCards().get(1).getTitle());
        assertEquals("zenith savings card", saveCreditCardResponse.getTitle());
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

        assertThrows(UserNotFoundException.class ,()->userService.saveCreditCard(saveCreditCardRequest));
    }

    @Test
    public void userLogsOut_SavesCreditCard_ThrowsExceptionTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());

        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("zenith savings card");
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        assertThrows(ProfileLockStateException.class, ()->userService.saveCreditCard(saveCreditCardRequest));
    }

    @Test
    public void userSavesCreditCard_TitleExists_ThrowsExceptionTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("gtb savings card");
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        assertThrows(IllegalArgumentException.class, ()->userService.saveCreditCard(saveCreditCardRequest));
    }

    @Test
    public void userSavesCreditCard_TitleIsEmpty_ThrowsExceptionTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("");
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        assertThrows(IllegalArgumentException.class, ()->userService.saveCreditCard(saveCreditCardRequest));
    }

    @Test
    public void userSavesCreditCard_TitleIsNull_ThrowsExceptionTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle(null);
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        assertThrows(IllegalArgumentException.class, ()->userService.saveCreditCard(saveCreditCardRequest));
    }

    @Test
    public void userSavesCreditCard_CreditCardNumberIsInvalid_ThrowsExceptionTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("zenith savings card");
        saveCreditCardRequest.setCardNumber("1234567890");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        assertThrows(IllegalArgumentException.class, ()->userService.saveCreditCard(saveCreditCardRequest));
    }

    @Test
    public void userSavesCreditCard_CreditCardPinIsInvalid_ThrowsExceptionTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("zenith savings card");
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("ABCD");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        assertThrows(IllegalArgumentException.class, ()->userService.saveCreditCard(saveCreditCardRequest));
    }

    @Test
    public void userSavesCreditCard_CreditCardCVVIsInvalid_ThrowsExceptionTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("zenith savings card");
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("ABC");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2026");

        assertThrows(IllegalArgumentException.class, ()->userService.saveCreditCard(saveCreditCardRequest));
    }

    @Test
    public void userSavesCreditCard_CreditCard_ExpiryMonthIsInvalid_ThrowsExceptionTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("zenith savings card");
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("13");
        saveCreditCardRequest.setExpiryYear("2026");

        assertThrows(DateMonthException.class, ()->userService.saveCreditCard(saveCreditCardRequest));
    }

    @Test
    public void userSavesCreditCard_CreditCard_ExpiryYearIsInvalid_ThrowsExceptionTest() {
        SaveCreditCardRequest saveCreditCardRequest = new SaveCreditCardRequest();
        saveCreditCardRequest.setUsername("jack123");
        saveCreditCardRequest.setTitle("zenith savings card");
        saveCreditCardRequest.setCardNumber("5328930010005009");
        saveCreditCardRequest.setCvv("123");
        saveCreditCardRequest.setPin("0987");
        saveCreditCardRequest.setExpiryMonth("2");
        saveCreditCardRequest.setExpiryYear("2023");

        assertThrows(DateMonthException.class, ()->userService.saveCreditCard(saveCreditCardRequest));
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


        EditCreditCardResponse editCreditCardResponse = userService.editCreditCard(editCreditCardRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");
        System.out.println(jackSafeBox.getVault().getCreditCards().get(0));
        assertEquals(1, jackSafeBox.getVault().getCreditCards().size());
        assertEquals("gtb current card", jackSafeBox.getVault().getCreditCards().getFirst().getTitle());
        assertEquals(1, creditCardRepository.count());
        assertEquals(jackSafeBox.getVault().getCreditCards().getFirst().getId(), editCreditCardResponse.getId());
    }

    @Test
    public void nonExistentUser_EditsCreditCard_ThrowsExceptionTest() {
        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jim456");
        editCreditCardRequest.setTitle("gtb savings card");
        editCreditCardRequest.setEditedTitle("gtb current card");
        editCreditCardRequest.setEditedCardNumber("5328930010502658");
        editCreditCardRequest.setEditedCVV("456");
        editCreditCardRequest.setEditedPin("6543");
        editCreditCardRequest.setEditedExpiryMonth("3");
        editCreditCardRequest.setEditedExpiryYear("2027");

        assertThrows(UserNotFoundException.class, ()->userService.editCreditCard(editCreditCardRequest));
    }

    @Test
    public void userLogsOut_EditsCreditCard_ThrowsExceptionTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());

        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jack123");
        editCreditCardRequest.setTitle("gtb savings card");
        editCreditCardRequest.setEditedTitle("gtb current card");
        editCreditCardRequest.setEditedCardNumber("5328930010502658");
        editCreditCardRequest.setEditedCVV("456");
        editCreditCardRequest.setEditedPin("6543");
        editCreditCardRequest.setEditedExpiryMonth("3");
        editCreditCardRequest.setEditedExpiryYear("2027");

        assertThrows(ProfileLockStateException.class, ()->userService.editCreditCard(editCreditCardRequest));
    }

    @Test
    public void userEditsCreditCard_EditedTitleIsInvalid_ThrowsExceptionTest() {
        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jack123");
        editCreditCardRequest.setTitle("gtb savings card");
        editCreditCardRequest.setEditedTitle("");
        editCreditCardRequest.setEditedCardNumber("5328930010502658");
        editCreditCardRequest.setEditedCVV("456");
        editCreditCardRequest.setEditedPin("6543");
        editCreditCardRequest.setEditedExpiryMonth("3");
        editCreditCardRequest.setEditedExpiryYear("2027");

        assertThrows(IllegalArgumentException.class ,()->userService.editCreditCard(editCreditCardRequest));
    }

    @Test
    public void userEditsCreditCard_CreditCardNumberIsInvalid_ThrowsExceptionTest() {
        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jack123");
        editCreditCardRequest.setTitle("gtb savings card");
        editCreditCardRequest.setEditedTitle("gtb current card");
        editCreditCardRequest.setEditedCardNumber("1234567890");
        editCreditCardRequest.setEditedCVV("456");
        editCreditCardRequest.setEditedPin("6543");
        editCreditCardRequest.setEditedExpiryMonth("3");
        editCreditCardRequest.setEditedExpiryYear("2027");

        assertThrows(IllegalArgumentException.class, ()->userService.editCreditCard(editCreditCardRequest));
    }

    @Test
    public void userEditsCreditCard_PinIsInvalid_ThrowsExceptionTest() {
        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jack123");
        editCreditCardRequest.setTitle("gtb savings card");
        editCreditCardRequest.setEditedTitle("gtb current card");
        editCreditCardRequest.setEditedCardNumber("5328930010502658");
        editCreditCardRequest.setEditedCVV("456");
        editCreditCardRequest.setEditedPin("ABCD");
        editCreditCardRequest.setEditedExpiryMonth("3");
        editCreditCardRequest.setEditedExpiryYear("2027");

        assertThrows(IllegalArgumentException.class, ()->userService.editCreditCard(editCreditCardRequest));
    }

    @Test
    public void userEditsCreditCard_CVVIsInvalid_ThrowsExceptionTest() {
        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jack123");
        editCreditCardRequest.setTitle("gtb savings card");
        editCreditCardRequest.setEditedTitle("gtb current card");
        editCreditCardRequest.setEditedCardNumber("5328930010502658");
        editCreditCardRequest.setEditedCVV("ABC");
        editCreditCardRequest.setEditedPin("6543");
        editCreditCardRequest.setEditedExpiryMonth("3");
        editCreditCardRequest.setEditedExpiryYear("2027");

        assertThrows(IllegalArgumentException.class, ()->userService.editCreditCard(editCreditCardRequest));
    }

    @Test
    public void userEditsCreditCard_ExpiryYearIsInvalid_ThrowsExceptionTest() {
        EditCreditCardRequest editCreditCardRequest = new EditCreditCardRequest();
        editCreditCardRequest.setUsername("jack123");
        editCreditCardRequest.setTitle("gtb savings card");
        editCreditCardRequest.setEditedTitle("gtb current card");
        editCreditCardRequest.setEditedCardNumber("5328930010502658");
        editCreditCardRequest.setEditedCVV("456");
        editCreditCardRequest.setEditedPin("6543");
        editCreditCardRequest.setEditedExpiryMonth("3");
        editCreditCardRequest.setEditedExpiryYear("2017");

        assertThrows(DateMonthException.class, ()->userService.editCreditCard(editCreditCardRequest));
    }

    @Test
    public void userViewsCreditCardTest() {
        ViewCreditCardRequest viewCreditCardRequest = new ViewCreditCardRequest();
        viewCreditCardRequest.setUsername("jack123");
        viewCreditCardRequest.setTitle("gtb savings card");
        ViewCreditCardResponse viewCreditCardResponse = userService.viewCreditCard(viewCreditCardRequest);

        assertEquals(1, creditCardRepository.count());
        assertEquals("gtb savings card", viewCreditCardResponse.getTitle());
        assertEquals("5328930010000034", viewCreditCardResponse.getCardNumber());
    }

    @Test
    public void nonExistentUser_ViewsCreditCard_ThrowsExceptionTest() {
        ViewCreditCardRequest viewCreditCardRequest = new ViewCreditCardRequest();
        viewCreditCardRequest.setUsername("jim456");
        viewCreditCardRequest.setTitle("gtb savings card");

        assertThrows(UserNotFoundException.class, ()->userService.viewCreditCard(viewCreditCardRequest));
    }

    @Test
    public void userLogsOut_ViewsCreditCard_ThrowsExceptionTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());

        ViewCreditCardRequest viewCreditCardRequest = new ViewCreditCardRequest();
        viewCreditCardRequest.setUsername("jack123");
        viewCreditCardRequest.setTitle("gtb savings card");

        assertThrows(ProfileLockStateException.class, ()-> userService.viewCreditCard(viewCreditCardRequest));
    }

    @Test
    public void userViews_NonExistentCreditCard_ThrowsExceptionTest() {
        ViewCreditCardRequest viewCreditCardRequest = new ViewCreditCardRequest();
        viewCreditCardRequest.setUsername("jack123");
        viewCreditCardRequest.setTitle("zenith savings card");

        assertThrows(CreditCardNotFoundException.class, ()->userService.viewCreditCard(viewCreditCardRequest));
    }

    @Test
    public void userDeletesCreditCardTest(){
        DeleteCreditCardRequest deleteCreditCardRequest = new DeleteCreditCardRequest();
        deleteCreditCardRequest.setUsername("jack123");
        deleteCreditCardRequest.setTitle("gtb savings card");
        deleteCreditCardRequest.setMasterPassword("Password123.");
        DeleteCreditCardResponse deleteCreditCardResponse = userService.deleteCreditCard(deleteCreditCardRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertEquals(0, jackSafeBox.getVault().getCreditCards().size());
        assertEquals(0, creditCardRepository.count());
        assertEquals("gtb savings card", deleteCreditCardResponse.getTitle());
    }

    @Test
    public void nonExistentUser_DeletesCreditCardTest_ThrowsExceptionTest(){
        DeleteCreditCardRequest deleteCreditCardRequest = new DeleteCreditCardRequest();
        deleteCreditCardRequest.setUsername("jim456");
        deleteCreditCardRequest.setTitle("gtb savings card");
        deleteCreditCardRequest.setMasterPassword("Password123.");

        assertThrows(UserNotFoundException.class, ()->userService.deleteCreditCard(deleteCreditCardRequest));
    }

    @Test
    public void userLogsOut_DeletesCreditCard_ThrowsExceptionTest(){
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());

        DeleteCreditCardRequest deleteCreditCardRequest = new DeleteCreditCardRequest();
        deleteCreditCardRequest.setUsername("jack123");
        deleteCreditCardRequest.setTitle("gtb savings card");
        deleteCreditCardRequest.setMasterPassword("Password123.");

        assertThrows(ProfileLockStateException.class, ()->userService.deleteCreditCard(deleteCreditCardRequest));
    }

    @Test
    public void userDeletesCreditCard_MasterPasswordIsInvalid_ThrowsExceptionTest(){
        DeleteCreditCardRequest deleteCreditCardRequest = new DeleteCreditCardRequest();
        deleteCreditCardRequest.setUsername("jack123");
        deleteCreditCardRequest.setTitle("gtb savings card");
        deleteCreditCardRequest.setMasterPassword("invalid password");

        assertThrows(InvalidPasswordException.class ,()->userService.deleteCreditCard(deleteCreditCardRequest));
    }

    @Test
    public void userDeletes_NonExistentCreditCard_ThrowsExceptionTest(){
        DeleteCreditCardRequest deleteCreditCardRequest = new DeleteCreditCardRequest();
        deleteCreditCardRequest.setUsername("jack123");
        deleteCreditCardRequest.setTitle("zenith savings card");
        deleteCreditCardRequest.setMasterPassword("Password123.");

        assertThrows(CreditCardNotFoundException.class, ()->userService.deleteCreditCard(deleteCreditCardRequest));
    }

    @Test
    public void userSavesPassportTest() {
        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle("my american passport");
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("06/06/2021");
        savePassportRequest.setExpiryDate("05/06/2026");
        SavePassportResponse savePassportResponse = userService.savePassport(savePassportRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertEquals(2, passportRepository.count());
        assertEquals(2, jackSafeBox.getVault().getPassports().size());
        assertEquals("my american passport", jackSafeBox.getVault().getPassports().get(1).getTitle());
        assertEquals(jackSafeBox.getVault().getPassports().get(1).getId(),savePassportResponse.getId());
    }

    @Test
    public void nonExistentUser_SavesPassport_ThrowsExceptionTest() {
        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jim456");
        savePassportRequest.setTitle("my american passport");
        savePassportRequest.setSurname("brown");
        savePassportRequest.setGivenNames("jim steve");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("06/06/2021");
        savePassportRequest.setExpiryDate("05/06/2026");

        assertThrows(UserNotFoundException.class, ()->userService.savePassport(savePassportRequest));
    }

    @Test
    public void userLogsOut_SavesPassport_ThrowsExceptionTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());

        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle("my american passport");
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("06/06/2021");
        savePassportRequest.setExpiryDate("05/06/2026");

        assertThrows(ProfileLockStateException.class, ()->userService.savePassport(savePassportRequest));
    }

    @Test
    public void userSavesPassport_TitleIsNull_ThrowsExceptionTest() {
        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle(null);
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("06/06/2021");
        savePassportRequest.setExpiryDate("05/06/2026");

        assertThrows(IllegalArgumentException.class, ()->userService.savePassport(savePassportRequest));
    }

    @Test
    public void userSavesPassport_TitleIsEmpty_ThrowsExceptionTest() {
        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle("");
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("06/06/2021");
        savePassportRequest.setExpiryDate("05/06/2026");

        assertThrows(IllegalArgumentException.class, ()->userService.savePassport(savePassportRequest));
    }

    @Test
    public void userSavesPassport_TitleExists_ThrowsExceptionTest() {
        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle("my british passport");
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("06/06/2021");
        savePassportRequest.setExpiryDate("05/06/2026");

        assertThrows(IllegalArgumentException.class, ()->userService.savePassport(savePassportRequest));
    }

    @Test
    public void userSavesPassport_IssueDateIsInvalid_ThrowsExceptionTest() {
        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle("my american passport");
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("32/06/2021");
        savePassportRequest.setExpiryDate("05/06/2026");

        assertThrows(InvalidPassportDateException.class, ()->userService.savePassport(savePassportRequest));
    }

    @Test
    public void userSavesPassport_ExpiryDateIsInvalid_ThrowsExceptionTest() {
        SavePassportRequest savePassportRequest = new SavePassportRequest();
        savePassportRequest.setUsername("jack123");
        savePassportRequest.setTitle("my american passport");
        savePassportRequest.setSurname("smith");
        savePassportRequest.setGivenNames("john scott");
        savePassportRequest.setPassportNumber("A09876543");
        savePassportRequest.setIssueDate("06/06/2021");
        savePassportRequest.setExpiryDate("05/13/2026");
        //REFACTOR TEST

        assertThrows(InvalidPassportDateException.class, ()->userService.savePassport(savePassportRequest));
    }

    @Test
    public void userEditsPassportTest(){
        EditPassportRequest editPassportRequest = new EditPassportRequest();
        editPassportRequest.setUsername("jack123");
        editPassportRequest.setTitle("my british passport");
        editPassportRequest.setEditedTitle("my canadian passport");
        editPassportRequest.setEditedPassportNumber("C1357911");
        editPassportRequest.setEditedIssueDate("07/06/2022");
        editPassportRequest.setEditedExpiryDate("06/07/2027");
        EditPassportResponse editPassportResponse = userService.editPassport(editPassportRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");
        System.out.println(jackSafeBox.getVault().getPassports().get(0));
        assertEquals(1, passportRepository.count());
        assertEquals(jackSafeBox.getVault().getPassports().getFirst().getId(), editPassportResponse.getId());
        assertEquals("my canadian passport", editPassportResponse.getTitle());
    }

    @Test
    public void nonExistentUser_EditsPassport_ThrowsExceptionTest(){
        EditPassportRequest editPassportRequest = new EditPassportRequest();
        editPassportRequest.setUsername("jim456");
        editPassportRequest.setTitle("my british passport");
        editPassportRequest.setEditedTitle("my canadian passport");
        editPassportRequest.setEditedPassportNumber("C1357911");
        editPassportRequest.setEditedIssueDate("07/06/2022");
        editPassportRequest.setEditedExpiryDate("06/07/2027");

        assertThrows(UserNotFoundException.class, ()->userService.editPassport(editPassportRequest));
    }

    @Test
    public void userLogsOut_EditsPassport_ThrowsExceptionTest(){
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());

        EditPassportRequest editPassportRequest = new EditPassportRequest();
        editPassportRequest.setUsername("jack123");
        editPassportRequest.setTitle("my british passport");
        editPassportRequest.setEditedTitle("my canadian passport");
        editPassportRequest.setEditedPassportNumber("C1357911");
        editPassportRequest.setEditedIssueDate("07/06/2022");
        editPassportRequest.setEditedExpiryDate("06/07/2027");

        assertThrows(ProfileLockStateException.class, ()->userService.editPassport(editPassportRequest));
    }

    @Test
    public void userEditsPassport_EditedTitleIsInvalid_ThrowsExceptionTest(){
        EditPassportRequest editPassportRequest = new EditPassportRequest();
        editPassportRequest.setUsername("jack123");
        editPassportRequest.setTitle("my british passport");
        editPassportRequest.setEditedTitle("");
        editPassportRequest.setEditedPassportNumber("C1357911");
        editPassportRequest.setEditedIssueDate("07/06/2022");
        editPassportRequest.setEditedExpiryDate("06/07/2027");

        assertThrows(IllegalArgumentException.class, ()->userService.editPassport(editPassportRequest));
    }

    @Test
    public void userEditsPassport_TitleIsEmpty_ThrowsExceptionTest(){
        EditPassportRequest editPassportRequest = new EditPassportRequest();
        editPassportRequest.setUsername("jack123");
        editPassportRequest.setTitle("my british passport");
        editPassportRequest.setEditedTitle("");
        editPassportRequest.setEditedPassportNumber("C1357911");
        editPassportRequest.setEditedIssueDate("07/06/2022");
        editPassportRequest.setEditedExpiryDate("06/07/2027");

        assertThrows(IllegalArgumentException.class, ()->userService.editPassport(editPassportRequest));
    }

    @Test
    public void userEditsPassport_EditedIssueDateIsInvalid_ThrowsExceptionTest(){
        EditPassportRequest editPassportRequest = new EditPassportRequest();
        editPassportRequest.setUsername("jack123");
        editPassportRequest.setTitle("my british passport");
        editPassportRequest.setEditedTitle("my canadian passport");
        editPassportRequest.setEditedPassportNumber("C1357911");
        editPassportRequest.setEditedIssueDate("32/06/2022");
        editPassportRequest.setEditedExpiryDate("06/07/2027");

        assertThrows(InvalidPassportDateException.class, ()->userService.editPassport(editPassportRequest));
    }

    @Test
    public void userEditsPassport_EditedExpiryDateIsInvalid_ThrowsExceptionTest(){
        EditPassportRequest editPassportRequest = new EditPassportRequest();
        editPassportRequest.setUsername("jack123");
        editPassportRequest.setTitle("my british passport");
        editPassportRequest.setEditedTitle("my canadian passport");
        editPassportRequest.setEditedPassportNumber("C1357911");
        editPassportRequest.setEditedIssueDate("07/06/2022");
        editPassportRequest.setEditedExpiryDate("06/13/2027");

        assertThrows(InvalidPassportDateException.class, ()->userService.editPassport(editPassportRequest));
    }

    @Test
    public void userViewsPassportTest() {
        ViewPassportRequest viewPassportRequest = new ViewPassportRequest();
        viewPassportRequest.setUsername("jack123");
        viewPassportRequest.setTitle("my british passport");
        ViewPassportResponse viewPassportResponse = userService.viewPassport(viewPassportRequest);

        assertEquals(1, passportRepository.count());
        assertEquals("my british passport", viewPassportResponse.getTitle());
    }

    @Test
    public void nonExistentUser_ViewsPassport_ThrowsExceptionTest(){
        ViewPassportRequest viewPassportRequest = new ViewPassportRequest();
        viewPassportRequest.setUsername("jim456");
        viewPassportRequest.setTitle("my british passport");

        assertThrows(UserNotFoundException.class, ()->userService.viewPassport(viewPassportRequest));
    }

    @Test
    public void userLogsOut_ViewsPassport_ThrowsExceptionTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());

        ViewPassportRequest viewPassportRequest = new ViewPassportRequest();
        viewPassportRequest.setUsername("jack123");
        viewPassportRequest.setTitle("my british passport");

        assertThrows(ProfileLockStateException.class, ()->userService.viewPassport(viewPassportRequest));
    }

    @Test
    public void userViews_NonExistentPassport_ThrowsExceptionTest(){
        ViewPassportRequest viewPassportRequest = new ViewPassportRequest();
        viewPassportRequest.setUsername("jack123");
        viewPassportRequest.setTitle("my american passport");

        assertThrows(PassportNotFoundException.class, ()->userService.viewPassport(viewPassportRequest));
    }

    @Test
    public void userDeletesPassportTest() {
        DeletePassportRequest deletePassportRequest = new DeletePassportRequest();
        deletePassportRequest.setTitle("my british passport");
        deletePassportRequest.setUsername("jack123");
        deletePassportRequest.setMasterPassword("Password123.");
        DeletePassportResponse deletePassportResponse = userService.deletePassport(deletePassportRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertEquals(0, passportRepository.count());
        assertEquals(0, jackSafeBox.getVault().getPassports().size());
        assertEquals("my british passport", deletePassportResponse.getTitle());
    }

    @Test
    public void userLogsOut_DeletesPassport_ThrowsExceptionTest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("jack123");
        LogoutResponse logoutResponse = userService.logout(logoutRequest);

        User jackSafeBox = userRepository.findByUsername("jack123");

        assertTrue(jackSafeBox.isLocked());
        assertEquals("jack123", logoutResponse.getUsername());

        DeletePassportRequest deletePassportRequest = new DeletePassportRequest();
        deletePassportRequest.setTitle("my british passport");
        deletePassportRequest.setUsername("jack123");
        deletePassportRequest.setMasterPassword("Password123.");

        assertThrows(ProfileLockStateException.class, ()->userService.deletePassport(deletePassportRequest));
    }

    @Test
    public void nonExistentUser_DeletesPassport_ThrowsExceptionTest() {
        DeletePassportRequest deletePassportRequest = new DeletePassportRequest();
        deletePassportRequest.setTitle("my british passport");
        deletePassportRequest.setUsername("jim456");
        deletePassportRequest.setMasterPassword("Password123.");

        assertThrows(UserNotFoundException.class, ()->userService.deletePassport(deletePassportRequest));
    }

    @Test
    public void userDeletes_NonExistentPassport_ThrowsExceptionTest() {
        DeletePassportRequest deletePassportRequest = new DeletePassportRequest();
        deletePassportRequest.setTitle("my american passport");
        deletePassportRequest.setUsername("jack123");
        deletePassportRequest.setMasterPassword("Password123.");

        assertThrows(PassportNotFoundException.class, ()->userService.deletePassport(deletePassportRequest));
    }
}