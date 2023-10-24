package com.coursetogo.controller.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.coursetogo.dto.user.CtgUserDTO;
import com.coursetogo.service.course.CourseService;
import com.coursetogo.service.review.CourseReviewService;
import com.coursetogo.service.user.CtgUserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CtgUserController {

	@Autowired
	private CtgUserService service;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private CourseReviewService courseReviewService;
	
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
	
	// 회원탈퇴 진행
	@PutMapping("/user/unsign")
	public String unsignUserByUserId(@RequestParam int userId, HttpSession session) {
		String view = "error";
		boolean result = false;
		
		try {
			result = service.unsignCtgUserByUserId(userId);
			
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
		
		user.setUserPhoto("/images/profile(0).png");
		user.setUserAdmin(1);
		boolean result = false;
		
		try {
			result = service.insertCtgUser(user);
		}catch(Exception e){
			return "error";
		}
		
		if(result) {
			
			CtgUserDTO userForSession = null;
			
			try {
				// 데이터베이스에 insert될때에 생성되는 userId를 가져오기 위해 DB에 접촉, user객체를 가져옴
				userForSession = service.getCtgUserByNaverIdAndName(user.getNaverId().substring(0, 10),
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
		return "user_SignupDonePage";
	}
	
	// 유저 정보 수정
	@PostMapping("/user/update")
	public String updateUser(@ModelAttribute CtgUserDTO user
							 ,HttpSession session) {
		CtgUserDTO DBUser = null;
		
		try {
			DBUser = service.getCtgUserByUserId(user.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DBUser.setUserNickname(user.getUserNickname());
		DBUser.setUserPhoto(user.getUserPhoto());
		DBUser.setUserIntroduce(user.getUserIntroduce());
		
		boolean result = false;
		
		try {
			result = service.updateCtgUser(DBUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result) {
			session.setAttribute("user", DBUser);
			return "user_InfoPage";
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
			DBUser = service.getCtgUserByUserId(user.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DBUser.setUserNickname(user.getUserNickname());
		DBUser.setUserEmail(user.getUserEmail());
		DBUser.setUserAdmin(user.getUserAdmin());
		DBUser.setUserIntroduce(user.getUserIntroduce());
		
		boolean result = false;
		
		try {
			result = service.updateCtgUser(DBUser);
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
	
	
	// 관리자 페이지 - userList 페이지를 구성하는 데에 필요한 정보들을 담아 돌려주는 메서드.
	public HashMap<String, Object> getUserListValues(int pageNum, int pageSize, int groupNum,
													 String category, String keyword) {
		int allUserCount = 0;
		int unsignedUserCount = 0;
		int searchedUserCount = 0;
		try {
			allUserCount = service.getAllUserCount();
			unsignedUserCount = service.getUnsignedUserCount();
			if(category != null) {
				searchedUserCount = service.getSearchedUserCount(category, keyword);
			}
		} catch (SQLException e) {
			log.warn("admin- 유저 수 조회에 실패하였습니다.");
			e.printStackTrace();
		}
		
		List<CtgUserDTO> userList = new ArrayList<CtgUserDTO>();
		
		if(category == null) {
			try {
				userList = service.getAllUserListByPage(pageNum, pageSize);
			} catch (SQLException e) {
				log.warn("admin- 전체 유저리스트 조회에 실패하였습니다.");
				e.printStackTrace();
			}
		}else {
			try {
				userList = service.getUserListByKeywordWithPage(pageNum, pageSize, category, keyword);
			} catch (SQLException e) {
				log.warn("admin- 유저리스트 키워드 검색에 실패하였습니다.");
				e.printStackTrace();
			}
		}
		
		
		List<Integer> userCourseCountList = new ArrayList<Integer>();
		List<Integer> userCourseReviewCountList = new ArrayList<Integer>();
		int count1 = 0;
		int count2 = 0;
		for(CtgUserDTO user : userList) {
			try {
				count1 = courseService.getUserCourseCount(user.getUserId());
				count2 = courseReviewService.getUserCourseReviewCount(user.getUserId());
			} catch (SQLException e) {
				log.warn("admin- 유저의 게시글 수 조회에 실패하였습니다.");
				e.printStackTrace();
			}
			userCourseCountList.add(count1);
			userCourseReviewCountList.add(count2);
		}
		
		HashMap<String, Object> ListValues = new HashMap<String, Object>();
		ListValues.put("allUserCount", allUserCount);	
		ListValues.put("unsignedUserCount", unsignedUserCount);	
		ListValues.put("allUserList", userList);		
		ListValues.put("userCourseCountList", userCourseCountList);
		ListValues.put("userCourseReviewCountList", userCourseReviewCountList);
		
		int totalPages = 0;
		if(category == null) {
			
			if( (allUserCount / pageSize) < ((double)allUserCount / (double)pageSize) &&
				((double)allUserCount / (double)pageSize) < (allUserCount / pageSize) + 1 ) {
				totalPages = (allUserCount / pageSize) + 1;
			} else {
				totalPages = (allUserCount / pageSize);
			}
	
			int totalGroups = 0;
			if( (totalPages / 10) < ((double)totalPages / 10) &&
				((double)totalPages / 10) < (totalPages / 10) + 1 ) {
				totalGroups = (totalPages / 10) + 1;
			} else {
				totalGroups = (totalPages / 10);
			}
			
			
			for(int i = 1; i <= totalGroups; i++) {
				if( (i-1) < ((double)pageNum / 10) && ((double)pageNum / 10) < i) {
					groupNum = i;
					break;
				}
			}
			
			ListValues.put("pageNum", pageNum);
			ListValues.put("pageSize", pageSize);
			ListValues.put("groupNum", groupNum);
			ListValues.put("totalPages", totalPages);
			ListValues.put("totalGroups", totalGroups);
			
		}else {
			
			if( (searchedUserCount / pageSize) < ((double)searchedUserCount / (double)pageSize) &&
					((double)searchedUserCount / (double)pageSize) < (searchedUserCount / pageSize) + 1 ) {
					totalPages = (searchedUserCount / pageSize) + 1;
				} else {
					totalPages = (searchedUserCount / pageSize);
				}
		
				int totalGroups = 0;
				if( (totalPages / 10) < ((double)totalPages / 10) &&
					((double)totalPages / 10) < (totalPages / 10) + 1 ) {
					totalGroups = (totalPages / 10) + 1;
				} else {
					totalGroups = (totalPages / 10);
				}
				
				
				for(int i = 1; i <= totalGroups; i++) {
					if( (i-1) < ((double)pageNum / 10) && ((double)pageNum / 10) < i) {
						groupNum = i;
						break;
					}
				}
				
				ListValues.put("pageNum", pageNum);
				ListValues.put("pageSize", pageSize);
				ListValues.put("groupNum", groupNum);
				ListValues.put("totalPages", totalPages);
				ListValues.put("totalGroups", totalGroups);
				ListValues.put("category", category);
				ListValues.put("keyword", keyword);
		}
		
		return ListValues;
	}
	
}