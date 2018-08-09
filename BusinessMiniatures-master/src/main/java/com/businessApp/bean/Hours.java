package com.businessApp.bean;

import java.util.ArrayList;
import java.util.List;

public class Hours
{
	private String time;
	private List<Employees> employees;

	public Hours() {
		this.employees = new ArrayList<>();
	}

	public String getTime()
	{
		return this.time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public List<Employees> getEmployees()
	{
		return this.employees;
	}

	public void setEmployees(List<Employees> employees)
	{
		this.employees = employees;
	}

	@Override
	public String toString()
	{
		return "Hours [time=" + this.time + ", employees=" + this.employees
				+ "]";
	}

}
