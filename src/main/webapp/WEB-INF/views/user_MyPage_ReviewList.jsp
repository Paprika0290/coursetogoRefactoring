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
		 	border-radius: 3px;
		 	border: 0px;
		 	padding: 6px 10px;
		 	margin-right: 30px;
		 	font-size: 11pt;
		 	margin-bottom: 25px;
		 }
		 
 		.wholeReviewContainer {
			padding: 15px;
			background-color: #FFFFFF;
			border-radius: 3px;
			border: 1px solid #E2E2E2;
	     	font-family: 'TheJamsil3Regular', sans-serif;
	     	color: #3C3C3C;
	     	margin-bottom: 10px;
		}
		
		.placeReviewContainer {
		
		}
		
		.courseInformContainer {
	     	font-family: 'TheJamsil3Regular', sans-serif;	
     		color: #3C3C3C;	
     		margin-bottom: 8px;
		}
		
		.reviewButton {
			background-color: #FF962B;
			border: 0px;
			border-radius: 3px;
			padding: 3px 7px;
			color: #ffffff;
			font-family: 'TheJamsil3Regular', sans-serif;
		}
	</style>
	
	<link rel="stylesheet" type="text/css" href="/css/fonts.css">	
    <link rel="stylesheet" type="text/css" href="/css/starScore.css">
<body>	

		<div class = "mainContent" style= "padding: 5px;">	
    	 	<c:forEach items="${entireReviewInfo}" var="reviewInfo" varStatus="reviewInfoSt">
    	 		<div class= "wholeReviewContainer">
    	 				
		    	 		<div class= "courseInformContainer">
		    	 			<span style= "color: #00008b;">${reviewInfo[1]}</span>
		    	 			<span style= "color: #3C3C3C;"> 님의 < </span>
		    	 			<span><a href= "/course/courseDetail?courseId=${reviewInfo[0]}" target= "_blank" style= "text-decoration: none; color: #FF962B;">${reviewInfo[2]}</a></span>
		    	 			<span style= "color: #3C3C3C;"> >에 남긴 리뷰 </span>		    	
							<span style= "color: #a3a3a3; font-size: 8pt;">&nbsp;&nbsp;&nbsp;${reviewInfo[11]}</span>&nbsp;&nbsp;
							
							<button id= "reviewModButton${reviewInfo[0]}" class= "reviewButton" style= "cursor: pointer;" onclick= "reviewMod(${reviewInfo[0]})">수정 / 삭제</button>&nbsp;
									    	 		        
	    	 		        	
		    	 		</div>
		    	 		
		    	 		<c:set var= "placeScores" value= "${fn:split(reviewInfo[7], ',')}" />

		    	 		<div class= "placeReviewContainer" style= "display:flex; align-items: center; justify-content: center; margin-top: 20px; text-align: center;">
							<c:forEach items="${fn:split(reviewInfo[4], ',')}" var="placeName" varStatus="placeNameSt">
								<div style= "background-color: #7FB3D5; border-radius: 5px; padding: 3px 10px; color: #ffffff; margin-right: 10px;"><a href="https://map.naver.com/p/search/${placeName}" style= "text-decoration: none; color: #ffffff;" target= "_blank">${placeName}</a><br>
								<span class="stars small" style= "font-size: 20px; background-color: #ffffff; border-radius: 3px; padding: 0px 5px;" data-score="${placeScores[placeNameSt.index]}"></span>
								</div>
						    </c:forEach>
		    	 		</div>
		    	 		
		    	 		<div class= "courseReviewContainer" style= "background-color: #f4f4f4; margin-top: 10px; padding: 10px; text-align: center;">
			    	 		<span class="stars small" style= "font-size: 20px; background-color: #ffffff; border-radius: 3px; padding: 0px 5px;" data-score="${reviewInfo[10]}"></span><br><br>
							<div>" ${reviewInfo[9]} "</div>
		    	 		</div>
    	 		</div>
	        </c:forEach>
		</div>
		
		<script>
			function reviewMod(courseId) {
				window.open("/course/courseDetail?courseId=" + courseId + "&isMod=true", '_blank');
			}
		</script>	
</body>
</html>