package com.businessApp.repositories;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.businessApp.model.Business;

@Transactional
public interface CustomBusinessRepository
{
	public String updateBusiness(Business business) throws Exception;
	public List<Business> listBusiness() throws Exception;
	// public Object listServiceCategories(String businessId) throws Exception;
	public List<Business> allBusinessServiceCategories() throws Exception;
}
