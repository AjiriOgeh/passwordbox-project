package com.passwordbox.data.repositories;

import com.passwordbox.data.models.LoginInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginInfoRepository extends MongoRepository<LoginInfo, String> {

}
