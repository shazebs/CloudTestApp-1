package com.gcu.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.models.Status;

import jakarta.validation.Valid;


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
	
	public MainController(DataSource dataSource)
	{
		this.dataSource = dataSource;
		this.database = new JdbcTemplate(dataSource);
	}
	
	@GetMapping("/")
	public String displayIndex(Model model) 
	{
		// ****
		List<String> posts = new ArrayList<String>();
		String sql = "SELECT * FROM posts"; 
		try {
			SqlRowSet response = database.queryForRowSet(sql);
			while (response.next()) {
				posts.add(response.getString("Caption"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		// ****
		model.addAttribute("posts", posts); 		
		return "index.html"; 
	}
	
	@GetMapping("/home")
	public String displayHome(Model model) {
		return "home.html"; 
	}
	
	@GetMapping("/post")
	public String displayPost(Model model) {
		model.addAttribute("status", new Status());
		return "post.html"; 
	}
	
	@GetMapping("/SubmitNewStatus")
	public String SubmitNewStatus(@Valid Status status, BindingResult bindingResult, Model model) {
		System.out.println("Hello world");
		return "index.html";
	}
}
