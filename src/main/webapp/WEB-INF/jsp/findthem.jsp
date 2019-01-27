<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<table border="1">
		<c:forEach items="${ result.data }" var="answer">
			<tr>
				<td>${ answer.content }</td>
			</tr>
		</c:forEach>
		</table>
	</div>
</body>
</html>