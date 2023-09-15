package com.coursetogo.dto.course;

import lombok.Builder;
import lombok.Data;

@Data
public class CourseDirectionDTO {
	
	// 순서: 위도 lat,경도 lng
	private double[] startLocation;
	private double[] endLocation;
	private double[][] stopOverLocation;
	private double[][] totalPath;
	private double[][] borderLocations;
	
	@Builder
	public CourseDirectionDTO (double[] oneLocation) {
		this.startLocation = oneLocation;
	}

	@Builder
	public CourseDirectionDTO (double[] startLocation, double[] endLocation) {
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}
	
	
	@Builder
    public CourseDirectionDTO (double[] startLocation, double[] endLocation, double[][] stopOverLocation) {
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.stopOverLocation = stopOverLocation;
		this.borderLocations = getBorderLocations(startLocation, endLocation, stopOverLocation);
    }
	
	private double[][] getBorderLocations(double[] startLocation, double[] endLocation, double[][] stopOverLocation) {
		double[][] borderLocations = new double[2][2];
				
		for(int i = 0; i < stopOverLocation.length ; i++) {
			if(i == 0) {
				// 비교용 최소 좌표값 초기화
				borderLocations[0][0] = stopOverLocation[i][0];
				borderLocations[0][1] = stopOverLocation[i][1];
				// 비교용 최대 좌표값 초기화
				borderLocations[1][0] = stopOverLocation[i][0];
				borderLocations[1][1] = stopOverLocation[i][1];
			}else {
				// 최소좌표값 비교
				borderLocations[0][0] = Math.min(borderLocations[0][0], stopOverLocation[i][0]);
				borderLocations[0][1] = Math.min(borderLocations[0][1], stopOverLocation[i][1]);
				// 최대좌표값 비교
				borderLocations[1][0] = Math.max(borderLocations[1][0], stopOverLocation[i][0]);
				borderLocations[1][1] = Math.max(borderLocations[1][1], stopOverLocation[i][1]);
			}
		}
		
		// 최소 위도값 비교
		borderLocations[0][0] = Math.min(borderLocations[0][0], Math.min(startLocation[0], endLocation[0]));
		// 최소 경도값 비교
		borderLocations[0][1] = Math.min(borderLocations[0][1], Math.min(startLocation[1], endLocation[1]));
		// 최대 위도값 비교
		borderLocations[1][0] = Math.max(borderLocations[1][0], Math.max(startLocation[0], endLocation[0]));
		// 최대 경도값 비교
		borderLocations[1][1] = Math.max(borderLocations[1][1], Math.max(startLocation[1], endLocation[1]));
		
		return borderLocations;
	}
	
	
	
	
}
