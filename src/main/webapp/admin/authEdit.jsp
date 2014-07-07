<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="padding: 5px;overflow: hidden;">
	<form method="post">
		<input name="cid" type="hidden" />
		<table class="tableForm">
			<tr>
				<th style="width: 70px;">Authority</th>
				<td><input name="cname" class="easyui-validatebox" data-options="required:true,missingMessage:'name required'" style="width: 155px;" />
				</td>
				<th style="width: 50px;">Sequence</th>
				<td><input name="cseq" class="easyui-numberspinner" data-options="min:0,max:999,editable:false,required:true,missingMessage:'sequence required'" value="10" style="width:120px;" />
				</td>
			</tr>
			<tr>
				<th>Address</th>
				<td colspan="3"><input name="curl" style="width:98%;" />
				</td>
			</tr>
			<tr>
				<th>Note</th>
				<td colspan="3"><input name="cdesc" style="width:98%;" />
				</td>
			</tr>
			<tr>
				<th>Parent</th>
				<td colspan="3"><input name="pid" style="width: 335px;" />
				</td>
			</tr>
		</table>
	</form>
</div>