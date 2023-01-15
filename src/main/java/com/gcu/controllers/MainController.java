package com.gcu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class MainController {
	
	@GetMapping("/")
	public String displayIndex(Model model) {
		return "index.html"; 
	}
	
	@GetMapping("/home")
	public String displayHome(Model model) {
		return "home.html"; 
	}
	
	@GetMapping("/post")
	public String displayPost(Model model) {
		return "post.html"; 
	}
}
