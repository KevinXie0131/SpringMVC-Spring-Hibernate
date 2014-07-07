<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div align="center" style="padding: 5px;overflow: hidden;">
	<form method="post">
		<input name="cid" type="hidden" />
		<table class="tableForm">
			<tr>
				<th style="width: 55px;">Role</th>
				<td><input name="cname" class="easyui-validatebox" data-options="required:true,missingMessage:'name required'" style="width:323px;" />
				</td>
			</tr>
			<tr>
				<th>Authority</th>
				<td><input name="authIds" style="width:327px;" />
				</td>
			</tr>
			<tr>
				<th>Note</th>
				<td><textarea name="cdesc" style="height: 100px;"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>