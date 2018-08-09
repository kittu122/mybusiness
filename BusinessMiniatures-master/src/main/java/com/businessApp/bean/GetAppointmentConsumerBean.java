package com.businessApp.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Component;

@Component
public class GetAppointmentConsumerBean
{

	private String businessId;
	private int businessStartTime;
	private int businessEndTime;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date date;
	private HashMap<Integer, ConsumerHours> hours;
	private Map<String, ConsumerAppointmentBean> appointments;

	public GetAppointmentConsumerBean() {
		this.appointments = new TreeMap<>();
	}

	public void setBusinessId(String businessId)
	{
		this.businessId = businessId;
	}

	public String getBusinessId()
	{
		return this.businessId;
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

	public Date getDate()
	{
		return this.date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public HashMap<Integer, ConsumerHours> getHours()
	{
		return this.hours;
	}

	public void setHours(HashMap<Integer, ConsumerHours> hours)
	{
		this.hours = hours;
	}

	public Map<String, ConsumerAppointmentBean> getAppointments()
	{
		return this.appointments;
	}

	public void setAppointments(
			Map<String, ConsumerAppointmentBean> appointments)
	{
		this.appointments = appointments;
	}

}
