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
		
		.placeDiv {
			padding:5px 5px;
			margin-bottom: 10px;
			background-color: #7FB3D5;
			border-radius:5px;
			color: white;
			font-family: 'TheJamsil3Regular', sans-serif;
			font-size: 11pt;"
		}
		
		.fontTheJamsil {
			font-family: 'TheJamsil5Bold', sans-serif;
		}
		
		.consonantButton {
			font-family: 'TheJamsil3Regular', sans-serif;
			background-color: #FF962B;
			color: #ffffff;
			border: 1px solid #ffffff;
			height: 22px;
			width: 30px;
			cursor: pointer;
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
							  		<option value="${area}">${area} &nbsp;&nbsp;</option>
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
								id= "categorySelect" disabled>
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
						    font-family: 'TheJamsil3Regular', sans-serif; cursor: pointer;" onclick="getPlaceList()">검색</button>
			</div>
			
			<div id= "consonantBox1" style= "display: none; position: absolute; top: 150px; left: 105px; z-index: 80; background-color: #ffffff; padding: 5px 10px; box-shadow: 5px 5px 5px 3px rgba(0.3, 0.3, 0.3, 0.3);">
				<button id= "korConsonant1" value= "ㄱ" class= "consonantButton">ㄱ</button>
				<button id= "korConsonant2" value= "ㄴ" class= "consonantButton">ㄴ</button>
				<button id= "korConsonant3" value= "ㄷ" class= "consonantButton">ㄷ</button>
				<button id= "korConsonant4" value= "ㄹ" class= "consonantButton">ㄹ</button>
				<button id= "korConsonant5" value= "ㅁ" class= "consonantButton">ㅁ</button>
				<button id= "korConsonant6" value= "ㅂ" class= "consonantButton">ㅂ</button>
				<button id= "korConsonant7" value= "ㅅ" class= "consonantButton">ㅅ</button>
			</div>
			<div id= "consonantBox2" style= "display: none; position: absolute; top: 180px; left: 105px; z-index: 80; background-color: #ffffff; padding: 5px 10px; box-shadow: 5px 5px 5px 3px rgba(0, 0, 0, 0.3);">
				<button id= "korConsonant8" value= "ㅇ" class= "consonantButton">ㅇ</button>
				<button id= "korConsonant9" value= "ㅈ" class= "consonantButton">ㅈ</button>
				<button id= "korConsonant10" value= "ㅊ" class= "consonantButton">ㅊ</button>
				<button id= "korConsonant11" value= "ㅋ" class= "consonantButton">ㅋ</button>
				<button id= "korConsonant12" value= "ㅌ" class= "consonantButton">ㅌ</button>
				<button id= "korConsonant13" value= "ㅍ" class= "consonantButton">ㅍ</button>
				<button id= "korConsonant14" value= "ㅎ" class= "consonantButton">ㅎ</button>
				<button id= "korConsonant15" value= "etc" class= "consonantButton" style= "width: 50px;">그 외</button>
			</div>
							
			
			<div class= "searchResultContainer" style = "background-color: rgba(255, 255, 255, 0.95); border-radius: 2px; position: absolute; top: 190px; left: 75px;
														 padding: 10px; width: 280px; max-height: 500px; z-index:51; overflow-y: auto; text-align: center; display: none;
														 box-shadow: 5px 5px 5px 3px rgba(0, 0, 0, 0.2);"
				 id= "searchResultList">
					
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
						margin-bottom: 20px;
						color: #3C3C3C;"
				 class= "fontTheJamsil">
				1. 좌측 상단 검색창을 통해 장소를 선택,<br>검색해주세요.
				(예시 : 지역명 - 홍대)<br><br>
				2. 표시되는 검색결과를 클릭하여 지도에 표시되는 위치를 확인하세요.<br>
				(업체명은 가나다 오름차순으로 정렬)<br><br>
				3. 화면에 표시되는 장소 확인 후, 우측 하늘색 바를 클릭시 장소가 추가됩니다.<br>한번 더 클릭 시 제거됩니다.<br><br>
				3. 코스의 이름과 설명을 작성해주세요.<br><br>
				4. 작성이 끝나면 코스 만들기 버튼을 클릭,<br>자신만의 코스를 만들어주세요.<br><br>
				<span style= "color: #FF962B; font-size: 1rem;">코스 만들기 기능은 로그인 후<br>사용 가능합니다.</span><br><br>
			</div>
		
		<!-- 코스 정보 입력 form start -->
		<form id = "courseInfo" method= "POST" accept-charset= "UTF-8"
			  action = "/course/courseMake">
			<span style= "font-family: 'TheJamsil5Bold', sans-serif; text-align: center;">코스 정보</span><br><br>
			
			
			<input id= "currentPlace" type= "hidden">
			
			<div id= "selectedPlaceDiv1" class= "placeDiv" style= "cursor: pointer;">첫번째 장소</div>
			<input id= "selectedPlaceId1" name= "selectedPlaceId1" type= "hidden">
			<div id= "selectedPlaceDiv2"  class= "placeDiv" style= "cursor: pointer;">두번째 장소</div>
			<input id= "selectedPlaceId2" name= "selectedPlaceId2" type= "hidden">
			<div id= "selectedPlaceDiv3" class= "placeDiv" style= "cursor: pointer;">세번째 장소</div>
			<input id= "selectedPlaceId3" name= "selectedPlaceId3" type= "hidden">
			<div id= "selectedPlaceDiv4" class= "placeDiv" style= "cursor: pointer;">네번째 장소</div>
			<input id= "selectedPlaceId4" name= "selectedPlaceId4" type= "hidden">
			<div id= "selectedPlaceDiv5" class= "placeDiv" style= "cursor: pointer;">다섯번째 장소</div>
			<input id= "selectedPlaceId5" name= "selectedPlaceId5" type= "hidden">
						
			<script>
				var placeDivs = document.querySelectorAll('[id^="selectedPlaceDiv"]');			
				var place1Selected = false;
				var place2Selected = false;
				var place3Selected = false;
				var place4Selected = false;
				var place5Selected = false;
				
				placeDivs.forEach(function(div) {
				    div.addEventListener('click', function() {
						var placeDivId = document.getElementById('currentPlace').value;
						var placeId = document.getElementById(placeDivId).querySelector('input').value;
						var placeName = document.getElementById(placeDivId).querySelector('p').textContent;									
						
						if(div.id === 'selectedPlaceDiv1'){				
							if(place1Selected) {
								div.textContent = '첫번째 장소';
								div.style.backgroundColor = '#7FB3D5';
								div.style.cursor = 'pointer';
								document.getElementById('selectedPlaceId1').value = null;
								place1Selected = false;
							}else {
								div.textContent = placeName;
								div.style.backgroundColor = '#FF962B';
								div.style.cursor = 'pointer';
								document.getElementById('selectedPlaceId1').value = placeId;
								place1Selected = true;
							}
						}else if(div.id === 'selectedPlaceDiv2') {
							if(place2Selected) {
								div.textContent = '두번째 장소';
								div.style.backgroundColor = '#7FB3D5';
								div.style.cursor = 'pointer';
								document.getElementById('selectedPlaceId2').value = null;
								place2Selected = false;
							}else {
								div.textContent = placeName;
								div.style.backgroundColor = '#FF962B';
								div.style.cursor = 'pointer';
								document.getElementById('selectedPlaceId2').value = placeId;
								place2Selected = true;
							}
						}else if(div.id === 'selectedPlaceDiv3') {
							if(place3Selected) {
								div.textContent = '세번째 장소';
								div.style.backgroundColor = '#7FB3D5';
								div.style.cursor = 'pointer';
								document.getElementById('selectedPlaceId3').value = null;
								place3Selected = false;
							}else {
								div.textContent = placeName;
								div.style.backgroundColor = '#FF962B';
								div.style.cursor = 'pointer';
								document.getElementById('selectedPlaceId3').value = placeId;
								place3Selected = true;
							}
						}else if(div.id === 'selectedPlaceDiv4') {
							if(place4Selected) {
								div.textContent = '네번째 장소';
								div.style.backgroundColor = '#7FB3D5';
								div.style.cursor = 'pointer';
								document.getElementById('selectedPlaceId4').value = null;
								place4Selected = false;
							}else {
								div.textContent = placeName;
								div.style.backgroundColor = '#FF962B';
								div.style.cursor = 'pointer';
								document.getElementById('selectedPlaceId4').value = placeId;
								place4Selected = true;
							}
						}else if(div.id === 'selectedPlaceDiv5') {
							if(place5Selected) {
								div.textContent = '다섯번째 장소';
								div.style.backgroundColor = '#7FB3D5';
								div.style.cursor = 'pointer';
								document.getElementById('selectedPlaceId5').value = null;
								place5Selected = false;
							}else {
								div.textContent = placeName;
								div.style.backgroundColor = '#FF962B';
								div.style.cursor = 'pointer';
								document.getElementById('selectedPlaceId5').value = placeId;
								place5Selected = true;
							}
						}
						
						// 현재 마커의 placeId, placeName

				    });
				});		
			</script>		
			<input id= "courseName" name= "courseName" class= "courseMakeInputBox" type= "text"
				   value= "코스 이름" style= "text-align: center; padding: 5px; width: 150px;" onfocus="clearInputValue(this)"><br>
			<textarea id= "courseContent" name= "courseContent" class= "courseMakeInputBox"
					  style= "margin-top: 10px; font-family: sans-serif; font-size: 10pt; margin-bottom: 10px; padding: 3px 10px;" rows= "10" cols= "30"
					  onfocus="clearTextAreaValue(this)" maxlength="400">
이 코스는 어떤 코스인가요?
			</textarea>
			
			 <c:if test="${not empty sessionScope.user.userId}">
			 <br>
			    <button id= "courseMakeButton" class="courseMakeButton" type="submit" style= "padding-left: 20px; padding-right: 20px; color: #FFFFFF">코스 만들기!</button>
			</c:if>
			
			<c:if test="${empty sessionScope.user.userId}">
			    <button id= "invalidButton" class="courseMakeButton" disabled > 로그인 후 사용가능합니다</button>
			</c:if>
			
			<input type= "hidden" name= "userId" value= "${sessionScope.user.userId}">
			<input id = "courseNumber" type= "hidden" name= "courseNumber" value= "">
		</form>
		<!-- 코스 정보 입력 form end -->	
	</div>
	
	<script>
	if(document.getElementById('invalidButton') === null) {
		document.getElementById('courseMakeButton').addEventListener('click', function(){
			var placeCount = 0;
			
			if(place1Selected){
				placeCount++;
			}
			if(place2Selected){
				placeCount++;
			}
			if(place3Selected){
				placeCount++;
			}
			if(place4Selected){
				placeCount++;
			}
			if(place5Selected){
				placeCount++;
			}
			
			if(placeCount == 0) {
				alert("장소는 1개 이상 선택되어야 해요.");
				window.location.reload();
			}
			document.getElementById('courseNumber').value = placeCount;
		});
	}
		
	</script>
	
	<script>
	<!-- 지도 출력 -->
		var mapDiv = document.getElementById('mapArea');
		var map = new naver.maps.Map(mapDiv);
		
		<!-- 지역명, 업종명 선택 후 검색 버튼 클릭 시 결과 출력 -->
		function getPlaceList() {
			<!-- 자음검색버튼 -->
			document.getElementById("consonantBox1").style.display = 'flex';
			document.getElementById("consonantBox2").style.display = 'flex';
			
			var areaSelect = document.getElementById("areaSelect").value;
			var categorySelect = document.getElementById("categorySelect").value;
			
		    if (areaSelect.trim() === "") {
		        alert("지역명을 선택해주세요.");
		        return;
		      }
		    console.log(areaSelect);
		    // area값과 category 값으로 placeList 조회 (category값은 필수 x)
		    axios.get('/place/getPlaceList/' + areaSelect)
	    	.then(function(response) {
	    		
	    		var resultContainer = document.getElementById('searchResultList');
	    		resultContainer.style.display = 'block';
	    		resultContainer.innerHTML = '';
	    		
				  map = new naver.maps.Map(mapDiv, {
						center: new naver.maps.LatLng(response.data[1].latitude, response.data[1].longitude),
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
	        		  				  '<input type="hidden" value="' + place.placeId  + '"id= "input' + divId + '">' +
	        		  				  '<input type="hidden" id= "lat' + divId + '" value= "' + place.latitude + '">' +
	           		  				  '<input type="hidden" id= "long' + divId + '" value= "' + place.longitude + '">' ;
					  resultContainer.appendChild(div);	

					  
					  var marker = new naver.maps.Marker({
						position: new naver.maps.LatLng(place.latitude, place.longitude),
						title: place.placeId,
						map: null
					  });
					  
					  var infoWindow = new naver.maps.InfoWindow({
				        content: '<div id= "windowContent" style="width:100px;text-align:center;padding:5px;font-family:\'TheJamsil3Regular\', sans-serif;font-size:10pt; color: #00008b;"><a href="https://map.naver.com/p/search/'+ place.placeName + " " + areaSelect +'" target="_blank"><b>'+ place.placeName +'</b> </a></div>',
				        map: null
				      });
					  
					  markers.push(marker);
					  infoWindows.push(infoWindow);
					  
					  document.getElementById(divId).addEventListener('click', function(){
						  showMarkerAndInfoWindow(index);
						  document.getElementById('currentPlace').value = divId;
						  
						  var currLat = document.getElementById('lat' + divId).value;
						  var currLong = document.getElementById('long' + divId).value; 
							
						  var placeLocation = new naver.maps.LatLng(currLat, currLong);
						  
						  map.panTo(placeLocation);
						  
					  }); 
					  
					  	<!-- 호출시 마커와 가게이름을 띄워주는 함수 start-->
			    		function showMarkerAndInfoWindow(index) {		    			
			    		    markers.forEach(function(marker, i) {
			    		        if (i === index) {
			    		            marker.setMap(map);
			    		            infoWindows[i].open(map, marker);
			    		        } else {
			    		            marker.setMap(null);
			    		            infoWindows[i].close();
			    		        }
			    		    });
			    		}
			    		<!-- 호출시 마커와 가게이름을 띄워주는 함수 end-->
					});
	    		

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
	    			
	    		

	    		
	    	}).catch(function(error) {
	    	});	
		}
		<!-- 지역명, 업종명 선택 후 검색 버튼 클릭 시 결과 출력 종료-->	
		
		<!-- 자음검색버튼 클릭시 자음으로 검색-->
		var consonantButtons = document.querySelectorAll('[id^="korConsonant"]');
		
		consonantButtons.forEach(consonantButton => {
			consonantButton.addEventListener('click', function() {
				var areaSelect = document.getElementById("areaSelect").value;
				var consonant = consonantButton.value;		
				
				axios.get('/place/getPlaceList/' + areaSelect + '/' + consonant)
					.then(function(response) {					
						var resultContainer = document.getElementById('searchResultList');
						resultContainer.innerHTML = '';
						
						map = new naver.maps.Map(mapDiv, {
							center: new naver.maps.LatLng(response.data[1].latitude, response.data[1].longitude),
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
				      		  				  '<input type="hidden" value="' + place.placeId  + '"id= "input' + divId + '">' +
				      		  				  '<input type="hidden" id= "lat' + divId + '" value= "' + place.latitude + '">' +
				         		  				  '<input type="hidden" id= "long' + divId + '" value= "' + place.longitude + '">' ;
						  resultContainer.appendChild(div);	

						  
						  var marker = new naver.maps.Marker({
							position: new naver.maps.LatLng(place.latitude, place.longitude),
							title: place.placeId,
							map: null
						  });
						  
						  var infoWindow = new naver.maps.InfoWindow({
					        content: '<div id= "windowContent" style="width:100px;text-align:center;padding:5px;font-family:\'TheJamsil3Regular\', sans-serif;font-size:10pt; color: #00008b;"><a href="https://map.naver.com/p/search/'+ place.placeName + " " + areaSelect +'" target="_blank"><b>'+ place.placeName +'</b> </a></div>',
					        map: null
					      });
						  
						  markers.push(marker);
						  infoWindows.push(infoWindow);
						  
						  document.getElementById(divId).addEventListener('click', function(){
							  showMarkerAndInfoWindow(index);
							  document.getElementById('currentPlace').value = divId;
							  
							  var currLat = document.getElementById('lat' + divId).value;
							  var currLong = document.getElementById('long' + divId).value; 
								
							  var placeLocation = new naver.maps.LatLng(currLat, currLong);
							  
							  map.panTo(placeLocation);
							  
						  }); 
						  
						  	<!-- 호출시 마커와 가게이름을 띄워주는 함수 start-->
				    		function showMarkerAndInfoWindow(index) {		    			
				    		    markers.forEach(function(marker, i) {
				    		        if (i === index) {
				    		            marker.setMap(map);
				    		            infoWindows[i].open(map, marker);
				    		        } else {
				    		            marker.setMap(null);
				    		            infoWindows[i].close();
				    		        }
				    		    });
				    		}
				    		<!-- 호출시 마커와 가게이름을 띄워주는 함수 end-->
						});

					}).catch(error => {
						
					});
				
			});	
		});
		
		
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