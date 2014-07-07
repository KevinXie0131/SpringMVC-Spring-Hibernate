<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML >
<html>
<head>
<title>SpringMVC+Spring+Hibernate</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="jslib/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="jslib/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<link rel="stylesheet" href="jslib/jquery-easyui-1.3.1/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="jslib/jquery-easyui-1.3.1/themes/icon.css" type="text/css"></link>
<script type="text/javascript" src="jslib/myUtil.js"></script>

<style type="text/css">
	#myul ul {
		list-style:none;
	}
	#myul ul li {
		margin-left:-20px;
		line-height:20px;
	}
</style>

</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height:40px;">
		<jsp:include page="layout/north.jsp"></jsp:include>
	</div>
	<div data-options="region:'south'" style="height:20px;"></div>
	<div data-options="region:'west'" style="width:200px;">
		<jsp:include page="layout/west.jsp"></jsp:include>
	</div>
	<div data-options="region:'east',title:'Other',split:true" style="width:220px;">
		<jsp:include page="layout/east.jsp"></jsp:include>
	</div>
	<div data-options="region:'center',title:'SpringMVC3.2.8+Spring3.2.8+Hibernate4.2.11+JQuery-EasyUI1.3.1+Oracle10g+Maven'" style="overflow: hidden;">
		<jsp:include page="layout/center.jsp"></jsp:include>
	</div>

	<jsp:include page="user/login.jsp"></jsp:include>
	<jsp:include page="user/reg.jsp"></jsp:include>
	
</body>
</html>
