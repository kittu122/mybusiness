package com.businessApp.repositories;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.businessApp.model.Business;

public class BusinessRepositoryImpl implements CustomBusinessRepository
{

	private static Logger logger = LoggerFactory
			.getLogger(BusinessRepositoryImpl.class);
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public String updateBusiness(Business business) throws Exception
	{

		if (business.getId() != null)
		{

			Query updQry = new Query(Criteria.where("id").is(business.getId()));
			Update upd = new Update();

			if (business.getName() != null)
			{
				upd.set("name", business.getName());
			}
			if (business.getDescription() != null)
			{
				upd.set("description", business.getDescription());
			}

			if (business.getServiceCategory() != null)
			{
				upd.set("serviceCategory", business.getServiceCategory());
			}

			if (business.getModifiedBy() != null)
			{
				upd.set("modifiedBy", business.getModifiedBy());
			}

			if (business.getModifiedTime() == null)
			{
				Date date = new Date();

				upd.set("modifiedTime", date);
			}

			this.mongoTemplate.upsert(updQry, upd, Business.class);

			return "Business has been successfully updated";
		}

		else
		{
			return "Business has not been  updated !";
		}

	}

	@Override
	public List<Business> listBusiness() throws Exception
	{
		Query listQry = new Query();

		listQry.fields().include("id");
		listQry.fields().include("name");
		listQry.fields().include("description");
		listQry.fields().include("icon");
		listQry.fields().include("createdBy");
		listQry.fields().include("modifiedBy");
		listQry.fields().include("serviceCategory");
		listQry.fields().include("createdTime");
		listQry.fields().include("modifiedTime");

		return this.mongoTemplate.find(listQry, Business.class);

	}

	// @Override
	// public Object listServiceCategories(String businessId) throws Exception
	// {
	//
	// Query sCatQry = new Query(Criteria.where("id").is(businessId));
	//
	// Business templateData = this.mongoTemplate.findOne(sCatQry,
	// Business.class);
	// if (templateData != null)
	// {
	// return templateData;
	// } else
	// {
	//
	// Query query = new Query(Criteria.where("id").is(businessId));
	//
	// PublisherBusiness pbBusinessData = this.mongoTemplate.findOne(query,
	// PublisherBusiness.class);
	//
	// return pbBusinessData;
	// }
	//
	// }

	@Override
	public List<Business> allBusinessServiceCategories() throws Exception
	{
		Query query = new Query();
		query.fields().include("id");
		query.fields().include("name");
		query.fields().include("serviceCategory");

		return this.mongoTemplate.find(query, Business.class);
	}

}
