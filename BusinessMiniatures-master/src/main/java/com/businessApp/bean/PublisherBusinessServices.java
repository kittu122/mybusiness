package com.businessApp.bean;

import org.bson.types.ObjectId;

import com.businessApp.model.ServiceType;

public class PublisherBusinessServices
{
	//// old
	private String publisherBusinessId;
	private ObjectId serviceCatId;
	private ServiceType service;
	private ObjectId serviceId;

	public ObjectId getServiceId()
	{
		return this.serviceId;
	}

	public void setServiceId(ObjectId serviceId)
	{
		this.serviceId = serviceId;
	}

	public String getPublisherBusinessId()
	{
		return this.publisherBusinessId;
	}

	public void setPublisherBusinessId(String publisherBusinessId)
	{
		this.publisherBusinessId = publisherBusinessId;
	}

	public ObjectId getServiceCatId()
	{
		return this.serviceCatId;
	}

	public void setServiceCatId(ObjectId serviceCatId)
	{
		this.serviceCatId = serviceCatId;
	}

	public ServiceType getService()
	{
		return this.service;
	}

	public void setService(ServiceType service)
	{
		this.service = service;
	}

}
