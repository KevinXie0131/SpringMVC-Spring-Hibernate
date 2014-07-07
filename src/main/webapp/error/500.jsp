<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP '500.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<STYLE type=text/css>.font14 {
		FONT-SIZE: 14px
	}
	.font12 {
		FONT-SIZE: 12px
	}
	.font12 a{
		FONT-SIZE: 12px; color: #CC0000; text-decoration:none;
	}
	</STYLE>
  </head>
  
  <body>
    <TABLE height=500 cellSpacing=0 cellPadding=0 width=500 align=center background=x.gif border=0>
	  	<TBODY>
	  		<TR>
	    		<TD height=330></TD>
	    	</TR>
	  		<TR>
	    		<TD vAlign=top>
	      		<DIV class=font14 align=center>
	      			<STRONG>The page you visit<FONT color=#0099ff>does not exist</FONT></STRONG>
	      		</DIV>
	      		</TD>
	      	</TR>
	     </TBODY>
     </TABLE>
  </body>
</html>
