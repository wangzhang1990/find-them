<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
	function previousPage() {
		var lastPage = "${ page - 1 }";
		if (lastPage <= 0 ) {
			$("[name='page']").val("1");
		} else {
			
			$("[name='page']").val("${ page - 1 }");
		}
		$("form").submit();
	}
	function nextPage() {
		$("[name='page']").val("${ page + 1 }");
		$("form").submit();
	}
	function locatePage() {
		$("[name='page']").val($("[name='page_located']").val());
		$("form").submit();
	}
	
</script>
</head>
<body>
	<div>
		<h3><a href="https://www.zhihu.com/people/wangzhang/activities">我的知乎主页<<====欢迎大家</a></h3>
		<h4>由于时间缘故，前端我没有做，还望大家先用这个原始页面将就一下，等有时间的时候，我会使用bootstrap把它做漂亮一些</h4>
		<h4>目前bug1: 由于数据字段截取没有搞定，所以暂时照片无法显示位置出会出现巨大空白。明天解决</h4>
		<h4>目前bug2: 数据更新功能未添加，明天会加入定时更新数据功能，到时候大家就能观察实时更新的数据了</h4>
	<hr>
	<hr>
	</div>
	<form action="find">
		关键词：<input type="text" name="keyword" value="${ keyword }">
				<input type="hidden" name="page" value="1">
				<input type="submit" value="搜索">
	</form>
	<span>当前第${ page }页=======>>></span>
	<a href="javascript:void(0)" onclick="previousPage()">上一页</a><<==>>
	<a href="javascript:void(0)" onclick="nextPage()">下一页</a>=======>>>
	第<input type="text" name="page_located" value="${ page }" style="width: 20px">页<a href="javascript:void(0)" onclick="locatePage()">跳转</a><br>
	<hr>
	<a href="javascript:void(0)">点击id可以进入答主主页哦~暂无法进入匿名答主主页</a>
	<div>
		<c:forEach items="${ result.data }" var="answer">
			<div style="border: 1px solid red; width: 800px">
				<h1>id：<a href="https://www.zhihu.com/people/${ answer.author.url_token }/activities">${ answer.author.name }</a></h1>
				<!-- <figure><img src="https://pic3.zhimg.com/v2-a31b9a1843bd950af33e9aea179b189e_b.jpg" ></figure> -->
				<div>${ answer.content }</div>
			</div>
		</c:forEach>

	</div>
</body>
</html>