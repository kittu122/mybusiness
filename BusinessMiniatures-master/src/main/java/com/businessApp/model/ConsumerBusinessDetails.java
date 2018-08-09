package com.businessApp.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "consumerBusinessDetails")
public class ConsumerBusinessDetails
{
	private String id;
	private String macId;
	// private List<ConsumerRegisteredBusiness> businesses;

	private List<String> businesses;

	ConsumerBusinessDetails() {
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

	public List<String> getBusinesses()
	{
		return this.businesses;
	}

	public void setBusinesses(List<String> businesses)
	{
		this.businesses = businesses;
	}

}
