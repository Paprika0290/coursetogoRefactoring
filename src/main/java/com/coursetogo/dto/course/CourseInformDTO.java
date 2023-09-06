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
public class CourseInformDTO {
    private int courseId;
    private String userNickname;
    private String courseName;
    private double courseAvgScore;
    private int courseNumber;
    private	String courseList;
    private	String courseIdList;
    private String courseContent;
    private String areaNameList;
    private String categoryNameList;
    private int isBookMarked;
    @Builder
    public CourseInformDTO(int courseId, String userNickName, String courseName, double courseAvgScore,
    		int courseNumber,String courseIdList,
    		String courseList,String courseContent,String areaNameList, 
    		String categoryNameList,int isBookMarked) {
        this.courseId = courseId;
        this.userNickname = userNickName;
        this.courseName = courseName;
        this.courseAvgScore = courseAvgScore;
        this.courseNumber = courseNumber;
        this.courseIdList = courseIdList;
        this.courseList = courseList;
        this.courseContent = courseContent;
        this.areaNameList = areaNameList;
        this.categoryNameList = categoryNameList;
        this.isBookMarked = isBookMarked;
    }
    
}