<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>CourseToGo / 코스 만들기</title>
    <script src="../../docs/js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="../../docs/js/examples-base.js"></script>
    <script type="text/javascript" src="../../docs/js/highlight.min.js"></script>
    <!-- ncpClientId는 등록 환경에 따라 일반(ncpClientId), 공공(govClientId), 금융(finClientId)으로 나뉩니다. 사용하는 환경에 따라 키 이름을 변경하여 사용하세요. 참고: clientId(네이버 개발자 센터)는 지원 종료 -->
    <script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=2r4z9xh4q5"></script>
    <link rel="stylesheet" type="text/css" href="../../docs/css/examples-base.css" />

    <style>	
		.mainContent {
			margin-top: 70px;
			margin-right: 300px; /* 코스 정보 작성하는 부분 */
		    height: 80vh;
		}
		
		.courseMakeInputBox {
			background-color: #F6F6F6;
			border: 0px;		
		}
		
		.courseMakeButton {
			background-color: #FF962B;
			border-radius: 5px;
			border: 0px solid;
			font-family: 'TheJamsil5Bold', sans-serif;
			padding: 10px;
		}
		
		.fontTheJamsil {
			font-family: 'TheJamsil5Bold', sans-serif;
		}
    </style>
    
	<link rel="stylesheet" type="text/css" href="/css/fonts.css">
	

</head>
<body>

	<jsp:include page="components/navigation.jsp" />
	
	<div class = "mainContent">	
			<div id="mapArea" class="map" style="width:100%; height:90vh;">
				지도...
				지도...
				지도...
			</div>		
	</div>
	
	<div id= "makeCourse" style= "background-color: white;
								  border: 1px solid #E5E5E5;
								  position: absolute;
								  margin-top: 70px;
								  top: 0;
								  right: 0;
								  width: 300px;
								  height: 100%;
								  text-align: center;">
		<p class= "fontTheJamsil" style= "color: #00008b;">
			Course 제작가이드			
		</p>
			<div style= "background-color: #F6F6F6;
						font-family: 'TheJamsil3Regular', sans-serif;
						font-size: 0.9rem;
						font-weight: 300;
						padding: 10px;
						margin-bottom: 50px;
						color: #3C3C3C"
				 class= "fontTheJamsil">
				1. 좌측 상단 검색창을 통해 장소를<br>검색해주세요.<br>
				(예시 : 지역명 - 홍대 / 업종명 - 음식점)<br><br>
				2. 표시되는 검색결과를 클릭, 지도에 표시되는 장소를 확인하세요.<br><br>
				장소는 최대 5개까지 선택 가능하며, 하단에<br>표시되는 장소명을 클릭하면 취소가 가능합니다.<br><br>
				3. 선택한 장소를 확인 후, 코스의 이름과 코스에 대한 설명을 작성해주세요.<br><br>
				4. 작성이 끝나면 코스 만들기 버튼을 클릭,<br>자신만의 코스를 만들어주세요.<br><br>
				<br>
				<span style= "color: #FF962B;">코스 만들기 기능은 로그인 후<br>사용 가능합니다.</span>
			</div>
		
		
		<form id = "courseInfo" method= "POST" accept-charset= "UTF-8">
			<span style= "font-family: 'TheJamsil5Bold', sans-serif; text-align: center;">코스 상세</span><br><br>
			<input id= "courseName" name= "courseName" class= "courseMakeInputBox" type= "text"
				   value= "코스 이름" style= "text-align: center;" onfocus="clearInputValue(this)"><br>
			<textarea id= "courseContent" name= "courseContent" class= "courseMakeInputBox"
					  style= "margin-top: 10px; font-family: sans-serif; font-size: 10pt; margin-bottom: 10px;" rows= "10" cols= "30"
					  onfocus="clearTextAreaValue(this)" maxlength="400">
이 코스는 어떤 코스인가요?
			</textarea>
			
			 <c:if test="${not empty sessionScope.user.userId}">
			 <br>
			    <button class="courseMakeButton" type="submit" style= "padding-left: 20px; padding-right: 20px; color: #FFFFFF">코스 만들기!</button>
			</c:if>
			
			<c:if test="${empty sessionScope.user.userId}">
			    <button class="courseMakeButton" disabled > 로그인 후 사용가능합니다</button>
			</c:if>
			
		</form>	
	</div>
	
	<!-- 지도 출력 -->
	<script>
		var mapDiv = document.getElementById('mapArea');
		var map = new naver.maps.Map(mapDiv);
	</script>

	<!-- 코스 제작 입력폼 비우기 -->	
	<script>
		function clearInputValue(input) {
		  if (input.value === input.defaultValue) {
		    input.value = '';
		  } 
		  
		}
		
		function clearTextAreaValue(textarea) {
		  if (textarea.value === textarea.defaultValue) {
		    textarea.value = '';
		  }
		}
	</script>

   	<footer>
		<jsp:include page="components/footer.jsp" />
	</footer>
</body>
</html>