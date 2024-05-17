package com.passwordbox.services;

import com.passwordbox.data.models.LoginInfo;
import com.passwordbox.data.models.Vault;
import com.passwordbox.data.repositories.LoginInfoRepository;
import com.passwordbox.dataTransferObjects.requests.DeleteLoginInfoRequest;
import com.passwordbox.dataTransferObjects.requests.EditLoginInfoRequest;
import com.passwordbox.dataTransferObjects.requests.SaveLoginInfoRequest;
import com.passwordbox.dataTransferObjects.responses.DeleteLoginInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.passwordbox.utilities.FindDetails.findLoginInfoInVault;
import static com.passwordbox.utilities.Mappers.*;
import static com.passwordbox.utilities.TitleInputValidation.validateEditedLoginInfoTitle;
import static com.passwordbox.utilities.TitleInputValidation.validateLoginInfoTitle;
import static com.passwordbox.utilities.ValidateInputs.*;

@Service
public class LoginInfoServiceImplementation implements LoginInfoService {

    @Autowired
    private LoginInfoRepository loginInfoRepository;
    @Override
    public LoginInfo saveLoginInfo(SaveLoginInfoRequest saveLoginInfoRequest, Vault vault) {
        validateLoginInfoTitle(saveLoginInfoRequest.getTitle(), vault);
        validateWebsite(saveLoginInfoRequest.getWebsite());
        validateLoginID(saveLoginInfoRequest.getLoginId());
        LoginInfo loginInfo = saveNewLoginInfoRequestMap(saveLoginInfoRequest);
        loginInfoRepository.save(loginInfo);
        return loginInfo;
    }

    @Override
    public LoginInfo editLoginInfo(EditLoginInfoRequest editLoginInfoRequest, Vault vault) {
        LoginInfo loginInfo = findLoginInfoInVault(editLoginInfoRequest.getTitle().toLowerCase(), vault);
        validateEditedLoginInfoTitle(editLoginInfoRequest.getTitle(), editLoginInfoRequest.getEditedTitle(), vault);
        validateWebsite(editLoginInfoRequest.getEditedWebsite());
        LoginInfo updatedLoginInfo = editLoginInfoRequestMap(editLoginInfoRequest, loginInfo);
        loginInfoRepository.save(updatedLoginInfo);
        return updatedLoginInfo;
    }

    @Override
    public DeleteLoginInfoResponse deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest, Vault vault) {
        LoginInfo loginInfo = findLoginInfoInVault(deleteLoginInfoRequest.getTitle().toLowerCase(), vault);
        DeleteLoginInfoResponse deleteLoginInfoResponse = deleteLoginInfoResponseMap(loginInfo);
        loginInfoRepository.delete(loginInfo);
        return deleteLoginInfoResponse;
    }

}