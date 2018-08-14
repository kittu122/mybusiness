package com.businessApp.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class Payment
{
	private Object Id = new ObjectId();
	private int fee;
	private String transType;
	private String bId;
	private String type;
	private String transId;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date validFrom, expiredOn;

	public String getId()
	{
		return this.Id.toString();
	}
	public void setId(Object id)
	{
		this.Id = id;
	}
	public int getFee()
	{
		return this.fee;
	}
	public void setFee(int fee)
	{
		this.fee = fee;
	}
	public String getTransType()
	{
		return this.transType;
	}
	public void setTransType(String transType)
	{
		this.transType = transType;
	}
	public String getbId()
	{
		return this.bId;
	}
	public void setbId(String bId)
	{
		this.bId = bId;
	}
	public String getType()
	{
		return this.type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public Date getValidFrom()
	{
		return this.validFrom;
	}
	public void setValidFrom(Date validFrom)
	{
		this.validFrom = validFrom;
	}
	public Date getExpiredOn()
	{
		return this.expiredOn;
	}
	public void setExpiredOn(Date expiredOn)
	{
		this.expiredOn = expiredOn;
	}
	public String getTransId()
	{
		return this.transId;
	}
	public void setTransId(String transId)
	{
		this.transId = transId;
	}

}
