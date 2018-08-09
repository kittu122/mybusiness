package com.businessApp.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection = "user")
public class User
{
	@Id
	private String id;
	private String name;
	private int type;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String zip;
	private String phone;
	private String email;
	private String password;
	private String modifiedBy;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date modifiedTime;

	public int getType()
	{
		return this.type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public String getPassword()
	{
		return this.password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getEmail()
	{
		return this.email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
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
	public String getAddress1()
	{
		return this.address1;
	}
	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}
	public String getAddress2()
	{
		return this.address2;
	}
	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}
	public String getCity()
	{
		return this.city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public String getState()
	{
		return this.state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	public String getCountry()
	{
		return this.country;
	}
	public void setCountry(String country)
	{
		this.country = country;
	}
	public String getZip()
	{
		return this.zip;
	}
	public void setZip(String zip)
	{
		this.zip = zip;
	}
	public String getPhone()
	{
		return this.phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getModifiedBy()
	{
		return this.modifiedBy;
	}
	public void setModifiedBy(String modifiedBy)
	{
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedTime()
	{
		return this.modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime)
	{
		this.modifiedTime = modifiedTime;
	}

}
