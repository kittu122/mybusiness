package com.businessApp.bean;

import java.util.ArrayList;
import java.util.List;

import com.businessApp.model.PublisherBusiness;

public class ConsumerScannnedBusinesses
{
	private String id;
	private String macId;
	private List<PublisherBusiness> businesses;

	public ConsumerScannnedBusinesses() {

		this.businesses = new ArrayList<>();
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMacId()
	{
		return this.macId;
	}

	public void setMacId(String macId)
	{
		this.macId = macId;
	}

	public List<PublisherBusiness> getBusinesses()
	{
		return this.businesses;
	}

	public void setBusinesses(List<PublisherBusiness> businesses)
	{
		this.businesses = businesses;
	}

}
