package com.businessApp.bean;

import java.util.ArrayList;
import java.util.List;

public class ConsumerServices
{

	private String serviceName;
	private String serviceId;
	private List<Appointment> appointment;

	public ConsumerServices() {

		this.appointment = new ArrayList<>();
	}

	public List<Appointment> getAppointment()
	{
		return this.appointment;
	}

	public void setAppointment(List<Appointment> appointment)
	{
		this.appointment = appointment;
	}

	public String getServiceName()
	{
		return this.serviceName;
	}
	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}
	public String getServiceId()
	{
		return this.serviceId;
	}
	public void setServiceId(String serviceId)
	{
		this.serviceId = serviceId;
	}

}
