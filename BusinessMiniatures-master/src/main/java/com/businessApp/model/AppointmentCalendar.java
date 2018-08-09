package com.businessApp.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection = "appointmentCalender")
public class AppointmentCalendar implements Comparable<AppointmentCalendar>
{

	private String id;
	private String businessId;
	private String publisherId;
	private String serviceId;
	private String customerId;
	private String name;
	private String email;
	private String phone;
	private int numberOfPeople;
	private String employeeId;
	private String serviceName;
	private String colour;
	private String couponId;
	private int status;
	private int start;
	private int end;
	private String modifiedBy;

	// Status Value
	// -3 no service
	// -2 break
	// -1:noboking
	// 0 --> Created(Booked)
	// 1--In Progress
	// 2--Completed
	// 3:cancelled

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date startScheduledTime, endScheduledTime, createDate, updateDate;

	public int getStart()
	{
		return this.start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public int getEnd()
	{
		return this.end;
	}

	public void setEnd(int end)
	{
		this.end = end;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPhone()
	{
		return this.phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	@Override
	public String toString()
	{
		return "AppointmentCalendar [id=" + this.id + ", businessId="
				+ this.businessId + ", publisherId=" + this.publisherId
				+ ", serviceId=" + this.serviceId + ", customerId="
				+ this.customerId + ", name=" + this.name + ", email="
				+ this.email + ", phone=" + this.phone + ", numberOfPeople="
				+ this.numberOfPeople + ", employeeId=" + this.employeeId
				+ ", couponId=" + this.couponId + ", status=" + this.status
				+ ", start=" + this.start + ", end=" + this.end
				+ ", startScheduledTime=" + this.startScheduledTime
				+ ", endScheduledTime=" + this.endScheduledTime
				+ ", createDate=" + this.createDate + ", UpdateDate="
				+ this.updateDate + "]";
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	public String getBusinessId()
	{
		return this.businessId;
	}
	public void setBusinessId(String businessId)
	{
		this.businessId = businessId;
	}
	public String getPublisherId()
	{
		return this.publisherId;
	}
	public void setPublisherId(String publisherId)
	{
		this.publisherId = publisherId;
	}
	public String getServiceId()
	{
		return this.serviceId;
	}
	public String getColour()
	{
		return this.colour;
	}

	public void setColour(String colour)
	{
		this.colour = colour;
	}

	public String getServiceName()
	{
		return this.serviceName;
	}

	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}

	public void setServiceId(String serviceId)
	{
		this.serviceId = serviceId;
	}
	public String getCustomerId()
	{
		return this.customerId;
	}
	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}

	public int getNumberOfPeople()
	{
		return this.numberOfPeople;
	}
	public void setNumberOfPeople(int numberOfPeople)
	{
		this.numberOfPeople = numberOfPeople;
	}
	public String getEmployeeId()
	{
		return this.employeeId;
	}
	public void setEmployeeId(String employeeId)
	{
		this.employeeId = employeeId;
	}
	public String getCouponId()
	{
		return this.couponId;
	}
	public void setCouponId(String couponId)
	{
		this.couponId = couponId;
	}
	public int getStatus()
	{
		return this.status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public Date getCreateDate()
	{
		return this.createDate;
	}
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}
	public Date getUpdateDate()
	{
		return this.updateDate;
	}
	public void setUpdateDate(Date updateDate)
	{
		this.updateDate = updateDate;
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

	@Override
	public int compareTo(AppointmentCalendar o)
	{

		return getStartScheduledTime().compareTo(o.getStartScheduledTime());
	}

	public String getModifiedBy()
	{
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy)
	{
		this.modifiedBy = modifiedBy;
	}

}
