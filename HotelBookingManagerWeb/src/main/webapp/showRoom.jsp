<%-- 
    Document   : hotelRooms
    Created on : 24.11.2012, 19:03:41
    Author     : Filip Bogyai
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
 
<s:layout-render name="/layout.jsp" nadpis="Hotel rooms">
    <s:layout-component name="content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" var="actionBean"/>
        Všechny izby hotela:
        <table>
            <tr>
                <th>id </th>
                <th>price </th>
                <th>hotel </th>                
            </tr>
            <c:forEach items="${actionBean.hotelRooms}" var="room">
                <tr>
                    <td>${room.id}</td>
                    <td><c:out value="${room.pricePerNight}"/></td>
                    <td><c:out value="${room.hotel.name}"/></td>                   
                    <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" event="delete"><s:param name="room.id" value="${room.id}"/>smazat</s:link> </td>
                    <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" event="edit"><s:param name="room.id" value="${room.id}"/>edit</s:link> </td>
    
                </tr>
            </c:forEach> 
                        
        </table>
        <s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" event="backToHotels">spet na spravu hotelov</s:link>
        <s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean">
            <fieldset><legend>Nová izba</legend>
                <%@include file="formRoom.jsp"%>
            <s:submit name="add">Vytvořit novú izbu</s:submit>
            </fieldset>
        </s:form>
     
    </s:layout-component>
</s:layout-render>
