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

import com.businessApp.bean.PublisherBusinessServices;
import com.businessApp.bean.QRCodeBean;
import com.businessApp.bean.ResponseBean;
import com.businessApp.bean.UpdatePBColor;
import com.businessApp.constants.StatusConstants;
import com.businessApp.model.BusinessBreakConfig;
import com.businessApp.model.PublisherBusiness;
import com.businessApp.service.PublisherService;

@RestController
@RequestMapping("/publisher")
public class PublisherController
{

	private static Logger logger = LoggerFactory
			.getLogger(PublisherController.class);

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	PublisherService publisherService;

	/**
	 * To Save the Publisher Business
	 * 
	 * @param publBusiness
	 * @return To get the message like "Successfully Created Publisher Business"
	 */
	@RequestMapping(value = "/business/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBean save(@RequestBody PublisherBusiness publBusiness)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{
			if (publBusiness.getCreatedTime() == null)
			{
				Date dt = new Date();
				publBusiness.setCreatedTime(dt);
				publBusiness.setUpdateTime(dt);
			}
			String report = this.publisherService.save(publBusiness);

			if (report.equals("UNSUCCESS"))

			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage("Publisher id  / Business id   invalid !");
				respBean.setResult(null);
			}

			else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage(
						" Publisher business has been successfully created");
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

	/**
	 * To add the service to the particular business and particular Service
	 * Category
	 * 
	 * @param addService
	 * @return To Get the message like " Service has been successfully added "
	 */

	@PostMapping(path = "/business/addservice", consumes = "application/json")
	public ResponseBean addService(
			@RequestBody PublisherBusinessServices addService)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{

			String report = this.publisherService
					.addServiceToPublisherBusiness(addService);

			if (report.equals("UnSuccess"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("Service has not  been  added  !");
				respBean.setResult(null);
			} else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage("Service has been  successfully added ");
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
	 * To remove the service from the particular business and particular Service
	 * Category
	 * 
	 * @param removeService
	 * @return To get the message like "Service has not been removed !"
	 */
	@PostMapping(path = "/business/removeservice", consumes = "application/json")
	public ResponseBean removeService(
			@RequestBody PublisherBusinessServices removeService)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{
			String report = this.publisherService
					.removeServiceFromPublisherBusiness(removeService);

			if (report.equals("UnSuccess"))
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("Service has not been  removed !");
				respBean.setResult(null);
			} else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage(" Service has been successfully removed !");
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
	 * List Of Publisher Businesses Based on publisherId
	 * 
	 * @param publisherId
	 * @return List Of Publisher Businesses
	 */
	@GetMapping(value = "/business/list/{id}", produces = "application/json")
	public ResponseBean businessListById(@PathVariable("id") String publisherId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{

			// Object obj = this.publisherService
			// .businessListBypublisherId(publisherId);

			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(this.publisherService
					.businessListBypublisherId(publisherId));
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
	 * Delete the Publisher Business Based on PublisherId
	 * 
	 * @param businessId
	 * @return To get the message like "Publisher Business has been Successfully
	 *         Deleted "
	 */
	@DeleteMapping(value = "/business/delete/{id}", produces = "application/json")
	public ResponseBean removeEmployeeById(
			@PathVariable("id") String businessId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{
			String report = this.publisherService
					.deleteBusinessById(businessId);

			if (report.equals("UNSUCCESS"))
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage(
						"Publisher business has not  been  deleted !");
				respBean.setResult(null);
			}

			else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage(
						"Publisher business has been successfully deleted ");
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
	 * To update the publisher Business based on given Publisher business object
	 * 
	 * @param updatePublBusiness
	 * @return To get the message like "Publisher Business has been Successfully
	 *         Updated "
	 */
	@PutMapping(value = "/business/update", produces = "application/json", consumes = "application/json")
	public ResponseBean updatePubisherBusiness(
			@RequestBody PublisherBusiness updatePublBusiness)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			if (updatePublBusiness.getUpdateTime() == null)
			{
				Date dt = new java.util.Date();
				updatePublBusiness.setUpdateTime(dt);
			}

			String reoprt = this.publisherService
					.updatePublisherBusiness(updatePublBusiness);
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(reoprt);
			respBean.setResult(null);

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
	 * To Get List Of Business Based AdminBusinessId
	 * 
	 * @param businessId
	 * @return
	 */
	@GetMapping(value = "/publisherbusiness/list/{id}", produces = "application/json")
	public ResponseBean publisherBusinessListByBId(
			@PathVariable("id") String businessId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(this.publisherService
					.publisherBusinessListByBId(businessId));
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

	@GetMapping(value = "/publisherbusiness/{id}", produces = "application/json")
	public ResponseBean publisherBusinessByBId(
			@PathVariable("id") String businessId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{
			Object obj = this.publisherService
					.publisherBusinessByBId(businessId);

			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(obj);
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
	 * UpdatePubisherBusinessServiceCatColor
	 * 
	 * @param updatePublBusinessColor
	 * @return
	 */
	@PutMapping(value = "/business/updatecolor", produces = "application/json", consumes = "application/json")
	public ResponseBean updatePubisherBusinessServiceCatColor(
			@RequestBody UpdatePBColor updatePublBusinessColor)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			String reoprt = this.publisherService
					.updatePubisherBusinessServiceCatColor(
							updatePublBusinessColor);
			if (reoprt.equals("Success"))
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage("Colour has been updated successfully");
				respBean.setResult(null);
			}

			else
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage("Colour has not been updated !");
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

	/**
	 * To Save Code
	 * 
	 * @param qrb
	 * @return
	 */

	@PostMapping(path = "/business/qrcode", consumes = "application/json")

	public ResponseBean uploadFile(@RequestBody QRCodeBean qrb)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{

			this.publisherService.saveQRCode(qrb);
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(null);

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
	 * 
	 * To Get QRCode By Publisher BusinessId
	 * 
	 * @param businessId
	 * @return
	 */
	@GetMapping(value = "/business/qrcode/{id}", produces = "application/json")
	public ResponseBean publisherBusinessQRCode(
			@PathVariable("id") String businessId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(this.publisherService
					.getpublisherBusinessQRCode(businessId));
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

	@PostMapping(path = "/business/breakconfig", consumes = "application/json")
	public ResponseBean breakConfig(
			@RequestBody BusinessBreakConfig businesBreak)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{

			String report = this.publisherService.saveBreakConfig(businesBreak);
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(report);
			respBean.setResult(null);

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();

		}

		return respBean;

	}

	@PutMapping(value = "/business/updatebreakconfig", produces = "application/json", consumes = "application/json")
	public ResponseBean updateBreakConfig(
			@RequestBody BusinessBreakConfig businesBreak)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			String reoprt = this.publisherService
					.updateBreakConfig(businesBreak);
			if (reoprt.equals("Success"))
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage(
						"Break config details  has been updated successfully");
				respBean.setResult(null);
			}

			else
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage(
						"Break config details has not been updated !");
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

	@GetMapping(value = "/business/getbreakconfig/{id}", produces = "application/json")
	public ResponseBean getBreakConfigDetails(
			@PathVariable("id") String businessId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{
			Object obj = this.publisherService
					.getBreakConfigDetails(businessId);

			if (obj != null)
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
				respBean.setResult(obj);
			} else
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage(StatusConstants.UNSUCCESS_MESSAGE);
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

	@PutMapping(value = "/business/updateservicecategories", produces = "application/json", consumes = "application/json")
	public ResponseBean updateServicCategories(
			@RequestBody PublisherBusiness updatePublBusiness)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			if (updatePublBusiness.getUpdateTime() == null)
			{
				Date dt = new java.util.Date();
				updatePublBusiness.setUpdateTime(dt);
			}

			String reoprt = this.publisherService
					.updateServicCategories(updatePublBusiness);
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(reoprt);
			respBean.setResult(null);

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

	@PutMapping(value = "/business/updateparticularservice", produces = "application/json", consumes = "application/json")
	public ResponseBean updateParticularService(
			@RequestBody PublisherBusinessServices updatePubService)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			String reoprt = this.publisherService
					.updateParticularService(updatePubService);

			if (reoprt.equals("Success"))
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage("Service has been successfully updated");
				respBean.setResult(null);
			}

			else
			{
				respBean.setCode(StatusConstants.NO_CONTENT);
				respBean.setMessage("Service has not been  updated !");
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

}
