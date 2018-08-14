package com.businessApp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.businessApp.model.User;

public interface UserRepository
		extends
			MongoRepository<User, Long>,
			CustomUserRepository

{

}
