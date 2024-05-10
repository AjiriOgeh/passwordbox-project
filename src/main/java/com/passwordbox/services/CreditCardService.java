package com.passwordbox.services;

import com.passwordbox.data.models.CreditCard;
import com.passwordbox.data.models.Vault;
import com.passwordbox.dataTransferObjects.requests.DeleteCreditCardRequest;
import com.passwordbox.dataTransferObjects.requests.EditCreditCardRequest;
import com.passwordbox.dataTransferObjects.requests.SaveCreditCardRequest;
import com.passwordbox.dataTransferObjects.responses.DeleteCreditCardResponse;

public interface CreditCardService {
    CreditCard saveCreditCard(SaveCreditCardRequest saveCreditCardRequest, Vault vault);

    CreditCard editCreditCard(EditCreditCardRequest editCreditCardRequest, Vault vault);

    DeleteCreditCardResponse deleteCreditCard(DeleteCreditCardRequest deleteCreditCardRequest, Vault vault);

}
