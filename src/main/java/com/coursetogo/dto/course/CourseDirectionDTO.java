package com.coursetogo.dto.course;

import lombok.Builder;
import lombok.Data;

@Data
public class CourseDirectionDTO {
	
	// 순서: 위도 lat,경도 lng
	private double[] startLocation;
	private double[] endLocation;
	private double[][] totalLocations;
	private double[][] stopOverLocations;
	private double[][] totalPath;
	private double[][] borderLocations;
	
	public CourseDirectionDTO (double[] oneLocation) {
		this.startLocation = oneLocation;
		
		this.totalLocations = new double[1][2];
		this.totalLocations[0] = oneLocation;
	}

	public CourseDirectionDTO (double[] startLocation, double[] endLocation) {
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		
		this.totalLocations = new double[2][2];
		this.totalLocations[0] = startLocation;
		this.totalLocations[1] = endLocation;		
	}
	
    public CourseDirectionDTO (double[] startLocation, double[] endLocation, double[][] stopOverLocations, double[][] totalLocations) {
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.stopOverLocations = stopOverLocations;
		this.totalLocations = totalLocations;
    }

	
}
