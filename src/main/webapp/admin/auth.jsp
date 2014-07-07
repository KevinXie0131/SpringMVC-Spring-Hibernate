<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../include.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var treegrid;
	$(function() {

		treegrid = $('#treegrid').treegrid({
			url : '${pageContext.request.contextPath}/authController/treegrid',
			toolbar : [ {
				text : 'Expand',
				iconCls : 'icon-redo',
				handler : function() {
					var node = treegrid.treegrid('getSelected');
					if (node) {
						treegrid.treegrid('expandAll', node.cid);
					} else {
						treegrid.treegrid('expandAll');
					}
				}
			}, '-', {
				text : 'Collapse',
				iconCls : 'icon-undo',
				handler : function() {
					var node = treegrid.treegrid('getSelected');
					if (node) {
						treegrid.treegrid('collapseAll', node.cid);
					} else {
						treegrid.treegrid('collapseAll');
					}
				}
			}, '-', {
				text : 'Reload',
				iconCls : 'icon-reload',
				handler : function() {
					treegrid.treegrid('reload');
				}
			}, '-', {
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
				iconCls : 'icon-back',
				handler : function() {
					treegrid.treegrid('unselectAll');
				}
			}, '-' ],
			title : '',
			iconCls : 'icon-save',
			fit : true,
			fitColumns : false,
			nowrap : false,
			animate : false,
			border : false,
			idField : 'cid',
			treeField : 'cname',
			frozenColumns : [ [ {
				title : 'cid',
				field : 'cid',
				width : 150,
				hidden : true
			}, {
				field : 'cname',
				title : 'Name',
				width : 180,
				formatter : function(value) {
					if (value) {
						return sy.fs('<span title="{0}">{1}</span>', value, value);
					}
				}
			} ] ],
			columns : [ [ {
				field : 'curl',
				title : 'Address',
				width : 250,
				formatter : function(value) {
					if (value) {
						return sy.fs('<span title="{0}">{1}</span>', value, value);
					}
				}
			}, {
				field : 'cdesc',
				title : 'Description',
				width : 250,
				formatter : function(value) {
					if (value) {
						return sy.fs('<span title="{0}">{1}</span>', value, value);
					}
				}
			}, {
				field : 'cseq',
				title : 'Sequence',
				width : 150
			}, {
				field : 'pid',
				title : 'Parent ID',
				width : 150,
				hidden : true
			}, {
				field : 'pname',
				title : 'Parent Name',
				width : 150
			} ] ],
			onContextMenu : function(e, row) {
				e.preventDefault();
				$(this).treegrid('unselectAll');
				$(this).treegrid('select', row.cid);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			},
			onLoadSuccess : function(row, data) {
				/*var t = $(this);
				if (data) {
					$(data).each(function(index, d) {
						if (this.state == 'closed') {
							t.treegrid('expandAll');
						}
					});
				}*/
			},
			onExpand : function(row) {
				treegrid.treegrid('unselectAll');
			},
			onCollapse : function(row) {
				treegrid.treegrid('unselectAll');
			}
		});

	});

	function append() {
		var p = parent.sy.dialog({
			title : 'Add authority',
			href : '${pageContext.request.contextPath}/authController/authAdd',
			width : 500,
			height : 200,
			buttons : [ {
				text : 'Add',
				handler : function() {
					var f = p.find('form');
					f.form('submit', {
						url : '${pageContext.request.contextPath}/authController/add',
						success : function(d) {
							var json = $.parseJSON(d);
							if (json.success) {
								treegrid.treegrid('reload');
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
				var pid = f.find('input[name=pid]');
				var ptree = pid.combotree({
					lines : true,
					url : '${pageContext.request.contextPath}/authController/doNotNeedSession_tree'
				});
			}
		});
	}
	
	function edit() {
		var node = treegrid.treegrid('getSelected');
		if (node) {
			var p = parent.sy.dialog({
				title : 'Edit authority',
				href : '${pageContext.request.contextPath}/authController/authEdit',
				width : 500,
				height : 200,
				buttons : [ {
					text : 'Edit',
					handler : function() {
						var f = p.find('form');
						f.form('submit', {
							url : '${pageContext.request.contextPath}/authController/edit',
							success : function(d) {
								var json = $.parseJSON(d);
								if (json.success) {
									treegrid.treegrid('reload');
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
					var pid = f.find('input[name=pid]');
					var ptree = pid.combotree({
						lines : true,
						url : '${pageContext.request.contextPath}/authController/doNotNeedSession_treeRecursive',
						onLoadSuccess : function() {
							parent.$.messager.progress('close');
						}
					});
					f.form('load', node);
				}
			});
		} else {
			parent.sy.messagerAlert('Message', 'Please select one to edit', 'error');
		}
	}
	
	function remove() {
		var node = treegrid.treegrid('getSelected');
		if (node) {
			parent.sy.messagerConfirm('Confirm', 'Are you sure to delete [' + node.cname + ']?', function(b) {
				if (b) {
					$.ajax({
						url : '${pageContext.request.contextPath}/authController/delete',
						data : {
							cid : node.cid
						},
						cache : false,
						dataType : 'JSON',
						success : function(r) {
							if (r.success) {
								treegrid.treegrid('remove', r.obj);
							}
							parent.sy.messagerShow({
								msg : r.msg,
								title : 'Message'
							});
						}
					});
				}
			});
		}
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false" style="overflow:hidden;">
		<table id="treegrid"></table>
	</div>

	<div id="menu" class="easyui-menu" style="width:120px;display:none;">
		<div onclick="append();" data-options="iconCls:'icon-add'">Add</div>
		<div onclick="remove();" data-options="iconCls:'icon-remove'">Delete</div>
		<div onclick="edit();" data-options="iconCls:'icon-edit'">Edit</div>
	</div>
</body>
</html>