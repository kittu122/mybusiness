package com.businessApp.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.businessApp.bean.LoginResult;
import com.businessApp.bean.ResponseBean;
import com.businessApp.bean.SaveConsumerBusinessDetails;
import com.businessApp.bean.UserBean;
import com.businessApp.constants.StatusConstants;
import com.businessApp.model.ConsumerBusinessDetails;
import com.businessApp.model.User;
import com.businessApp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController
{

	private static Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@Autowired
	LoginResult loginResult;

	@Autowired
	ApplicationContext applicationContext;

	/**
	 * To Create New User
	 * 
	 * @param user
	 * @return To get Message like "User has been successfully created"
	 */
	@PostMapping(path = "/create", consumes = "application/json")

	public ResponseBean saveUser(@RequestBody User user)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			if (user.getModifiedTime() == null)
			{
				user.setModifiedTime(new java.util.Date());
			}

			String report = this.userService.saveUser(user);

			if (report.equals("Email_Req"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("Email Required !");
				respBean.setResult(null);

			} else if (report.equals("Email_Exist"))
			{
				respBean.setCode(StatusConstants.EXIT_ERROR_CODE);
				respBean.setMessage("Email Already Exist !");
				respBean.setResult(null);

			} else if (report.equals("Phone_Exist"))
			{
				respBean.setCode(StatusConstants.EXIT_ERROR_CODE);
				respBean.setMessage("Phone Number Already Exist !");
				respBean.setResult(null);

			} else if (report.equals("Email_And_Phone_Exist"))
			{
				respBean.setCode(StatusConstants.EXIT_ERROR_CODE);
				respBean.setMessage("Email And Phone Number  Already Exist !");
				respBean.setResult(null);

			} else
			{

				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage("User has been successfully created");
				respBean.setResult(null);

			}

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();

		}
		return respBean;
	}

	/**
	 * To get the list of publisher
	 * 
	 * @return List of All Users
	 */
	@GetMapping(path = "/publisherlist/{id}", produces = "application/json")
	public ResponseBean list(@PathVariable("id") String id)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(this.userService.publisherList(id));

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}
		return respBean;
	}

	/**
	 * User Login with his/him Credentials
	 * 
	 * @param user
	 * @return User data and his/him Business Data
	 */
	@PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
	public ResponseBean logIn(@RequestBody UserBean user)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{

			String report = this.userService.loginUserWithId(user);

			if (report.equals("EmailOrPhoneRequired"))

			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("Email or Phone Number Required !");
				respBean.setResult(null);

			} else if (report.equals("INVALID_LOGIN"))
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage("Invalid Login Details!");
				respBean.setResult(null);

			}

			else if (report.equals("InvalidUser"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("Invalid User Details or Sign Up !");
				respBean.setResult(null);

			}

			else if (report.equals("Success"))
			{

				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);

				HashMap<String, Object> resultData = new HashMap<>();
				resultData.put("userData", this.loginResult.getUserData());

				if (this.loginResult.getBusinessData() != null
						&& !this.loginResult.getBusinessData().isEmpty())
				{
					resultData.put("businessData",
							this.loginResult.getBusinessData());
				}

				else
				{
					resultData.put("businessData", null);
				}

				respBean.setResult(resultData);

			}

		}

		catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}

		return respBean;

	}

	/**
	 * To get the User Data based on userId
	 * 
	 * @param userId
	 * @return User data of given userId
	 */
	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseBean getUserById(@PathVariable("id") String userId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(this.userService.getUserById(userId));

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}
		return respBean;
	}

	/**
	 * To Update User data
	 * 
	 * @param updateUser
	 * @return To get the Message like "User has been Successfully Updated "
	 */
	@PostMapping(path = "/update", consumes = "application/json")
	public ResponseBean updateUser(@RequestBody User updateUser)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			if (updateUser.getModifiedTime() == null)
			{
				updateUser.setModifiedTime(new java.util.Date());
			}

			Object report = this.userService.updateUser(updateUser);

			if (report != null)
			{
				HashMap<String, Object> resultData = new HashMap<>();
				resultData.put("userData", report);

				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage("Profile has been Successfully  Updated");
				respBean.setResult(resultData);

			} else
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("Data Reqiured");
				respBean.setResult(null);
			}

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();

		}
		return respBean;
	}

	/**
	 * To delete the particular user based on userId
	 * 
	 * @param userId
	 * @return To get the message like "User has been successfully deleted"
	 */
	@DeleteMapping(value = "/delete/{id}", produces = "application/json")
	public ResponseBean deleteUserById(@PathVariable("id") String userId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{
			String report = this.userService.deleteUserById(userId);

			if ((report.equals("INVALID")))
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage(StatusConstants.INVALID_DETAILS);
				respBean.setResult(null);

			}

			else if ((report.equals("UNSUCCESS")))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("NO User Found");
				respBean.setResult(null);

			} else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage("User has been successfully deleted");
				respBean.setResult(null);

			}

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}
		return respBean;
	}

	@PostMapping(path = "/consumerbusiness", consumes = "application/json")
	public ResponseBean consumerBusinessDetails(
			@RequestBody ConsumerBusinessDetails consumerBusiness)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			Object report = this.userService
					.consumerBusinessDetails(consumerBusiness);

			if (report.equals("Data_Required"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("Consumer Device MAC Id Required !");
				respBean.setResult(null);
			}

			else
			{

				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage("Consumer Business Details");
				respBean.setResult(report);

			}

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();

		}
		return respBean;
	}

	@PostMapping(path = "/saveconsumerbusinessdetails", consumes = "application/json")
	public ResponseBean saveConsumerBusinessDetails(
			@RequestBody SaveConsumerBusinessDetails consumerBusiness)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			Object report = this.userService
					.saveConsumerBusinessDetails(consumerBusiness);

			if (report.equals("Success"))
			{

				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage("Consumer business details added");
				respBean.setResult(null);

			} else
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("Consumer business details not added !");
				respBean.setResult(null);
			}

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();

		}
		return respBean;
	}

	@GetMapping(path = "/consumerbusinesslist/{id}", produces = "application/json")
	public ResponseBean consumerBusinessList(@PathVariable("id") String macId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		Object obj = null;
		try
		{

			obj = this.userService.consumerBusinessList(macId);

			if (obj.equals("INVALID"))
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage(StatusConstants.INVALID_DETAILS);
				respBean.setResult(null);
			}

			else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage("Consumer Business Details");
				respBean.setResult(obj);

			}

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();

		}
		return respBean;
	}

	@GetMapping(path = "/countrieslist", produces = "application/json")
	public ResponseBean countriesList()
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage("Consumer Business Details");
			respBean.setResult(this.userService.countriesList());

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();

		}
		return respBean;
	}

}
