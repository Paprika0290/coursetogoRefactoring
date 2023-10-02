package com.coursetogo.service.course;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.coursetogo.dto.course.CourseDTO;
import com.coursetogo.dto.course.CourseInformDTO;
import com.coursetogo.mapper.course.CourseMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseService {
	
	@Autowired
	private CourseMapper mapper;
	

	public int insertCourse(CourseDTO course) throws Exception {
		int res = mapper.insertCourse(course);
		
		if(res == 0) {
			log.warn("새로운 course 만들기 실패");
		} else {
			log.info("새로운 course 만들기 성공");
		}
		return res;
		
	}
	
	public List<CourseInformDTO> getAllCourses(int userId) throws Exception {
		List<CourseInformDTO> res = new ArrayList<>();
		
		res= mapper.getAllCourses(userId);
		
		if(!res.isEmpty()) {
			
		} else {
			log.warn("전체 코스 검색 실패");
		}
		return res;
	}
	
	public List<CourseInformDTO> getAllCoursesByPage(int userId, int pageNum, int pageSize) throws Exception {
		List<CourseInformDTO> res = new ArrayList<>();
		
		int startRow = ((pageNum-1) * pageSize) + 1;
		int endRow = ((pageNum-1) * pageSize) + pageSize;
		res= mapper.getAllCoursesByPage(userId, startRow, endRow);
		
		if(!res.isEmpty()) {
		} else {
			log.warn("전체 코스(+페이지네이션) 검색 실패");
		}
		return res;
	}
	
	public int getCourseCount() throws Exception  {
		int count = 0;
		count = mapper.getCourseCount();
		
		return count;
	}
	
	public int getCourseCountWithArea(int area) throws Exception  {
		int count = 0;
		count = mapper.getCourseCountWithArea(area);
		
		return count;
	}
	
	
	public CourseInformDTO  getCourseInformByCourseId(int courseId) throws Exception {
		CourseInformDTO res= null;
				
		res= mapper.getCourseInformByCourseId(courseId);
				
		if(res!= null) {
			
		} else {
			log.warn("코스ID로 코스Inform 검색 실패");
		}
		return res;
		
	}
	
	public List<CourseInformDTO> getCourseInformByUserId(int userId) throws Exception {
		List<CourseInformDTO> res= new ArrayList<>();
		
		res= mapper.getCourseInformByUserId(userId);
		
		if(res!= null) {
			
		} else {
			log.warn("유저가 작성한 코스 검색 실패");
		}
		return res;
		
	}
	
	public List<CourseInformDTO> getBookmarkedCourseInformByUserId(int userId) throws Exception {
		
		List<CourseInformDTO> res = new ArrayList<>();
		
		res= mapper.getBookmarkedCourseInformByUserId(userId);
		 
		if(res!= null) {
			
		} else {
			log.warn("유저가 북마크한 코스 검색 실패");
		}
		return res;
		
	}
	
	public CourseDTO getCourseById(int courseId) throws SQLException {
		CourseDTO res= null;
		 res= mapper.getCourseById(courseId);
		if(res == null) {
			log.warn("코스ID로 코스 검색 실패");
		} else {
		}
		return res;
	}


	

	
	// 코스작성왕
	public List<Integer> getCourseTop3() throws SQLException	{
		return mapper.getCourseTop3();
	}
	
	
	
	

}