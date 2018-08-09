package com.businessApp.bean;

import java.util.ArrayList;
import java.util.List;

public class ConsumerHours
{
	private String time;
	private List<ConsumerServices> services;

	public ConsumerHours() {
		this.services = new ArrayList<>();
	}

	public String getTime()
	{
		return this.time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public List<ConsumerServices> getServices()
	{
		return this.services;
	}
	public void setServices(List<ConsumerServices> services)
	{
		this.services = services;
	}

}
