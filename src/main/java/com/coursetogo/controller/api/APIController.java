package com.coursetogo.controller.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coursetogo.controller.user.CtgUserController;
import com.coursetogo.dto.course.CourseDTO;
import com.coursetogo.dto.user.CtgUserDTO;
import com.coursetogo.service.course.CourseService;
import com.coursetogo.service.user.CtgUserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class APIController {
	
	@Autowired
	private CtgUserController userController;
	
	@Autowired
	private CtgUserService userService;
	
	@Autowired
	private CourseService courseService;
	
	
	// 닉네임 중복 확인 (userInfoUpdate.jsp / userSignup.jsp)
	@GetMapping("/user/userNicknameCheck")
	@ResponseBody
	public int userNicknameCheck(@RequestParam("userNickname") String userNickname) {
		int res = 0;
		
		if(userNickname == null || userNickname == "") {
			res = -1;
			return res;
		}else {
			try {
				res = userService.nicknameCheck(userNickname);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return res;
		}		
	}
	
	// courseInformDTO의 userNickname으로 userPhoto 조회 -> axios로 사용할 예정이었으나 사용하지 못하게 됨. 일단 잔류 
	@GetMapping("/user/userPhoto")
	public String getUserPhoto(@RequestParam("courseId") int courseId) {

		CourseDTO course = null;
		CtgUserDTO user = null;
		
		try {
			course = courseService.getCourseById(courseId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			user = userService.getCtgUserByUserId(course.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(user.getUserPhoto());
		return user.getUserPhoto();
	}
	
	// 회원탈퇴 진행
	@PutMapping("/user/unsign")
	public String unsignUserByUserId(@RequestParam int userId, HttpSession session) {
		String view = "error";
		boolean result = false;
		
		try {
			result = userService.unsignCtgUserByUserId(userId);
			
			if(result) {
				session.invalidate();
				view = "redirect:/";
				return view;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return view;
		}
		return view;
	}
	
	// 회원가입 성공 후 세션에 값 부여
	@PostMapping("/user/sign_up_done")
	public String signUpDone(@ModelAttribute CtgUserDTO user,
							 HttpSession session) {		
		boolean result = false;
		
		try {
			result = userController.insertCtgUser(user);
		}catch(Exception e){
			return "error";
		}
		
	
		
		if(result) {
			
			CtgUserDTO userForSession = null;
			
			try {
				// 데이터베이스에 insert될때에 생성되는 userId를 가져오기 위해 DB에 접촉, user객체를 가져옴
				userForSession = userService.getCtgUserByNaverIdAndName(user.getNaverId().substring(0, 10),
											   		   user.getNaverId().substring(user.getNaverId().length() -10),
													   user.getUserName());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			// 꼭 필요한 값만을 가진 user객체를 세션에 부여
			userForSession = CtgUserDTO.builder().userId(userForSession.getUserId())
												 .userNickname(userForSession.getUserNickname())
												 .userEmail(userForSession.getUserEmail())
												 .userPhoto(userForSession.getUserPhoto())
												 .userIntroduce(userForSession.getUserIntroduce())
												 .build();
			
			session.setAttribute("user", userForSession);
		}
		return "userSignupDone";
	}
	
	// 유저 정보 수정
	@PostMapping("/user/update")
	public String updateUser(@ModelAttribute CtgUserDTO user
							 ,HttpSession session) {
		CtgUserDTO DBUser = null;
		
		try {
			DBUser = userService.getCtgUserByUserId(user.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DBUser.setUserNickname(user.getUserNickname());
		DBUser.setUserPhoto(user.getUserPhoto());
		DBUser.setUserIntroduce(user.getUserIntroduce());
		
		boolean result = false;
		
		try {
			result = userService.updateCtgUser(DBUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result) {
			session.setAttribute("user", DBUser);
			return "userInfoUpdate";
		}
		
		return "redirect:/";
	}
	
	// 유저 정보 수정 (관리자)
	@PostMapping("/admin/user/update")
	public String updateUserByAdmin(@ModelAttribute CtgUserDTO user
							 		,HttpSession session) {
		CtgUserDTO DBUser = null;
		System.out.println(user);
		
		try {
			DBUser = userService.getCtgUserByUserId(user.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DBUser.setUserNickname(user.getUserNickname());
		DBUser.setUserEmail(user.getUserEmail());
		DBUser.setUserAdmin(user.getUserAdmin());
		DBUser.setUserIntroduce(user.getUserIntroduce());
		
		boolean result = false;
		
		try {
			result = userService.updateCtgUser(DBUser);
		} catch (Exception e) {
			log.warn("admin- 회원 정보 수정에 실패하였습니다.");
			e.printStackTrace();
		}
		
		if(result) {
			session.setAttribute("user", DBUser);
			return "redirect:/admin/user";
		}
		
		return "redirect:/admin/user";
	}
	
}
