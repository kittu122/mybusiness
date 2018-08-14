package com.businessApp.model;

import org.springframework.stereotype.Component;

@Component
public class BusinessHours
{
	// To Store Business Hours

	private int holiday;
	private int open;
	private int close;

	public int getOpen()
	{
		return this.open;
	}
	public void setOpen(int open)
	{
		this.open = open;
	}
	public int getClose()
	{
		return this.close;
	}
	public void setClose(int close)
	{
		this.close = close;
	}
	public int getHoliday()
	{
		return this.holiday;
	}
	public void setHoliday(int holiday)
	{
		this.holiday = holiday;
	}

}
