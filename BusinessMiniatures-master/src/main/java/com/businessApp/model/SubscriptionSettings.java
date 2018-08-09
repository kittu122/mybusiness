package com.businessApp.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection = "subscriptionSettings")
public class SubscriptionSettings
{
	@Id
	private String id;
	private String type;
	private int price;
	private String duration;
	private int businessCount;

	// private List<SubscriptionDetails> details;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date modifiedTime, createdTime;

	SubscriptionSettings() {
		// this.details = new ArrayList<>();
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
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

	public String getType()
	{
		return this.type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getPrice()
	{
		return this.price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	public String getDuration()
	{
		return this.duration;
	}

	public void setDuration(String duration)
	{
		this.duration = duration;
	}

	public int getBusinessCount()
	{
		return this.businessCount;
	}

	public void setBusinessCount(int businessCount)
	{
		this.businessCount = businessCount;
	}

}
