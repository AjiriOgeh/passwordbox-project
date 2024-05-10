package com.passwordbox.services;

import com.passwordbox.data.models.LoginInfo;
import com.passwordbox.data.models.Vault;
import com.passwordbox.dataTransferObjects.requests.DeleteLoginInfoRequest;
import com.passwordbox.dataTransferObjects.requests.EditLoginInfoRequest;
import com.passwordbox.dataTransferObjects.requests.SaveLoginInfoRequest;
import com.passwordbox.dataTransferObjects.responses.DeleteLoginInfoResponse;

public interface LoginInfoService {
    LoginInfo saveLoginInfo(SaveLoginInfoRequest saveLoginInfoRequest, Vault vault);

    LoginInfo editLoginInfo(EditLoginInfoRequest editLoginInfoRequest, Vault vault);

    DeleteLoginInfoResponse deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest, Vault vault);

}
