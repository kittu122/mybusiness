package com.businessApp.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class ResponseBean implements Serializable
{

	private static final long serialVersionUID = -7408970937530747871L;

	private int code;
	private String message;
	private Object result;

	// private List<Object> result = new ArrayList<>();

	public ResponseBean() {
		super();

		this.result = new ArrayList<>();
		this.code = 0;
		this.message = "";
		// this.result = "";
	}
	public int getCode()
	{
		return this.code;
	}
	public void setCode(int code)
	{
		this.code = code;
	}
	public String getMessage()
	{
		return this.message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public Object getResult()
	{
		return this.result;
	}
	public void setResult(Object result)
	{
		this.result = result;
	}

}
