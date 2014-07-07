<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	var regDialog;
	var regForm;
	$(function() {

		regDialog = $('#regDialog').show().dialog({
			title : 'Register',
			modal : true,
			buttons : [ {
				text : 'Register',
				handler : function() {
					regForm.submit();
				}
			} ],
			onOpen : function() {
				$(this).find('input[name=cname]').focus();
			},
			onClose : function() {
			//	$(this).find('input').val('');
				loginDialog.find('input[name=cname]').focus();
			}
		}).dialog('close');

		regForm = regDialog.find('form').form({
			url : '${pageContext.request.contextPath}/userController/doNotNeedSession_reg',
			success : function(data) {
				var obj = jQuery.parseJSON(data);
				if (obj.success) {
					regDialog.dialog('close');
				} else {
					regDialog.find('input[name=cname]').focus();
				}
				$.messager.show({
					title : 'Message',
					msg : obj.msg
				});
			}
		});
		
		$('form input').bind('keyup', function(event) {/* enter to submit */
			if (event.keyCode == '13') {
				$(this).submit();
			}
		});

	});
</script>
<div id="regDialog" style="display: none;width:250px;padding: 5px;">
	<form method="post">
		<table class="tableForm">
			<tr>
				<th style="width: 55px;">Username</th>
				<td><input name="cname" class="easyui-validatebox" data-options="required:'true',missingMessage:'Username required'" value="admin" /></td>
			</tr>
			<tr>
				<th>Password</th>
				<td><input name="cpwd" type="password" class="easyui-validatebox" data-options="required:'true',missingMessage:'Password required'" value="admin" /></td>
			</tr>
			<tr>
				<th>Reenter</th>
				<td><input type="password" class="easyui-validatebox" data-options="required:'true',missingMessage:'Password required',validType:'eqPassword[\'#regDialog input[name=cpwd]\']'" /></td>
			</tr>
		</table>
	</form>
</div>