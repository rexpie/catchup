<%@page session="true"%>
<html>
<body>
	<h1>Title : ${title}</h1>
	<h1>Message : ${message}</h1>

	<%if (((HttpServletRequest)pageContext.getRequest()).getUserPrincipal().getName() != null ){ %>
		<h2>Welcome : ${pageContext.request.userPrincipal.name} | <a href="/j_spring_security_logout" >Logout</a></h2>  
	<%} %>


</body>
</html>