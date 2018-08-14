package com.businessApp.model;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "publisher_businessQRCode")
public class QRCode
{

	private String id;
	private Binary qrCode;

	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public Binary getQrCode()
	{
		return this.qrCode;
	}
	public void setQrCode(Binary qrCode)
	{
		this.qrCode = qrCode;
	}

}
