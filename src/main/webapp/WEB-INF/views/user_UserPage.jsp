<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CourseToGo / 유저페이지</title>
 	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
 	 
	<style>
		body {
			background-color: #F7F9F9;
		}
		
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
		 	cursor: pointer;
		 }
		 
		 .mainContent {
		 	height: 100vh; 
		 	padding-top: 50px;
		 	padding-bottom: 50px;	 	
		 }
		 
		 .userDetails {
		 	margin-left: 0px;					
		 	margin-top: 30px;
		 	background-color: #ffffff;
		 	width: 35vw;
		 	height: 100px;
		 	border-radius: 2px;
		 	box-shadow: 5px 5px 4px 2px rgba(0, 0, 0, 0.2);
		 	border: 2px solid #E4E4E4;
		 	padding: 15px;
		 	text-align: center;
		 }
		 
		 .userContent {
		 	display: flex;
		 	margin-top: 20px;
		 	gap: 0vw;
		 }
		 
		 .userContentBox {
		 	margin: 10px;
		 	overflow: auto;
		 	height: 60vh;
		 	width: 30vw;
		 	background-color: #ffffff;
		 }
		 
	</style>
	<link rel="stylesheet" type="text/css" href="/css/fonts.css">
    <link rel="stylesheet" type="text/css" href="/css/starScore.css">
	
<body>	
	<header>
		<jsp:include page="components/navigation.jsp" />	
	</header>
	
	<div class = "mainContent">
		<div class= "userDetails">
			<div style= "display: flex; align-items: center;">
				&nbsp;&nbsp;	
				<img src="${userDetail.userPhoto}" width="40px">&nbsp;&nbsp;
				<span style= "background-color: #FF962B; border-radius: 3px; padding: 3px 5px; color: #FFFFFF;"><b>${userDetail.userNickname}</b></span>&nbsp;
				<span style= "color: #373737;"><b>님의 페이지입니다.</b></span>
			</div>
			
			<div style= "background-color: #F7F9F9; width: 90%; margin-top: 10px; padding: 5px; overflow: auto;">
				<c:if test="${empty userDetail.userIntroduce}">
					<b>"</b><span style= "color: #717171;"> <b>-</b> </span><b>"</b>
				</c:if>
				<c:if test="${not empty userDetail.userIntroduce}">
					<b>"</b><span> ${userDetail.userIntroduce} </span><b>"</b>
				</c:if>
			</div>
		</div>
		<div class = "userContent">
			<div class= "userContentBox" style= "width: 40vw;">
				<c:forEach items="${courseInformList}" var="courseInform" varStatus="courseSt">
						<div style= "padding: 10px; margin: 10px; border: 1px solid #E2E2E2; border-radius: 5px; font-family: 'TheJamsil3Regular', sans-serif;">
							<div style= "display: flex; flex-direction: row; align-items: center; margin-top: 5px;">
								&nbsp;<&nbsp;<a href="/course/courseDetail?courseId=${courseInform.courseId}" style= "text-decoration: none;"><span style= "color: #FF962B;">${courseInform.courseName}</span></a>&nbsp;>
								
								<div class="stars small" id= "courseAvgScore${courseSt.index}" style= "margin-left: 10px; font-size: 20px; display: flex;" data-score="${courseInform.courseAvgScore}"></div>
			                            <script>
			                               var courseStars = document.getElementById("courseAvgScore${courseSt.index}");
			                               courseStars.setAttribute("data-score", Math.floor(courseStars.getAttribute("data-score")));
			                            </script>	
							</div>
									
						<div style= "color: #373737; padding: 10px; background-color: #F7F9F9; margin-top: 5px;">
							<span>${courseInform.courseContent}</span>
						</div>
					</div>
				</c:forEach>
			</div>
			
			<div class= "userContentBox" style= "width: 35vw;">
				<c:forEach items="${courseReviewList}" var="courseReview" varStatus="courseReviewSt">
					<div style= "padding: 10px; margin: 10px; border: 1px solid #E2E2E2; border-radius: 5px; font-family: 'TheJamsil3Regular', sans-serif;">
						<div style= "display: flex; flex-direction: row; align-items: center; margin-top: 5px;">
							&nbsp;<&nbsp;<a href="/course/courseDetail?courseId=${courseReview.courseId}" style= "text-decoration: none;">
								<span style= "color: #FF962B;">${courseNameList.get(courseReviewSt.index)}</span>
							</a>&nbsp;>&nbsp;&nbsp;
							<div class="stars small" id= "courseReviewScore${courseReviewSt.index}" style= "font-size: 20px; display: flex;" data-score="${courseReview.courseScore}"></div>
			                            <script>
			                               var courseReviewStars = document.getElementById("courseReviewScore${courseReviewSt.index}");
			                               courseReviewStars.setAttribute("data-score", Math.floor(courseReviewStars.getAttribute("data-score")));
			                            </script>	
						</div>
						<div style= "color: #373737; padding: 10px; background-color: #F7F9F9; margin-top: 5px;">
							${courseReview.content}
						</div>
					</div>				
				</c:forEach>
			</div>
			
			<div class= "userContentBox" style= "width: 20vw;">
				<c:forEach items="${placeReviewList}" var="placeReview" varStatus="placeReviewSt">
					<div style= "padding: 5px; margin: 10px; border: 1px solid #E2E2E2; border-radius: 5px; font-family: 'TheJamsil3Regular', sans-serif;">
						<div style= "display: flex; flex-direction: row; align-items: center; margin-top: 5px;">
							&nbsp;<a href="https://map.naver.com/p/search/${placeNameList.get(placeReviewSt.index)}" style= "text-decoration: none;">${placeNameList.get(placeReviewSt.index)}</a>
							&nbsp;&nbsp;&nbsp;<div class="stars small" id= "placeReviewScore${placeReviewSt.index}" style= "font-size: 20px; display: flex;" data-score="${placeReview.placeScore}"></div>
			                            <script>
			                               var placeReviewStars = document.getElementById("placeReviewScore${placeReviewSt.index}");
			                               placeReviewStars.setAttribute("data-score", Math.floor(placeReviewStars.getAttribute("data-score")));
			                            </script>	      
						</div>
					</div>	
				</c:forEach>
			</div>
		</div>
	</div>
	
	<footer>
		<jsp:include page="components/footer.jsp" />
	</footer>
</body>
</html>