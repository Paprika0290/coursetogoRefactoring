package com.coursetogo.controller.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coursetogo.controller.api.N_LoginAPIController;
import com.coursetogo.controller.map.CourseController;
import com.coursetogo.controller.review.ReviewController;
import com.coursetogo.dto.course.CourseDTO;
import com.coursetogo.dto.course.CourseInformDTO;
import com.coursetogo.dto.review.CourseReviewDTO;
import com.coursetogo.service.course.CourseService;
import com.coursetogo.service.user.CtgUserService;

import lombok.extern.slf4j.Slf4j;

// 페이지 연결 등을 담당하는 컨트롤러
@Controller
@Slf4j
public class MainController {
	@Autowired
	private N_LoginAPIController loginApiController;
	
	@Autowired
	private CourseController courseController;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private CtgUserService userService;
	
	
	// 도메인 주소로 접속 시 첫 화면 출력
	@GetMapping("/")
	public String getHomePage(HttpSession session) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());		
		return "home";
	}
	
	// 회원가입
	@GetMapping("/user/sign_up")
	public String getSignUpPage(HttpSession session) {
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
	public String getUpdateUserInfoPage() {
		return "userInfoUpdate";
	}
	
	// 코스 만들기 페이지
	@GetMapping("/course/courseMake")
	public String getCourseMakePage() {
		return "map_CourseMake";
	}
	
	// 코스 만들기 (INSERT)
	@PostMapping("/course/courseMake")
	public String insertNewCourse(@ModelAttribute CourseDTO course,
								  Model mdoel) {
		System.out.println(course);
		
		
		
		return "";
	}
	
	// 코스 상세 페이지
	@GetMapping("course/courseDetail")
	public String getCourseDetailPage(@RequestParam("courseId") String courseId,
									  Model model) {
		CourseInformDTO courseInform = null;

		String userPhoto = null;
		
		try {
			log.info(courseId + "번 코스 조회");
			courseInform = courseService.getCourseInformByCourseId(Integer.parseInt(courseId));
			int userId = courseService.getCourseById(courseInform.getCourseId()).getUserId();
			userPhoto = userService.getCtgUserByUserId(userId).getUserPhoto();
		} catch (Exception e) {
			log.warn("코스 상세 페이지 return 실패");
			e.printStackTrace();
		}

		model.addAttribute("courseInform", courseInform);
		model.addAttribute("userPhoto", userPhoto);
		
		return "map_CourseDetail";
	}
	
	// 코스 찾기 페이지
	@GetMapping("/course/courseList")
	public String getCourseListPage(HttpSession session, Model model) {
		Map<String, Object> getCourseList = courseController.makeCourseList(session);

		List<CourseInformDTO> courseInformList = (List<CourseInformDTO>) getCourseList.get("courseInformList");
		List<CourseInformDTO> recommendedCourseInformList = (List<CourseInformDTO>) getCourseList.get("recommendedCourseInformList");
		
		List<String> userPhotoSrcList = new ArrayList<String>();
		List<String> courseDetailPageList = new ArrayList<String>();
		
		int userId = 0;
		String userPhoto = null;
		
		for(CourseInformDTO courseInform : courseInformList) {
			
			try {
				userId = courseService.getCourseById(courseInform.getCourseId()).getUserId();
				userPhoto = userService.getCtgUserByUserId(userId).getUserPhoto();
			} catch (SQLException e) {
				log.warn("코스를 작성한 유저의 사진을 가져오는 과정에 에러 발생");
				e.printStackTrace();
			}
			
			userPhotoSrcList.add(userPhoto);
		}
		
		for (CourseInformDTO course : courseInformList) {
        	int courseId = course.getCourseId();
            String courseIdList = course.getCourseIdList();
            String[] placeIds = courseIdList.split(",");
            String query = "";
            int courseNumber = course.getCourseNumber();
         
            query += ("courseId="+ String.valueOf(courseId));
        
            courseDetailPageList.add(query);    
		}			
		
		model.addAttribute("courseInformList", courseInformList);
		model.addAttribute("pageInfo", getCourseList.get("pageInfo"));
		model.addAttribute("recommendedCourseInformList", recommendedCourseInformList);
		model.addAttribute("userPhotoSrcList", userPhotoSrcList);
		model.addAttribute("courseDetailPageList", courseDetailPageList);
		
		return "map_CourseList";
	}
	
	
}