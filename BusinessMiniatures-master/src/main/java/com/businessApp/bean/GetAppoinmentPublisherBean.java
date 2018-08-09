package com.businessApp.bean;

import java.util.Date;
import java.util.HashMap;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class GetAppoinmentPublisherBean
{
	private String businessId;
	private int businessStartTime;
	private int businessEndTime;
	private int noOfemployee;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date date;
	private HashMap<Integer, Hours> hours;

	public GetAppoinmentPublisherBean() {
		this.hours = new HashMap<>();
	}

	public String getBusinessId()
	{
		return this.businessId;
	}

	public void setBusinessId(String businessId)
	{
		this.businessId = businessId;
	}

	public int getBusinessStartTime()
	{
		return this.businessStartTime;
	}

	public void setBusinessStartTime(int businessStartTime)
	{
		this.businessStartTime = businessStartTime;
	}

	public int getBusinessEndTime()
	{
		return this.businessEndTime;
	}

	public void setBusinessEndTime(int businessEndTime)
	{
		this.businessEndTime = businessEndTime;
	}

	public int getNoOfemployee()
	{
		return this.noOfemployee;
	}

	public void setNoOfemployee(int noOfemployee)
	{
		this.noOfemployee = noOfemployee;
	}

	public Date getDate()
	{
		return this.date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public HashMap<Integer, Hours> getHours()
	{
		return this.hours;
	}

	public void setHours(HashMap<Integer, Hours> hours)
	{
		this.hours = hours;
	}

	@Override
	public String toString()
	{
		return "SetAppoinmentBean [businessId=" + this.businessId
				+ ", businessStartTime=" + this.businessStartTime
				+ ", businessEndTime=" + this.businessEndTime
				+ ", noOfemployee=" + this.noOfemployee + ", date=" + this.date
				+ ", hours=" + this.hours + "]";
	}

}
