package com.businessApp.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class SubscriptionDetails
{
	@Id
	private ObjectId id = new ObjectId();
	private String type;
	private int price;
	private String duration;
	private int businessCount;

	public String getId()
	{
		return this.id.toString();
	}
	public void setId(ObjectId id)
	{
		this.id = id;
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
