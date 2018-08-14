package com.businessApp.bean;

import java.util.ArrayList;
import java.util.List;

public class Employees
{
	private String employeeName;
	private String empId;

	private List<Appointment> appointment;

	public Employees() {

		this.appointment = new ArrayList<>();
	}

	public String getEmployeeName()
	{
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName)
	{
		this.employeeName = employeeName;
	}

	public String getEmpId()
	{
		return this.empId;
	}

	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public List<Appointment> getAppointment()
	{
		return this.appointment;
	}

	public void setAppointment(List<Appointment> appointment)
	{
		this.appointment = appointment;
	}

	@Override
	public String toString()
	{
		return "Employees [employeeName=" + this.employeeName + ", empId="
				+ this.empId + ", appointment=" + this.appointment + "]";
	}

}
