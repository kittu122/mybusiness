package com.businessApp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import com.businessApp.model.BusinessBreakConfig;

@Transactional
public interface BusinessBreakConfigRepository
		extends
			MongoRepository<BusinessBreakConfig, Long>
{

}
