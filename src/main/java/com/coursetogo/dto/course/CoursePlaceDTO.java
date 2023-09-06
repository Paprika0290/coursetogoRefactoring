package com.coursetogo.dto.course;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CoursePlaceDTO {
    private int courseId;
    private int placeId;
    private int selectionOrder;

 
    @Builder
    public CoursePlaceDTO(int courseId, int placeId, int selectionOrder) {
        this.courseId = courseId;
        this.placeId = placeId;
        this.selectionOrder = selectionOrder;
    }
	
}
