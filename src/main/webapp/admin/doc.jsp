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
			url : '${pageContext.request.contextPath}/docController/datagrid',
			title : 'Document list',
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
			sortName : 'cid',
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
				title : 'Model',
				field : 'cmodel',
				width : 100,
				sortable : true
			} ] ],
			columns : [ [ {
				title : 'Name',
				field : 'cname',
				width : 280,
				sortable : true
			}, {
				title : 'Producer',
				field : 'cproducer',
				width : 130,	
				sortable : true
			}, {
				title : 'Number',
				field : 'cno',
				sortable : true,
				width : 60
			}, {
				title : 'Manual',
				field : 'cmanual',
				sortable : true,
				width : 200
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
			title : 'Add document',
			href : '${pageContext.request.contextPath}/docController/docAdd',
			width : 500,
			height : 200,
			buttons : [ {
				text : 'Add',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/docController/add',
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
			} ]
		});
	}
	
	function edit() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = parent.sy.dialog({
				title : 'Edit document',
				href : '${pageContext.request.contextPath}/docController/docEdit',
				width : 500,
				height : 200,
				buttons : [ {
					text : 'Edit',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/docController/edit',
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
					f.form('load', {
						cid : rows[0].cid,
						cmodel : rows[0].cmodel,
						cname : rows[0].cname,
						cproducer : rows[0].cproducer,
						cno : rows[0].cno					
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
						url : '${pageContext.request.contextPath}/docController/delete',
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
	
	function equipSearch() {
		datagrid.datagrid('load', sy.serializeObject($('#searchForm')));
	}
	
	function clearAndSearch() {
		datagrid.datagrid('load', {});
		$('#searchForm input').val('');
	}
	
	function Upload() {
		var rows = datagrid.datagrid('getSelections');
		if (rows.length == 1) {
			var p = parent.sy.dialog({
				title : 'Upload manual',
				href : '${pageContext.request.contextPath}/docController/docUpload',
				width : 400,
				height : 160,
				buttons : [ {
					text : 'Upload',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/docController/upload',
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
					f.form('load', {
						cid : rows[0].cid,	
					});
				}
			});
		} 
		else {
			parent.sy.messagerAlert('Message', 'Please select one to upload manual', 'error');
		}
	}

</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false,title:'Search'" style="height: 60px;overflow: hidden;" align="left">
		<form id="searchForm">
			<strong style="margin-left:10px;"> Name: </strong><input name="cname" style="width:100px;" /></td>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="equipSearch();">Search</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="clearAndSearch();">Clear</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-help',plain:true" onclick="Upload();">Upload Manual</a>
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