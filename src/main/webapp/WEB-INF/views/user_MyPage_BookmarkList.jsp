<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CourseToGo / 마이페이지 - 북마크</title>
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
	<input type= "hidden" id= "userId" value= "${sessionScope.user.userId}">
	
	<div class = "mainContent">
		<c:forEach items= "${userCourseList}" var= "courseInformDTO" varStatus= "courseSt">
			<div class= "courseBox">
					<div id= "courseTitle" style= "display: flex; align-items: center;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;			

	                    <c:forEach items="${userPhotoSrcList}" var= "userPhotoSrc" varStatus= "photoSt">
	                        <c:if test="${courseSt.index eq photoSt.index}">
	                           <c:set var="photoSrc" value="${userPhotoSrc}" scope= "request" />
	                        </c:if>
	                    </c:forEach> 	
							
						<img class="userPhotoOfCourse" src="${photoSrc}" style= "width: 30px;">				
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
						
						<c:if test= "${not empty sessionScope.user}">
			                  <c:if test= "${courseInformDTO.isBookMarked eq 1}">
			                  	<img src= "/images/bookmarked.png" width= "30px;" style= "position: flex; margin-left: auto; cursor: pointer;"
			                  		 id= "bookmarkOut${courseInformDTO.courseId}">
			                  </c:if>
		                 </c:if>
						
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
	
	<script>
		var thisUserId = document.getElementById("userId").value;
	
		<!-- 주황색 북마크 버튼 클릭시 북마크 삭제 -->
		document.querySelectorAll('[id^="bookmarkOut"]').forEach(function(bookmarkOut) {
			bookmarkOut.addEventListener('click', function(event) {
				
				var result = confirm("이 코스의 즐겨찾기를 해제할까요?");
				
				if(result == true) {
					var targetIdOut = event.target.id;
			        var thisCourseIdOut = targetIdOut.replace("bookmarkOut", "");
			        
			        axios.post('/user/bookmark/delete',  null, {
			        	params: {
			        		courseId: thisCourseIdOut,
				        	userId: thisUserId	
			        	}
			        })
			        .then(function (response) {
			            if(response.data) {
			            	window.location.reload();
			            }
			        })
			        .catch(function (error) {
			            console.error("북마크 삭제 요청 실패:", error);
			        });
				}else {
					
				}
		    });
		});
	
	</script>
	
</body>
</html>