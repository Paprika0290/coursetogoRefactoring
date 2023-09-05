package com.coursetogo.controller.main;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.coursetogo.controller.api.APIController;
import com.coursetogo.controller.api.N_LoginAPIController;
import com.coursetogo.controller.user.CtgUserController;
import com.coursetogo.dto.user.CtgUserDTO;
import com.coursetogo.service.user.CtgUserService;

import lombok.extern.slf4j.Slf4j;

// 페이지 연결 등을 담당하는 컨트롤러
@Controller
@Slf4j
public class MainController {
	@Autowired
	private N_LoginAPIController loginApiController;
	
	@Autowired
	private CtgUserController userController;
	
	@Autowired
	private CtgUserService userService;
	
	@Autowired
	private APIController apiController;
	
	// 도메인 주소로 접속 시 첫 화면 출력
	@GetMapping("/")
	public String home(HttpSession session) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());		
		return "home";
	}
	
	// 회원가입
	@GetMapping("/user/sign_up")
	public String signUp(HttpSession session) {
		return "userSignup";
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
	
	// 회원정보 수정 페이지
	@GetMapping("/user/updateUserInfo")
	public String updateUserInfo() {
		return "userInfoUpdate";
	}
	
	
	
	
}