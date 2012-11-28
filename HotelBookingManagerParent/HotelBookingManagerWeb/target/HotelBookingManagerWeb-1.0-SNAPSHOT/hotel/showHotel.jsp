<%--
    Document   : showHotel
    Created on : 22.11.2012, 22:58:03
    Author     : Filip Bogyai
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:message var="pageTitle" key="hotel.pageTitle"/>
<s:layout-render name="/layout.jsp" title="${pageTitle}" pageInfo="showHotel.jsp">
    <s:layout-component name="left_content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" var="actionBean"/>

            <div class="content_left_section">
            	<div class="content_title_01"><fmt:message key="newHotel"/></div>

                <s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean">
                    <%@include file="formHotel.jsp"%>
                    <div class="cleaner_h20">&nbsp;</div>
                    <s:submit name="add"><fmt:message key="addHotel"/></s:submit>
                </s:form>

                <div class="cleaner">&nbsp;</div>
            </div> <!-- end of booking -->

            <div class="cleaner_h30">&nbsp;</div>
            <div class="cleaner_horizontal_divider_01">&nbsp;</div>
            <div class="cleaner_h30">&nbsp;</div>

    </s:layout-component>

    <s:layout-component name="right_content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" var="actionBean"/>

            <div class="content_right_section">
                    <div class="content_title_01"><fmt:message key="hotelManagement"/></div>
                    <img src="${pageContext.request.contextPath}/images/templatemo_image_04.jpg" alt="image" />
                    <fmt:message key="hotelManagementDescription"/>
                </div>

                <div class="cleaner_h40">&nbsp;</div>


                <div class="content_right_section">
                    <div class="content_title_02"><fmt:message key="allHotels"/></div>
                    <c:choose>
                        <c:when test="${empty actionBean.hotels}">
                            <p class="emptyDb"><fmt:message key="noHotels"/></p>
                        </c:when>
                        <c:otherwise>
                            <table>
                                <tr>
                                    <th><fmt:message key="id"/></th>
                                    <th><fmt:message key="hotel.name"/></th>
                                    <th><fmt:message key="hotel.phone"/></th>
                                    <th><fmt:message key="hotel.email"/></th>
                                    <th><fmt:message key="hotel.address"/></th>
                                    <th><fmt:message key="hotel.city"/></th>
                                    <th><fmt:message key="hotel.country"/></th>
                                    <th><fmt:message key="hotel.rooms"/></th>
                                    <th></th>
                                    <th></th>
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
                                        <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" event="rooms"><s:param name="hotel.id" value="${hotel.id}"/><fmt:message key="hotel.rooms"/></s:link> </td>
                                        <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" event="edit"><s:param name="hotel.id" value="${hotel.id}"/><fmt:message key="edit"/></s:link> </td>
                                        <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" event="delete"><s:param name="hotel.id" value="${hotel.id}"/><fmt:message key="delete"/></s:link> </td>

                                    </tr>
                                </c:forEach>
                            </table>
                        </c:otherwise>
                    </c:choose>
            </div>
    </s:layout-component>
</s:layout-render>

