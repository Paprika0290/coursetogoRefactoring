<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.coursetogo.dto.map.PlaceDTO" %>
<%@ page import="com.coursetogo.dto.review.CourseReviewDTO" %>

<!DOCTYPE html>
<html>
<head>
 <title>CourseToGo / 코스 상세</title>
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
 
 <meta charset="UTF-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
 <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=2r4z9xh4q5&submodules=geocoder&callback=init"></script>
 
 <!-- <script  src="http://code.jquery.com/jquery-latest.min.js"></script> -->
 <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
 <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script> 

	<style>
		.mainContent {
			margin-top: 70px;
			margin-right: 200px; /* 코스 정보 작성하는 부분 */
		    height: 80vh;
		}
		
		.informArea {
			background-color: white;
   		    border: 1px solid #E5E5E5;
		    position: absolute;
		    margin-top: 70px;
		    top: 0;
		    right: 0;
		    width: 200px;
		    height: 90%;
		    text-align: center;
   		    z-index: 10;
   		    font-family: 'TheJamsil3Regular', sans-serif;
   		    color: #3C3C3C;
		}
		
		.courseDetailButton {
			background-color: #FF962B;
			border-radius: 5px;
			border: 0px solid;
			font-family: 'TheJamsil5Bold', sans-serif;
			padding: 10px;
		}
		
        .stars::before {
		  content: "★";
		}
		
		.stars[data-score="1"]::before {
		  content: "★";
		  color:#ffd400;
   		  font-size:20px;
		}
		
		.stars[data-score="2"]::before {
		  content: "★★";
		  color:#ffd400;
   		  font-size:20px;	  
		}
		
		.stars[data-score="3"]::before {
		  content: "★★★";
		  color:#ffd400;
   		  font-size:20px;
  		}
		
		.stars[data-score="4"]::before {
		  content: "★★★★";
		  color:#ffd400;
   		  font-size:20px;
		}
		
		.stars[data-score="5"]::before {
		  content: "★★★★★";
		  color:#ffd400;
   		  font-size:20px;
		}
	</style>

</head>

<body>
	<header>
		<jsp:include page="components/navigation.jsp" />	
	</header>
	
	<div class = "mainContent">	
			<div id="mapArea" class="map" style="width:100%; height:90vh;">

			</div>		
			
			<div class="informArea">
				<p style= "margin-bottom: 40px;">
					<img src="${userPhoto}" style= "width: 40px;"><br>
					<span style= "color: #FF962B;">${courseInform.userNickname}</span>님의<br><br>
					<b><span style= "color: #00008b; font-family: 'TheJamsil5Bold', sans-serif; font-size: 14pt;"><${courseInform.courseName}></span></b>
				</p>
				
				<c:forEach items="${fn:split(courseInform.courseList, ',')}" var="place" varStatus= "placeSt">
                       <div class="item" style = "padding:10px 10px;
                       							  margin-bottom: 10px;
	                       						  background-color: #7FB3D5;
	                       						  border-radius:5px;
	                       						  color: white;
	                       						  font-family: 'TheJamsil3Regular', sans-serif;
	                       						  font-size: 11pt;">${place}</div>                       						     						                         						  
	                   <c:if test="${placeSt.index lt courseInform.courseNumber-1}">
	                   	 	<img src="/images/courseArrow.png" style= "width: 15px; margin-bottom: 5px;">
	                   </c:if>
                </c:forEach>
             
                <div id= "courseContent">
               	   <div style= "background-color: #F6F6F6;
               	   				padding: 10px;
               	   				margin-top: 20px;
               	   				width: 180px;
               	   				font-size: 11pt;">
               	   		<span style= "font-size: 15pt;">" </span>${courseInform.courseContent}<span style= "font-size: 15pt;">"</span>
               	   </div>
                 </div>
             
             
             	<button class="courseDetailButton" type="button" id= "showCourseReviews"
             			style= "margin-top: 50px; margin-bottom: 20px; padding-left: 15px; padding-right: 15px; color: #FFFFFF;">
             		작성된 리뷰 보기
             	</button>
             	
			 	<c:if test="${not empty sessionScope.user.userId}">
				 	<br>
				    <button class="courseDetailButton" type="button" style= "padding-left: 20px; padding-right: 20px; color: #FFFFFF">리뷰 작성하기</button>
				</c:if>
				
				<c:if test="${empty sessionScope.user.userId}">
				    <button class="courseDetailButton" disabled style= "margin-top: 5px;"> 로그인 후 리뷰 작성하기</button>
				</c:if> 		                              
			</div>
			
			
			
			
			
			
			<input type= "hidden" id= "thisCourseId" value="${courseInform.courseId}">
			<input type= "hidden" id= "thisCourseReviewList">
						

			<div id="reviewContent" class="informArea" style= "right: 200px; overflow: auto; width: 300px; display: none;">
				<div id= "userReviewList" >
		 			

		 		</div>			
			</div>
			
	</div>
	
   	<footer>
		<jsp:include page="components/footer.jsp" />
	</footer>
	
	<script>
	<!-- 지도 출력 -->
		var mapDiv = document.getElementById('mapArea');
		var map = new naver.maps.Map(mapDiv);
		
	<!-- 리뷰 보기 버튼 클릭시 리뷰리스트 출력 -->
		var isVisible = false;
	
		document.getElementById('showCourseReviews').addEventListener('click', function() {
			var courseIdForAPI= document.getElementById("thisCourseId").value; 
			var reviewArea = document.getElementById("reviewContent");
			
			if(isVisible) {
				reviewArea.style.display = 'none'; // 숨기기
		    } else {
		    	reviewArea.style.display = 'block'; // 보이기
		    }
			
			isVisible = !isVisible;
			
			axios.get('/course/courseDetail/reviewList', {params: {courseId: courseIdForAPI}})
			.then(function(response) {
				
				console.log(response.data);
				
				var reviewContainer = document.getElementById('userReviewList');
				
				reviewContainer.innerHTML = '';
				
				response.data.forEach(function(review) {
					  var div = document.createElement('div');
					  div.style.backgroundColor = '#F6F6F6';
					  div.style.padding = '5px 10px';
					  div.style.marginTop = '10px';
					  div.style.fontFamily = 'TheJamsil3Regular, sans-serif';
				      div.style.fontSize = '11pt';
					  
					  div.innerHTML = '<div class="stars" style = "text-align: center; margin-top:5px; margin-bottom:-10px;" data-score="'+ review.courseScore + '"></div>' +
					  				  '<p style="background-color: #ffffff; padding: 10px 10px; border-radius: 5px; ">' + review.content + '</p>';
					  				  
					  reviewContainer.appendChild(div);
					});
				
			})
			.catch(function(error) {
				
			});
			
		});
		
	</script>
</body>
</html>