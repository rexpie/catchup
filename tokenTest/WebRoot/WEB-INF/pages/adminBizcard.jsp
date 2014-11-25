<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<c:url value="/resources/css/bootstrap.min.css"/>"
	rel="stylesheet">
<link href="<c:url value="/resources/css/jquery.fancybox.css"/>"
	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.fancybox.pack.js"/>"></script>

</head>
<body>

	<table class="table">
		<thead>
			<tr>
				<th>id</th>
				<th>昵称</th>
				<th>公司</th>
				<th>行业</th>
				<th>职业</th>
				<th>楼宇</th>
				<th>性别</th>
				<th>电话</th>
				<th>名片</th>
				<th>同意认证</th>
				<th>拒绝认证</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="user" items="${users}" varStatus="count">
				<tr id="row${count.index}">
					<td><c:out value="${user.id}" /></td>
					<td><c:out value="${user.nickname}" /></td>
					<td><c:out value="${user.company}" /></td>
					<td><c:out value="${user.industry}" /></td>
					<td><c:out value="${user.job}" /></td>
					<td><c:out value="${user.building}" /></td>
					<td><c:out value="${user.sex}" /></td>
					<td><c:out value="${user.phone_number}" /></td>

					<td><a
						href="<c:url value="/user/getBizcard?id=${user.id}&token=${user.token}"/>"
						class="fancybox"> <img
							src="<c:url value="/user/getBizcard?id=${user.id}&token=${user.token}&isThumb=1"/>" />
					</a></td>
					<td>
					<button onClick="validate('<c:url value="/user/validateBizcard?id=${user.id}&token=${user.token}&validated=true"/>', 'row${count.index}' )">
					同意认证
					</button>
					</td>
					<td>
					<button onClick="validate('<c:url value="/user/validateBizcard?id=${user.id}&token=${user.token}&validated=false"/>', 'row${count.index}' )">
					拒绝认证
					</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".fancybox").fancybox({
				type : "image"
			});
		});
		
		function validate(url, id){
			$.get(url, function(result){
				var row = document.getElementById(id);
				$(row).hide();
			  });
		}
	</script>
</body>
</html>