<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.coursetogo.dto.map.PlaceDTO" %>
<%@ page import="com.coursetogo.dto.review.CourseReviewDTO" %>
<%@ page import="com.coursetogo.dto.course.CourseDirectionDTO" %>

<!DOCTYPE html>
<html>
<head>
 <title>CourseToGo / 코스 상세</title>
 <link rel="stylesheet" type="text/css" href="/css/starScore.css">
 	
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

		.courseDetailButton:hover {
			background-color: #F0B27A;
			border-radius: 5px;
			border: 0px solid;
			font-family: 'TheJamsil5Bold', sans-serif;
			padding: 10px;
			cursor: pointer;
		}
		
		.placeButton:hover {
			background-color: #5D6D7E;
			padding: 3px;
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
				<p>
					<img src="${userPhoto}" style= "width: 40px;"><br>
					<span style= "color: #FF962B;">${courseInform.userNickname}</span>님의<br><br>
					<b><span style= "color: #00008b; font-family: 'TheJamsil5Bold', sans-serif; font-size: 14pt;">< ${courseInform.courseName} ></span></b><br>
				</p>
				<div class="stars" id= "courseAvgScore" style= "margin-bottom: 20px;" data-score="${courseInform.courseAvgScore}"></div>
	
	<script>
	var courseStars = document.getElementById("courseAvgScore");
	courseStars.setAttribute("data-score", Math.floor(courseStars.getAttribute("data-score")));
	</script>
				
				<c:forEach items="${fn:split(courseInform.courseList, ',')}" var="place" varStatus= "placeSt">
                       <div class="place" style = "padding:10px 10px;
		                       							 margin-bottom: 10px;
			                       						 background-color: #7FB3D5;
			                       						 border-radius:5px;
			                       						 color: white;
			                       						 font-family: 'TheJamsil3Regular', sans-serif;
			                       						 font-size: 11pt;">
					  		<a class = "placeButton" href="https://map.naver.com/p/search/${place}" style= "text-decoration: none; color: inherit;" target="_blank">
					  			${place}
				  			</a>
					   </div>
					                         						     						                         						  
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
             
				
				<!-- 버튼 영역 -->
             	<button class="courseDetailButton" type="button" id= "showCourseReviewList"
             			style= "margin-top: 50px; margin-bottom: 20px; padding-left: 15px; padding-right: 15px; color: #FFFFFF;">
             		작성된 리뷰 보기
             	</button>

			 	<c:if test="${not empty sessionScope.user.userId}">
			 		<c:if test = "${isAlreadyWroteUser eq false}">
				 		<button class="courseDetailButton" type="button" id= "showReviewWrite"
					    		style= "padding-left: 20px; padding-right: 20px; color: #FFFFFF">리뷰 작성하기</button>
			 		</c:if>
			 		
			 		<c:if test = "${isAlreadyWroteUser eq true}">
				 		<button class="courseDetailButton" type="button" id= "showReviewUpdate"
					    		style= "padding-left: 20px; padding-right: 20px; color: #FFFFFF">리뷰 수정하기</button>
					    			
			 		</c:if>  
				</c:if>
				
				<c:if test="${empty sessionScope.user.userId}">
				    <button class="courseDetailButton" disabled style= "margin-top: 5px;"> 로그인 후 리뷰 작성하기</button>
				</c:if>              	
				<!-- 버튼 영역 -->
				             	
		                              
			</div>	
			
			
			<!-- JS에서 활용을 위해 담아둔 값들 -->
			<input type= "hidden" id= "thisCourseId" value="${courseInform.courseId}">
			<input type= "hidden" id= "thisCourseReviewList">
			<input type="hidden" id ="thisUserId" value="${sessionScope.user.userId}">
			<input type= "hidden" id= "isAlreadyWrote" value= "${isAlreadyWroteUser}">
			<input type= "hidden" id= "placeCount" value= "${courseInform.courseNumber}">
			<input type= "hidden" id= "placeList" value= "${courseInform.courseIdList}">
			<div id="isMod" style="display: none;">${isMod}</div>
						
						
			<!-- 토글 영역  start -->
			<div id="reviewListPage" class="informArea" style= "right: 200px; overflow: auto; width: 300px; display: none;">
				<div id= "userReviewList" >
		 		</div>			
			</div>
			
			<div id="reviewWritePage" class="informArea" style= "right: 200px; overflow: auto; width: 300px; display: none;">
				<div id= "reviewWrite" style= "margin-top: 30px;">
					<br><br>
						<div style= "margin-bottom: 40px;">
							<b style = "background-color: #F4F4F4;
		               	   				padding: 20px 110px;
		               	   				font-size: 15pt;">리뷰 작성</b>
						</div>
		
						<div style= "text-align: left; color: #454545; font-family:'TheJamsil3Regular', sans-serif; margin-top: 63px;">
							 &nbsp; ▶ 각 장소에 별점을 매겨주세요.
						</div>	
						
						<div style = "margin-top: 11px;">
							<form action= "/review/reviewWrite" method= "POST" id = "insertReview">
							
								<c:forEach items= "${fn:split(courseInform.courseList, ',')}" var="place" varStatus= "placeSt">
				                      <div class="place" style = "padding: 4.5px 10px;
						                       							 margin-bottom: 32px;
							                       						 background-color: #B8CCD9;
							                       						 color: white;
							                       						 font-family: 'TheJamsil3Regular', sans-serif;
							                       						 font-size: 11pt;">
	           						<div class="starsscore${placeSt.index + 1}" style = "display: flex; justify-content: center; align-items: center;">	
							      	  	<input type="radio" id="stars${placeSt.index + 1}5" name = "placeScore${placeSt.index + 1}" value="5" />
				  			            <label for ="stars${placeSt.index + 1}5" title = "5" >★</label>
										<input type="radio" id="stars${placeSt.index + 1}4" name = "placeScore${placeSt.index + 1}" value="4" />
					        		    <label for ="stars${placeSt.index + 1}4" title = "4" >★</label>						
										<input type="radio" id="stars${placeSt.index + 1}3" name = "placeScore${placeSt.index + 1}" value="3" />
					        		    <label for ="stars${placeSt.index + 1}3" title = "3">★</label>								
										<input type="radio" id="stars${placeSt.index + 1}2" name = "placeScore${placeSt.index + 1}" value="2" />
					        		    <label for ="stars${placeSt.index + 1}2" title = "2" >★</label>								
										<input type="radio" id="stars${placeSt.index + 1}1" name = "placeScore${placeSt.index + 1}" value="1" />
					        		    <label for ="stars${placeSt.index + 1}1" title = "1" >★</label>			
					        		    
					        		    <c:forEach items= "${fn:split(courseInform.courseIdList, ',')}" var= "placeId" varStatus= "placeIdSt">
					        		    	<c:if test= "${placeSt.index eq placeIdSt.index}">
					        		    		<input type="hidden" name="place${placeSt.index + 1}" id="place${placeSt.index + 1}" value = "${placeId}">	
					        		    	</c:if>
					        		    </c:forEach>
										
					      	  		</div>
							           	          						       						 
							          </div>
								
								</c:forEach>
								
								<div style= "text-align: left; color: #454545; font-family:'TheJamsil3Regular', sans-serif; margin-top:10px;">
									 &nbsp; ▶ 코스에 대한 리뷰를 남겨주세요.
								</div>	
					    	    
					    	    <div class="courseStarsscore" style = "display: flex; justify-content: center; align-items: center; margin-top: 10px;">
										<input type="radio" id="courseStars5" name="courseScore" value="5" />
							            <label for ="courseStars5" title = "5">★</label>
							            <input type="radio" id="courseStars4" name="courseScore" value="4"/>
							            <label for ="courseStars4" title = "4" >★</label>
							            <input type="radio" id="courseStars3" name="courseScore" value="3" />
							            <label for ="courseStars3" title = "3" >★</label>
							            <input type="radio" id="courseStars2" name="courseScore" value="2" />
							            <label for ="courseStars2" title = "2" >★</label>
							            <input type="radio" id="courseStars1" name="courseScore" value="1" />
							            <label for ="courseStars1" title = "1" >★</label>
					           	</div>	
								
								<input type="hidden" name="courseId" value= "${courseInform.courseId}">
								<input type="hidden" name="userId" value= "${sessionScope.user.userId}">
								<textarea id= "reviewSubmitContent" rows="13" cols= "30" name= "content"
										  style= "padding: 10px; background-color: #F4F4F4; margin: 10px 20px;
										  		  border: 0px solid" maxlength="400"></textarea>	
										  		  
								<button id= "reviewSubmit" type= "submit" class= "courseDetailButton" style= "color: #ffffff; padding-left: 15px; padding-right: 15px;">리뷰 등록</button> 		  		  					
									<script>
										document.getElementById('reviewSubmit').addEventListener('click', function(event) {
											var submitContent = document.getElementById('reviewSubmitContent').value;
											if(submitContent.trim() === '') {
												alert('내용을 입력해주세요.');
												event.preventDefault();
											}
											
											var placeStars = document.querySelectorAll('[id^="stars"]');
											var checked = 0;
											placeStars.forEach(function(element) {
												if(element.checked) {
													checked++;
												}
											});
											
												if(checked < document.getElementById('placeCount').value) {
													alert('장소 별점을 입력해주세요.');
													event.preventDefault();
												}
											
											var courseStar = document.querySelector('[id^="courseStars"]:checked');
											if(!courseStar) {
												alert('코스 별점을 입력해주세요.');
												event.preventDefault();
											}
										});
									</script>
							</form>
							
						</div>
			 		</div>
		 		</div>
		 		
				<div id="reviewUpdatePage" class="informArea" style= "right: 200px; overflow: auto; width: 300px; display: none;">
					<div id= "reviewUpdate" style= "margin-top: 30px;">
					<br><br>
						<div style= "margin-bottom: 40px;">
							<b style = "background-color: #F4F4F4;
		               	   				padding: 20px 110px;
		               	   				font-size: 15pt;">리뷰 수정</b>
						</div>
		
						<div style= "text-align: left; color: #454545; font-family:'TheJamsil3Regular', sans-serif; margin-top: 63px;">
							 &nbsp; ▶ 각 장소 별점을 수정해주세요.
						</div>	
						
						<div style = "margin-top: 11px;">
							<form action= "/review/reviewUpdate" method= "POST" id = "UpdateReview">
							
								<c:forEach items= "${fn:split(courseInform.courseList, ',')}" var="place" varStatus= "placeSt">
				                      <div class="place" style = "padding: 4.5px 10px;
						                       							 margin-bottom: 32px;
							                       						 background-color: #B8CCD9;
							                       						 color: white;
							                       						 font-family: 'TheJamsil3Regular', sans-serif;
							                       						 font-size: 11pt;">
	           						<div class="starsscore${placeSt.index + 1}" style = "display: flex; justify-content: center; align-items: center;">	
							      	  	<input type="radio" id="starsUpdate${placeSt.index + 1}5" name = "placeScore${placeSt.index + 1}" value="5" />
				  			            <label for ="starsUpdate${placeSt.index + 1}5" title = "5" >★</label>
										<input type="radio" id="starsUpdate${placeSt.index + 1}4" name = "placeScore${placeSt.index + 1}" value="4" />
					        		    <label for ="starsUpdate${placeSt.index + 1}4" title = "4" >★</label>						
										<input type="radio" id="starsUpdate${placeSt.index + 1}3" name = "placeScore${placeSt.index + 1}" value="3" />
					        		    <label for ="starsUpdate${placeSt.index + 1}3" title = "3">★</label>								
										<input type="radio" id="starsUpdate${placeSt.index + 1}2" name = "placeScore${placeSt.index + 1}" value="2" />
					        		    <label for ="starsUpdate${placeSt.index + 1}2" title = "2" >★</label>								
										<input type="radio" id="starsUpdate${placeSt.index + 1}1" name = "placeScore${placeSt.index + 1}" value="1" />
					        		    <label for ="starsUpdate${placeSt.index + 1}1" title = "1" >★</label>
					        		    					        		    
					        		    <c:forEach items= "${fn:split(courseInform.courseIdList, ',')}" var= "placeId" varStatus= "placeIdSt">
					        		    	<c:if test= "${placeSt.index eq placeIdSt.index}">
					        		    		<input type="hidden" name="place${placeSt.index + 1}" id="place${placeSt.index + 1}" value = "${placeId}">	
					        		    	</c:if>
					        		    </c:forEach>
										
					      	  		</div>
							           	          						       						 
							          </div>
								
								</c:forEach>
								
								<div style= "text-align: left; color: #454545; font-family:'TheJamsil3Regular', sans-serif; margin-top:10px;">
									 &nbsp; ▶ 코스에 대한 리뷰를 수정해주세요.
								</div>	
					    	    
					    	    <div class="courseStarsscore" style = "display: flex; justify-content: center; align-items: center; margin-top: 10px;">
										<input type="radio" id="courseStarsUpdate5" name="courseScore" value="5" />
							            <label for ="courseStarsUpdate5" title = "5">★</label>
							            <input type="radio" id="courseStarsUpdate4" name="courseScore" value="4"/>
							            <label for ="courseStarsUpdate4" title = "4" >★</label>
							            <input type="radio" id="courseStarsUpdate3" name="courseScore" value="3" />
							            <label for ="courseStarsUpdate3" title = "3" >★</label>
							            <input type="radio" id="courseStarsUpdate2" name="courseScore" value="2" />
							            <label for ="courseStarsUpdate2" title = "2" >★</label>
							            <input type="radio" id="courseStarsUpdate1" name="courseScore" value="1" />
							            <label for ="courseStarsUpdate1" title = "1" >★</label>
					           	</div>	
								
				    		
								<input type="hidden" name= "courseReviewId" id="UpdateCourseReviewId">
								<input type="hidden" name="courseId" value= "${courseInform.courseId}">
								<input type="hidden" name="userId" value= "${sessionScope.user.userId}">
								<textarea rows="13" cols= "30" name= "content" id="courseReviewContent"
										  style= "padding: 10px; background-color: #F4F4F4; margin: 10px 20px;
										  		  border: 0px solid" maxlength="400"></textarea>	
										  		  
								<button type= "submit" class= "courseDetailButton" style= "color: #ffffff; padding-left: 15px; padding-right: 15px;"
										id="courseReviewMod">
								리뷰 수정</button>			
								<button type= "button" class= "courseDetailButton" style= "color: #ffffff; padding-left: 15px; padding-right: 15px;"
							 			id="courseReviewDelete">
							 	리뷰 삭제</button>		
							 	
							 	<script>
							 		document.getElementById('courseReviewDelete').addEventListener('click', function() {
							 			window.location.reload();
							 		});
							 		
									document.getElementById('courseReviewMod').addEventListener('click', function(event) {
										var submitContent = document.getElementById('courseReviewContent').value;
										if(submitContent.trim() === '') {
											alert('내용을 입력해주세요.');
											event.preventDefault();
										}
										
									});
							 	</script>
							 		
							 				
							</form>
							
						</div>
		 		</div>						
			</div>		
			<!-- 토글 영역 end -->
	</div>
	
	
   	<footer>
		<jsp:include page="components/footer.jsp" />
	</footer>
	
	<script>
	
	var places = document.getElementById("placeList").value;
	var placeCount = document.getElementById("placeCount").value;
	
	
	<!-- 지도 출력 -->
		var mapDiv = document.getElementById('mapArea');
		<!-- 지도에 표시될 경로 표시용 -->
		let polyLinePath = [];
		
		var receivedPathArray = null;
		var borderArray = null;		
		var totalLocArray = null;
		
		axios.get('/map/getDirection', {params: {places: places}})
		  .then(function (response) {
		    
		    <!-- response로 CourseDirectionDTO 객체를 돌려받음. -->
		    borderArray = response.data.borderLocations;
		    receivedPathArray = response.data.totalPath;
		    totalLocArray = response.data.totalLocations;
		    
			<!-- 기본 위치 설정 -->
			var border = new naver.maps.LatLngBounds(
											    new naver.maps.LatLng(borderArray[0][0]-0.002, borderArray[0][1]-0.002),
											    new naver.maps.LatLng(borderArray[1][0]+0.002, borderArray[1][1]+0.002));	
				
			var map = new naver.maps.Map(mapDiv, {
				center: new naver.maps.LatLng((borderArray[0][0] + borderArray[1][0])/2,
						                      (borderArray[0][1] + borderArray[1][1])/2),
				zoom: 16,
				maxBounds: border,
			});
			
			if(totalLocArray.length == 1){
				var marker = new naver.maps.Marker({
		            position: new naver.maps.LatLng(totalLocArray[0][0],totalLocArray[0][1]),
		            map: map,
		            icon: {url: '../images/directionFrom.png'}
		        });	
				
		        
			}else if(totalLocArray.length >= 2) {
				for(var i = 0; i < receivedPathArray.length; i ++){
			    	polyLinePath.push(new naver.maps.LatLng(receivedPathArray[i][0], receivedPathArray[i][1]));
			    }
			    
			    const polyline = new naver.maps.Polyline({
			        path: polyLinePath,
			        strokeColor: "#271DD7", 
			        strokeOpacity: 0.7,
			        strokeWeight: 7, 
			        map: map
			      });
			    
			    for(let i = 0; i < totalLocArray.length; i++) {
		        	if(totalLocArray.length == 1) {	        	     
		        	} else{
		        		if(i == 0) {
		    		        var marker = new naver.maps.Marker({
		    		            position: new naver.maps.LatLng(totalLocArray[i][0],totalLocArray[i][1]),
		    		            map: map,
		    		            icon: {url: '../images/directionFrom.png'}
		    		        });	
		        		}else if(i == totalLocArray.length-1) {
		        			var marker = new naver.maps.Marker({
		    		            position: new naver.maps.LatLng(totalLocArray[i][0],totalLocArray[i][1]),
		    		            map: map,
		    		            icon: {url: '../images/directionTo.png'}
		    		        });	
		        		}else {
		    		        var marker = new naver.maps.Marker({
		    		            position: new naver.maps.LatLng(totalLocArray[i][0],totalLocArray[i][1]),
		    		            map: map,
		    		            icon: {url: '../images/directionStopOver.png'}
		    		        });		     			
		        		}

		        	}     
		        }	 
			}
		    
	        
	        
		  })
		  .catch(function (error) {
		    console.error('direction 요청 실패');
		  });
		
		
	<!-- 리뷰 보기 버튼 클릭시 리뷰리스트 출력 + 리뷰 작성 버튼 클릭시 지도 페이지가 리뷰 작성 페이지로 변화 -->
		var userIdInput = document.getElementById('thisUserId').value;	
		var isAlready = document.getElementById('isAlreadyWrote').value;
		var courseIdForAPI= document.getElementById("thisCourseId").value; 
		
		var isListVisible = false;
		var isWriteVisible = false;
		var isUpdateVisible = false;
		var reviewArea = document.getElementById("reviewListPage");
		var reviewWriteArea = document.getElementById("reviewWritePage");
		var reviewUpdateArea = document.getElementById("reviewUpdatePage");
		
		if(userIdInput !== "") {
			
			if(isAlready === "true") {
				
				// 코스리뷰 content
				var reviewContent = document.getElementById("courseReviewContent");
				var places = document.getElementById("placeList").value;
				var placeCount = document.getElementById("placeCount").value;
				
				
				// 리뷰수정 버튼을 눌렀을때 작동하는 함수
				var showReviewUpdate = function() {
			    	if(reviewArea.style.display ==='block' ) {
			    		reviewArea.style.display = 'none';	
			    		isListVisible = false;
			    	}
			    	
					if(isUpdateVisible) {
						reviewUpdateArea.style.display = 'none'; // 숨기기
				    } else {
				    	reviewUpdateArea.style.display = 'block'; // 보이기
				    	
				    	// 코스리뷰 조회
				    	axios.get('/review/getCourseReview', {
							  params: {
								    userId: userIdInput,
								    courseId: courseIdForAPI
								  }
								}
				    			
				    	).then(function(response) {
				    		var res = response.data;
				    		reviewContent.value = res.content;
				    		var elementId = "courseStarsUpdate" + res.courseScore;
				    		document.getElementById(elementId).checked = true;
				    		
				    		document.getElementById("UpdateCourseReviewId").value = res.courseReviewId;
				    	}).catch(function(error) {
				    		console.log("기존 코스리뷰 조회 불가");
				    	});
				    	
				    	// 장소리뷰 조회
				    	axios.get('/review/getPlaceReviews', {
							  params: {
								    userId: userIdInput,
								    places: places
								  }
								}
				    			
				    	).then(function(response) {
				    		var res = response.data;
				    		
				    		for (let i = 0; i < placeCount; i++) {
								var elementId = "starsUpdate" + (i + 1) + res[i];
								document.getElementById(elementId).checked = true;
				    		}
				    		
				    	}).catch(function(error) {
				    		console.log("기존 장소리뷰 조회 불가");
				    	});
				    	
				    	
				    	reviewContent.addEventListener("click", function() {
				    		reviewContent.value = "";
				    	  });
				    	
				    }
					
					isUpdateVisible = !isUpdateVisible;
				};
				
				document.getElementById('showReviewUpdate').addEventListener('click', showReviewUpdate);
			}else if(isAlready === "false") {
				console.log("사용자가 리뷰하지 않은 코스");
				document.getElementById('showReviewWrite').addEventListener('click', function() {
			    	if(reviewArea.style.display ==='block' ) {
			    		reviewArea.style.display = 'none';	
			    		isListVisible = false;
			    	}
			    	
					if(isWriteVisible) {
						reviewWriteArea.style.display = 'none'; // 숨기기
				    } else {
				    	reviewWriteArea.style.display = 'block'; // 보이기
				    }
					isWriteVisible = !isWriteVisible;
				});
			}
			
		}	
		
		document.getElementById('showCourseReviewList').addEventListener('click', function() {
	    	if(reviewWriteArea.style.display ==='block' ) {
	    		reviewWriteArea.style.display = 'none';
	    		isWriteVisible = false;
	    	} else if (reviewUpdateArea.style.display === 'block') {
	    		reviewUpdateArea.style.display = 'none';
	    		isUpdateVisible = false;
	    	}
			
			if(isListVisible) {
				reviewArea.style.display = 'none'; // 숨기기
		    } else {
		    	reviewArea.style.display = 'block'; // 보이기
		    }
			
			isListVisible = !isListVisible;
			
			axios.get('/course/courseDetail/reviewList', {params: {courseId: courseIdForAPI}})
			.then(function(response) {
				
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
		
		var reviewDelete = document.getElementById('courseReviewDelete');
		var thisCourseReviewId = document.getElementById("UpdateCourseReviewId").value;
		
		reviewDelete.addEventListener('click', function() {
			  axios.get('/review/reviewDelete', {params: {userId: userIdInput, courseId: courseIdForAPI}})
			    .then(response => {
			      console.log('리뷰 삭제 성공');
			    })
			    .catch(error => {
			      console.log('리뷰 삭제 실패');
			    });
			});
		
		var isMod = document.getElementById('isMod').textContent;
		
		if (isMod === "true") {
	        showReviewUpdate();
	    } else {
	    }
		

	</script>
	
	
	
</body>
</html>