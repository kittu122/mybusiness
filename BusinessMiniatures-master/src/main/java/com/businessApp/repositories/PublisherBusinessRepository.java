package com.businessApp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import com.businessApp.model.PublisherBusiness;

@Transactional
public interface PublisherBusinessRepository
		extends
			MongoRepository<PublisherBusiness, Long>,
			CustomPublisherBusinessRepository
{

}
