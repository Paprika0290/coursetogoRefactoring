<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>footer</title>
    <style>
		footer {
		    position: absolute;
		    bottom: 0;
		    left: 0;
		    width: 100%;
		    border-top: 1px solid #e4e4e4;
		    background-color:#f8f9fa;
		    padding:1rem 0;
		    margin:1rem 0;
		    transform: translateY(150px);
		    z-index: 50;
		}
		
		.withoutSidebar {
			margin-left: calc(5vw + 200px);
			display: flex;
		    flex-direction: column;
		    justify-content: center;
		}    
	
	</style>
</head>

<body>
	  <div class="inner">
	  	<div class = "withoutSidebar">
		    <div class="footer-message" style = "font-weight: bold;
											     font-size:0.9rem;
											     color:#545e6f;
											     margin-bottom:0.3rem;
											     margin:0 0 0 0.6rem;">
				당신의 휴일이 즐거움으로 가득하도록. 즐거운 CourseToGo 되세요.
			</div>
		    <div class="footer-contact" style = "font-size:0.9rem;
  											     color:#545e6f;
											     margin:0.6rem;">
		    	📧  p0209y@gmail.com<br><br>
		    	© 2023 6CanDoIt All rights reserved
		    </div>
		</div>
	  </div>
</body>
</html>