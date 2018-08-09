package com.businessApp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.businessApp.model.QRCode;

public interface QRCodeRepository extends MongoRepository<QRCode, Long>
{

}
