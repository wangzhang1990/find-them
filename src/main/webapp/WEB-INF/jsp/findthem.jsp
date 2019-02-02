<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="referrer" content="no-referrer" />
<title>^_^ Find Them ^_^</title>
<script type="text/javascript" src="/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript">
	function previousPage() {
		var lastPage = "${ page - 1 }";
		if (lastPage <= 0) {
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
	function keywordInit() {
		$("[name='keyword']").val("");
	}
</script>
</head>
<body>
	<div>
		<h1>
			<a href="https://www.zhihu.com/people/wangzhang/activities">我的知乎主页<<====欢迎大家</a>
		</h1>
		<h4>由于时间缘故，前端我没有做，大家先用这个原始页面将就一下，等有时间的时候，我会使用bootstrap把它做漂亮一些</h4>
		<h4>数据每小时更新一次，可以更新最新发布/修改的回答</h4>
		<h5><font color="red"></font></h5>
		<hr>
	</div>
	<form action="find">
		关键词：<input type="text" name="keyword" value="${ keyword }" onfocus="keywordInit()">
		<input
			type="hidden" name="page" value="1"> <input type="submit"
			value="搜索">&nbsp&nbsp&nbsp<font color="red">建议单词搜索，比如"西安"。尽量不要用组合词，如"陕西西安"，可能会漏结果。</font>
	</form>
	<span>当前第${ page }页=======>>></span>
	<a href="javascript:void(0)" onclick="previousPage()">上一页</a><<==>>
	<a href="javascript:void(0)" onclick="nextPage()">下一页</a>=======>>> 第
	<input type="text" name="page_located" value="${ page }"
		style="width: 50px">页
	<a href="javascript:void(0)" onclick="locatePage()">跳转</a>
	<br>
	<hr>
	<font color="red">点击id可以进入答主答题页面哦~~</font>
	<div>
		<jsp:useBean id="dateObject" class="java.util.Date" scope="page"></jsp:useBean>
		<c:forEach items="${ result.data }" var="answer">
			<div style="border: 3px solid red; width: 1000px">
				<h1>
					id：<a
						href="https://www.zhihu.com/people/${ answer.author.url_token }/activities">${ answer.author.name }</a>
				</h1>
				<jsp:setProperty property="time" name="dateObject"
					value="${ answer.updated_time * 1000 }" />
				<h4>
					<i>last updated：</i>
					<fmt:formatDate value="${dateObject}" pattern="yyyy-MM-dd HH:mm:ss" />
				</h4>
				<hr>
				<div>${ answer.content }</div>
			</div>
		</c:forEach>

	</div>
	<hr>
	<span>当前第${ page }页=======>>></span>
	<a href="javascript:void(0)" onclick="previousPage()">上一页</a><<==>>
	<a href="javascript:void(0)" onclick="nextPage()">下一页</a>=======>>> 第
	<input type="text" name="page_located" value="${ page }"
		style="width: 50px">页
	<a href="javascript:void(0)" onclick="locatePage()">跳转</a>
	<br>
	<hr>
</body>
</html>