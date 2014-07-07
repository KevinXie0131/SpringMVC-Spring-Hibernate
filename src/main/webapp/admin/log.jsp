<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../include.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var datagrid;
	$(function() {

		datagrid = $('#datagrid').datagrid({
			url : '${pageContext.request.contextPath}/logController/datagrid',
			title : 'Loglist',
			iconCls : 'icon-save',
			pagination : true,
			pagePosition : 'bottom',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40 ],
			fit : true,
			fitColumns : false,
			nowrap : false,
			border : false,
			idField : 'cid',
			sortName : 'cdatetime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : true,
			frozenColumns : [ [ {
				title : 'ID',
				field : 'cid',
				width : 150,
				sortable : true,
				checkbox : true
			}, {
				title : 'Username',
				field : 'cname',
				width : 150,
				sortable : true
			}, {
				title : 'IP',
				field : 'cip',
				width : 150,
				sortable : true
			}  ] ],
			columns : [ [ {
				title : 'Time',
				field : 'cdatetime',
				sortable : true,
				width : 200
			}, {
				title : 'Message',
				field : 'cmsg',
				width : 300
			} ] ]
		});

	});
	
	function userSearch() {
		datagrid.datagrid('load', sy.serializeObject($('#searchForm')));
	}
	
	function clearAndSearch() {
		datagrid.datagrid('load', {});
		$('#searchForm input').val('');
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false,title:'Search'" style="height: 60px;overflow: hidden;" align="left">
		<form id="searchForm">
			<strong style="margin-left:10px;"> Username: </strong><input name="cname" style="width:100px;" /></td>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="userSearch();">Search</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="clearAndSearch();">Clear</a>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="datagrid"></table>
	</div>

</body>
</html>