package com.coursetogo.controller.map;

import org.springframework.stereotype.Controller;

@Controller
public class NaverMapController {

	public double[][] getBorderLocations(double[] startLocation, double[] endLocation, double[][] stopOverLocations) {
		double[][] borderLocations = new double[2][2];
		if(endLocation == null) {
			for(int i = 0; i < 2; i++) {
				borderLocations[i][0] = startLocation[0];
				borderLocations[i][1] = startLocation[1];
			}
			return borderLocations;
			
		}else {
			//최소 위도값----------------------------------------------
			double comparison = startLocation[0];
			
			if(endLocation[0] < comparison) {
				comparison = endLocation[0];
			}
			
			if(stopOverLocations != null) {
				if(stopOverLocations.length == 1) {
					if(stopOverLocations[0][0] < comparison) {
						comparison = stopOverLocations[0][0];
					}
				}else if(stopOverLocations.length >= 2) {
					for(int i = 0; i < stopOverLocations.length; i++) {
						if(stopOverLocations[i][0] < comparison) {
							comparison = stopOverLocations[i][0];
						}
					}
				}
			}
			
			borderLocations[0][0] = comparison;
			
			//최소 경도값----------------------------------------------------------
			comparison = startLocation[1];
			
			if(endLocation[1] < comparison) {
				comparison = endLocation[1];
			}
			
			if(stopOverLocations != null) {
				if(stopOverLocations.length == 1) {
					if(stopOverLocations[0][1] < comparison) {
						comparison = stopOverLocations[0][1];
					}
				}else if(stopOverLocations.length >= 2) {
					for(int i = 0; i < stopOverLocations.length; i++) {
						if(stopOverLocations[i][1] < comparison) {
							comparison = stopOverLocations[i][1];
						}
					}
				}
			}
			
			borderLocations[0][1] = comparison;		
			
			//최대 위도값-----------------------------------------------
			comparison = startLocation[0];
			
			if(endLocation[0] > comparison) {
				comparison = endLocation[0];
			}
			
			if(stopOverLocations != null) {
				if(stopOverLocations.length == 1) {
					if(stopOverLocations[0][0] > comparison) {
						comparison = stopOverLocations[0][0];
					}
				}else if(stopOverLocations.length >= 2) {
					for(int i = 0; i < stopOverLocations.length; i++) {
						if(stopOverLocations[i][0] > comparison) {
							comparison = stopOverLocations[i][0];
						}
					}
				}
			}
			
			borderLocations[1][0] = comparison;			

			//최대 경도값-------------------------------------------------
			comparison = startLocation[1];
			
			if(endLocation[1] > comparison) {
				comparison = endLocation[1];
			}
			
			if(stopOverLocations != null) {
				if(stopOverLocations.length == 1) {
					if(stopOverLocations[0][1] > comparison) {
						comparison = stopOverLocations[0][1];
					}
				}else if(stopOverLocations.length >= 2) {
					for(int i = 0; i < stopOverLocations.length; i++) {
						if(stopOverLocations[i][1] > comparison) {
							comparison = stopOverLocations[i][1];
						}
					}
				}
			}
			
			borderLocations[1][1] = comparison;		
			return borderLocations;
		}

	}
	
}
