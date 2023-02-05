package com.gcu.models;

public class Status 
{
	private int Id; 
	private String Author;
	private String Message;
	private String PhotoUrl; 
	private String Datetime; 
	
	public Status(){}
	
	/**
	 * Populate all Status properties. 
	 * @param id
	 * @param text
	 * @param datetime
	 */
	public Status(int id, String author, String message, String photoUrl, String datetime)
	{
		Id = id;
		Author = author;
		Message = message; 
		PhotoUrl = photoUrl; 
		Datetime = datetime;
	}
	
	// getters
	public int getId() { return Id; }	
	public String getAuthor() { return Author; }
	public String getMessage() { return Message; }
	public String getDatetime() { return Datetime; }
	public String getPhotoUrl() { return PhotoUrl; }
	
	// setters
	public void setId(int id) { Id = id; }
	public void setAuthor(String author) { Author = author; }
	public void setMessage(String message) { Message = message; }
	public void setDatetime(String datetime) { Datetime = datetime; }
	public void setPhotoUrl(String photoUrl) { PhotoUrl = photoUrl; }
	
	// toString
}
