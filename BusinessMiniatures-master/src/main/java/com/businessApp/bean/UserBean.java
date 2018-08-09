package com.businessApp.bean;

import org.springframework.stereotype.Component;

@Component
public class UserBean
{
	private String logInId;
	private String password;
	private String userType;

	public String getLogInId()
	{
		return this.logInId;
	}
	public void setLogInId(String logInId)
	{
		this.logInId = logInId;
	}
	public String getPassword()
	{
		return this.password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getUserType()
	{
		return this.userType;
	}
	public void setUserType(String userType)
	{
		this.userType = userType;
	}

}
