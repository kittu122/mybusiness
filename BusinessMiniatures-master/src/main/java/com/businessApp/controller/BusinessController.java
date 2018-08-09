package com.businessApp.controller;

import java.io.FileInputStream;
import java.util.Date;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.businessApp.bean.ResponseBean;
import com.businessApp.constants.StatusConstants;
import com.businessApp.model.Business;
import com.businessApp.service.BusinessService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@RestController
@RequestMapping("/business")
public class BusinessController
{
	private static Logger logger = LoggerFactory
			.getLogger(BusinessController.class);

	@Autowired
	BusinessService businessService;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	GridFsOperations gridOperations;

	@Autowired
	ServletContext context;

	/**
	 * To Create the Business
	 * 
	 * @param business
	 * @return To get the message like "Business has been successfully Created"
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBean saveBusiness(@RequestBody Business business)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{

			if (business.getCreatedTime() == null)
			{
				Date date = new Date();
				business.setCreatedTime(date);
				business.setModifiedTime(date);
			}

			this.businessService.save(business);

			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage("Business has been successfully Created");
			respBean.setResult(null);

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			e.printStackTrace();
		}
		return respBean;
	}

	/**
	 * To Update the Business data based on given Business Object
	 * 
	 * @param business
	 * @return To get the message like "Business has been successfully updated"
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBean updateBusiness(@RequestBody Business business)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{
			if (business.getModifiedTime() == null)
			{
				Date dt = new java.util.Date();
				business.setModifiedTime(dt);
			}

			String report = this.businessService.update(business);
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(report);
			respBean.setResult(null);
		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			e.printStackTrace();
		}
		return respBean;
	}

	/**
	 * To get the list of all Business created by Admin
	 * 
	 * @return To get the list of all Business
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBean list()
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(this.businessService.businessList());
		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			e.printStackTrace();
		}
		return respBean;
	}

	/**
	 * To get the Business data based on TemplatebusinessId
	 * 
	 * @param businessId
	 * @return To get the Business data based on businessId
	 */

	@RequestMapping(value = "/servicecategory/list/{businessId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBean serviceCategorylist(
			@PathVariable("businessId") String businessId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(
					this.businessService.listServiceCategories(businessId));
		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			e.printStackTrace();
		}
		return respBean;
	}

	@RequestMapping(value = "/servicecategory/{businessId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBean serviceCategoryByBId(
			@PathVariable("businessId") String businessId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{
			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(this.businessService
					.listServiceCategoriesByBId(businessId));
		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			e.printStackTrace();
		}
		return respBean;
	}

	@GetMapping(value = "/allservicecategories", produces = "application/json")
	public ResponseBean allBusinessServiceCategories()
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);

		try
		{

			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage(StatusConstants.SUCCESS_MESSAGE);
			respBean.setResult(
					this.businessService.allBusinessServiceCategories());

		} catch (Exception e)
		{
			respBean.setCode(StatusConstants.INTERNAL_SERVER_ERROR);
			respBean.setMessage(StatusConstants.INTERNAL_SERVER_ERROR_MESSAGE);
			respBean.setResult(null);
			e.printStackTrace();
		}

		return respBean;

	}

	@DeleteMapping(value = "/delete/{id}", produces = "application/json")
	public ResponseBean removeEmployeeById(
			@PathVariable("id") String businessId)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		try
		{
			String report = this.businessService.deleteBusinessById(businessId);

			if (report.equals("UNSUCCESS"))
			{
				respBean.setCode(StatusConstants.NOT_VALID);
				respBean.setMessage("Business has not  been  deleted !");
				respBean.setResult(null);
			}

			else
			{
				respBean.setCode(StatusConstants.SUCCESS_CODE);
				respBean.setMessage("Business has been successfully deleted ");
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

	@PostMapping(path = "/imagesave", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

	public ResponseBean iconSave(@RequestParam("file") MultipartFile file)
	{
		ResponseBean respBean = this.applicationContext
				.getBean(ResponseBean.class);
		FileInputStream sourceStream = null;
		DBObject metaData = new BasicDBObject();
		metaData.put("organization", "Business Miniatures");
		String imageFileId = "";
		final String dir = System.getProperty("user.dir");

		try
		{
			String fileName = file.getOriginalFilename();

			byte[] filePart = file.getBytes();

			logger.info("filePart ----" + filePart);
			logger.info("filePart  Length----" + filePart.length);

			logger.info("dir----" + dir);
			logger.info("fileName----" + fileName);

			// metaData.put("type", "image");
			// Store file to MongoDB
			// imageFileId = this.gridOperations
			// .store(filePart, "" + fileName, "image/png", metaData)
			// .getId().toString();
			// System.out.println("ImageFileId = " + imageFileId);
			// //System.out.println(
			// "FULL PATH = " + convFile.getAbsolutePath().toString());

			respBean.setCode(StatusConstants.SUCCESS_CODE);
			respBean.setMessage("Image has been successfully stored");
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

	// -----------------------------------------------------

	// @GetMapping("/save")
	// public String saveFiles() throws FileNotFoundException
	// {
	// // Define metaData
	// DBObject metaData = new BasicDBObject();
	// metaData.put("organization", "JavaSampleApproach");
	//
	// /**
	// * 1. save an image file to MongoDB
	// */
	//
	// // Get input file
	// InputStream iamgeStream = new FileInputStream("D:\\JSA\\jsa-logo.png");
	//
	// metaData.put("type", "image");
	//
	// // Store file to MongoDB
	// imageFileId = this.gridOperations
	// .store(iamgeStream, "jsa-logo.png", "image/png", metaData)
	// .getId().toString();
	// System.out.println("ImageFileId = " + imageFileId);
	//
	// /**
	// * 2. save text files to MongoDB
	// */
	//
	// // change metaData
	// metaData.put("type", "data");
	//
	// // Store files to MongoDb
	// this.gridOperations.store(new FileInputStream("D:\\JSA\\text-1.txt"),
	// "text-1.txt", "text/plain", metaData);
	// this.gridOperations.store(new FileInputStream("D:\\JSA\\text-2.txt"),
	// "text-2.txt", "text/plain", metaData);
	//
	// return "Done";
	// }

}
