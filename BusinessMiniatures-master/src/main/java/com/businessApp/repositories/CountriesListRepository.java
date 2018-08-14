package com.businessApp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.businessApp.model.CountriesList;

public interface CountriesListRepository
		extends
			MongoRepository<CountriesList, Long>
{

}
