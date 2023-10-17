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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coursetogo.controller.api.N_LoginAPIController;
import com.coursetogo.controller.api.N_MapAPIController;
import com.coursetogo.controller.map.CourseController;
import com.coursetogo.controller.map.PlaceController;
import com.coursetogo.controller.review.ReviewController;
import com.coursetogo.controller.user.CtgUserController;
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
	private CtgUserController userController;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private PlaceController placeController;
	
	@Autowired
	private CtgUserService userService;
	
	@Autowired
	private CourseReviewService courseReviewService;
	
	@Autowired
	private PlaceReviewService placeReviewService;
	
	@Autowired
	private ReviewController reviewController;
	
	@Autowired
	private PlaceService placeService;
	
	// 도메인 주소로 접속 시 첫 화면 출력
	@GetMapping("/")
	public String getHomePage(HttpSession session, Model model) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());	
		
		List<Integer> kingIdList = new ArrayList<Integer>();
		List<String> kingNicknameList = new ArrayList<String>();
		
		try {
			kingIdList.addAll(courseService.getCourseTop3());
			kingIdList.addAll(courseReviewService.getReviewTop3());
			kingIdList.addAll(placeReviewService.getReviewTop3());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		CtgUserDTO user;
		
		for(int a : kingIdList) {
			
			try {
				user = userService.getCtgUserByUserId(a);
				
				if(user != null) {
					kingNicknameList.add(user.getUserNickname());
				}else {
					kingNicknameList.add("-");
				}
				
			} catch (SQLException e) {
				log.warn("top3리스트 가져오기 실패");
				e.printStackTrace();
			}
		}
		
		List<CourseDTO> recommendCourseList = new ArrayList<CourseDTO>();
		try {
			recommendCourseList = courseService.recommendCourseTop5();
		} catch (SQLException e) {
			log.warn("top3리스트 가져오기 실패");
			e.printStackTrace();
		}
		
		List<String> userPhotoSrcList = new ArrayList<String>();
		List<String> courseDetailPageList = new ArrayList<String>();
		List<String> userNicknameList = new ArrayList<String>();
		user = null;
		String query = "";
		
		for(CourseDTO course : recommendCourseList) {
			try {
				user = userService.getCtgUserByUserId(course.getUserId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			userPhotoSrcList.add(user.getUserPhoto());
			userNicknameList.add(user.getUserNickname());
			
            query = "";
            query += ("courseId="+ String.valueOf(course.getCourseId()));
            
			courseDetailPageList.add(query);
		}
		
		model.addAttribute("kingNicknameList", kingNicknameList);
		model.addAttribute("recommendCourseList", recommendCourseList);
		model.addAttribute("userPhotoSrcList", userPhotoSrcList);
		model.addAttribute("userNicknameList", userNicknameList);
		model.addAttribute("courseDetailPageList", courseDetailPageList);

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
				
		model.addAttribute("entireReviewInfo", entireReviewInfo);
		
		return "user_MyPage_ReviewList";
	}

	// 유저 마이페이지 - 즐겨찾기리스트
	@GetMapping("user/myPage/bookmarkList")
	public String getUserBookmarkList(HttpSession session, Model model) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());	
	
		int userId = -1;
		if(session.getAttribute("user") != null) {
			userId = ((CtgUserDTO) session.getAttribute("user")).getUserId();
		}		

		List<CourseInformDTO> userCourseList = new ArrayList<CourseInformDTO>();
		List<String> courseDetailPageList = new ArrayList<String>();
		List<String> userPhotoSrcList = new ArrayList<String>();
		
		try {
			userCourseList = courseService.getBookmarkedCourseInformByUserId(userId);
		} catch (Exception e) {
			log.warn("유저의 코스리스트 조회 실패");
			e.printStackTrace();
		}
		
		String userPhoto = null;
				
		for (CourseInformDTO course : userCourseList) {
        	int courseId = course.getCourseId();
        	CourseDTO thisCourse = null;
        	try {
				thisCourse = courseService.getCourseById(courseId);
				userPhoto = userService.getCtgUserByUserId(thisCourse.getUserId()).getUserPhoto();
				userPhotoSrcList.add(userPhoto);
			} catch (SQLException e) {
				log.warn("즐겨찾기 리스트 - 유저의 코스 조회 실패");
				e.printStackTrace();
			}

            String query = "";
         
            query += ("courseId="+ String.valueOf(courseId));
        
            courseDetailPageList.add(query);    
		}
		
		model.addAttribute("userCourseList", userCourseList);
		model.addAttribute("userPhotoSrcList", userPhotoSrcList);
		model.addAttribute("courseDetailPageList", courseDetailPageList);		
		
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
		
		int userId = -1;
		
		if(session.getAttribute("user") != null) {
			userId = ((CtgUserDTO) session.getAttribute("user")).getUserId();
		}
		
		// reviewController에서 만들어 사용했던 filterNullValues 메서드를 사용, null이 아닌 값들로 배열 생성
		String[] placeIds = reviewController.filterNullValues(placeId1, placeId2, placeId3, placeId4, placeId5);
		
		if(placeIds.length < 1) {
			return "redirect:/course/courseMake";
		}else {
			courseController.insertCourse(newCourse, placeIds);

			List<CourseInformDTO> courseList = new ArrayList<CourseInformDTO>();
			
			try {
				courseList = courseService.getCourseInformByUserId(userId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			attributes.addAttribute("courseId", courseList.get(courseList.size()-1).getCourseId());
			return "redirect:/course/courseDetail";
		}
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
			System.out.println(courseInform);
			model.addAttribute("courseInform", courseInform);
			model.addAttribute("userPhoto", userPhoto);
			model.addAttribute("isMod", isMod);
			
		return "map_CourseDetail";
	}
	
	// 코스 찾기 페이지
	@GetMapping("/course/courseList")
	public String getCourseListPage(HttpSession session, Model model,
									@RequestParam(name= "pageNum", defaultValue= "1") int pageNum,
									@RequestParam(name= "pageSize", defaultValue= "10") int pageSize,
									@RequestParam(name= "groupNum", required= false, defaultValue = "1") Integer groupNum,
									@RequestParam(name= "areaName", defaultValue = "전체") String areaName) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());	
		
		int userId = -1;
		
		if(session.getAttribute("user") != null) {
			userId = ((CtgUserDTO) session.getAttribute("user")).getUserId();
		}

		HashMap<String, Object> ListValues = new HashMap<String, Object>();
		ListValues = courseController.getCourseInformListValues(areaName, userId, pageNum, pageSize, groupNum);
		
		model.addAttribute("ListValues", ListValues);		
		return "map_CourseList";
	}
	
	// 관리자페이지 - 메인
	@GetMapping("/admin")
	public String getAdminPage(HttpSession session, Model model) {
		session.setAttribute("loginApiURL", loginApiController.getloginAPIUrl());	
		return "admin_AdminPage";
	}
	
	// 관리자페이지 - 유저 
	@GetMapping(value= {"/admin/user", "/admin/user/{category}/{keyword}"})
	public String getAdminUserPage(@PathVariable(required= false) String category,
								   @PathVariable(required= false) String keyword,
								   @RequestParam(name= "pageNum", defaultValue= "1") int pageNum,
								   @RequestParam(name= "pageSize", defaultValue= "15") int pageSize,
								   @RequestParam(name= "groupNum", required= false, defaultValue = "1") Integer groupNum,
								   Model model, HttpSession session) {
		if (((CtgUserDTO)session.getAttribute("user")).getUserAdmin() == 0) {
			HashMap<String, Object> adminUserInfo = userController.getUserListValues(pageNum, pageSize, groupNum, category, keyword);
			model.addAttribute("adminUserInfo", adminUserInfo);
		}
		
		return "admin_AdminPage_User";
	}
	
	// 관리자페이지 - 코스
	@GetMapping(value= {"/admin/course", "/admin/course/{category}/{keyword}"})
	public String getAdminCoursePage(@PathVariable(required= false) String category,
								     @PathVariable(required= false) String keyword,
								     @RequestParam(name= "pageNum", defaultValue= "1") int pageNum,
								     @RequestParam(name= "pageSize", defaultValue= "15") int pageSize,
								     @RequestParam(name= "groupNum", required= false, defaultValue = "1") Integer groupNum,
								     Model model, HttpSession session) {
		
		if (((CtgUserDTO)session.getAttribute("user")).getUserAdmin() == 0) {
			HashMap<String, Object> adminCourseInfo = courseController.getCourseInformListValuesForAdmin(category, keyword, pageNum, pageSize, groupNum);
			model.addAttribute("adminCourseInfo", adminCourseInfo);
		}
		
		return "admin_AdminPage_Course";
	}
	
	// 관리자페이지 - 장소
	@GetMapping(value= {"/admin/place", "/admin/place/{category}/{keyword}"})
	public String getAdminPlacePage(@PathVariable(required= false) String category,
								    @PathVariable(required= false) String keyword,
								    @RequestParam(name= "pageNum", defaultValue= "1") int pageNum,
								    @RequestParam(name= "pageSize", defaultValue= "15") int pageSize,
								    @RequestParam(name= "groupNum", required= false, defaultValue = "1") Integer groupNum,
								    Model model, HttpSession session) {
		
		if (((CtgUserDTO)session.getAttribute("user")).getUserAdmin() == 0) {
			HashMap<String, Object> adminPlaceInfo = placeController.getPlaceListValuesForAdmin(category, keyword, pageNum, pageSize, groupNum);
			model.addAttribute("adminPlaceInfo", adminPlaceInfo);
		}
		
		return "admin_AdminPage_Place";
	}
}