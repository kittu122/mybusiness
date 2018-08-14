package com.businessApp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.businessApp.model.Subscription;

public interface SubscriptionRepository
		extends
			MongoRepository<Subscription, Long>
{

}
