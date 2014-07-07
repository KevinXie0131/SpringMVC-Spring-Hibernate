<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript" src="${pageContext.request.contextPath}/error/files/allsite.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/error/files/mod_foot.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/error/files/qing.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/error/files/qbase_datauri.css" type="text/css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/error/files/qbase.css" type="text/css"></link>
</head>
<body>
	<SECTION class="mod-page-body">
		<DIV class="mod-page-main wordwrap clearfix" style="margin-left:40px;" >
			<DIV class="x-page-container">
				<DIV class="mod-notfound grid-98"><IMG class="img-notfound" height=320 src="${pageContext.request.contextPath}/error/files/notfound.gif" width=520>
				<P style="FONT-SIZE: 24px; LINE-HEIGHT: 70px">Opps! Page Not Found.</P>
				<P style="FONT-SIZE: 16px; MARGIN-BOTTOM: 30px">${msg}</P>
				</DIV>
			</DIV>
		</DIV>
	</SECTION>
</body>
</html>