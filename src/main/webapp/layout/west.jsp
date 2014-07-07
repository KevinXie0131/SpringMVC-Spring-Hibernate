<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	var ctrlTree;
	$(function() {

		ctrlTree = $('#ctrlTree').tree({
			url : '${pageContext.request.contextPath}/menuController/doNotNeedSession_tree',
			lines : true,
			onLoadSuccess : function(node, data){			
				$(this).tree('expandAll') 
			},
			onClick : function(node) {
				if (node.attributes.url) {
					addTab(node);
				}
			},
			onDblClick : function(node) {
				if (node.state == 'closed') {
					$(this).tree('expand', node.target);
				} else {
					$(this).tree('collapse', node.target);
				}
			}
		});

	});
</script>
<div class="easyui-accordion" data-options="fit:true,border:false">
	<div title="Menu" data-options="isonCls:'icon-save'">
		<ul id="ctrlTree" style="margin-top: 5px;"></ul>
	</div>
</div>