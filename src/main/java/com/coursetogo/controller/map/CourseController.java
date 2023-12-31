package com.coursetogo.controller.map;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coursetogo.dto.course.CourseDTO;
import com.coursetogo.dto.course.CourseInformDTO;
import com.coursetogo.dto.course.CoursePlaceDTO;
import com.coursetogo.dto.user.CtgUserDTO;
import com.coursetogo.enumType.Area;
import com.coursetogo.service.course.CoursePlaceService;
import com.coursetogo.service.course.CourseService;
import com.coursetogo.service.review.CourseReviewService;
import com.coursetogo.service.user.CtgUserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CourseController {
	
	@Autowired
	private CtgUserService userService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private CoursePlaceService coursePlaceService;
	
	@Autowired
	private CourseReviewService courseReviewService;
	
	
	public int insertCourse(CourseDTO course, String[] placeIds) {
		int result = -1;
		
		try {
			result = courseService.insertCourse(course);
		} catch (Exception e) {
			log.warn("새로운 course 만들기 실패");
			e.printStackTrace();
		}
		
		CoursePlaceDTO coursePlace = null;
		
		if(result != -1) {			
			for(int i = 0; i < placeIds.length; i++) {
				coursePlace = CoursePlaceDTO.builder().courseId(result).placeId(Integer.parseInt(placeIds[i])).selectionOrder(i+1).build();
				try {
					coursePlaceService.insertCoursePlace(coursePlace);
				} catch (Exception e) {
					log.warn("coursePlace 관계 생성 실패");
					e.printStackTrace();
				}
			}
		}
		
		return result; 
	}
	
	
	// courseList 페이지를 구성하는 데에 필요한 정보들을 담아 돌려주는 메서드. 
	public HashMap<String, Object> getCourseInformListValues(String areaName, int userId,
															 int pageNum, int pageSize, int groupNum) {	
		List<CourseInformDTO> courseInformList = new ArrayList<CourseInformDTO>();
		int totalCourseCount = 0;
		
		if(areaName.equals("전체")) {
			try {
				courseInformList = courseService.getAllCoursesByPage(userId, pageNum, pageSize);
	            totalCourseCount = courseService.getCourseCount();
			} catch (Exception e1) {
				log.warn("전체 courseList 조회 실패");
				e1.printStackTrace();
			}		
		}else if(!areaName.equals("전체")) {
			try {
				courseInformList = courseService.getCoursesOfAreaByPage(userId, areaName, pageNum, pageSize);
				totalCourseCount = courseService.getCourseCountWithArea(areaName);
			} catch (Exception e) {
				log.warn("area로 검색된 courseList 조회 실패");
				e.printStackTrace();
			}
		}
	
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
		
		HashMap<String, Object> listValues = new HashMap<String, Object>();
		listValues.put("courseInformList", courseInformList);
		listValues.put("userPhotoSrcList", userPhotoSrcList);
		listValues.put("courseDetailPageList", courseDetailPageList);
		listValues.put("areaList", areaListToString);
		listValues.put("totalCourseCount", totalCourseCount);

//		System.out.println("pageNum : " + pageNum);
//		System.out.println("pageSize : " + pageSize);
//		System.out.println("totalCourseCount : " + totalCourseCount);
		
		// pageNum : 기본-1, 페이지 번호 누를시 새로 입력됨 / pageSize: 기본-10
		int totalPages = 0;
		if( (totalCourseCount / pageSize) < ((double)totalCourseCount / (double)pageSize) &&
			((double)totalCourseCount / (double)pageSize) < (totalCourseCount / pageSize) + 1 ) {
			totalPages = (totalCourseCount / pageSize) + 1;
		} else {
			totalPages = (totalCourseCount / pageSize);
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
//		System.out.println("groupnum: " + groupNum);
//		System.out.println("totalgroups: " + totalGroups);
		
		listValues.put("pageNum", pageNum);
		listValues.put("pageSize", pageSize);
		listValues.put("groupNum", groupNum);
		listValues.put("totalPages", totalPages);
		listValues.put("totalGroups", totalGroups);
		
		return listValues;
		
	}

	// 관리자 페이지 - courseList 페이지를 구성하는 데에 필요한 정보들을 담아 돌려주는 메서드.
	public HashMap<String, Object> getCourseInformListValuesForAdmin(String category, String keyword, int pageNum,
																	 int pageSize, Integer groupNum) {
		HashMap<String, Object> listValues = new HashMap<String, Object>();
		List<CourseInformDTO> courseInformList = new ArrayList<CourseInformDTO>();
		
		if(category == null) {
			try {
				courseInformList = courseService.getAllCourseInformForAdminWithPage(pageNum, pageSize);
			} catch (SQLException e) {
				log.warn("admin- 전체 코스(+페이지네이션) 검색 실패");
				e.printStackTrace();
			}
		}else {
			try {
				courseInformList = courseService.getCourseInformListByKeywordForAdminWithPage(category, keyword, pageNum, pageSize);
			} catch (SQLException e) {
				log.warn("admin- 코스리스트 키워드 검색에 실패하였습니다.");
				e.printStackTrace();
			}
		}
		
		List<Integer> reviewCountList = new ArrayList<Integer>();
		int reviewCount = 0;
		
		for(CourseInformDTO courseInform : courseInformList) {
			int courseId = courseInform.getCourseId();
            try {
				reviewCount = courseReviewService.getCourseReviewCountByCourseId(courseId);
			} catch (SQLException e) {
				log.warn("admin- 해당 코스의 리뷰 수 조회 실패");
				e.printStackTrace();
			}
            reviewCountList.add(reviewCount);
		}
				
		
		
		listValues.put("courseInformList", courseInformList);
		listValues.put("reviewCountList", reviewCountList);	
		
		
		// pageNum : 기본-1, 페이지 번호 누를시 새로 입력됨 / pageSize: 기본-10
		int totalPages = 0;
		int totalCourseCount= 0;
		int searchedCourseCount = 0;
		
		try {
			totalCourseCount = courseService.getCourseCount();
			if(category != null) {
				searchedCourseCount = courseService.getSearchedCourseCount(category, keyword);
			}
		} catch (Exception e) {
			log.warn("admin- 전체 코스 수 실패");
			e.printStackTrace();
		}
		
		if(category == null) {
			if( (totalCourseCount / pageSize) < ((double)totalCourseCount / (double)pageSize) &&
					((double)totalCourseCount / (double)pageSize) < (totalCourseCount / pageSize) + 1 ) {
					totalPages = (totalCourseCount / pageSize) + 1;
				} else {
					totalPages = (totalCourseCount / pageSize);
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

				listValues.put("pageNum", pageNum);
				listValues.put("pageSize", pageSize);
				listValues.put("groupNum", groupNum);
				listValues.put("totalPages", totalPages);
				listValues.put("totalGroups", totalGroups);
		}else {
			if( (searchedCourseCount / pageSize) < ((double)searchedCourseCount / (double)pageSize) &&
					((double)searchedCourseCount / (double)pageSize) < (searchedCourseCount / pageSize) + 1 ) {
					totalPages = (searchedCourseCount / pageSize) + 1;
				} else {
					totalPages = (searchedCourseCount / pageSize);
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

				listValues.put("pageNum", pageNum);
				listValues.put("pageSize", pageSize);
				listValues.put("groupNum", groupNum);
				listValues.put("totalPages", totalPages);
				listValues.put("totalGroups", totalGroups);
				listValues.put("category", category);
				listValues.put("keyword", keyword);

				System.out.println(searchedCourseCount);
				System.out.println(pageNum);
				System.out.println(pageSize);
				System.out.println(groupNum);
				System.out.println(totalPages);
				System.out.println(totalGroups);
				System.out.println(category);
				System.out.println(keyword);
		}
		
		listValues.put("totalCourseCount", totalCourseCount);
		
		
		return listValues;
	}
	
	
}
