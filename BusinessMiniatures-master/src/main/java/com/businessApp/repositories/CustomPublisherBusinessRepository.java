package com.businessApp.repositories;

import org.springframework.transaction.annotation.Transactional;

import com.businessApp.model.PublisherBusiness;

@Transactional
public interface CustomPublisherBusinessRepository
{
	public void isBusinessNameExists() throws Exception;

	public String updateBusines(PublisherBusiness updatePublBusiness)
			throws Exception;

	// public List<PublisherBusiness> publisherBusinessListByBId(String
	// businessId)
	// throws Exception;

}
