<%-- 
    Document   : editClient
    Created on : Nov 24, 2012, 10:31:07 PM
    Author     : Andrej Galád
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout.jsp" title="Booking Manager - Client" pageInfo="client.jsp">
    <s:layout-component name="content">
       <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ClientsActionBean" var="actionBean"/>
 
       <s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ClientsActionBean">
           <s:hidden name="client.id"/>
            <fieldset><legend>Change Client Attributes</legend>
                <%@include file="formClient.jsp"%>
            <s:submit name="save">Save</s:submit>
            </fieldset>
        </s:form>
 
    </s:layout-component>
</s:layout-render>
