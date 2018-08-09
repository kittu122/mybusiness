package com.businessApp.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.businessApp.model.PublisherBusiness;
import com.businessApp.model.User;
@Component
public class LoginResult
{
	private User userData;
	private List<PublisherBusiness> businessData;

	public LoginResult() {

		this.businessData = new ArrayList<>();
	}

	public User getUserData()
	{
		return this.userData;
	}
	public void setUserData(User userData)
	{
		this.userData = userData;
	}
	public List<PublisherBusiness> getBusinessData()
	{
		return this.businessData;
	}
	public void setBusinessData(List<PublisherBusiness> businessData)
	{
		this.businessData = businessData;
	}

}
