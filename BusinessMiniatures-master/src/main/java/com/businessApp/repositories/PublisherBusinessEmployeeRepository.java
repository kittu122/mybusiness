package com.businessApp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.businessApp.model.PublisherBusinessEmployee;

public interface PublisherBusinessEmployeeRepository
		extends
			MongoRepository<PublisherBusinessEmployee, Long>,
			CustomPublisherBusinessEmployeeRepository
{

}
