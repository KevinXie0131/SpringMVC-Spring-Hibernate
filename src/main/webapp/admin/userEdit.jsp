<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="padding: 5px;overflow: hidden;">
	<form id="userForm" method="post">
		<input name="cid" type="hidden" />
		<table class="tableForm">
			<tr>
				<th style="width: 80px;">Username</th>
				<td><input name="cname" class="easyui-validatebox" data-options="required:'true',missingMessage:'Username required'" style="width: 150px;" />
				</td>
				<th style="width: 80px;">Role</th>
				<td><input name="roleIds" style="width: 150px;" />
				</td>
			</tr>
			<tr>
				<th>Password</th>
				<td><input name="cpwd" type="password" class="easyui-validatebox" data-options="required:'true',missingMessage:'Password required'" />
				</td>
				<th>Reenter</th>
				<td><input type="password" class="easyui-validatebox" data-options="required:'true',missingMessage:'Password required',validType:'eqPassword[\'#userForm input[name=cpwd]\']'" />
				</td>
			</tr>
		</table>
		<input name="ccreatedatetime" type="hidden" />
	</form>
</div>