package com.businessApp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import com.businessApp.model.Business;

@Transactional
public interface BusinessRepository
		extends
			MongoRepository<Business, Long>,
			CustomBusinessRepository
{

}
