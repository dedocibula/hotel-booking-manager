<%--
    Document   : hotelRooms
    Created on : 24.11.2012, 19:03:41
    Author     : Filip Bogyai
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<fmt:message var="pageTitle" key="room.pageTitle"/>
<s:layout-render name="/layout.jsp" title="${pageTitle}" pageInfo="room.jsp">
    <sec:authorize access="hasRole('ROLE_ADMIN')">
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
    </sec:authorize>
    <s:layout-component name="right_content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" var="actionBean"/>

            <div class="content_right_section">
                    <div class="content_title_01"><sec:authorize access="hasRole('ROLE_ADMIN')"><fmt:message key="roomManagement"/></sec:authorize><sec:authorize access="hasRole('ROLE_USER')"><fmt:message key="roomOverview"/></sec:authorize></div>
                    <img src="${pageContext.request.contextPath}/images/templatemo_image_04.jpg" alt="image" />
                    <sec:authorize access="hasRole('ROLE_ADMIN')"><fmt:message key="roomManagementDescription"/></sec:authorize><sec:authorize access="hasRole('ROLE_USER')"><fmt:message key="roomManagementDescriptionUser"/></sec:authorize>
                </div>

                <div class="cleaner_h40">&nbsp;</div>


                <div class="content_right_section">
                    <div class="content_title_02"><fmt:message key="allRooms"/></div>
                    <s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" event="backToHotels"><fmt:message key="backToHotels"/></s:link>              
                    <div class="cleaner_h20"></div>
                    <c:choose>
                        <c:when test="${empty actionBean.hotelRooms}">
                            <p class="emptyDb"><fmt:message key="noRooms"/></p>
                        </c:when>
                        <c:otherwise>
                            <table id="table">
                                <thead>
                                    <th><fmt:message key="id"/></th>
                                    <th><fmt:message key="room.roomType"/></th>
                                    <th><fmt:message key="room.pricePerNight"/></th>
                                    <th><fmt:message key="room.hotel"/></th>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <th></th>
                                    <th></th>
                                    </sec:authorize>
                                </thead>
                                <c:forEach items="${actionBean.hotelRooms}" var="room">
                                    <tr>
                                        <td>${room.id}</td>
                                        <td><c:out value="${room.roomType}"/></td>
                                             <td><c:out value="${room.pricePerNight}"/></td>
                                             <td><c:out value="${room.hotel.name}"/></td>
                                             <sec:authorize access="hasRole('ROLE_ADMIN')">
                                             <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" event="edit"><s:param name="room.id" value="${room.id}"/><img src="${pageContext.request.contextPath}/images/edit_icon.png" alt="edit" /></s:link> </td>
                                             <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" event="delete" class="delete"><s:param name="room.id" value="${room.id}"/><img src="${pageContext.request.contextPath}/images/remove_icon.png" alt="delete" /></s:link> </td>
                                             </sec:authorize>
                                             </tr>
                                </c:forEach>
                            </table>
                        </c:otherwise>                            
                    </c:choose>              
            </div>
    </s:layout-component>
</s:layout-render>

