package com.businessApp.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection = "subscription")
public class Subscription
{
	@Id
	private String id;
	private String businessId;
	private String publisherId;
	private Payment payment = new Payment();

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date modifiedTime, createdTime;

	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getBusinessId()
	{
		return this.businessId;
	}
	public void setBusinessId(String businessId)
	{
		this.businessId = businessId;
	}
	public Payment getPayment()
	{
		return this.payment;
	}
	public void setPayment(Payment payment)
	{
		this.payment = payment;
	}
	public String getPublisherId()
	{
		return this.publisherId;
	}
	public void setPublisherId(String publisherId)
	{
		this.publisherId = publisherId;
	}
	public Date getModifiedTime()
	{
		return this.modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime)
	{
		this.modifiedTime = modifiedTime;
	}
	public Date getCreatedTime()
	{
		return this.createdTime;
	}
	public void setCreatedTime(Date createdTime)
	{
		this.createdTime = createdTime;
	}

}
