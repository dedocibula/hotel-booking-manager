<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
 
<s:layout-render name="/layout.jsp" nadpis="Test">
    <s:layout-component name="content">
		<s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.TestActionBean" var="actionBean"/>
		Test
    </s:layout-component>
</s:layout-render>