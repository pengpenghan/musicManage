package com.hpp.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hpp.utils.MyLogger;

@Controller
public class HomeController {
	
	private static final MyLogger LOGGER = new MyLogger(HomeController.class);

	@RequestMapping(value={"/","/index"})
	public String index(HttpSession session, Model model) {
		
		return "login";
	}
}
