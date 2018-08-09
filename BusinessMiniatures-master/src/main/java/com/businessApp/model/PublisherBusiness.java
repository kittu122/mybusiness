package com.businessApp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection = "publisher_business")
public class PublisherBusiness implements Serializable
{

	private static final long serialVersionUID = -5659483882312790629L;

	@Id
	private String id;
	private String publisherId;
	private String businessId;
	private String name;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private String phone;
	private String email;
	private String website;
	private int noOfEmployees;
	private List<ServiceCategory> serviceCategory;
	private HashMap<String, BusinessHours> businessHours;
	// private int breakStatus;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date createdTime, updateTime;

	public Date getUpdateTime()
	{
		return this.updateTime;
	}
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}
	public PublisherBusiness() {
		// this.breakInfo = new BusinessBreakConfig();
		this.businessHours = new HashMap<>();
		this.serviceCategory = new ArrayList<>();
	}
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
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
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getAddress1()
	{
		return this.address1;
	}
	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}
	public String getAddress2()
	{
		return this.address2;
	}
	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}
	public String getCity()
	{
		return this.city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public String getState()
	{
		return this.state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	public String getCountry()
	{
		return this.country;
	}
	public void setCountry(String country)
	{
		this.country = country;
	}
	public String getZipCode()
	{
		return this.zipCode;
	}
	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}
	public String getPhone()
	{
		return this.phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getEmail()
	{
		return this.email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getWebsite()
	{
		return this.website;
	}
	public void setWebsite(String website)
	{
		this.website = website;
	}
	public int getNoOfEmployees()
	{
		return this.noOfEmployees;
	}
	public void setNoOfEmployees(int noOfEmployees)
	{
		this.noOfEmployees = noOfEmployees;
	}
	public List<ServiceCategory> getServiceCategory()
	{
		return this.serviceCategory;
	}
	public void setServiceCategory(List<ServiceCategory> serviceCategory)
	{
		this.serviceCategory = serviceCategory;
	}
	public Date getCreatedTime()
	{
		return this.createdTime;
	}
	public void setCreatedTime(Date createdTime)
	{
		this.createdTime = createdTime;
	}

	public HashMap<String, BusinessHours> getBusinessHours()
	{
		return this.businessHours;
	}
	// public int getBreakStatus()
	// {
	// return this.breakStatus;
	// }
	// public void setBreakStatus(int breakStatus)
	// {
	// this.breakStatus = breakStatus;
	// }
	public void setBusinessHours(HashMap<String, BusinessHours> businessHours)
	{
		this.businessHours = businessHours;
	}

}
