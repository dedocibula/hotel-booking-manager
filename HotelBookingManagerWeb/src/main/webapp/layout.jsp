<%-- 
    Document   : layout
    Created on : 21.11.2012, 2:09:13
    Author     : Marian Rusnak
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<s:layout-definition>
	<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title><c:out value="${nadpis}" /></title>
			<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css" />
			<s:layout-component name="head"/>
		</head>
		<body>
			<div id="banner">
				<ul>
					<li><s:link href="/index.jsp">Home</s:link></li>
					<li><s:link href="/test/home">Test</s:link></li>
				</ul>
			</div>
			<div id="content">
				<s:layout-component name="content"/>
			</div>
		</body>
	</html>
</s:layout-definition>
