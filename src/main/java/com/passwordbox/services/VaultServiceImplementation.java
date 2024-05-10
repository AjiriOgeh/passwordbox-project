package com.passwordbox.services;

import com.passwordbox.data.models.*;
import com.passwordbox.dataTransferObjects.requests.*;
import com.passwordbox.dataTransferObjects.responses.DeleteCreditCardResponse;
import com.passwordbox.dataTransferObjects.responses.DeleteLoginInfoResponse;
import com.passwordbox.dataTransferObjects.responses.DeletePassportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.passwordbox.utilities.FindDetails.*;

@Service
public class VaultServiceImplementation implements VaultService{

    @Autowired
    private LoginInfoService loginInfoService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private PassportService passportService;

    @Override
    public LoginInfo saveLoginInfo(SaveLoginInfoRequest saveLoginInfoRequest, Vault vault) {
        LoginInfo loginInfo = loginInfoService.saveLoginInfo(saveLoginInfoRequest, vault);
        vault.getLoginInfos().add(loginInfo);
        return loginInfo;
    }

    @Override
    public LoginInfo editLoginInfo(EditLoginInfoRequest editLoginInfoRequest, Vault vault) {
        return loginInfoService.editLoginInfo(editLoginInfoRequest, vault);
    }

    @Override
    public DeleteLoginInfoResponse deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest, Vault vault) {
        LoginInfo loginInfo = findLoginInfoInVault(deleteLoginInfoRequest.getTitle().toLowerCase(), vault);
        DeleteLoginInfoResponse deleteLoginInfoResponse = loginInfoService.deleteLoginInfo(deleteLoginInfoRequest, vault);
        vault.getLoginInfos().remove(loginInfo);
        return deleteLoginInfoResponse;
    }

    @Override
    public CreditCard saveCreditCard(SaveCreditCardRequest saveCreditCardRequest, Vault vault) {
        CreditCard creditCard = creditCardService.saveCreditCard(saveCreditCardRequest, vault);
        vault.getCreditCards().add(creditCard);
        return creditCard;
    }

    @Override
    public CreditCard editCreditCard(EditCreditCardRequest editCreditCardRequest, Vault vault) {
        return creditCardService.editCreditCard(editCreditCardRequest, vault);
    }

    @Override
    public DeleteCreditCardResponse deleteCreditCard(DeleteCreditCardRequest deleteCreditCardRequest, Vault vault) {
        CreditCard creditCard = findCreditCardInVault(deleteCreditCardRequest.getTitle(), vault);
        DeleteCreditCardResponse deleteCreditCardResponse = creditCardService.deleteCreditCard(deleteCreditCardRequest, vault);
        vault.getCreditCards().remove(creditCard);
        return deleteCreditCardResponse;
    }

    @Override
    public Passport savePassport(SavePassportRequest savePassportRequest, Vault vault) {
        Passport passport = passportService.savePassport(savePassportRequest, vault);
        vault.getPassports().add(passport);
        return passport;
    }

    @Override
    public Passport editPassport(EditPassportRequest editPassportRequest, Vault vault) {
        return passportService.editPassport(editPassportRequest, vault);
    }

    @Override
    public DeletePassportResponse deletePassport(DeletePassportRequest deletePassportRequest, Vault vault) {
        Passport passport = findPassportInVault(deletePassportRequest.getTitle(), vault);
        DeletePassportResponse deletePassportResponse = passportService.deletePassport(deletePassportRequest, vault);
        vault.getPassports().remove(passport);
        return deletePassportResponse;
    }

}