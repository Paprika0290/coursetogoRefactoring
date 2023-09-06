package com.coursetogo.mapper.course;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coursetogo.dto.course.CourseDTO;
import com.coursetogo.dto.course.CourseInformDTO;
import com.coursetogo.dto.course.PageRequestDTO;
import com.coursetogo.dto.course.SearchKeywordDTO;

@Mapper
public interface CourseMapper {

	public int insertCourse(CourseDTO course) throws SQLException;

	public List<CourseInformDTO> getAllCourses() throws SQLException;

	public CourseDTO getCourseById(int courseId) throws SQLException;

	public List<CourseInformDTO> getCourseBySearchKeyword(SearchKeywordDTO searchKeyword)throws SQLException;

	public int getTotalCount(PageRequestDTO pageRequest)throws SQLException;

	public List<CourseInformDTO> getCourseWithPageRequest(PageRequestDTO pageRequest);

	public CourseInformDTO getCourseInformByCourseId(int courseId);

	public List<CourseInformDTO> getCourseInformByUserId(int userId)throws SQLException;

	
	
	// 코스작성왕
	public List<Integer> getCourseTop3();

	

}
