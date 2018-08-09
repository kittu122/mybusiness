package com.businessApp.bean;

import org.bson.types.ObjectId;

public class UpdatePBColor
{
	private String id;
	private ObjectId servCatId = new ObjectId();
	private String colour;
	private int type;

	public ObjectId getServCatId()
	{
		return this.servCatId;
	}
	public void setServCatId(ObjectId servCatId)
	{
		this.servCatId = servCatId;
	}
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
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
