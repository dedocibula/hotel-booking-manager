<%-- 
    Document   : showHotel
    Created on : 22.11.2012, 22:58:03
    Author     : Filip Bogyai
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
 
<s:layout-render name="/layout.jsp" nadpis="Hotels">
    <s:layout-component name="content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" var="actionBean"/>
        Všechny hotely:
        <table>
            <tr>
                <th>id</th>
                <th>name</th>
                <th>phone</th>
                <th>email</th>
                <th>address</th>
                <th>city</th>
                <th>country</th>
                <th>rooms</th>
                <th>zmazat</th>
                <th>editovat</th>
            </tr>
            <c:forEach items="${actionBean.hotels}" var="hotel">
                <tr>
                    <td>${hotel.id}</td>
                   <td><c:out value="${hotel.name}"/></td>
                    <td><c:out value="${hotel.contact.phone}"/></td>
                    <td><c:out value="${hotel.contact.email}"/></td>
                    <td><c:out value="${hotel.contact.address}"/></td>
                    <td><c:out value="${hotel.contact.city}"/></td>
                    <td><c:out value="${hotel.contact.country}"/> </td>
                    <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" event="rooms"><s:param name="hotel.id" value="${hotel.id}"/>sprava</s:link> </td>
                    <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" event="delete"><s:param name="hotel.id" value="${hotel.id}"/>smazat</s:link> </td>
                    <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" event="edit"><s:param name="hotel.id" value="${hotel.id}"/>edit</s:link> </td>
    
                </tr>
            </c:forEach> 
        </table>
           
        <s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean">
            <fieldset><legend>Nový hotel</legend>
                <%@include file="formHotel.jsp"%>
            <s:submit name="add">Vytvořit nový hotel</s:submit>
            </fieldset>
        </s:form>
     
    </s:layout-component>
</s:layout-render>
