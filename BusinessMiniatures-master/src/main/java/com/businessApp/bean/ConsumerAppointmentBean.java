package com.businessApp.bean;

public class ConsumerAppointmentBean
{
	private String serviceId;
	private String serviceName;
	private String appointmentStartTime;
	private String appointmentEndTime;
	private String status;
	private String slots;
	private int empCount;

	public ConsumerAppointmentBean() {
		super();
	}
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
	public String getAppointmentStartTime()
	{
		return this.appointmentStartTime;
	}
	public void setAppointmentStartTime(String appointmentStartTime)
	{
		this.appointmentStartTime = appointmentStartTime;
	}
	public String getAppointmentEndTime()
	{
		return this.appointmentEndTime;
	}
	public void setAppointmentEndTime(String appointmentEndTime)
	{
		this.appointmentEndTime = appointmentEndTime;
	}
	public String getStatus()
	{
		return this.status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getSlots()
	{
		return this.slots;
	}
	public void setSlots(String slots)
	{
		this.slots = slots;
	}
	public int getEmpCount()
	{
		return this.empCount;
	}
	public void setEmpCount(int empCount)
	{
		this.empCount = empCount;
	}

}
