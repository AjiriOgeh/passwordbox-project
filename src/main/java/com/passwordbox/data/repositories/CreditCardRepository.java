package com.passwordbox.data.repositories;

import com.passwordbox.data.models.CreditCard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreditCardRepository extends MongoRepository<CreditCard, String> {
}
