package com.coursetogo.dto.review;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceReviewDTO {
	
	private int placeReviewId; // 장소 리뷰 아이디
	private int userId; // 사용자
	private int placeId;   // 장소 아이디 
	private Date reviewDate; // 리뷰가 작성된 날짜 
	private int placeScore; // 리뷰 평점 
	
	@Builder
	public PlaceReviewDTO(int userId, int placeId, int placeScore) {
		this.userId = userId;
		this.placeId = placeId;
		this.placeScore = placeScore;
	}
}