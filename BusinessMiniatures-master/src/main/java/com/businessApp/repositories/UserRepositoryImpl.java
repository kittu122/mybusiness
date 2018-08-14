package com.businessApp.repositories;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.businessApp.model.User;
import com.businessApp.util.EncryptUtil;

public class UserRepositoryImpl implements CustomUserRepository
{
	private static Logger logger = LoggerFactory
			.getLogger(UserRepositoryImpl.class);

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public String saveUser(User user) throws Exception
	{
		if (user.getPassword() != null)
		{
			// System.out.println(user.getPassword());

			user.setPassword(EncryptUtil.encrypt(user.getPassword()));
			// System.out.println(EncryptUtil.encrypt(user.getPassword()));
		}

		this.mongoTemplate.save(user);
		return "User_Created";
	}

	@Override
	public List<User> listUser(String id) throws Exception
	{
		int type = Integer.parseInt(id);

		Query query = new Query();

		query.addCriteria(Criteria.where("type").is(type));

		query.fields().include("id");
		query.fields().include("name");
		query.fields().include("type");
		query.fields().include("address1");
		query.fields().include("address2");
		query.fields().include("city");
		query.fields().include("state");
		query.fields().include("country");
		query.fields().include("zip");
		query.fields().include("phone");
		query.fields().include("email");
		query.fields().include("modifiedBy");
		query.fields().include("modifiedTime");

		return this.mongoTemplate.find(query, User.class);
	}

	@Override
	public User getUserById(String userId) throws Exception
	{

		Query listQry = new Query();
		listQry.addCriteria(Criteria.where("id").is(userId));
		listQry.fields().include("id");
		listQry.fields().include("name");
		listQry.fields().include("type");
		listQry.fields().include("address1");
		listQry.fields().include("address2");
		listQry.fields().include("city");
		listQry.fields().include("state");
		listQry.fields().include("country");
		listQry.fields().include("zip");
		listQry.fields().include("phone");
		listQry.fields().include("email");
		listQry.fields().include("modifiedBy");
		listQry.fields().include("modifiedTime");

		return this.mongoTemplate.findOne(listQry, User.class);
	}

	@Override
	public Object updateUser(User user) throws Exception
	{

		if (user.getId() != null)
		{
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(user.getId()));
			Update update = new Update();
			if (user.getName() != null)
			{
				update.set("name", user.getName());
			}

			if (user.getCity() != null)
			{
				update.set("city", user.getCity());
			}

			if (user.getState() != null)
			{
				update.set("state", user.getState());
			}

			if (user.getCountry() != null)
			{
				update.set("country", user.getCountry());
			}

			if (user.getZip() != null)
			{
				update.set("zip", user.getZip());
			}

			if (user.getAddress1() != null)
			{
				update.set("address1", user.getAddress1());
			}

			if (user.getAddress2() != null)
			{
				update.set("address2", user.getAddress2());
			}

			if (user.getModifiedTime() != null)
			{
				update.set("modifiedTime", user.getModifiedTime());
			}
			query.fields().exclude("phone");
			query.fields().exclude("email");
			query.fields().exclude("password");

			return this.mongoTemplate.findAndModify(query, update, User.class);

		} else
		{
			return null;

		}

	}
}
