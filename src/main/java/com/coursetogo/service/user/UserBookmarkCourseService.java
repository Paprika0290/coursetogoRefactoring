package com.coursetogo.service.user;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursetogo.mapper.user.UserBookmarkCourseMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserBookmarkCourseService {

	@Autowired
	private UserBookmarkCourseMapper mapper;

	// 해당 코스를 북마크 처리하는 메서드
	public boolean insertNewBookmark(int userId, int courseId) throws SQLException {
		int res = mapper.insertNewBookmark(userId, courseId);
		
		if (res != 0) {
			return true;
		}
		return false;
	}

	public boolean deleteBookmark(int userId, int courseId) throws SQLException {
		int res = mapper.deleteBookmark(userId, courseId);
		
		if (res == 1) {
			return true;
		}
		return false;
	}

	public int deleteBookmarkByCourseId(int courseId) throws SQLException {
		int res = mapper.deleteBookmarkByCourseId(courseId);
		return res;
	}
	
	// 유저가 찜한 코스 개수 가져오는 메서드--------------------------------------------------------------
	public int getUserBookmarkCount(int userId) throws SQLException{
		return mapper.getUserBookmarkCount(userId);
	}
	

	
}
