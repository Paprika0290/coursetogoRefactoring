package com.coursetogo.controller.user;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coursetogo.dto.user.CtgUserDTO;
import com.coursetogo.service.user.CtgUserService;

@Controller
public class CtgUserController {

	@Autowired
	private CtgUserService service;
	
	// 네이버 회원 정보가 맵핑된 CtgUser객체를 받아와 회원 가입 진행하는 메서드---------------------------------------------------------------
	// 성공:true 실패:false	
	public boolean insertCtgUser(CtgUserDTO user) {
		user.setUserPhoto("/images/profile(0).png");
		user.setUserAdmin(1);
		boolean result = false;
		
		try {
			result = service.insertCtgUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	
	// 네이버 회원 정보가 맵핑된 CtgUser객체에서 받아온 naverId, userName 이용하여 기존에 존재하는 회원인지 확인하는 메서드----------------------------
	// 존재하면 : 해당 CtgUser 객체 / 존재하지 않으면 : null
	public CtgUserDTO getCtgUserByNaverIdAndName(String naverIdFront, String naverIdRear, String userName) {
		CtgUserDTO user = null;
		
		try {
			user = service.getCtgUserByNaverIdAndName(naverIdFront, naverIdRear, userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	
	// userId를 받아 회원DTO를 가져오는 메서드---------------------------------------------------------------------------------------
	// 존재하면 : 해당 CtgUser 객체 / 존재하지 않으면 : null
	public CtgUserDTO getCtgUserByUserId(int UserId) {
		CtgUserDTO user = null;
		
		try {
			user = service.getCtgUserByUserId(UserId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	
	// 수정된 userDTO를 변수로 받아 업데이트하는 메서드---------------------------------------------------------------------------------------
	// 성공하면 : true / 실패하면 : false
	public boolean updateCtgUser(CtgUserDTO user) {
		boolean result = false;
		try {
			result = service.updateCtgUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 회원 탈퇴를 진행하는 메서드---------------------------------------------------------------------------------------
	// 성공하면 : true / 실패하면 : false
	@RequestMapping(value = "/myPageInformModify", method = RequestMethod.PUT)
	public String unsignCtgUserByUserId(@RequestParam int userId,
										   HttpSession session) {
		String view = "error";
		boolean result = false;
		
		try {
			result = service.unsignCtgUserByUserId(userId);
			
			if(result) {
				session.invalidate();
				view = "redirect:/home";
				return view;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return view;
		}
		return view;
	}

	// 닉네임 중복 검증
	public int nicknameCheck(String userNickname) {
		int result = 0;
		
		try {
			result = service.nicknameCheck(userNickname);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 나의 코스 개수 가져오는 메서드--------------------------------------------------------------
	public int getMyCourseCount(int userId) {
		int count = 0;
		
		try {
			count = service.getMyCourseCount(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	// 찜한 코스 개수 가져오는 메서드--------------------------------------------------------------
	public int getMyBookmarkCount(int userId) {
		int count = 0;
		
		try {
			count = service.getMyBookmarkCount(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	// 나의 리뷰 개수 가져오는 메서드--------------------------------------------------------------
	public int getMyReviewCount(int userId) {
		int count = 0;
		
		try {
			count = service.getMyReviewCount(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return count;
	}
}