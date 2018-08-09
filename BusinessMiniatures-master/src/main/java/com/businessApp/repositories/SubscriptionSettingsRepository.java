package com.businessApp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import com.businessApp.model.SubscriptionSettings;

@Transactional
public interface SubscriptionSettingsRepository
		extends
			MongoRepository<SubscriptionSettings, Long>
{

}
