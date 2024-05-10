package com.passwordbox.services;


import com.passwordbox.data.models.Passport;
import com.passwordbox.data.models.Vault;
import com.passwordbox.data.repositories.PassportRepository;
import com.passwordbox.dataTransferObjects.requests.DeletePassportRequest;
import com.passwordbox.dataTransferObjects.requests.EditPassportRequest;
import com.passwordbox.dataTransferObjects.requests.SavePassportRequest;
import com.passwordbox.dataTransferObjects.responses.DeletePassportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.passwordbox.utilities.FindDetails.findPassportInVault;
import static com.passwordbox.utilities.Mappers.*;
import static com.passwordbox.utilities.PassportDatesValidation.*;
import static com.passwordbox.utilities.TitleInputValidation.validateEditedPassportTitle;
import static com.passwordbox.utilities.TitleInputValidation.validatePassportTitle;
import static com.passwordbox.utilities.ValidateInputs.validatePassportNumberField;

@Service
public class PassportServiceImplementation implements PassportService{

    @Autowired
    private PassportRepository passportRepository;

    @Override
    public Passport savePassport(SavePassportRequest savePassportRequest, Vault vault) {
        validatePassportTitle(savePassportRequest.getTitle(), vault);
        validatePassportNumberField(savePassportRequest.getPassportNumber());
        validatePassportDates(savePassportRequest.getIssueDate(), savePassportRequest.getExpiryDate());
        Passport passport = savePassportRequestMap(savePassportRequest);
        passportRepository.save(passport);
        return passport;
    }

    @Override
    public Passport editPassport(EditPassportRequest editPassportRequest, Vault vault) {
        Passport passport = findPassportInVault(editPassportRequest.getTitle().toLowerCase(), vault);
        validateEditedPassportTitle(editPassportRequest.getTitle(), editPassportRequest.getEditedTitle(), vault);
        if (editPassportRequest.getEditedPassportNumber() != null) validatePassportNumberField(editPassportRequest.getEditedPassportNumber());
        if (areEditedIssueAndExpiryDateFilled(editPassportRequest)) validateEditPassportDatesInputs(editPassportRequest);
        Passport updatedPassport = editPassportRequestMap(editPassportRequest, passport);
        passportRepository.save(passport);
        return updatedPassport;
    }

    @Override
    public DeletePassportResponse deletePassport(DeletePassportRequest deletePassportRequest, Vault vault) {
        Passport passport = findPassportInVault(deletePassportRequest.getTitle(), vault);
        DeletePassportResponse deletePassportResponse = deletePassportResponseMap(passport);
        passportRepository.delete(passport);
        return deletePassportResponse;
    }

}