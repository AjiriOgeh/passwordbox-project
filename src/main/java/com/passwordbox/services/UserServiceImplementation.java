package com.passwordbox.services;

import com.passwordbox.data.models.*;
import com.passwordbox.data.repositories.UserRepository;
import com.passwordbox.dataTransferObjects.requests.*;
import com.passwordbox.dataTransferObjects.responses.*;
import com.passwordbox.exceptions.*;
import com.passwordbox.utilities.PasscodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static com.passwordbox.utilities.FindDetails.*;
import static com.passwordbox.utilities.Mappers.*;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VaultService vaultService;

    @Override
    public RegisterResponse signUp(SignUpRequest signUpRequest) {
        validateUsername(signUpRequest.getUsername());
        validatePassword(signUpRequest.getMasterPassword());
        User user = registerRequestMap(signUpRequest);
        userRepository.save(user);
        return registerResponseMap(user);
    }

    private void validateUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null. Please enter a valid username.");
        if (username.isEmpty()) throw new IllegalArgumentException("Username cannot be empty. Please enter a valid username.");
        if (username.contains(" ")) throw new IllegalArgumentException("Username cannot have space character. Please enter a valid username.");
        if (doesUsernameExist(username.toLowerCase())) throw new UsernameExistsException("Username exists. Please try again.");
    }

    private void validatePassword(String masterPassword) {
        if (masterPassword == null) throw new IllegalArgumentException("Password cannot be null. Please enter a valid password.");
        if (masterPassword.isEmpty()) throw new IllegalArgumentException("Password field cannot be empty. Please enter a valid password.");
        if (masterPassword.length() < 10) throw new IllegalArgumentException("Password is less than 10 characters. Please try again.");
    }

    private boolean doesUsernameExist(String username) {
        User user = userRepository.findByUsername(username.toLowerCase());
        return user != null;
    }

    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        User user = userRepository.findByUsername(logoutRequest.getUsername().toLowerCase());
        if (user == null) throw new UserNotFoundException(String.format("%s does not exist.", logoutRequest.getUsername()));
        user.setLocked(true);
        userRepository.save(user);
        return logoutResponseMap(user);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername().toLowerCase());
        if (user == null) throw new UserNotFoundException("Invalid login details. Please try again.");
        if (!user.getMasterPassword().equals(loginRequest.getPassword())) throw new InvalidPasswordException("Invalid login details. Please try again.");
        user.setLocked(false);
        userRepository.save(user);
        return loginResponseMap(user);
    }

    @Override
    public SaveLoginInfoResponse saveLoginInfo(SaveLoginInfoRequest saveLoginInfoRequest) {
        User user = userRepository.findByUsername(saveLoginInfoRequest.getUsername().toLowerCase());
        if (user == null) throw new UserNotFoundException(String.format("User %s does not exist.", saveLoginInfoRequest.getUsername()));
        if (user.isLocked()) throw new ProfileLockStateException("Please login to save loginInfo.");
        LoginInfo loginInfo = vaultService.saveLoginInfo(saveLoginInfoRequest, user.getVault());
        userRepository.save(user);
        return saveNewLoginInfoResponseMap(loginInfo);
    }

    @Override
    public EditLoginInfoResponse editLoginInfo(EditLoginInfoRequest editLoginInfoRequest) {
        User user = userRepository.findByUsername(editLoginInfoRequest.getUsername().toLowerCase());
        if (user == null) throw new UserNotFoundException(String.format("User %s does not exist.", editLoginInfoRequest.getUsername()));
        if (user.isLocked()) throw new ProfileLockStateException("Please login to edit loginInfo.");
        LoginInfo loginInfo = vaultService.editLoginInfo(editLoginInfoRequest, user.getVault());
        userRepository.save(user);
        return editLoginInfoResponseMap(loginInfo);
    }

    @Override
    public ViewLoginInfoResponse viewLoginInfo(ViewLoginInfoRequest viewLoginInfoRequest) {
        User user = userRepository.findByUsername(viewLoginInfoRequest.getUsername().toLowerCase());
        if (user == null) throw new UserNotFoundException(String.format("User %s does not exist.", viewLoginInfoRequest.getUsername()));
        if (user.isLocked()) throw new ProfileLockStateException("Please login to view loginInfo.");
        LoginInfo loginInfo = findLoginInfoInVault(viewLoginInfoRequest.getTitle().toLowerCase(), user.getVault());
        return viewLoginInfoResponseMap(loginInfo);
    }

    @Override
    public DeleteLoginInfoResponse deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest) {
        User user = userRepository.findByUsername(deleteLoginInfoRequest.getUsername().toLowerCase());
        if (user == null) throw new UserNotFoundException(String.format("User %s does not exist.", deleteLoginInfoRequest.getUsername()));
        if (user.isLocked()) throw new ProfileLockStateException("Please login to delete loginInfo.");
        if (!user.getMasterPassword().equals(deleteLoginInfoRequest.getMasterPassword())) throw new InvalidPasswordException("Incorrect password. Please try again.");
        DeleteLoginInfoResponse deleteLoginInfoResponse =  vaultService.deleteLoginInfo(deleteLoginInfoRequest, user.getVault());
        userRepository.save(user);
        return deleteLoginInfoResponse;
    }

    public GeneratePasswordResponse generatePassword(GeneratePasswordRequest generatePasswordRequest) {
        validatePasscodeLength(generatePasswordRequest.getLength());
        String password = PasscodeGenerator.generatePassword(generatePasswordRequest);
        return generatePasswordResponseMap(password);
    }

    private static void validatePasscodeLength(String passcodeLength) {
        if (!passcodeLength.matches("\\d+")) throw new InvalidPasscodeLengthException("Please enter a valid number.");
        if (Integer.parseInt(passcodeLength) < 1 || Integer.parseInt(passcodeLength) > 40) throw new InvalidPasscodeLengthException("Please enter a number between 1 - 30.");
    }

    @Override
    public SaveCreditCardResponse saveCreditCard(SaveCreditCardRequest saveCreditCardRequest) {
        User user = userRepository.findByUsername(saveCreditCardRequest.getUsername());
        if (user == null) throw new UserNotFoundException(String.format("User %s does not exist.", saveCreditCardRequest.getUsername()));
        if (user.isLocked()) throw new ProfileLockStateException("Please login to save credit card.");
        CreditCard creditCard = vaultService.saveCreditCard(saveCreditCardRequest, user.getVault());
        userRepository.save(user);
        return saveCreditCardResponseMap(creditCard);
    }

    @Override
    public EditCreditCardResponse editCreditCard(EditCreditCardRequest editCreditCardRequest) {
        User user = userRepository.findByUsername(editCreditCardRequest.getUsername());
        if (user == null) throw new UserNotFoundException(String.format("User %s does not exist.", editCreditCardRequest.getUsername()));
        if (user.isLocked()) throw new ProfileLockStateException("Please login to edit credit card.");
        CreditCard creditCard = vaultService.editCreditCard(editCreditCardRequest, user.getVault());
        return editCreditCardResponseMap(creditCard);
    }

    @Override
    public ViewCreditCardResponse viewCreditCard(ViewCreditCardRequest viewCreditCardRequest) {
        User user = userRepository.findByUsername(viewCreditCardRequest.getUsername());
        if (user == null) throw new UserNotFoundException(String.format("User %s does not exist.", viewCreditCardRequest.getUsername()));
        if (user.isLocked()) throw new ProfileLockStateException("Please login to view credit card.");
        CreditCard creditCard = findCreditCardInVault(viewCreditCardRequest.getTitle(), user.getVault());
        return viewCreditCardResponseMap(creditCard);
    }

    @Override
    public DeleteCreditCardResponse deleteCreditCard(DeleteCreditCardRequest deleteCreditCardRequest) {
        User user = userRepository.findByUsername(deleteCreditCardRequest.getUsername());
        if (user == null) throw new UserNotFoundException(String.format("User %s does not exist.", deleteCreditCardRequest.getUsername()));
        if (user.isLocked()) throw new ProfileLockStateException("Please login to delete credit card.");
        if (!user.getMasterPassword().equals(deleteCreditCardRequest.getMasterPassword())) throw new InvalidPasswordException("Incorrect password. Please try again.");
        DeleteCreditCardResponse deleteCreditCardResponse = vaultService.deleteCreditCard(deleteCreditCardRequest, user.getVault());
        userRepository.save(user);
        return deleteCreditCardResponse;
    }

    @Override
    public SavePassportResponse savePassport(SavePassportRequest savePassportRequest) {
        User user = userRepository.findByUsername(savePassportRequest.getUsername());
        if (user == null) throw new UserNotFoundException(String.format("User %s does not exist.", savePassportRequest.getUsername()));
        if (user.isLocked()) throw new ProfileLockStateException("Please login to save passport.");
        Passport passport = vaultService.savePassport(savePassportRequest, user.getVault());
        userRepository.save(user);
        return savePassportResponseMap(passport);
    }

    @Override
    public EditPassportResponse editPassport(EditPassportRequest editPassportRequest) {
        User user = userRepository.findByUsername(editPassportRequest.getUsername().toLowerCase());
        if (user == null) throw new UserNotFoundException(String.format("User %s does not exist.", editPassportRequest.getUsername()));
        if (user.isLocked()) throw new ProfileLockStateException("Please login to edit passport.");
        Passport passport = vaultService.editPassport(editPassportRequest, user.getVault());
        userRepository.save(user);
        return editPassportResponseMap(passport);
    }

    @Override
    public ViewPassportResponse viewPassport(ViewPassportRequest viewPassportRequest) {
        User user = userRepository.findByUsername(viewPassportRequest.getUsername().toLowerCase());
        if (user == null) throw new UserNotFoundException(String.format("User %s does not exist.", viewPassportRequest.getUsername()));
        if (user.isLocked()) throw new ProfileLockStateException("Please login to view password.");
        Passport passport = findPassportInVault(viewPassportRequest.getTitle(), user.getVault());
        return viewPassportResponseMap(passport);
    }

    @Override
    public DeletePassportResponse deletePassport(DeletePassportRequest deletePassportRequest) {
        User user = userRepository.findByUsername(deletePassportRequest.getUsername());
        if (user == null) throw new UserNotFoundException(String.format("User %s does not exist.", deletePassportRequest.getUsername()));
        if (user.isLocked()) throw new ProfileLockStateException("Please login to delete passport.");
        if (!user.getMasterPassword().equals(deletePassportRequest.getMasterPassword())) throw new InvalidPasswordException("Incorrect password. Please try again.");
        DeletePassportResponse deletePassportResponse = vaultService.deletePassport(deletePassportRequest, user.getVault());
        userRepository.save(user);
        return deletePassportResponse;
    }

}

