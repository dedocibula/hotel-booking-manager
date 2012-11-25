<%--
    Document   : hotelRooms
    Created on : 24.11.2012, 19:03:41
    Author     : Filip Bogyai
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:message var="pageTitle" key="room.pageTitle"/>
<s:layout-render name="/layout.jsp" title="${pageTitle}" pageInfo="showRoom.jsp">
    <s:layout-component name="left_content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" var="actionBean"/>

            <div class="content_left_section">
            	<div class="content_title_01"><fmt:message key="newRoom"/></div>

                <s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean">
                    <%@include file="formRoom.jsp"%>
                    <div class="cleaner_h20">&nbsp;</div>
                    <s:submit name="add"><fmt:message key="addRoom"/></s:submit>
                </s:form>

                <div class="cleaner">&nbsp;</div>
            </div> <!-- end of booking -->

            <div class="cleaner_h30">&nbsp;</div>
            <div class="cleaner_horizontal_divider_01">&nbsp;</div>
            <div class="cleaner_h30">&nbsp;</div>

    </s:layout-component>

    <s:layout-component name="right_content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" var="actionBean"/>

            <div class="content_right_section">
                    <div class="content_title_01"><fmt:message key="roomManagement"/></div>
                    <img src="${pageContext.request.contextPath}/images/templatemo_image_04.jpg" alt="image" />
                    <fmt:message key="roomManagementDescription"/>
                </div>

                <div class="cleaner_h40">&nbsp;</div>


                <div class="content_right_section">
                    <div class="content_title_02"><fmt:message key="allRooms"/></div>
                    <c:choose>
                        <c:when test="${empty actionBean.hotelRooms}">
                            <p class="emptyDb"><fmt:message key="noRooms"/></p>
                        </c:when>
                        <c:otherwise>
                            <table>
                                <tr>
                                    <th><fmt:message key="id"/></th>
                                    <th><fmt:message key="room.pricePerNight"/></th>
                                    <th><fmt:message key="room.hotel"/></th>

                                    <th></th>
                                    <th></th>
                                </tr>
                                <c:forEach items="${actionBean.hotelRooms}" var="room">
                                    <tr>
                                        <td>${room.id}</td>
                                             <td><c:out value="${room.pricePerNight}"/></td>
                                             <td><c:out value="${room.hotel.name}"/></td>
                                             <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" event="edit"><s:param name="room.id" value="${room.id}"/><fmt:message key="edit"/></s:link> </td>
                                             <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" event="delete"><s:param name="room.id" value="${room.id}"/><fmt:message key="delete"/></s:link> </td>
                                             </tr>
                                </c:forEach>
                            </table>
                        </c:otherwise>
                    </c:choose>
            </div>
    </s:layout-component>
</s:layout-render>

