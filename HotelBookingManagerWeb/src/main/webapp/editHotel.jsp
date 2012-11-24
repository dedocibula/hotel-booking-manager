<%-- 
    Document   : editHotel
    Created on : 22.11.2012, 22:58:14
    Author     : Filip Bogyai
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout.jsp" nadpis="Editace hotelu">
    <s:layout-component name="content">
       <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" var="actionBean"/>
 
       <s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean">
           <s:hidden name="hotel.id"/>
            <fieldset><legend>Změna údajů</legend>
                <%@include file="formHotel.jsp"%>
            <s:submit name="save">Uložit</s:submit>
            </fieldset>
        </s:form>
 
    </s:layout-component>
</s:layout-render>
