<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="padding: 5px;overflow: hidden;">
	<form id="docForm" method="post">
		<input name="cid" type="hidden" />
		<table class="tableForm">
			<tr>
				<th style="width: 80px;">Model</th>
				<td><input name="cmodel" class="easyui-validatebox" data-options="required:'true',missingMessage:'Model required'" style="width: 150px;" />
				</td>
				<th style="width: 80px;">Name</th>
				<td><input name="cname" style="width: 150px;" />
				</td>
			</tr>
			<tr>
				<th>Producer</th>
				<td><input name="cproducer" style="width: 150px;" />
				</td>
				<th>Number</th>
				<td><input name="cno" style="width: 40px;" class="easyui-validatebox" validType="checkNumber" />
				</td>
			</tr>
		</table>
	</form>
</div>