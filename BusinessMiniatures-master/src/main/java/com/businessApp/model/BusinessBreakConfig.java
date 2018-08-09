package com.businessApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "BusinessBreakConfig")
public class BusinessBreakConfig
{

	@Id
	private String id;
	private String bId;
	private String frequency; // frequency in hours
	private int interval; // interval in minutes

	public int getBreakStatus()
	{
		return this.breakStatus;
	}
	public void setBreakStatus(int breakStatus)
	{
		this.breakStatus = breakStatus;
	}
	private int breakStatus;

	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getFrequency()
	{
		return this.frequency;
	}
	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}
	public int getInterval()
	{
		return this.interval;
	}
	public void setInterval(int interval)
	{
		this.interval = interval;
	}

	public String getbId()
	{
		return this.bId;
	}
	public void setbId(String bId)
	{
		this.bId = bId;
	}

}
