package com.businessApp.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.businessApp.bean.ConsumerScannnedBusinesses;
import com.businessApp.bean.LoginResult;
import com.businessApp.bean.SaveConsumerBusinessDetails;
import com.businessApp.bean.UserBean;
import com.businessApp.model.ConsumerBusinessDetails;
import com.businessApp.model.CountriesList;
import com.businessApp.model.PublisherBusiness;
import com.businessApp.model.User;
import com.businessApp.repositories.ConsumerBusinessDetailsRepository;
import com.businessApp.repositories.UserRepository;
import com.businessApp.util.EncryptUtil;

@Service
public class UserService
{

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepo;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	LoginResult loginResult;

	@Autowired
	ConsumerBusinessDetailsRepository consumerBusiness;

	@Autowired
	PublisherService publisherService;

	public void save(User user) throws Exception
	{
		this.userRepo.save(user);
	}

	public List<User> publisherList(String id) throws Exception
	{
		return this.userRepo.listUser(id);
	}

	public Object updateUser(User user) throws Exception
	{
		return this.userRepo.updateUser(user);
	}

	public String saveUser(User user) throws Exception
	{

		if ((user.getEmail() != null) || (user.getPhone() != null))
		{
			if ((user.getEmail() != null) && (user.getPhone() != null))
			{
				User emaiExist = emailExist(user);
				User phoneExist = phonelExist(user);

				if ((emaiExist != null) && (phoneExist != null))
				{
					return "Email_And_Phone_Exist";
				}

				else if ((emaiExist != null))
				{
					return "Email_Exist";
				}

				else if ((phoneExist != null))
				{
					return "Phone_Exist";
				}

				else
				{
					return this.userRepo.saveUser(user);
				}
			}

			else if (user.getEmail() != null)
			{
				User emaiExist = emailExist(user);

				if ((emaiExist != null))
				{

					return "Email_Exist";
				}

				else
				{

					return this.userRepo.saveUser(user);
				}
			}

			else if (user.getPhone() != null)
			{
				User phoneExist = phonelExist(user);

				if ((phoneExist != null))
				{

					return "Phone_Exist";
				}

				else
				{

					return this.userRepo.saveUser(user);
				}
			}

		}

		else
		{
			return "Email_Req";
		}

		return "Email_Req";

	}

	public User emailExist(User user)
	{

		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(user.getEmail()));
		query.fields().include("email");

		User emailExist = this.mongoTemplate.findOne(query, User.class);
		return emailExist;
	}

	public User phonelExist(User user)
	{
		Query query2 = new Query();
		query2.addCriteria(Criteria.where("phone").is(user.getPhone()));
		query2.fields().include("phone");
		User phonelExist = this.mongoTemplate.findOne(query2, User.class);
		return phonelExist;
	}

	public User getUserById(String userId) throws Exception
	{

		return this.userRepo.getUserById(userId);
	}

	// ------------Login -----------------------

	public String loginUserWithId(UserBean user) throws Exception
	{

		if ((user.getLogInId() != null) && ((user.getUserType() != null)))
		{
			boolean loginType = phoneValidator(user.getLogInId());

			if (!loginType)
			{
				boolean emailValid = emailValidator(user.getLogInId());
				if ((emailValid != true))
				{

					return "INVALID_LOGIN";
				}

				else
				{

					return loginUser(user);

				}
			}

			else
			{
				boolean phoneValid = phoneValidator(user.getLogInId());

				if ((phoneValid != true))
				{
					return "INVALID_LOGIN";
				}

				else
				{

					return loginUser(user);
				}
			}

		}

		else
		{

			return "EmailOrPhoneRequired";

		}

	}

	public boolean emailValidator(String email)
	{

		try
		{

			String emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
			Boolean b = email.matches(emailregex);

			if (b == false)
			{
				return false;
			} else if (b == true)
			{
				return true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();

		}
		return false;
	}

	public boolean phoneValidator(String phoneNo)
	{
		if (phoneNo.matches("\\d{10}"))
		{
			return true;
		} else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
		{
			return true;
		} else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
		{
			return true;
		} else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public String toFindLogInType(String loginId)
	{
		String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";

		if (loginId.matches(pattern))
		{
			return "phone";
		}

		else
		{
			return "email";
		}

	}

	public User getUserDetails()
	{

		return this.loginResult.getUserData();
	}

	public String loginUser(UserBean user) throws Exception
	{

		int userType = Integer.parseInt(user.getUserType());
		User userDetails = null;
		Query query = new Query();

		if (userType == 2)
		{
			query.addCriteria(Criteria.where("type").is(userType).orOperator(
					Criteria.where("email").is(user.getLogInId()),
					Criteria.where("phone").is(user.getLogInId())));
		} else
		{

			if (user.getPassword() != null)
			{

				user.setPassword(EncryptUtil.encrypt(user.getPassword()));
			}
			query.addCriteria(Criteria.where("password").is(user.getPassword())
					.andOperator(Criteria.where("type").is(userType).orOperator(
							Criteria.where("email").is(user.getLogInId()),
							Criteria.where("phone").is(user.getLogInId()))));
		}
		userDetails = this.mongoTemplate.findOne(query, User.class);

		if (userDetails != null)
		{

			this.loginResult.setUserData(userDetails);
			if (userType == 2)
			{
				this.loginResult.setBusinessData(this.publisherService
						.businessListBypublisherId(userDetails.getId()));
			} else
			{
				this.loginResult.setBusinessData(null);
			}
			return "Success";

		}

		else
		{

			return "InvalidUser";
		}
	}

	public String deleteUserById(String userId)
	{

		if (userId != null)
		{
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(userId));
			User deletedEmp = this.mongoTemplate.findAndRemove(query,
					User.class);

			if (deletedEmp != null)
			{
				return "SUCCESS";
			}

			else
			{
				return "UNSUCCESS";
			}

		} else
		{
			return "INVALID";
		}

	}

	public Object consumerBusinessDetails(ConsumerBusinessDetails consumerBusin)
	{

		if (consumerBusin.getMacId() != null)
		{

			ConsumerBusinessDetails conBusData = getConsumerBusinessDetails(
					consumerBusin.getMacId());

			if (conBusData == null)
			{

				this.consumerBusiness.save(consumerBusin);

				return consumerBusin;
			}

			else
			{
				ConsumerScannnedBusinesses consumer = getComsumerBusinessList(
						conBusData);
				return consumer;
			}
		} else
		{
			return "Data_Required";
		}

	}

	public Object saveConsumerBusinessDetails(
			SaveConsumerBusinessDetails consumerBusiness)
	{

		String s;

		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(consumerBusiness.getId())
				.andOperator(Criteria.where("macId")
						.is(consumerBusiness.getMacId())));
		Update update = new Update();

		update.addToSet("businesses", consumerBusiness.getbId());

		ConsumerBusinessDetails add = this.mongoTemplate.findAndModify(query,
				update, ConsumerBusinessDetails.class);

		if (add != null)
		{
			s = "Success";
		} else
		{
			s = "UnSuccess";
		}

		return s;

	}

	public Object consumerBusinessList(String macId)

	{

		ConsumerBusinessDetails tmp = getConsumerBusinessDetails(macId);

		if (tmp != null)
		{

			return getComsumerBusinessList(tmp);
		}

		else
		{
			return "INVALID";
		}

	}

	private ConsumerScannnedBusinesses getComsumerBusinessList(
			ConsumerBusinessDetails tmp)
	{
		ConsumerScannnedBusinesses consumer = new ConsumerScannnedBusinesses();
		List<PublisherBusiness> pB = new ArrayList<>();

		consumer.setId(tmp.getId());
		consumer.setMacId(tmp.getMacId());

		if ((tmp.getBusinesses() != null) && !(tmp.getBusinesses()).isEmpty())
		{

			for (int i = 0; i < tmp.getBusinesses().size(); i++)
			{

				Query query2 = new Query();
				query2.addCriteria(
						Criteria.where("id").is(tmp.getBusinesses().get(i)));
				PublisherBusiness pBData = this.mongoTemplate.findOne(query2,
						PublisherBusiness.class);
				if (pBData != null)
				{
					this.publisherService.addNaming(pBData);
					pB.add(pBData);
				}
			}

			consumer.setBusinesses(pB);

		}

		return consumer;
	}

	ConsumerBusinessDetails getConsumerBusinessDetails(String macId)
	{

		Query query = new Query();
		query.addCriteria(Criteria.where("macId").is(macId));
		ConsumerBusinessDetails tmp = this.mongoTemplate.findOne(query,
				ConsumerBusinessDetails.class);

		return tmp;
	}

	public CountriesList countriesList()
	{

		Query query = new Query();

		CountriesList countriesData = this.mongoTemplate.findOne(query,
				CountriesList.class);
		return countriesData;
	}

}
