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
    <!-- ncpClientId는 등록 환경에 따라 일반(ncpClientId), 공공(govClientId), 금융(finClientId)으로 나뉩니다. 사용하는 환경에 따라 키 이름을 변경하여 사용하세요. 참고: clientId(네이버 개발자 센터)는 지원 종료 -->
    <script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=2r4z9xh4q5"></script>
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

    <style>	
		.mainContent {
			margin-top: 70px;
			margin-right: 300px; /* 코스 정보 작성하는 부분 */
		    height: 80vh;
		}
		
		.courseMakeInputBox {
			background-color: #F0F0F0;
			border: 0px;		
		}
		
		.courseMakeButton {
			background-color: #FF962B;
			border-radius: 5px;
			border: 0px solid;
			font-family: 'TheJamsil5Bold', sans-serif;
			padding: 10px;
		}
		
		.searchContainer {
			position: absolute;
			top: 100px;
			left: 50px;
		}
		
		.searchInput {
			position: absolute;
			background-color: white;
		}
		
		.fontTheJamsil {
			font-family: 'TheJamsil5Bold', sans-serif;
		}
    </style>
    
	<link rel="stylesheet" type="text/css" href="/css/fonts.css">
	

</head>
<body>
	<header>
		<jsp:include page="components/navigation.jsp" />	
	</header>
	
	<div class = "mainContent">	
			<div id="mapArea" class="map" style="width:100%; height:90vh;">

			</div>		
			<div class= "searchContainer" style= "background-color: rgba(255, 255, 255, 0.8); padding: 20px; width: 330px; height: 28px;
												  border-radius: 10px; border: 2px solid #ffffff; box-shadow: 5px 5px 5px 3px rgba(0, 0, 0, 0.3); z-index: 50;">	
					<div id= "areaSearch" class= "searchInput" >
						<select style= "padding: 2px;
										text-align: center;
										width: 120px;
										border: 3px solid #FF962B;
										border-radius: 10px;"
								id= "areaSelect">
							  <option value="" selected>지역명 선택 </option>	
							  
							  <c:forEach items = "${areaList}" var="area" varStatus="areaSt">
							  	<c:if test="${areaSt.index eq 0}">
							  		<option value="${area}">${area} &nbsp;&nbsp;</option>
							  	</c:if>
							  	<c:if test="${areaSt.index ne 0}">
							  		<option style= "background-color: #cccccc" value="${area}"
							  				disabled> ${area} &nbsp;&nbsp;</option>
							  	</c:if>	
							  </c:forEach>					  							
						</select>
					</div>
					
					<div id= "categorySearch" class= "searchInput" style= "left: 150px;">
						<select style= "padding: 2px;
										text-align: center;
										width: 120px;
										border: 3px solid #FF962B;
										border-radius: 10px;
										background-color: white;"
								id= "categorySelect">
							  <option value="" selected>장소 분류 선택 </option>	
							  
							  <c:forEach items = "${categoryList}" var="category" varStatus="categorySt">
							  	<c:if test="${categorySt.index eq 0}">
							  		<option value="${category}">${category} &nbsp;&nbsp;</option>
							  	</c:if>
							  	<c:if test="${categorySt.index ne 0}">
							  		<option style= "background-color: #cccccc" value="${category}"
							  				disabled> ${category} &nbsp;&nbsp;</option>
							  	</c:if>	
							  </c:forEach>					  							
						</select>
					</div>		
					
					<button class= "searchInput" style= "left: 290px; border-radius: 5px; padding: 4px 12px; background-color: #FF9F3D; border: 2px solid #FF9F3D;
						    font-family: 'TheJamsil3Regular', sans-serif;" onclick="getPlaceList()">검색</button>
			</div>
			
			<div class= "searchResultContainer" style = "background-color: rgba(255, 255, 255, 0.95); border-radius: 2px; position: absolute; top: 180px; left: 75px;
														 padding: 10px; width: 280px; max-height: 500px; z-index:51; overflow-y: auto; text-align: center; display: none;
														 box-shadow: 5px 5px 5px 3px rgba(0, 0, 0, 0.2);"
				 id= "searchResultList">
					
			</div>
			
			<div id = "selectedMarkersContainer" style= "background-color: rgba(255, 255, 255, 0.95); border-radius: 2px;
														 position: absolute; top: 150px; right: 330px; text-align:center; display: none; font-family: 'TheJamsil5Bold', sans-serif;
														 padding: 10px 5px; padding-top: 20px;">
				<span>선택된 장소</span>
		
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
						color: #3C3C3C;"
				 class= "fontTheJamsil">
				1. 좌측 상단 검색창을 통해 장소를<br>검색해주세요.<br>
				(예시 : 지역명 - 홍대 / 업종명 - 음식점)<br><br>
				2. 표시되는 검색결과를 클릭, 지도에 표시되는 장소를 확인하세요.<br><br>
				장소는 최대 5개까지 선택 가능하며, 하단에<br>표시되는 장소명을 클릭하면 취소가 가능합니다.<br><br>
				3. 선택한 장소를 확인 후, 코스의 이름과 코스에 대한 설명을 작성해주세요.<br><br>
				4. 작성이 끝나면 코스 만들기 버튼을 클릭,<br>자신만의 코스를 만들어주세요.<br><br>
				<br>
				<span style= "color: #FF962B; font-size: 1rem;">코스 만들기 기능은 로그인 후<br>사용 가능합니다.</span><br><br>
			</div>
		
		<!-- 코스 정보 입력 form start -->
		<form id = "courseInfo" method= "POST" accept-charset= "UTF-8"
			  action = "/course/courseMake">
			<span style= "font-family: 'TheJamsil5Bold', sans-serif; text-align: center;">코스 상세</span><br><br>
			<input id= "courseName" name= "courseName" class= "courseMakeInputBox" type= "text"
				   value= "코스 이름" style= "text-align: center; padding: 5px; width: 150px;" onfocus="clearInputValue(this)"><br>
			<textarea id= "courseContent" name= "courseContent" class= "courseMakeInputBox"
					  style= "margin-top: 10px; font-family: sans-serif; font-size: 10pt; margin-bottom: 10px; padding: 3px 10px;" rows= "10" cols= "30"
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
			
			<input type= "hidden" name= "userId" value= "${sessionScope.user.userId}">
			<input type= "hidden" name= "courseNumber" value= "">
			
			<input type= "hidden" name= "placeOfCourse1">
			<input type= "hidden" name= "placeOfCourse2">
			<input type= "hidden" name= "placeOfCourse3">
			<input type= "hidden" name= "placeOfCourse4">
			<input type= "hidden" name= "placeOfCourse5">
			
		</form>
		<!-- 코스 정보 입력 form end -->	
	</div>
	
	
	
	<script>
	<!-- 지도 출력 -->
		var mapDiv = document.getElementById('mapArea');
		var map = new naver.maps.Map(mapDiv);
		
		<!-- 지역명, 업종명 선택 후 검색 버튼 클릭 시 결과 출력 -->
		function getPlaceList() {
			var areaSelect = document.getElementById("areaSelect").value;
			var categorySelect = document.getElementById("categorySelect").value;
			
		    if (areaSelect.trim() === "") {
		        alert("지역명을 선택해주세요.");
		        return;
		      }
		    
		    // area값과 category 값으로 placeList 조회 (category값은 필수 x)
		    axios.get('/place/getPlaceList', {
				  params: {
					    areaName: areaSelect,
					    categoryName: categorySelect
					  }
					}
	    			
	    	).then(function(response) {
	    		var resultContainer = document.getElementById('searchResultList');
	    		var selectedMarkers = document.getElementById('selectedMarkersContainer');
	    		resultContainer.style.display = 'block';
	    		resultContainer.innerHTML = '';
	    		
				  map = new naver.maps.Map(mapDiv, {
						center: new naver.maps.LatLng(response.data[0].latitude, response.data[0].longitude),
						zoom: 16
				  });
				
				var markers = [],
				    infoWindows = [];
				  
				// return받은 place정보에서 name을 리스트뷰로 표시  
	    		response.data.forEach(function(place, index) {
					  var div = document.createElement('div');
					  var divId = 'placeDiv' + (index + 1);
					  
					  div.setAttribute('id', divId);
					  div.style.backgroundColor = '#F6F6F6';
					  div.style.marginTop = '2px';
					  div.style.fontFamily = 'TheJamsil3Regular, sans-serif';
				      div.style.fontSize = '11pt';
				      div.style.cursor = 'pointer';
					  
					  div.innerHTML = '<p style="background-color: #FFD1A1; padding: 10px 10px; border-radius: 5px; ">' + place.placeName + '</p>' +
	        		  				  '<input type="hidden" value="' + place.placeId  + '"id= "input' + divId + '">';
					  resultContainer.appendChild(div);	

					  
					  var marker = new naver.maps.Marker({
						position: new naver.maps.LatLng(place.latitude, place.longitude),
						title: place.placeId,
						map: map
					  });
					  
					  var infoWindow = new naver.maps.InfoWindow({
					        content: '<div style="width:100px;text-align:center;padding:5px;font-family:\'TheJamsil3Regular\', sans-serif;font-size:10pt; color: #00008b;"><b>'+ place.placeName +'</b></div>'
					    });
					  
					  markers.push(marker);
					  infoWindows.push(infoWindow);
					  console.log(divId);
					});
	    		
	    		
	    		
	    		naver.maps.Event.addListener(map, 'idle', function() {
	    		    updateMarkers(map, markers);
	    		});
	    		
	    		function updateMarkers(map, markers) {

	    		    var mapBounds = map.getBounds();
	    		    var marker, position;

	    		    for (var i = 0; i < markers.length; i++) {

	    		        marker = markers[i]
	    		        position = marker.getPosition();

	    		        if (mapBounds.hasLatLng(position)) {
	    		            showMarker(map, marker);
	    		        } else {
	    		            hideMarker(map, marker);
	    		        }
	    		    }
	    		}

	    		function showMarker(map, marker) {

	    		    if (marker.setMap()) return;
	    		    marker.setMap(map);
	    		}

	    		function hideMarker(map, marker) {

	    		    if (!marker.setMap()) return;
	    		    marker.setMap(null);
	    		}

	    		// 해당 마커의 인덱스를 seq라는 클로저 변수로 저장하는 이벤트 핸들러를 반환.
	    		function getClickHandler(seq) {
	    		    return function(e) {
	    		        var marker = markers[seq],
	    		            infoWindow = infoWindows[seq];

	    		        if (infoWindow.getMap()) {
	    		            infoWindow.close();
	    		        } else {
	    		            infoWindow.open(map, marker);
	    		        }
	    		    }
	    		}

	    		for (var i=0, ii=markers.length; i<ii; i++) {
	    		    naver.maps.Event.addListener(markers[i], 'click', getClickHandler(i));
	    		}
	    			
	    		
	    		
	    		<!-- 클릭된 업체명에 이벤트 리스너 추가 -->
	    		var searchedNames = document.querySelectorAll('[id^="placeDiv"]');
	    		
	    		searchedNames.forEach(function(element) {
	    		    element.addEventListener('click', function() {
	    		    	var divContent = document.getElementById(element.id).textContent;
	    		    	var inputDivContent = document.getElementById('input' + element.id).value;
	    		    
	    		        console.log(element.id);
	    		        console.log(divContent);
						console.log(inputDivContent);
						
						
						// placeid와 placename반환 
						
	    		    });
	    		});
	    			
	    		
	    		
	    		
	    		
	    		
	    		
	    	}).catch(function(error) {
	    		console.log("장소 조회 불가");
	    	});	
		}
		<!-- 지역명, 업종명 선택 후 검색 버튼 클릭 시 결과 출력 종료-->
	

		
		
	<!-- 코스 제작 입력폼 클릭시 내용 비우기 -->	
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