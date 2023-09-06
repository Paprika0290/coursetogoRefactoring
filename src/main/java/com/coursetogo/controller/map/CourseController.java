package com.coursetogo.controller.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.coursetogo.dto.course.CourseInformDTO;
import com.coursetogo.dto.course.PageRequestDTO;
import com.coursetogo.dto.course.PageResponseDTO;
import com.coursetogo.dto.user.CtgUserDTO;
import com.coursetogo.service.course.CourseService;
import com.coursetogo.service.course.RankingService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private RankingService rankingService;
	
	// 코스 리스트를 만들어주는 메서드
	public Map<String, Object> makeCourseList(HttpSession session) {
		List<CourseInformDTO> courseInformList = new ArrayList<>();
		
		CtgUserDTO user =  (CtgUserDTO) session.getAttribute("user");
		PageRequestDTO pageRequest = new PageRequestDTO();
		
		int userId = -1;
		try{
			userId = user.getUserId();
			log.info("회원 코스 조회.");
		}
		catch (Exception e) {
			log.warn("비회원 코스 조회.");
		}
		
		int total = 0;
		try {
			courseInformList = courseService.getCourseWithPageRequest(pageRequest);
			total = courseService.getTotalCount(pageRequest);
		} catch (Exception e) {
			log.warn("전체 코스 수를 count하지 못함.");
			System.out.println(e);
		}	
	    
		PageResponseDTO pageResponse = new PageResponseDTO(total, 10, pageRequest);

		//추천 코스 id
		List<String> recommendedCourses = rankingService.sortCourseIdByCount();
		List<CourseInformDTO> recommendedCourseInformList = new ArrayList<CourseInformDTO>();
		
		for(String temp : recommendedCourses) {
			try {
			CourseInformDTO recommendedCourseInform	= courseService.getCourseInformByCourseId(Integer.parseInt(temp));
			recommendedCourseInformList.add(recommendedCourseInform);
			} catch (Exception e) {
				log.warn("추천 코스를 가져오지 못함.");
				System.out.println(e);
			}
		}
		
		Map<String, Object> getCourseList = new HashMap<String, Object>();
		getCourseList.put("courseInformList", courseInformList);
		getCourseList.put("pageInfo", pageResponse);
		getCourseList.put("recommendedCourseInformList",recommendedCourseInformList);
		
		return getCourseList;
	}
	
	
}
