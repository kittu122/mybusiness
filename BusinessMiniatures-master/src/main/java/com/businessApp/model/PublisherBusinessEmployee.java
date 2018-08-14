package com.businessApp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document(collection = "publisherBusinessEmployee")

@JsonInclude(Include.NON_EMPTY)
public class PublisherBusinessEmployee implements Serializable
{

	private static final long serialVersionUID = -2364953401769516962L;
	@Id
	private String id;
	private String businessId;
	private String publisherId;
	private String firstName;
	private String lastName;
	private String phone;
	private int status;
	private int priority;
	private List<ServiceCategory> serviceCategory;
	private HashMap<String, BusinessHours> employeeHours;
	private String empImage;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date updatedTime, createdTime;

	public PublisherBusinessEmployee() {

		this.serviceCategory = new ArrayList<>();
		this.employeeHours = new HashMap<>();
	}

	public String getPublisherId()
	{
		return this.publisherId;
	}

	public void setPublisherId(String publisherId)
	{
		this.publisherId = publisherId;
	}

	public String getBusinessId()
	{
		return this.businessId;
	}

	public void setBusinessId(String businessId)
	{
		this.businessId = businessId;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getFirstName()
	{
		return this.firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return this.lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getPhone()
	{
		return this.phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public int getStatus()
	{
		return this.status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public Date getCreatedTime()
	{
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime)
	{
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime()
	{
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime)
	{
		this.updatedTime = updatedTime;
	}

	public List<ServiceCategory> getServiceCategory()
	{
		return this.serviceCategory;
	}

	public void setServiceCategory(List<ServiceCategory> serviceList)
	{
		this.serviceCategory = serviceList;
	}

	public int getPriority()
	{
		return this.priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public HashMap<String, BusinessHours> getEmployeeHours()
	{
		return this.employeeHours;
	}

	public void setEmployeeHours(HashMap<String, BusinessHours> businessHours)
	{
		this.employeeHours = businessHours;
	}

	// public static Comparator<PublisherBusinessEmployee> getEmpPriority()
	// {
	// return PublisherBusinessEmployeePriority;
	// }
	// public static void setEmpPriority(
	// Comparator<PublisherBusinessEmployee> empPriority)
	// {
	// PublisherBusinessEmployeePriority = empPriority;
	// }

	public static Comparator<PublisherBusinessEmployee> PublisherBusinessEmployeePriority = new Comparator<PublisherBusinessEmployee>() {

		@Override
		public int compare(PublisherBusinessEmployee e1,
				PublisherBusinessEmployee e2)
		{

			int priority1 = e1.getPriority();
			int priority2 = e2.getPriority();

			/* For ascending order */
			return priority1 - priority2;

		}
	};

	public static Comparator<PublisherBusinessEmployee> getPublisherBusinessEmployeePriority()
	{
		return PublisherBusinessEmployeePriority;
	}

	public static void setPublisherBusinessEmployeePriority(
			Comparator<PublisherBusinessEmployee> publisherBusinessEmployeePriority)
	{
		PublisherBusinessEmployeePriority = publisherBusinessEmployeePriority;
	}

	public String getEmpImage()
	{
		return this.empImage;
	}

	public void setEmpImage(String empImage)
	{
		this.empImage = empImage;
	}
}
