package com.businessApp.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.businessApp.bean.ResponseBean;
import com.businessApp.constants.StatusConstants;
import com.businessApp.model.Subscription;
import com.businessApp.model.SubscriptionSettings;
import com.businessApp.service.SubscriptionService;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController
{
	private static Logger logger = LoggerFactory
			.getLogger(SubscriptionController.class);

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	SubscriptionService subService;

	@RequestMapping(value = "/subscriptionsettingssave", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBean subscriptionSettingSave(
			@RequestBody SubscriptionSettings sub)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{
			if (sub.getCreatedTime() == null)
			{
				Date date = new Date();
				sub.setCreatedTime(date);
				sub.setModifiedTime(date);
			}

			this.subService.subscriptionSettingsSave(sub);
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(
					"Subscription setting record has been successfully inserted");
			respBean.setResult(null);
		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			e.printStackTrace();
		}
		return respBean;
	}

	@GetMapping(value = "/listsubscriptionsettings", produces = "application/json")
	public ResponseBean listSubscriptionSettings()
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{

			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(this.subService.listSubscriptionDetails());

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}

		return respBean;

	}

	@PutMapping(value = "/updatesubscriptionsettings", produces = "application/json", consumes = "application/json")
	public ResponseBean updateSubscriptionSetings(
			@RequestBody SubscriptionSettings updateSub)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			if (updateSub.getModifiedTime() == null)
			{
				Date dt = new java.util.Date();
				updateSub.setModifiedTime(dt);
			}

			String reoprt = this.subService
					.updateSubscriptionDetails(updateSub);

			if (reoprt.equals("UNSUCCESS"))
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage(
						"Subscription setting record has not been updated !");
				respBean.setResult(null);

			} else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage(
						"Subscription setting record has been successfully updated");
				respBean.setResult(null);
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

	@DeleteMapping(value = "/delete/{id}", produces = "application/json")
	public ResponseBean removeEmployeeById(@PathVariable("id") String subId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{
			String report = this.subService.deleteSubScriptionSettings(subId);

			if (report.equals("UNSUCCESS"))
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage(
						"Subscription setting record has not been deleted !");
				respBean.setResult(null);
			}

			else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage(
						"Subscription setting record has been successfully deleted");
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

	@PostMapping(value = "subscriptiondetails", consumes = "application/json")
	public ResponseBean subscriptionDetails(@RequestBody Subscription sub)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{
			if (sub.getCreatedTime() == null)
			{
				Date date = new Date();
				sub.setCreatedTime(date);
				sub.setModifiedTime(date);
			}

			this.subService.subscriptionSave(sub);
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage("Subscription  has been successfully done");
			respBean.setResult(null);
		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			e.printStackTrace();
		}
		return respBean;
	}

	@GetMapping(value = "/subscriptionsettings/{id}", produces = "application/json")
	public ResponseBean getSubscriptionSettings(
			@PathVariable("id") String subId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{

			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(this.subService.getSubscriptionDetails(subId));

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
