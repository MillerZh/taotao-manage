<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String callback = request.getParameter("callback");
	if (null != callback && !callback.isEmpty()){
		out.print(callback + "({\"abc\":123})");
	} else {
		out.print("fun({\"abc\":123})");
	}
%>