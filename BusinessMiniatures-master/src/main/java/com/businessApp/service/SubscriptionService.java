package com.businessApp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.businessApp.model.Subscription;
import com.businessApp.model.SubscriptionSettings;
import com.mongodb.WriteResult;

@Service
public class SubscriptionService
{

	@Autowired
	MongoTemplate mongoTemplate;

	// @Autowired
	// SubscriptionSettingsRepository subRepo;

	public void subscriptionSave(Subscription sub)
	{
		this.mongoTemplate.save(sub);

	}

	public void subscriptionSettingsSave(SubscriptionSettings sub)
	{
		this.mongoTemplate.save(sub);

	}

	public List<SubscriptionSettings> listSubscriptionDetails()
	{
		Query query = new Query();

		return this.mongoTemplate.find(query, SubscriptionSettings.class);

	}

	public String updateSubscriptionDetails(SubscriptionSettings updateSub)
	{

		if (updateSub.getId() != null)
		{
			Query updQry = new Query(
					Criteria.where("id").is(updateSub.getId()));
			Update upd = new Update();

			if ((updateSub.getType() != null))
			{
				upd.set("type", updateSub.getType());
			}

			if ((updateSub.getPrice() != 0))
			{
				upd.set("price", updateSub.getPrice());
			}

			if ((updateSub.getDuration() != null))
			{
				upd.set("duration", updateSub.getDuration());
			}

			if ((updateSub.getBusinessCount() != 0))
			{
				upd.set("businessCount", updateSub.getBusinessCount());
			}

			if (updateSub.getModifiedTime() == null)
			{
				Date date = new Date();

				upd.set("modifiedTime", date);
			}

			WriteResult tmp = this.mongoTemplate.upsert(updQry, upd,
					SubscriptionSettings.class);

			if (tmp.getN() > 0)
			{
				return "SUCCESS";
			} else
			{
				return "UNSUCCESS";
			}

		}

		return "UNSUCCESS";
	}

	public String deleteSubScriptionSettings(String subId)
	{

		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(subId));
		SubscriptionSettings deletedEmp = this.mongoTemplate
				.findAndRemove(query, SubscriptionSettings.class);

		if (deletedEmp != null)
		{
			return "SUCCESS";
		}

		else
		{
			return "UNSUCCESS";
		}
	}

	public SubscriptionSettings getSubscriptionDetails(String subId)
	{

		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(subId));
		SubscriptionSettings tmp = this.mongoTemplate.findOne(query,
				SubscriptionSettings.class);

		if (tmp != null)
		{
			return tmp;
		}

		else
		{
			return null;
		}

	}

}
