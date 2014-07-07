<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	function logout() {
		$('#sessionInfoDiv').html('');
		$.post('${pageContext.request.contextPath}/userController/doNotNeedSession_logout', function() {
				loginDialog.dialog('open');
		});
	}
</script>
<div id="sessionInfoDiv" style="position: absolute;right: 80px;top:15px;">
	<c:if test="${sessionInfo.userId != null}">
		Welcome [<strong>${sessionInfo.loginName}</strong>] [<strong>${sessionInfo.ip}</strong>]
	</c:if>
</div>
<div id="logoutDiv" style="position: absolute;right: 5px;top:10px;">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-no',plain:true" onclick="logout();">Logout</a>
</div>