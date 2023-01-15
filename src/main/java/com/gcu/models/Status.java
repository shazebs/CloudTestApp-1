package com.gcu.models;

import java.util.List;

public class Status 
{
	// properties
	private int Id; 
	private String Text;
	private String Datetime;
	
	// constructors
	public Status(){}
	
	/**
	 * Populate all Status properties. 
	 * @param id
	 * @param text
	 * @param datetime
	 */
	public Status(int id, String text, String datetime)
	{
		Id = id;
		Text = text;
		Datetime = datetime;
	}
	
	// getters
	public int getId() { return Id; }	
	public String getText() { return Text; }
	public String getDatetime() { return Datetime; }
	
	// setters
	public void setId(int id) { Id = id; }
	public void setText(String text) { Text = text; }
	public void setDatetime(String datetime) { Datetime = datetime; }
	
	// toString
}
