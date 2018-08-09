package com.businessApp.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Component;

@Component
public class Pto
{
	@Id
	private ObjectId id = new ObjectId();
	private String name;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date startDate, endDate;

	private int status;

	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Date getStartDate()
	{
		return this.startDate;
	}
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}
	public Date getEndDate()
	{
		return this.endDate;
	}
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
	public String getId()
	{
		return this.id.toString();
	}

	public void setId(ObjectId id)
	{
		this.id = id;
	}
	public int getStatus()
	{
		return this.status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}

}
