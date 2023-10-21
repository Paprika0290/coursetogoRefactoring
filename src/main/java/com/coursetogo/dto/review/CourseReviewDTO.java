package com.coursetogo.dto.review;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseReviewDTO {
	
	private int courseReviewId; //리뷰 아이디
	private int courseId; // 코스아이디
	private int userId;   // 작성자아이디
	private String content; // 리뷰내용 
	private int courseScore; // 리뷰 평점 
	private Date reviewDate; // 리뷰 날짜 
	
	@Builder
	public CourseReviewDTO(int courseId, int userId, String content, int courseScore, Date reviewDate) {
		this.courseId = courseId;
		this.userId = userId;
		this.content = content;
		this.courseScore = courseScore;
		this.reviewDate = reviewDate;
	}
	
    @Builder
    public CourseReviewDTO(int courseReviewId, int courseId, int userId, String content, int courseScore) {
        this.courseReviewId = courseReviewId;
        this.courseId = courseId;
        this.userId = userId;
        this.content = content;
        this.courseScore = courseScore;
    }
    

}
