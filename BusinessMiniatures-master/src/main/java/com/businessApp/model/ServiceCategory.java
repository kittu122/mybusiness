package com.businessApp.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
public class ServiceCategory
{

	@Id
	private ObjectId id = new ObjectId();
	private String name;
	private String description;
	private String colour;
	private int type;

	private List<ServiceType> service;

	public ServiceCategory() {
		this.service = new ArrayList<>();
	}

	public String getId()
	{
		return this.id.toString();
	}

	public void setId(ObjectId id)
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
	public List<ServiceType> getService()
	{
		return this.service;
	}
	public void setService(List<ServiceType> service)
	{
		this.service = service;
	}

	public String getColour()
	{
		return this.colour;
	}

	public void setColour(String colour)
	{
		this.colour = colour;
	}

	public int getType()
	{
		return this.type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

}
