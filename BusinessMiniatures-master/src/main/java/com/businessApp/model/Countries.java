package com.businessApp.model;

import java.util.ArrayList;
import java.util.List;

public class Countries
{
	private String name;
	private String dial_code;
	private String code;
	public List<States> getStates()
	{
		return this.states;
	}

	public void setStates(List<States> states)
	{
		this.states = states;
	}
	private List<States> states;

	public Countries() {
		this.states = new ArrayList<>();
	}

	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDial_code()
	{
		return this.dial_code;
	}
	public void setDial_code(String dial_code)
	{
		this.dial_code = dial_code;
	}
	public String getCode()
	{
		return this.code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
}
