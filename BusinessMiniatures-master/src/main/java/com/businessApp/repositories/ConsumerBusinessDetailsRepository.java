package com.businessApp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import com.businessApp.model.ConsumerBusinessDetails;

@Transactional
public interface ConsumerBusinessDetailsRepository
		extends
			MongoRepository<ConsumerBusinessDetails, Long>,
			CustomConsumerBusinessDetailsRepository
{

}
