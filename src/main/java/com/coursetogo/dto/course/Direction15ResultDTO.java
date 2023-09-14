package com.coursetogo.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Direction15ResultDTO {
	
	private LocationDTO[] bbox;
	private LocationDTO[] path;
	
}
