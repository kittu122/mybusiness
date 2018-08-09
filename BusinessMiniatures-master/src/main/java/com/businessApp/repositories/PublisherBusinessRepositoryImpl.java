package com.businessApp.repositories;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.businessApp.model.PublisherBusiness;

public class PublisherBusinessRepositoryImpl
		implements
			CustomPublisherBusinessRepository
{

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void isBusinessNameExists() throws Exception
	{

	}

	@Override
	public String updateBusines(PublisherBusiness upPublB) throws Exception
	{
		if (upPublB.getId() != null)
		{
			Query updQry = new Query(Criteria.where("id").is(upPublB.getId()));
			Update upd = new Update();

			if (upPublB.getName() != null)
			{
				upd.set("name", upPublB.getName());
			}
			if (upPublB.getAddress1() != null)
			{
				upd.set("address1", upPublB.getAddress1());
			}

			if (upPublB.getAddress2() != null)
			{
				upd.set("address2", upPublB.getAddress2());
			}

			if (upPublB.getCity() != null)
			{
				upd.set("city", upPublB.getCity());
			}

			if (upPublB.getState() != null)
			{
				upd.set("state", upPublB.getState());
			}

			if (upPublB.getCountry() != null)
			{
				upd.set("country", upPublB.getCountry());
			}

			if (upPublB.getZipCode() != null)
			{
				upd.set("zipCode", upPublB.getZipCode());
			}

			if (upPublB.getPhone() != null)
			{
				upd.set("phone", upPublB.getPhone());
			}

			if (upPublB.getEmail() != null)
			{
				upd.set("email", upPublB.getEmail());
			}

			if (upPublB.getWebsite() != null)
			{
				upd.set("website", upPublB.getWebsite());
			}

			if (upPublB.getNoOfEmployees() != 0)
			{
				upd.set("noOfEmployees", upPublB.getNoOfEmployees());
			}

			if ((upPublB.getServiceCategory() != null)
					&& !(upPublB.getServiceCategory().isEmpty()))
			{

				upd.set("serviceCategory", upPublB.getServiceCategory());
			}

			if ((upPublB.getBusinessHours() != null)
					&& !(upPublB.getBusinessHours().isEmpty()))
			{
				upd.set("businessHours", upPublB.getBusinessHours());
			}

			if (upPublB.getUpdateTime() == null)
			{
				Date date = new Date();

				upd.set("modifiedTime", date);
			}

			this.mongoTemplate.upsert(updQry, upd, PublisherBusiness.class);

			return "Service categories has been successfully updated";

		}

		return "Not Updated";

	}

}
