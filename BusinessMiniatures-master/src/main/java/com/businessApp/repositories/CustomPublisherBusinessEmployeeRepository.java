package com.businessApp.repositories;

import org.springframework.transaction.annotation.Transactional;

import com.businessApp.model.EmployeePto;
import com.businessApp.model.PublisherBusinessEmployee;

@Transactional
public interface CustomPublisherBusinessEmployeeRepository
{

	public String updateEmployee(PublisherBusinessEmployee publBusinessEmployee)
			throws Exception;
	// public List<PublisherBusinessEmployee> listEmployee(
	// PublisherBusinessEmployee pBE) throws Exception;

	public String updateEmployeePto(EmployeePto updatePto) throws Exception;

}
