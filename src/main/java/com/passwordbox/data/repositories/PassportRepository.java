package com.passwordbox.data.repositories;

import com.passwordbox.data.models.Passport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PassportRepository extends MongoRepository<Passport, String> {
}
