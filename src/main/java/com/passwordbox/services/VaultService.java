package com.passwordbox.services;

import com.passwordbox.data.models.*;
import com.passwordbox.dataTransferObjects.requests.*;
import com.passwordbox.dataTransferObjects.responses.DeleteCreditCardResponse;
import com.passwordbox.dataTransferObjects.responses.DeleteLoginInfoResponse;
import com.passwordbox.dataTransferObjects.responses.DeletePassportResponse;

public interface VaultService {

    LoginInfo saveLoginInfo(SaveLoginInfoRequest saveLoginInfoRequest, Vault vault);

    LoginInfo editLoginInfo(EditLoginInfoRequest editLoginInfoRequest, Vault vault);

    DeleteLoginInfoResponse deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest, Vault vault);

    CreditCard saveCreditCard(SaveCreditCardRequest saveCreditCardRequest, Vault vault);

    CreditCard editCreditCard(EditCreditCardRequest editCreditCardRequest, Vault vault);

    DeleteCreditCardResponse deleteCreditCard(DeleteCreditCardRequest deleteCreditCardRequest, Vault vault);

    Passport savePassport(SavePassportRequest savePassportRequest, Vault vault);
    Passport editPassport(EditPassportRequest editPassportRequest, Vault vault);

    DeletePassportResponse deletePassport(DeletePassportRequest deletePassportRequest, Vault vault);

}
