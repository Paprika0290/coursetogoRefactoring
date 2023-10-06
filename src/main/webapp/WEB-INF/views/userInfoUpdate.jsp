<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="userId" value="${sessionScope.user.userId}" />

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CourseToGo / 회원정보 수정</title>
	
	<style>
		.mainContent {
			margin-top: 150px;
		    display: flex;
		    justify-content: center;
		    align-items: center;
		    height: 100vh-100px;
		    text-align: center;
		}
		
		.profileImageButton {
			background-color: #FF962B;
			padding : 3px 10px;
			color: white;
   		    font-weight: bold;
   		    border-radius: 5px;
   		    border: 1px solid #FF962B;
   		    cursor: pointer;
		}
		
		.profileUpdateButton {
			background-color: #FF962B;
			padding: 10px;
			padding-left: 20px;
			padding-right: 20px;
			color: white;
   		    font-weight: bold;
  		    border-radius: 5px;
  		    border: 1px solid #FF962B;
  		    cursor: pointer;
		}
		
        .popup {
            display: none;
            position: fixed;
            top: 35%;
            left: 50%;
            background-color: #fff;
            padding: 30px 50px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
            z-index: 9999;
        }
        
        .profileImages img {
        	cursor:pointer;
        }
		
		.fontTheJamsil {
			font-family: 'TheJamsil5Bold', sans-serif;
		}
	</style>
	
	<link rel="stylesheet" type="text/css" href="/css/fonts.css">

	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	
</head>
<body>
	<header>
		<jsp:include page="components/navigation.jsp" />	
	</header>
	
	<div class= "mainContent" >

		<form action= "/user/update" id= "updateForm" method = "POST" >
			<div style= "border: 1px solid #E5E5E5;
						 padding: 30px;
						 padding-left: 150px;
						 padding-right: 150px;
						 display: flex;
						 flex-direction: row;
					     align-items: center;
					     font-family: 'TheJamsil5Bold', sans-serif;
					     color:#3c3c3c; ">
					     
					     
				<div class= "leftBox">
					<span class= "fontTheJamsil" style= "font-size: 1.3rem; color:#3C3C3C;
												 margin-bottom: 70px;">
					회원 정보를 수정합니다.
					</span>	     
					
					<div style = "display: flex;
								  flex-direction:column;
							      align-items: center;
							      border-radius: 5px;
							      background-color: #F6F6F6;
							      padding: 10px;
							      margin-top: 40px;">
						<br>
						<img src="${sessionScope.user.userPhoto}" width= "50px" style= "margin-bottom: 10px;">
						<p class= "fontTheJamsil" style = "color: #3C3C3C; font-size: 1rem;">
							<span style = "color: #FF962B;">${sessionScope.user.userNickname}</span> 님 안녕하세요!
							<br><br>
							<span style = "background-color: #ffffff; padding: 5px; padding-left: 20px; padding-right: 20px;
										   border-radius: 10px;">
								${sessionScope.user.userEmail}
							</span>
						</p>	
					</div>
				</div>
					     
				<div class = "rightBox" style= "display: flex; flex-direction: column; margin-left: 100px;">
					<br><br><br>
					<div>
						<span style= "color: #FF962B;">변경을 원하시는 회원정보의 내용을 수정해주세요.</span><br><br><br>
						<span>프로필 이미지</span><br>
					</div>
					<div style= "margin-bottom: 20px;">
						<img id = displayedImage src="${sessionScope.user.userPhoto}" width= "50px" style= "margin-top: 20px;">					
					</div>
					
					<div id= "profileImageList" class= "popup">
						<div style= "margin-bottom:20px;">
							클릭시 이미지가 적용됩니다.
						</div>
						<div class = "profileImages">
							<img src="/images/profile(0)s.png" width= "50px;">
							<img src="/images/profile(1)s.png" width= "50px;">
							<img src="/images/profile(2)s.png" width= "50px;">
							<img src="/images/profile(3)s.png" width= "50px;">
							<img src="/images/profile(4)s.png" width= "50px;"><br>
							<img src="/images/profile(5)s.png" width= "50px;">
							<img src="/images/profile(6)s.png" width= "50px;">
							<img src="/images/profile(7)s.png" width= "50px;">
							<img src="/images/profile(8)s.png" width= "50px;">
							<img src="/images/profile(9)s.png" width= "50px;">		
						</div>
						<br><br>
						<button type= "button" class="profileImageButton" id = "profileImageUpdateClose">닫기</button>
								
					</div>

					<div>
						<button type = "button" class="profileImageButton" id="profileImageUpdate" onclick="profileImageList()">수정</button>
						<button type = "button" class="profileImageButton" id="profileImageDelete" onclick="deleteProfileImage()">삭제</button><br>
					</div>
					<br>
					<div id= "userNicknameLine">
						<div style= "display: flex; flex-firection: row; align-items: center; margin-top: 20px;">
							<span style= "margin-left: 40px;">닉네임</span>
							<input type= "text" id= "userNickname" name= "userNickname" value="${sessionScope.user.userNickname}" maxlength="8"
								   style= "background-color: #F1F1F1; border: 1px solid #F1F1F1; width: 180px; height: 15px;
								   		   padding: 3px; margin-left: 10px; margin-top: 5px; margin-bottom: 5px;">
						</div>
					 	<span id= "userNicknameCheck" style= "display:block; font-size: 2px; width:150px; height:15px;
					 										  margin-left: 100px;"> </span>
					</div>

					<div style= "margin-right: 190px; margin-top: 10px;">
						자기소개
					</div>
					<br>
					<div style= "margin-left: 10px;">
						<textarea id= "userIntroduceArea" rows= "4" cols= "30" name= "userIntroduce"
							  style= "background-color: #F1F1F1; border: 1px solid #F1F1F1;
				   		      padding: 3px; width: 250px; height: 80px;" maxlength="50">${sessionScope.user.userIntroduce}</textarea><br>			
					</div>
					
					<input type= "hidden" name= "userId" value= "${sessionScope.user.userId}">	
					<input type= "hidden" name= "userPhoto" id= "updatePhoto" value= "${sessionScope.user.userPhoto}">
					
					<div style= "display: flex; flex-direction: row; align-items: center; margin-top: 20px; margin-bottom: 80px;">
						<input id= "modifyButton" type= "submit" class= "profileUpdateButton" value= "회원정보 수정" style= "margin-left: 50px;">
						<input type= "button" class= "profileUpdateButton" id="unsign" value="회원 탈퇴" onclick="unsignConfirm()" style= "background-color: #BFB7B7; border: 1px solid #BFB7B7; margin-left: 10px;">
					</div>
				</div>

			</div>
		</form>
		
	</div>
	
	<c:if test="${not empty sessionScope.newUser}">
		<c:remove var="newUser" scope="session" />
	</c:if>
	
   	<footer>
		<jsp:include page="components/footer.jsp" />
	</footer>
	
	<script>
	<!-- 이미지 선택 팝업 창 start-->
		// 버튼 클릭 시 이미지 팝업창 노출
	    document.getElementById('profileImageUpdate').addEventListener('click', function () {
	        document.getElementById('profileImageList').style.display = 'block';
	    });
		
		// 이미지 클릭 시 해당 이미지 적용
		const selectedImage = document.querySelectorAll('.profileImages img');
		const displayedImage = document.getElementById('displayedImage');
        const updateImage = document.getElementById('updatePhoto');
		
		selectedImage.forEach((img, index) => {
            img.addEventListener('click', () => {
                // 클릭한 이미지의 src를 가져와서 교체될 이미지의 src로 설정
                displayedImage.src = img.src;
                updateImage.value = img.src.replace(/^https?:\/\/[^/]+/, '');
        	});
		});
				
	    // 닫기 버튼 클릭 시 이미지 팝업창 숨김
	    document.getElementById('profileImageUpdateClose').addEventListener('click', function () {
	        document.getElementById('profileImageList').style.display = 'none';
	    });
	<!-- 이미지 선택 팝업 창 end-->
	
	<!-- 이미지 삭제 start-->
	    document.getElementById('profileImageDelete').addEventListener('click', function () {
			const displayedImage = document.getElementById('displayedImage');
		    const updateImage = document.getElementById('updatePhoto');
	
			displayedImage.src = '/images/profile(0)s.png';
	        updateImage.value = '/images/profile(0)s.png';
	    });	    
    <!-- 이미지 삭제 end-->	
	
	<!-- 닉네임 중복 확인 start-->
		var userNicknameInput = document.getElementById('userNickname');
		
	    userNicknameInput.addEventListener('input', function() {
		    var updatedValue = userNicknameInput.value;	
		    
 		      axios.get('/user/userNicknameCheck', {
				  params: {
					    userNickname: updatedValue
					  }
					})
			  .then(function (response) {
				  var res = response.data;
				  var userNicknameCheckSpan = document.getElementById('userNicknameCheck');

				  if(res === 1) {
					  userNicknameCheckSpan.innerHTML = '이미 사용중인 닉네임입니다.';
					  userNicknameCheckSpan.style.color = '#E74C3C';
						  document.getElementById("modifyButton").addEventListener("click", function(event) {
							  event.preventDefault();
							});
				  }else if (res === 0) {
					  userNicknameCheckSpan.innerHTML = '사용 가능한 닉네임입니다.';
					  userNicknameCheckSpan.style.color = '#138D75';
						  document.getElementById("modifyButton").addEventListener("click", function(event) {
							});
				  } else if (res === -1) {
					  userNicknameCheckSpan.innerHTML = '닉네임은 비어있을 수 없습니다.';
					  userNicknameCheckSpan.style.color = '#E74C3C';
						  document.getElementById("modifyButton").addEventListener("click", function(event) {
							  event.preventDefault();
							});
				    }
				  
			    console.log(response.data);
			  })
			  .catch(function (error) {
				  console.log("닉네임 검증 불가");
			  });
	    })
	<!-- 닉네임 중복 확인 end-->    
	
	<!-- 자기소개 내용 확인 start-->
		document.getElementById('modifyButton').addEventListener('click', function(event) {
			var submitContent = document.getElementById('userIntroduceArea').value;
			if(submitContent.trim() === '') {
				alert('자기소개를 입력해주세요.');
				event.preventDefault();
			}
			
		});
	<!-- 자기소개 내용 확인 end-->
	    
	    
	<!-- 회원탈퇴 버튼 클릭 시 사용자에 확인 요청 -->
		function unsignConfirm() {
			var result1 = confirm("정말로 회원을 탈퇴하시겠습니까?");
			if (result1) {
				var result2 = confirm("탈퇴 후에는 이전 정보를 되돌릴 수 없습니다. 탈퇴하시겠습니까?");
				if(result2) {
					unsignUser();
				}
			}
		}		
	<!-- 회원탈퇴 버튼 클릭 시 사용자에 확인 요청 -->
	
	<!-- 유저가 회원탈퇴 확인 시 탈퇴처리 -->
	    function unsignUser() {
	    	var userId = parseInt('<%= pageContext.getAttribute("userId") %>');
	
	        var form = document.createElement('form');
	        form.method = 'POST';
	        form.action = '/user/unsign';
	
	        var input = document.createElement('input');
	        input.type = 'hidden';
	        input.name = '_method';
	        input.value = 'PUT';
	        form.appendChild(input);
	
	        input = document.createElement('input');
	        input.type = 'hidden';
	        input.name = 'userId';
	        input.value = userId;
	        form.appendChild(input);
	
	        document.body.appendChild(form);
	        form.submit();
		}
	<!-- 유저가 회원탈퇴 확인 시 탈퇴처리 -->	    
	</script>
	
   	<footer>
		<jsp:include page="components/footer.jsp" />
	</footer>	
	
</body>
</html>