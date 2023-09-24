package com.coursetogo.controller.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coursetogo.controller.api.N_LoginAPIController;
import com.coursetogo.controller.api.N_MapAPIController;
import com.coursetogo.controller.map.CourseController;
import com.coursetogo.controller.review.ReviewController;
import com.coursetogo.dto.course.CourseDTO;
import com.coursetogo.dto.course.CourseInformDTO;
import com.coursetogo.dto.map.PlaceDTO;
import com.coursetogo.dto.review.CourseReviewDTO;
import com.coursetogo.dto.review.PlaceReviewDTO;
import com.coursetogo.dto.user.CtgUserDTO;
import com.coursetogo.enumType.Area;
import com.coursetogo.enumType.Category;
import com.coursetogo.service.course.CoursePlaceService;
import com.coursetogo.service.course.CourseService;
import com.coursetogo.service.map.PlaceService;
import com.coursetogo.service.review.CourseReviewService;
import com.coursetogo.service.review.PlaceReviewService;
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
	
	@Autowired
	private CourseReviewService courseReviewService;
	
	@Autowired
	private PlaceReviewService placeReviewService;
	
	@Autowired
	private ReviewController reviewController;
	
	
	// 도메인 주소로 접속 시 첫 화면 출력
	@GetMapping("/")
	public String getHomePage(HttpSession session) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());		
		return "home";
	}
	
	// 회원가입
	@GetMapping("/user/sign_up")
	public String getSignUpPage(HttpSession session) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());	
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
	public String getUpdateUserInfoPage(HttpSession session) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());	
		return "userInfoUpdate";
	}
	
	// 유저 마이페이지
	@GetMapping("user/myPage")
	public String getMyPage(HttpSession session) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());			
		return "user_MyPage";
	}
	
	// 유저 마이페이지 - 로그인 되어 있지 않은 경우
	@GetMapping("user/myPage/loginRequired")
	public String getLoginRequiredPage(HttpSession session) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());	
		return "user_MyPage_Null";
	}
	
	// 유저 마이페이지 - 코스리스트
	@GetMapping("user/myPage/courseList")
	public String getUserCourseList(HttpSession session, Model model) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());	
		
		int userId = -1;
		if(session.getAttribute("user") != null) {
			userId = ((CtgUserDTO) session.getAttribute("user")).getUserId();
		}		
		
		List<CourseInformDTO> userCourseList = new ArrayList<CourseInformDTO>();
		List<String> courseDetailPageList = new ArrayList<String>();
		
		String userPhoto = "";
		
		try {
			userCourseList = courseService.getCourseInformByUserId(userId);
		} catch (Exception e) {
			log.warn("유저의 코스리스트 조회 실패");
			e.printStackTrace();
		}
		
		userPhoto = ((CtgUserDTO) session.getAttribute("user")).getUserPhoto();
		
		for (CourseInformDTO course : userCourseList) {
        	int courseId = course.getCourseId();
            String query = "";
         
            query += ("courseId="+ String.valueOf(courseId));
        
            courseDetailPageList.add(query);    
		}
		
		model.addAttribute("userCourseList", userCourseList);
		model.addAttribute("userPhoto", userPhoto);
		model.addAttribute("courseDetailPageList", courseDetailPageList);
		
		return "user_MyPage_CourseList";
	}
	
	// 유저 마이페이지 - 리뷰리스트
	@GetMapping("user/myPage/reviewList")
	public String getUserReviewList(HttpSession session, Model model) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());	
		int userId = -1;
		if(session.getAttribute("user") != null) {
			userId = ((CtgUserDTO) session.getAttribute("user")).getUserId();
		}	
			
		List<CourseReviewDTO> courseReviewList = null;

		try {
			courseReviewList = courseReviewService.getCourseReviewByUserId(userId);
		} catch (SQLException e) {
			log.warn("유저 리뷰리스트 - 코스리뷰리스트 조회 실패");
			e.printStackTrace();
		}
		
		// 리뷰리스트에 필요한 정보만 담은 이중배열을 model을 통해 전달
		String[][] entireReviewInfo = new String[courseReviewList.size()][12];
		
		for(int j = 0; j < courseReviewList.size(); j++) {
				CourseInformDTO courseInform = null;			
					try {
						courseInform = courseService.getCourseInformByCourseId(courseReviewList.get(j).getCourseId());
					} catch (Exception e) {
						log.warn("유저 리뷰리스트 - 코스inform리스트 조회 실패");		
						e.printStackTrace();
					}
				
				String[] placeIds = courseInform.getCourseIdList().split(",");
				PlaceReviewDTO[] placeReviews = new PlaceReviewDTO[placeIds.length];
				StringBuilder builder1 = new StringBuilder();
				StringBuilder builder2 = new StringBuilder();
				
					for(int i = 0; i < placeIds.length; i++) {	
						try {
							placeReviews[i] = placeReviewService.getPlaceReviewByUserIdAndPlaceId(userId, Integer.parseInt(placeIds[i]));
							
							if(i == placeIds.length - 1) {
								builder1.append(placeReviews[i].getPlaceReviewId());
								builder2.append((int)(Math.floor(placeReviews[i].getPlaceScore())));
							}else {
								builder1.append(placeReviews[i].getPlaceReviewId());
								builder1.append(",");
								
								builder2.append((int)(Math.floor(placeReviews[i].getPlaceScore())));
								builder2.append(",");
							}
						} catch (NumberFormatException | SQLException e) {
							log.warn("유저 리뷰리스트 - "+ placeIds[i] + "번 장소 조회 실패");
							e.printStackTrace();
						}	
					}	
					
			entireReviewInfo[j][0] = String.valueOf(courseInform.getCourseId());                            
			entireReviewInfo[j][1] = courseInform.getUserNickname();										  
			entireReviewInfo[j][2] = courseInform.getCourseName();										   
			entireReviewInfo[j][3] = String.valueOf((int)(Math.floor(courseInform.getCourseAvgScore())));   	
			entireReviewInfo[j][4] = courseInform.getCourseList();										  
			entireReviewInfo[j][5] = courseInform.getCourseIdList();										   
			entireReviewInfo[j][6] = builder1.toString();											
			entireReviewInfo[j][7] = builder2.toString();							                     
			entireReviewInfo[j][8] = String.valueOf(courseReviewList.get(j).getCourseReviewId());					 
			entireReviewInfo[j][9] = courseReviewList.get(j).getContent();						                      
			entireReviewInfo[j][10] = String.valueOf((int)(Math.floor(courseReviewList.get(j).getCourseScore())));			
			entireReviewInfo[j][11] = String.valueOf(courseReviewList.get(j).getReviewDate());						  
		}
		
//		for(String[] reviewInfo : entireReviewInfo) {
//			System.out.println("리뷰정보출력 -------------------------------");
//			for(String info : reviewInfo) {
//				System.out.println(info);
//			}
//		}
				
		model.addAttribute("entireReviewInfo", entireReviewInfo);
		
		return "user_MyPage_ReviewList";
	}

	// 유저 마이페이지 - 즐겨찾기리스트
	@GetMapping("user/myPage/bookmarkList")
	public String getUserBookmarkList(HttpSession session) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());	
		return "user_MyPage_BookmarkList";
	}
	
	// 코스 만들기 페이지
	@GetMapping("/course/courseMake")
	public String getCourseMakePage(HttpSession session, Model model) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());	
		
		Area[] areaList = Area.values();
		Category[] categoryList = Category.values();

		model.addAttribute("areaList", areaList);
		model.addAttribute("categoryList", categoryList);
		
		return "map_CourseMake";
	}
	
	// 코스 만들기 (INSERT)
	@PostMapping("/course/courseMake")
	public String insertNewCourse(@ModelAttribute CourseDTO newCourse,
								  @ModelAttribute("selectedPlaceId1") String placeId1,
								  @ModelAttribute("selectedPlaceId2") String placeId2,
								  @ModelAttribute("selectedPlaceId3") String placeId3,
								  @ModelAttribute("selectedPlaceId4") String placeId4,
								  @ModelAttribute("selectedPlaceId5") String placeId5,
								  RedirectAttributes attributes, Model model, HttpSession session) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());	
		
		// reviewController에서 만들어 사용했던 filterNullValues 메서드를 사용, null이 아닌 값들로 배열 생성
		String[] placeIds = reviewController.filterNullValues(placeId1, placeId2, placeId3, placeId4, placeId5);
		courseController.insertCourse(newCourse, placeIds);

		List<CourseInformDTO> courseList = new ArrayList<CourseInformDTO>();
		
		try {
			courseList = courseService.getCourseInformByUserId(185);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		attributes.addAttribute("courseId", courseList.get(courseList.size()-1).getCourseId());
		return "redirect:/course/courseDetail";
	}
	
	
	// 코스 상세 페이지
	@GetMapping("course/courseDetail")
	public String getCourseDetailPage(@RequestParam("courseId") String courseId,
									  @RequestParam(value= "isMod", required= false) String isMod,
									  Model model, HttpSession session) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());	

		// 해당 코스 정보 영역 - 리뷰 - 이미 리뷰를 작성한 유저인지 판별, 판별값을 페이지로 전달 (리뷰 작성/수정버튼 출력 판별용)
	    // 유저 아이디는 1부터 시작. NullPointerException 대책으로 설정 
			int userId = -1;
			
			if(session.getAttribute("user") != null) {
				userId = ((CtgUserDTO) session.getAttribute("user")).getUserId();
			}
				
			boolean isAlreadyWroteUser = false; //true: 이미 작성함 / false: 작성하지 않음
			
			try {
				if(courseReviewService.getCourseReviewByUserIdAndCourseId(userId, Integer.parseInt(courseId)) != null) {
					isAlreadyWroteUser = true;
				};
			} catch (NumberFormatException | SQLException e) {
				log.warn("리뷰 작성여부 확인 실패");
			}		

			model.addAttribute("isAlreadyWroteUser", isAlreadyWroteUser);
			
		// 해당 코스 정보 영역 - 작성자의 사진과 코스 정보를 조회, 페이지로 전달
			CourseInformDTO courseInform = null;
			String userPhoto = null;
			
			try {
				log.info(courseId + "번 코스 조회");
				courseInform = courseService.getCourseInformByCourseId(Integer.parseInt(courseId));
				int courseMadeUserId = courseService.getCourseById(courseInform.getCourseId()).getUserId();
				userPhoto = userService.getCtgUserByUserId(courseMadeUserId).getUserPhoto();
			} catch (Exception e) {
				log.warn("코스 상세 페이지 return 실패");
				e.printStackTrace();
			}

			model.addAttribute("courseInform", courseInform);
			model.addAttribute("userPhoto", userPhoto);
			model.addAttribute("isMod", isMod);
			
		return "map_CourseDetail";
	}
	
	// 코스 찾기 페이지
	@GetMapping("/course/courseList")
	public String getCourseListPage(HttpSession session, Model model) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());	

		int userId = -1;
		
		if(session.getAttribute("user") != null) {
			userId = ((CtgUserDTO) session.getAttribute("user")).getUserId();
		}
		
		List<CourseInformDTO> courseInformList = new ArrayList<CourseInformDTO>();
		
		try {
			courseInformList = courseService.getAllCourses(userId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		Collections.sort(courseInformList, new Comparator<CourseInformDTO>() {
			@Override
			public int compare(CourseInformDTO course1, CourseInformDTO course2) {
				return Integer.compare(course2.getCourseId(), course1.getCourseId());
			}
			
		});
		
		List<String> userPhotoSrcList = new ArrayList<String>();
		List<String> courseDetailPageList = new ArrayList<String>();
		
		userId = -1;
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
            String query = "";
         
            query += ("courseId="+ String.valueOf(courseId));
        
            courseDetailPageList.add(query);    
		}			
		
		Area[] areaList = Area.values();
		String[] areaListToString = new String[areaList.length];
				
		for(int i = 0; i < areaList.length; i++) {
			areaListToString[i] = areaList[i].toString();
		}
		
		Arrays.sort(areaListToString);
		
		model.addAttribute("courseInformList", courseInformList);
		model.addAttribute("userPhotoSrcList", userPhotoSrcList);
		model.addAttribute("courseDetailPageList", courseDetailPageList);
		model.addAttribute("areaList", areaListToString);
		
		System.out.println(courseInformList);
		
		return "map_CourseList";
	}
	
	
}