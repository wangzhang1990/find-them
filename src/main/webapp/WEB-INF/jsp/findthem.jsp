<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<h3>notice</h3>
		<h4>由于时间缘故，前端我没有做，还望大家先用这个原始页面将就一下，等有时间的时候，我会使用bootstrap把它做漂亮一些</h4>
		<h4>目前bug1: 由于数据字段截取没有搞定，所以暂时照片无法显示位置出会出现巨大空白。两天内解决</h4>
		<h4>目前bug2: 没有做翻页功能，暂时只有搜索结果的前5个，预计今晚加入。肚子饿，吃饭先。。。。</h4>
	<hr>
	<hr>
	</div>
	<form action="find">
		关键词：<input type="text" name="keyword" value="${ keyword }">
		&nbsp <input type="submit" value="搜索">
	</form>
	<br>
	<input type="hidden" name="page" value="${ page + 1 }">
	<input type="submit" value="更新数据"> 每分每秒都有很多朋友更新或添加自己的回答，用此键可以更新数据，（暂未开放）
	<div>
		<c:forEach items="${ result.data }" var="answer">
			<div style="border: 1px solid red; width: 800px">
				<h1>id：${ answer.author.name }</h1>
				<!-- <figure><img src="https://pic3.zhimg.com/v2-a31b9a1843bd950af33e9aea179b189e_b.jpg" ></figure> -->
				<div>${ answer.content }</div>
			</div>
		</c:forEach>

	</div>
</body>
</html>