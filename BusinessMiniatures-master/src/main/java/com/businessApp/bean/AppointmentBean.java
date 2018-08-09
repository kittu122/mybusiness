package com.businessApp.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class AppointmentBean
{

	private String publisherId;
	private String businessId;
	private String serviceId;
	private String couponId;
	private String name;
	private String phone;
	private String email;
	private String empId;
	private String deviceId;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date startScheduledTime, endScheduledTime;

	public String getPublisherId()
	{
		return this.publisherId;
	}
	public String getCouponId()
	{
		return this.couponId;
	}
	public void setCouponId(String couponId)
	{
		this.couponId = couponId;
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
	public String getServiceId()
	{
		return this.serviceId;
	}
	public void setServiceId(String serviceId)
	{
		this.serviceId = serviceId;
	}

	public Date getStartScheduledTime()
	{

		return this.startScheduledTime;

	}
	public void setStartScheduledTime(Date startScheduledTime)
	{
		this.startScheduledTime = startScheduledTime;
	}
	public Date getEndScheduledTime()
	{
		return this.endScheduledTime;
	}
	public void setEndScheduledTime(Date endScheduledTime)
	{
		this.endScheduledTime = endScheduledTime;
	}
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
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
	public String getEmpId()
	{
		return this.empId;
	}
	public void setEmpId(String empId)
	{
		this.empId = empId;
	}
	public String getDeviceId()
	{
		return this.deviceId;
	}
	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

}
