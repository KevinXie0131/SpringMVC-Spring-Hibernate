<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	var loginDialog;
	var loginInputForm;
	var sessionInfo;
	$(function() {

		loginInputForm = $('#loginInputForm').form({
			url : '${pageContext.request.contextPath}/userController/doNotNeedSession_login',
			success : function(data) {
				var obj = jQuery.parseJSON(data);
				if (obj.success) {
					loginDialog.dialog('close');
					
					$('#sessionInfoDiv').html(sy.fs(
						'Welcome [<strong>{0}</strong>] [<strong>{1}</strong>]', obj.obj.loginName, obj.obj.ip)
					);			
					
					flashOnlineDatagrid();
				}
				$.messager.show({
					title : 'Message',
					msg : obj.msg
				});
			}
		});

		loginDialog = $('#loginDialog').show().dialog({
			closable : false,
			title : 'Logon',
			modal : true,
			buttons : [ {
				text : 'Register',
				handler : function() {
					regDialog.val('');
					regDialog.dialog('open');
				}
			}, {
				text : 'Login',
				handler : function() {
					loginInputForm.submit();
				}
			} ],
			onOpen : function() {
				var t = $(this);
				window.setTimeout(function() {
					t.find('input[name=cname]').focus();
				}, 0);
			}
		});

		$('form input').bind('keyup', function(event) {/* enter to submit */
			if (event.keyCode == '13') {
				$(this).submit();
			}
		});

		window.setTimeout(function() {
			$('form input[name=cname]').focus();
		}, 0);
		
		sessionInfo = {
			userId : '${sessionInfo.userId}',
			loginName : '${sessionInfo.loginName}',
			ip : '${sessionInfo.ip}'
		};
		if (sessionInfo.userId) {/*if userId exists, the user has logoned*/
			window.setTimeout(function() {
				$('div.validatebox-tip').remove();
			}, 500);
			loginDialog.dialog('close');
			flashOnlineDatagrid();
		}

	});

	function flashOnlineDatagrid() {/*refresh online user*/
		window.setTimeout(function() {
			if ($('#onlineDatagrid').length > 0) {
				onlineDatagrid.datagrid({
					pagination : true,
					url : '${pageContext.request.contextPath}/onlineController/doNotNeedSession_onlineDatagrid'
				});
				var p = onlineDatagrid.datagrid('getPager');
				p.pagination({
					showPageList : false,
					showRefresh : false,
					beforePageText : '',
					afterPageText : '/{pages}',
					displayMsg : ''
				});
			}
		}, 3000);
	}
</script>
<div id="loginDialog" style="width:250px;height:160px;display: none;overflow: hidden;">
	<div style="color:blue;">Default account: admin/admin</div>
	<div id="loginTabs" data-options="fit:true,border:false">
		<div align="center">
			<form id="loginInputForm" method="post">
				<table>
					<tr>
						<th style="text-align: right;">Username</th>
						<td><input name="cname" class="easyui-validatebox" data-options="required:true,missingMessage:'Username required'" value="admin" style="width: 150px;" /></td>
					</tr>
					<tr>
						<th style="text-align: right;">Password</th>
						<td><input name="cpwd" type="password" class="easyui-validatebox" data-options="required:true,missingMessage:'Password required'" value="admin" style="width: 150px;" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>