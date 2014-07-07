<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="padding: 5px;overflow: hidden;">
	<form id="docForm"  enctype="multipart/form-data"  method="post">
		<input name="cid" type="hidden" />
		<table class="tableForm">
			<tr>
				<td>Choose a manual to upload</td>
			</tr>
			<tr>
				<td><input type="file" name="uploadFile" value="Choose file"></td>
			</tr>
		</table>
	</form>
</div>