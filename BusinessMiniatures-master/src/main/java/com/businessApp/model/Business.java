package com.businessApp.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document(collection = "business")
@JsonInclude(Include.NON_EMPTY)
public class Business
{
	@Id
	private String id;
	private String name;
	private String description;
	private String icon;
	private String createdBy;
	private String modifiedBy;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date modifiedTime, createdTime;

	private List<ServiceCategory> serviceCategory;

	public Business() {
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

	public String getIcon()
	{
		return this.icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getCreatedBy()
	{
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() throws ParseException
	{

		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime)
	{
		this.createdTime = createdTime;
	}

	public String getModifiedBy()
	{
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy)
	{
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedTime()
	{
		return this.modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime)
	{
		this.modifiedTime = modifiedTime;
	}

	public List<ServiceCategory> getServiceCategory()
	{
		return this.serviceCategory;
	}

	public void setServiceCategory(List<ServiceCategory> serviceCategory)
	{
		this.serviceCategory = serviceCategory;
	}

}
