package com.businessApp.bean;

import java.util.Comparator;

public class Appointment implements Comparable<Appointment>
{

	private String id;
	private String serviceId;
	private String empId;
	private String serviceName;
	private int appointmentStartTime;
	private int appointmentEndTime;
	private String type;
	private String consumerName;
	private String consumerPhoneNo;
	private String colour;
	private int status;
	private int slots;
	private int empCount;

	public int getSlots()
	{
		return this.slots;
	}
	public void setSlots(int slots)
	{
		this.slots = slots;
	}
	public String getColour()
	{
		return this.colour;
	}
	public void setColour(String colour)
	{
		this.colour = colour;
	}
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
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
	public int getAppointmentStartTime()
	{
		return this.appointmentStartTime;
	}
	public void setAppointmentStartTime(int date)
	{
		this.appointmentStartTime = date;
	}
	public int getAppointmentEndTime()
	{
		return this.appointmentEndTime;
	}
	public void setAppointmentEndTime(int appointmentEndTime)
	{
		this.appointmentEndTime = appointmentEndTime;
	}

	public String getConsumerName()
	{
		return this.consumerName;
	}
	public void setConsumerName(String consumerName)
	{
		this.consumerName = consumerName;
	}
	public String getConsumerPhoneNo()
	{

		return this.consumerPhoneNo;
	}
	public void setConsumerPhoneNo(String consumerPhoneNo)
	{
		this.consumerPhoneNo = consumerPhoneNo;
	}
	public String getType()
	{
		return this.type;
	}
	public void setType(String type)
	{
		this.type = type;
	}

	public int getStatus()
	{
		return this.status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public String getEmpId()
	{
		return this.empId;
	}
	public void setEmpId(String empId)
	{
		this.empId = empId;
	}
	public int getEmpCount()
	{
		return this.empCount;
	}
	public void setEmpCount(int empCount)
	{
		this.empCount = empCount;
	}

	public static Comparator<Appointment> AppointmentsSorting = new Comparator<Appointment>() {

		@Override
		public int compare(Appointment a, Appointment b)
		{

			int start1 = a.getAppointmentStartTime();
			int start2 = b.getAppointmentStartTime();

			/* For ascending order */
			return start1 - start2;

		}
	};

	@Override
	public String toString()
	{
		return "Appointment [id=" + this.id + ", serviceId=" + this.serviceId
				+ ", serviceName=" + this.serviceName
				+ ", appointmentStartTime=" + this.appointmentStartTime
				+ ", appointmentEndTime=" + this.appointmentEndTime + ", type="
				+ this.type + ", consumerName=" + this.consumerName
				+ ", consumerPhoneNo=" + this.consumerPhoneNo + ", colour="
				+ this.colour + "]";
	}

	@Override
	public int compareTo(Appointment o)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	public static Comparator<Appointment> getAppointmentsSorting()
	{
		return AppointmentsSorting;
	}
	public static void setAppointmentsSorting(
			Comparator<Appointment> appointmentsSorting)
	{
		AppointmentsSorting = appointmentsSorting;
	}

}
