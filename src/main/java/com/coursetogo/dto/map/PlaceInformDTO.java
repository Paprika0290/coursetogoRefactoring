package com.coursetogo.dto.map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceInformDTO {
	private int placeId;
	private String placeName;
	private double latitude;
	private double longitude;
	private String address;
	private double placeAvgScore;
	private int includedCourseCount;
	private String areaName;
}
