package com.businessApp.bean;

import com.businessApp.model.Pto;

public class AddPto
{
	private String id;
	private String empId;
	private Pto pto;

	public String getEmpId()
	{
		return this.empId;
	}
	public void setEmpId(String empId)
	{
		this.empId = empId;
	}
	public Pto getPto()
	{
		return this.pto;
	}
	public void setPto(Pto pto)
	{
		this.pto = pto;
	}
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
	}

}
