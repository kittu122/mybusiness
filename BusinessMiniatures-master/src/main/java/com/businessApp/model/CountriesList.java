package com.businessApp.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "countries")
public class CountriesList
{
	@Id
	private String id;
	private List<Countries> countries;

	public CountriesList() {
		this.countries = new ArrayList<>();

	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<Countries> getCountries()
	{
		return this.countries;
	}

	public void setCountries(List<Countries> countries)
	{
		this.countries = countries;
	}

}
