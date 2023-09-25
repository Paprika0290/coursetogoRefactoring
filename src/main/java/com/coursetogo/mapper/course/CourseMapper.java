package com.coursetogo.mapper.course;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coursetogo.dto.course.CourseDTO;
import com.coursetogo.dto.course.CourseInformDTO;
import com.coursetogo.dto.course.CoursePlaceDTO;
import com.coursetogo.dto.course.PageRequestDTO;
import com.coursetogo.dto.course.SearchKeywordDTO;

@Mapper
public interface CourseMapper {

	public int insertCourse(CourseDTO course) throws SQLException;

	public List<CourseInformDTO> getAllCourses(@Param("userId") int userId) throws SQLException;
	
	public List<CourseInformDTO> getAllCoursesByPage(@Param("userId") int userId,
													 @Param("startRow") int startRow, @Param("endRow") int endRow) throws SQLException;
	
	public int getCourseCount() throws SQLException;
	public int getCourseCountWithArea(@Param("area") int area) throws SQLException;

	
	public CourseDTO getCourseById(int courseId) throws SQLException;

	public List<CourseInformDTO> getCourseBySearchKeyword(SearchKeywordDTO searchKeyword) throws SQLException;

	public int getTotalCount(PageRequestDTO pageRequest) throws SQLException;

	public List<CourseInformDTO> getCourseWithPageRequest(PageRequestDTO pageRequest);

	public CourseInformDTO getCourseInformByCourseId(@Param("courseId") int courseId) throws SQLException;

	public List<CourseInformDTO> getCourseInformByUserId(@Param("userId") int userId) throws SQLException;
	
	public List<CourseInformDTO> getBookmarkedCourseInformByUserId(@Param("userId") int userId) throws SQLException;

	
	// 코스작성왕
	public List<Integer> getCourseTop3() throws SQLException;

	

	
	

	

}
