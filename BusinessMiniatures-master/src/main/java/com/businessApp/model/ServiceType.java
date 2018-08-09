package com.businessApp.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
public class ServiceType
{

	@Id
	private ObjectId id = new ObjectId();
	private String name;
	private String description;
	private int type = 0; // whether service is created by admin/publisher . By
							// default type=0 i.e admin created it
	private String createdBy; // it will be null if created by admin, else
								// created by publisher have publishedId
	private int duration;
	private double price;
	private String unit;

	public String getId()
	{
		return this.id.toString();
	}
	public void setId(ObjectId id)
	{
		this.id = id;
	}
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDescription()
	{
		return this.description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public int getType()
	{
		return this.type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public String getCreatedBy()
	{
		return this.createdBy;
	}
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	public int getDuration()
	{
		return this.duration;
	}
	public void setDuration(int duration)
	{
		this.duration = duration;
	}
	public double getPrice()
	{
		return this.price;
	}
	public void setPrice(double price)
	{
		this.price = price;
	}
	public String getUnit()
	{
		return this.unit;
	}
	public void setUnit(String unit)
	{
		this.unit = unit;
	}

}
