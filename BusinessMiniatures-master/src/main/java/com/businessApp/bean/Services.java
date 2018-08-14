package com.businessApp.bean;

import org.springframework.stereotype.Component;

@Component
public class Services
{
	private String serviceId;
	private String serviceName;
	private String description;
	private int duration;

	public String getServiceId()
	{
		return this.serviceId;
	}
	public void setServiceId(String serviceId)
	{
		this.serviceId = serviceId;
	}
	public String getServiceName()
	{
		return this.serviceName;
	}
	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}
	public String getDescription()
	{
		return this.description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public int getDuration()
	{
		return this.duration;
	}
	public void setDuration(int duration)
	{
		this.duration = duration;
	}

}
