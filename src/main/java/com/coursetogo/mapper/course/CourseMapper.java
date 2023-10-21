package com.coursetogo.mapper.course;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coursetogo.dto.course.CourseDTO;
import com.coursetogo.dto.course.CourseInformDTO;
import com.coursetogo.dto.course.CoursePlaceDTO;

@Mapper
public interface CourseMapper {

	public int insertCourse(CourseDTO course) throws SQLException;

	public List<CourseInformDTO> getAllCourses(@Param("userId") int userId) throws SQLException;
	
	public List<CourseInformDTO> getAllCoursesByPage(@Param("userId") int userId,
													 @Param("startRow") int startRow, @Param("endRow") int endRow) throws SQLException;

	public List<CourseInformDTO> getAllCoursesOfAreaByPage(@Param("userId") int userId, @Param("areaName") String areaName,
			 											   @Param("startRow") int startRow, @Param("endRow") int endRow) throws SQLException;
	
	public int getCourseCount() throws SQLException;
	public int getCourseCountWithArea(@Param("areaName") String areaName) throws SQLException;

	
	public CourseDTO getCourseById(int courseId) throws SQLException;

	public CourseInformDTO getCourseInformByCourseId(@Param("courseId") int courseId) throws SQLException;

	public List<CourseInformDTO> getCourseInformByUserId(@Param("userId") int userId) throws SQLException;
	
	public List<CourseInformDTO> getBookmarkedCourseInformByUserId(@Param("userId") int userId) throws SQLException;

	public int deleteCourse(@Param("courseId") int courseId) throws SQLException;
	
	public int updateCourseAvgScore(int courseId) throws SQLException;
	
	public List<CourseDTO> recommendCourseTop5() throws SQLException;

	// 유저의 코스 개수 가져오는 메서드--------------------------------------------------------------
	public int getUserCourseCount(int userId);
	
	// 전체 코스 확인 / 페이지네이션(관리자 페이지)
	public List<CourseInformDTO> getAllCourseInformForAdminWithPage(@Param("startRow") int startRow, @Param("endRow") int endRow) throws SQLException;	

	// 검색된 코스 확인 / 페이지네이션(관리자 페이지)
	public List<CourseInformDTO> getCourseInformListByKeywordForAdminWithPage
												(@Param("category") String category, @Param("keyword") String keyword,
												 @Param("startRow") int startRow, @Param("endRow") int endRow) throws SQLException;	

	// 검색된 코스 수 확인(관리자 페이지)
	public int getSearchedCourseCount(@Param("category") String category, @Param("keyword") String keyword) throws SQLException;

	// 코스 ID로 코스 이름 검색
	public String getCourseNameByCourseId(int courseId) throws SQLException;
	
	// 코스작성왕
	public List<Integer> getCourseTop3() throws SQLException;
	
	// 코스리뷰 삭제 후 평균별점 업데이트
	public void updateEntireAvgScore() throws SQLException;



	

	

	

	


	

	
	

	

}
