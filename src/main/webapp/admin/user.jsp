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
			url : '${pageContext.request.contextPath}/userController/datagrid',
			title : 'Userlist',
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
				title : 'Username',
				field : 'cname',
				width : 150,
				sortable : true
			} ] ],
			columns : [ [ {
				title : 'Password',
				field : 'cpwd',
				width : 100,
				formatter : function(value, rowData, rowIndex) {
					return '******';
				}
			}, {
				title : 'Created datetime',
				field : 'ccreatedatetime',
				sortable : true,
				width : 150
			}, {
				title : 'Modified datetime',
				field : 'cmodifydatetime',
				sortable : true,
				width : 150
			}, {
				title : 'RoleID',
				field : 'roleIds',
				width : 150,
				hidden : true
			}, {
				title : 'Role',
				field : 'roleNames',
				width : 150
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
				text : 'Batch edit role',
				iconCls : 'icon-edit',
				handler : function() {
					editRole();
				}
			}, '-', {
				text : 'Unselect',
				iconCls : 'icon-undo',
				handler : function() {
					datagrid.datagrid('clearSelections');
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
			title : 'Add user',
			href : '${pageContext.request.contextPath}/userController/userAdd',
			width : 500,
			height : 200,
			buttons : [ {
				text : 'Add',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/userController/add',
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
				var roleIds = f.find('input[name=roleIds]');
				var roleIdsCombobox = roleIds.combobox({
					url : '${pageContext.request.contextPath}/roleController/doNotNeedSession_combobox',
					valueField : 'cid',
					textField : 'cname',
					multiple : true,
					editable : false,
					panelHeight : 'auto'
				});
			}
		});
	}
	
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = parent.sy.dialog({
				title : 'Edit user',
				href : '${pageContext.request.contextPath}/userController/userEdit',
				width : 500,
				height : 200,
				buttons : [ {
					text : 'Edit',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/userController/edit',
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
					var roleIds = f.find('input[name=roleIds]');
					var roleIdsCombobox = roleIds.combobox({
						url : '${pageContext.request.contextPath}/roleController/doNotNeedSession_combobox',
						valueField : 'cid',
						textField : 'cname',
						multiple : true,
						editable : false,
						panelHeight : 'auto',
						onLoadSuccess : function() {
							parent.$.messager.progress('close');
						}
					});
					f.form('load', {
						cid : rows[0].cid,
						cname : rows[0].cname,
						roleIds : sy.getList(rows[0].roleIds),
						ccreatedatetime : rows[0].ccreatedatetime,
			//			cmodifydatetime : rows[0].cmodifydatetime
					});
				}
			});
		} 
		else {
			parent.sy.messagerAlert('Message', 'Please select one to edit', 'error');
		}
	}
	
	function remove() {
		var rows = datagrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.sy.messagerConfirm('Confirm', 'Are you sure to delete', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].cid);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/userController/delete',
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
	
	function editRole() {
		var rows = datagrid.datagrid('getSelections');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].cid);
			}
			var p = parent.sy.dialog({
				title : 'Batch edit role',
				href : '${pageContext.request.contextPath}/userController/userRoleEdit',
				width : 250,
				height : 130,
				buttons : [ {
					text : 'Edit',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/userController/roleEdit',
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
					var idsInput = f.find('input[name=ids]');
					var roleIds = f.find('input[name=roleIds]');
					var roleIdsCombobox = roleIds.combobox({
						url : '${pageContext.request.contextPath}/roleController/doNotNeedSession_combobox',
						valueField : 'cid',
						textField : 'cname',
						multiple : true,
						editable : false,
						panelHeight : 'auto',
						onLoadSuccess : function() {
							f.form('load', {
								ids : ids.join(',')
							});
						}
					});
				}
			});
		} else {
			parent.sy.messagerAlert('Message', 'Please select one to edit', 'error');
		}
	}
	
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

	<div id="menu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="append();" data-options="iconCls:'icon-add'">Add</div>
		<div onclick="remove();" data-options="iconCls:'icon-remove'">Delete</div>
		<div onclick="edit();" data-options="iconCls:'icon-edit'">Edit</div>
	</div>
</body>
</html>