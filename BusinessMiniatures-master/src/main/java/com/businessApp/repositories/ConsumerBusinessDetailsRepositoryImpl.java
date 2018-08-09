package com.businessApp.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class ConsumerBusinessDetailsRepositoryImpl
		implements
			CustomConsumerBusinessDetailsRepository
{

	@Autowired
	MongoTemplate mongoTemplate;
}
