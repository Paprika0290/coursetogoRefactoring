<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CourseToGo / 마이페이지 - 코스</title>
 	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
 	 
	<style>
		 .myPageButton {
		 	width: 130px;
		 	background-color: #FF962B;
		 	font-family: 'TheJamsil3Regular', sans-serif;
		 	color: #ffffff;
		 	border-radius: 5px;
		 	border: 0px;
		 	padding: 6px 10px;
		 	margin-right: 30px;
		 	font-size: 11pt;
		 	margin-bottom: 25px;
		 }
 		.mainContent {
 			padding: 5px;
 		}
 		.courseBox {
			padding: 5px;
			background-color: #FFFFFF;
			border-radius: 3px;
			border: 1px solid #E2E2E2;
	     	font-family: 'TheJamsil3Regular', sans-serif;
	     	color: #3C3C3C;
	     	margin-bottom: 10px;
		}
	</style>
	<link rel="stylesheet" type="text/css" href="/css/fonts.css">	
    <link rel="stylesheet" type="text/css" href="/css/starScore.css">
	
<body>	
	<div class = "mainContent">
		<c:forEach items= "${userCourseList}" var= "courseInformDTO" varStatus= "courseSt">
			<div class= "courseBox">
					<div id= "courseTitle" style= "display: flex; align-items: center;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;			
							
						<img class="userPhotoOfCourse" src="${userPhoto}" style= "width: 30px;">				
							<c:forEach items="${requestScope.courseDetailPageList}" var="coursePage" varStatus="pageSt">
			    	 			<c:if test="${courseSt.index eq pageSt.index}">
			    	 				<c:set var="query" value="${coursePage}" scope="request" />
			    	 			</c:if>
			    	 		</c:forEach>
						<p style= "position: flex; flex-direction: row; justify-content: center;">
							&nbsp;&nbsp;&nbsp;
							${courseInformDTO.userNickname} 님의 < <a href="/course/courseDetail?${query}" style= "text-decoration: none;" target= "_blank"><span style="color: #FF962B;">${courseInformDTO.courseName}</span></a> > 코스 &nbsp;&nbsp;
						</p>
						<div style = "background-color: #eeeeee; display: flex; align-items: center; border-radius: 5px; padding: 0px 10px;" >
							<span style = "color: #636363;"> 평균 별점 : </span>
							<div class="stars small" id= "courseAvgScore${courseSt.index}" style= "margin-left: 10px; font-size: 20px; display: flex; justify-content: flex-end; " data-score="${courseInformDTO.courseAvgScore}"></div>
										<script>
											var courseStars = courseStars = document.getElementById("courseAvgScore${courseSt.index}");
											courseStars.setAttribute("data-score", Math.floor(courseStars.getAttribute("data-score")));
										</script>
						</div>
						
						
					</div>	
					
					<div class="inline-items" style = "border-radius:5px;
	                								   display:flex;
	                								   align-items: center;
	                								   justify-content: center;">
	                								   
	                    <c:forEach items="${fn:split(courseInformDTO.courseList, ',')}" var="place">
	                        <div class="item" style = "padding:5px 10px;
	                        						   background-color: #7FB3D5;
	                        						   border-radius:5px;
	                        						   color: white;
	                        						   margin-right:15px;
	                        						   font-family: 'TheJamsil3Regular', sans-serif;">${place}</div>
	                    </c:forEach>
	                </div>
					
					<div style= "background-color: #F7F9F9; padding: 5px; margin-top: 10px; margin-bottom: 10px; text-align: center;">
						<span>"&nbsp; ${courseInformDTO.courseContent} &nbsp;"</span>
					</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>