package com.businessApp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.businessApp.model.EmployeePto;

public interface PublisherBusinessEmpPtoRepository
		extends
			MongoRepository<EmployeePto, Long>,
			CustomPublisherBusinessEmpPtoRepository
{

}
