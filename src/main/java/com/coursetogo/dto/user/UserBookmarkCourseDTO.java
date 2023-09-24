package com.coursetogo.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookmarkCourseDTO {
	private int bookmarkId;
	private int userId;
	private int courseId;
	
	
	public UserBookmarkCourseDTO(int userId, int courseId) {
		this.userId = userId;
		this.courseId = courseId;
	}
}
