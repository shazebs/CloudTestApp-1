package com.gcu.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcu.models.Status;


@Controller
@RequestMapping("/")
public class MainController 
{	
	@Autowired
	@SuppressWarnings("unused")
	private DataSource dataSource;
	
	@Autowired
	@SuppressWarnings("unused")
	private JdbcTemplate database;
	
	/**
	 * Constructor. 
	 * @param dataSource
	 */
	public MainController(DataSource dataSource)
	{
		this.dataSource = dataSource;
		this.database = new JdbcTemplate(dataSource);
	}
	
	/**
	 * Display Index page.
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String displayIndex(Model model) 
	{
		// query to read Post records from database
		/*
		List<String> posts = new ArrayList<String>();
		String sql = "SELECT * FROM posts"; 
		try {
			SqlRowSet response = database.queryForRowSet(sql);
			while (response.next()) 
			{
				posts.add(response.getString("Caption"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		
		// prepare view models
		model.addAttribute("posts", posts); 	
		*/
		
		return "index.html"; 
	}
	
	/**
	 * Display Home page. 
	 * @param model
	 * @return
	 */
	@GetMapping("/home")
	public String displayHome(Model model) 
	{
		// try to establish a connection to the database 
		try {
			Integer count = database.queryForObject("SELECT COUNT(*) FROM statuses", Integer.class);
	    	if (count >= 0) 
	    	{
	    		System.out.println("\nDatabase table statuses exists, processing new status save to DB now...");
	    	}		
			
		}
		catch (Exception e)
		{
			String createTableSql = "CREATE TABLE statuses (" +  
					"Id INT PRIMARY KEY AUTO_INCREMENT, " +
					"Author VARCHAR(255) NOT NULL, " + 
					"Message VARCHAR(1000) NOT NULL, " +
					"PhotoUrl VARCHAR(1000) NULL, " + 
					"Datetime VARCHAR(100) NOT NULL" +
					");";
			database.execute(createTableSql); 
			System.out.println("\nSql table statuses did not exist so application created one.");
		}
				
		List<Status> statuses = new ArrayList<Status>(); 
		try 
		{
			String sql = "SELECT * FROM statuses ORDER BY Id DESC";
			SqlRowSet record = database.queryForRowSet(sql);
			while (record.next())
			{
				statuses.add(new Status(
							record.getInt("Id"),
							record.getString("Author"),
							record.getString("Message"),
							record.getString("PhotoUrl"),
							record.getString("Datetime")
						));
				System.out.println("\nRead " + statuses.size() + " from statuses table.");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Error occurred while retrieving statuses from DB");
		}
		
		model.addAttribute("statuses", statuses);
		model.addAttribute("status", new Status()); 
		
		return "home.html"; 
	}
		/*try {
			// query to see if a database connection exists.
			SqlRowSet rowSet = database.queryForRowSet("SELECT 1"); 			
			if (rowSet.next()) 
			{
			   System.out.println("Successful database connection");
			} 
			else {
			   System.out.println("Unsuccessful database connection");
			}
			
		    try {	
				// query to see if database table exists	    	
		    	Integer count = database.queryForObject("SELECT COUNT(*) FROM " + "notes", Integer.class);
		    	if (count >= 0) {
		    		System.out.println("Database notes exitss");
		    	}
		    }
		    catch (Exception e)
		    {
		    	//e.printStackTrace();
		    	System.out.println("Error finding table in database, gonna try to creat it now");
		    	
		    	// query to create a table [AnonymousNotes]
			    String createTableSql = "CREATE TABLE notes (" +  
			    							"id INT PRIMARY KEY AUTO_INCREMENT, " +
		    								"author VARCHAR(255), " + 
			    							"content VARCHAR(1000), " +
		    								"datetime VARCHAR(100)" +
		    							");";
			    
			    database.execute(createTableSql); 
			    System.out.println("Databse created"); 
		    }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}*/	
	
	/**
	 * Display Post page.
	 * @param model
	 * @return
	 */
	@GetMapping("/post")
	public String displayPost(Model model) 
	{
		model.addAttribute("status", new Status());
		return "post.html"; 
	}
	
	/**
	 * Save a new Status to back end database.
	 * @param status
	 * @param model
	 * @return
	 */
	@PostMapping("/post")
	public String SubmitStatus(@ModelAttribute Status status, Model model)
	{
		System.out.printf
		(
			"\nid: %d \nauthor: %s \nmessage: %s \nphotoUrl: %s \ndatetime: %s\n", 
		    status.getId(),
		    status.getAuthor(),
		    status.getMessage(),
		    status.getPhotoUrl(),
		    status.getDatetime()
	    );
		
    	LocalDateTime timestamp = LocalDateTime.now();   
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a, M-dd-yyyy");
		status.setDatetime(timestamp.format(formatter));
		
		// try to establish a connection to the database 
		try {
			Integer count = database.queryForObject("SELECT COUNT(*) FROM statuses", Integer.class);
	    	if (count >= 0) 
	    	{
	    		System.out.println("\nDatabase table statuses exists, processing new status save to DB now...");
	    	}		
			
		}
		catch (Exception e)
		{
			String createTableSql = "CREATE TABLE statuses (" +  
					"Id INT PRIMARY KEY AUTO_INCREMENT, " +
					"Author VARCHAR(255) NOT NULL, " + 
					"Message VARCHAR(1000) NOT NULL, " +
					"PhotoUrl VARCHAR(1000) NULL, " + 
					"Datetime VARCHAR(100) NOT NULL" +
				");";
			database.execute(createTableSql); 
			System.out.println("\nSql table statuses did not exist so application created one.");
		}
		
		List<Status> statuses = new ArrayList<Status>(); 
		// post the new Status to database
		try {
    		String sql = "INSERT INTO statuses (Id, Author, Message, PhotoUrl, Datetime) VALUES (?, ?, ?, ?, ?)";
			int result = database.update(
							sql, 
							null,
							status.getAuthor(),
							status.getMessage(),
							status.getPhotoUrl(),
							status.getDatetime()
						);
			System.out.println("\nRecord saved to statuses table at " + status.getDatetime());
			
			sql = "SELECT * FROM statuses ORDER BY Id DESC";
			SqlRowSet record = database.queryForRowSet(sql);
			while (record.next())
			{
				statuses.add(new Status(
							record.getInt("Id"),
							record.getString("Author"),
							record.getString("Message"),
							record.getString("PhotoUrl"),
							record.getString("Datetime")
						));
			}
		}
		catch (Exception e)
		{	
			e.printStackTrace();
			System.out.println("\nError saving new status to database table statuses.");
		}

		// prepare view models  
		model.addAttribute("statuses", statuses);
		model.addAttribute("status", new Status()); 
		
		// return 
		return "home.html";
	}
	
	@GetMapping("/deleteStatus")
	public String DeleteStatus(@ModelAttribute Status status, Model model) {
		System.out.println("id = " + status.getId()); 
		
		return "home.html";
	}
	
}
