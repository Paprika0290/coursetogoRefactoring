package com.coursetogo.controller.main;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.coursetogo.controller.api.N_LoginAPIController;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	@Autowired
	private N_LoginAPIController loginApiController;
	
	
	
	@GetMapping("/")
	public String home(HttpSession session) {
		
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());
		
		return "home";
	}
	
	@GetMapping("/user/sign_up")
	public String signUp(HttpSession session) {
		System.out.println(session.getAttribute("newUser"));
		return "userSignup";
	}
	
}