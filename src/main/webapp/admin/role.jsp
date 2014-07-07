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
			url : '${pageContext.request.contextPath}/roleController/datagrid',
			title : '',
			iconCls : 'icon-save',
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40 ],
			fit : true,
			fitColumns : true,
			nowrap : false,
			border : false,
			idField : 'cid',
			sortName : 'cname',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : true,
			frozenColumns : [ [ {
				title : 'ID',
				field : 'cid',
				width : 150,
				sortable : true,
				checkbox : true
			}, {
				title : 'Role',
				field : 'cname',
				width : 150,
				sortable : true
			} ] ],
			columns : [ [ {
				title : 'Note',
				field : 'cdesc',
				width : 150
			}, {
				title : 'Auth ID',
				field : 'authIds',
				width : 300,
				hidden : true
			}, {
				title : 'Authority',
				field : 'authNames',
				width : 300
			} ] ],
			toolbar : [ {
				text : 'Add',
				iconCls : 'icon-add',
				handler : function() {
					append();
				}
			}, '-', {
				text : 'Delete',
				iconCls : 'icon-remove',
				handler : function() {
					remove();
				}
			}, '-', {
				text : 'Edit',
				iconCls : 'icon-edit',
				handler : function() {
					edit();
				}
			}, '-', {
				text : 'Unselect',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('unselectAll');
				}
			}, '-' ],
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});

	});

	function append() {
		var p = parent.sy.dialog({
			title : 'Add role',
			href : '${pageContext.request.contextPath}/roleController/roleAdd',
			width : 460,
			height : 260,
			buttons : [ {
				text : 'Add',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/roleController/add',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								datagrid.datagrid('reload');
								p.dialog('close');
							}
							parent.sy.messagerShow({
								msg : json.msg,
								title : 'Message'
							});
						}
					});
				}
			} ],
			onLoad : function() {
				var f = p.find('form');
				var authIds = f.find('input[name=authIds]');
				var authIdsTree = authIds.combotree({
					lines : true,
					url : '${pageContext.request.contextPath}/authController/doNotNeedSession_treeRecursive',
					checkbox : true,
					multiple : true
				});
			}
		});
	}
	
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = parent.sy.dialog({
				title : 'Edit role',
				href : '${pageContext.request.contextPath}/roleController/roleEdit',
				width : 460,
				height : 260,
				buttons : [ {
					text : 'Edit',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/roleController/edit',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									datagrid.datagrid('reload');
									p.dialog('close');
								}
								parent.sy.messagerShow({
									msg : json.msg,
									title : 'Message'
								});
							}
						});
					}
				} ],
				onLoad : function() {
					var f = p.find('form');
					var authIds = f.find('input[name=authIds]');
					var authIdsTree = authIds.combotree({
						lines : true,
						url : '${pageContext.request.contextPath}/authController/doNotNeedSession_treeRecursive',
						checkbox : true,
						multiple : true,
						onLoadSuccess : function() {
							parent.$.messager.progress('close');
						}
					});
					f.form('load', {
						cid : rows[0].cid,
						cname : rows[0].cname,
						cdesc : rows[0].cdesc,
						authIds : sy.getList(rows[0].authIds)
					});
				}
			});
		} else {
			parent.sy.messagerAlert('Message', 'Please select one to edit', 'error');
		}
	}

	function remove() {
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.sy.messagerConfirm('Confirm', 'Are you sure to delete?', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].cid);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/roleController/delete',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(d) {
							datagrid.datagrid('load');
							datagrid.datagrid('unselectAll');
							parent.sy.messagerShow({
								title : 'Message',
								msg : d.msg
							});
						}
					});
				}
			});
		} else {
			parent.sy.messagerAlert('Message', 'Please select one to delete', 'error');
		}
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<table id="datagrid"></table>
	</div>

	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="append();" data-options="iconCls:'icon-add'">Add</div>
		<div onclick="remove();" data-options="iconCls:'icon-remove'">Delete</div>
		<div onclick="edit();" data-options="iconCls:'icon-edit'">Edit</div>
	</div>
</body>
</html>