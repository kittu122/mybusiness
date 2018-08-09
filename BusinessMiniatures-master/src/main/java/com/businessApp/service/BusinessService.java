package com.businessApp.service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.businessApp.model.Business;
import com.businessApp.model.PublisherBusiness;
import com.businessApp.repositories.BusinessRepository;
import com.businessApp.repositories.SubscriptionSettingsRepository;
@Service
public class BusinessService
{

	private static Logger logger = LoggerFactory
			.getLogger(BusinessService.class);
	@Autowired
	BusinessRepository businessRepo;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	SubscriptionSettingsRepository subRepo;

	@Autowired
	PublisherService pbService;

	public void save(Business business) throws Exception
	{
		this.businessRepo.save(business);
	}

	public String update(Business business) throws Exception
	{
		return this.businessRepo.updateBusiness(business);
	}

	public List<Business> businessList() throws Exception
	{
		return this.businessRepo.listBusiness();
	}

	public Object listServiceCategories(String businessId) throws Exception
	{

		Query sCatQry = new Query(Criteria.where("id").is(businessId));

		Business templateData = this.mongoTemplate.findOne(sCatQry,
				Business.class);
		if (templateData != null)
		{
			return templateData;
		} else
		{

			Query query = new Query(Criteria.where("id").is(businessId));

			PublisherBusiness pbBusinessData = this.mongoTemplate.findOne(query,
					PublisherBusiness.class);

			if (pbBusinessData != null)
			{
				this.pbService.addNaming(pbBusinessData);
			}

			return pbBusinessData;
		}

		// return this.businessRepo.listServiceCategories(businessId);
	}

	public List<Business> allBusinessServiceCategories() throws Exception
	{

		return this.businessRepo.allBusinessServiceCategories();
	}

	public List<Business> listServiceCategoriesByBId(String businessId)
	{

		Query sCatQry = new Query(Criteria.where("id").is(businessId));

		return this.mongoTemplate.find(sCatQry, Business.class);
	}

	public String deleteBusinessById(String businessId)
	{
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(businessId));
		Business deletedEmp = this.mongoTemplate.findAndRemove(query,
				Business.class);

		if (deletedEmp != null)
		{
			return "SUCCESS";
		}

		else
		{
			return "UNSUCCESS";
		}
	}

}
