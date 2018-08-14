package com.businessApp.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employeePto")
// @JsonInclude(Include.NON_EMPTY)
public class EmployeePto
{

	@Id
	private String id;
	private String empId;
	private List<Pto> pto;

	public EmployeePto() {
		this.pto = new ArrayList<>();
	}

	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getEmpId()
	{
		return this.empId;
	}
	public void setEmpId(String empId)
	{
		this.empId = empId;
	}

	public List<Pto> getPto()
	{
		return this.pto;
	}

	public void setPto(List<Pto> pto)
	{
		this.pto = pto;
	}

}
