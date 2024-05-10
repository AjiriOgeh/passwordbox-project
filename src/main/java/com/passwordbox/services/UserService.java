package com.passwordbox.services;

import com.passwordbox.dataTransferObjects.requests.*;
import com.passwordbox.dataTransferObjects.responses.*;

public interface UserService {
    RegisterResponse signUp(SignUpRequest signUpRequest);

    LogoutResponse logout(LogoutRequest logoutRequest);

    LoginResponse login(LoginRequest loginRequest);

    SaveLoginInfoResponse saveLoginInfo(SaveLoginInfoRequest saveLoginInfoRequest);

    EditLoginInfoResponse editLoginInfo(EditLoginInfoRequest editLoginInfoRequest);

    ViewLoginInfoResponse viewLoginInfo(ViewLoginInfoRequest viewLoginInfoRequest);

    DeleteLoginInfoResponse deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest);

    GeneratePasswordResponse generatePassword(GeneratePasswordRequest generatePasswordRequest);

    SaveCreditCardResponse saveCreditCard(SaveCreditCardRequest saveCreditCardRequest);

    EditCreditCardResponse editCreditCard(EditCreditCardRequest editCreditCardRequest);

    ViewCreditCardResponse viewCreditCard(ViewCreditCardRequest viewCreditCardRequest);

    DeleteCreditCardResponse deleteCreditCard(DeleteCreditCardRequest deleteCreditCardRequest);

    SavePassportResponse savePassport(SavePassportRequest savePassportRequest);

    EditPassportResponse editPassport(EditPassportRequest editPassportRequest);

    ViewPassportResponse viewPassport(ViewPassportRequest viewPassportRequest);

    DeletePassportResponse deletePassport(DeletePassportRequest deletePassportRequest);


}
