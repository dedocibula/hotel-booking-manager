<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<f:message var="pageTitle" key="reservation.pageTitle"/>
<s:layout-render name="/layout.jsp" title="${pageTitle}" pageInfo="reservation.jsp">
    <sec:authorize access="hasRole('ROLE_ADMIN')">
	<s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean" var="actionBean"/>
	<s:layout-component name="left_content">

		<div class="content_left_section">
			<div class="content_title_01"><f:message key="selectDateInterval"/></div>

			<s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean">
				<s:errors />
				<%@include file="/../components/dateChoose.jsp" %>
				<div>
					<div>
						<s:label for="hotel" name="hotel"/>
					</div>
					<div>
						<s:select id="hotel" name="hotel.id">
							<option value="-1">- <f:message key="any"/> -</option>
							<s:options-collection collection="${actionBean.hotels}" value="id" label="name" />
						</s:select>
					</div>
				</div>
				<div class="cleaner_h20">&nbsp;</div>
				<s:submit name="all"><f:message key="submit"/></s:submit>
			</s:form>

			<div class="cleaner">&nbsp;</div>
		</div>

		<div class="cleaner_h30">&nbsp;</div>
		<div class="cleaner_horizontal_divider_01">&nbsp;</div>
		<div class="cleaner_h30">&nbsp;</div>

    </s:layout-component>
</sec:authorize>
    <s:layout-component name="right_content">

		<div class="content_right_section">
			<div class="content_title_01"><f:message key="reservationManagement"/></div>
			<img src="${pageContext.request.contextPath}/images/templatemo_image_04.jpg" alt="image" />
			<p><f:message key="reservationManagementDescription"/></p>
		</div>

		<div class="cleaner_h40">&nbsp;</div>

		<div class="content_right_section">
			<div class="content_title_02"><f:message key="newReservation"/></div>
                        <p><f:message key="addReservationButtonDescription"/></p>
			<div class="rc_btn_02"><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean" event="create"><f:message key="create"/></s:link></div>
			</div>

			<div class="cleaner_h40">&nbsp;</div>


			<div class="content_right_section">
				<div class="content_title_02"><f:message key="reservations"/></div>

			<c:choose>
				<c:when test="${empty actionBean.reservations}">
					<p class="emptyDb"><f:message key="noReservations"/></p>
				</c:when>
				<c:otherwise>
					<table id="table">
						<thead>
						<th><f:message key="client"/></th>
						<th><f:message key="room"/></th>
						<th><f:message key="dateFrom"/></th>
						<th><f:message key="dateTo"/></th>
						<th></th>
						</thead>
						<c:forEach items="${actionBean.reservations}" var="reservation">
							<tr>
								<td>${reservation.client.firstName} ${reservation.client.lastName}</td>
								<td>${reservation.room.roomType} - ${reservation.room.hotel.name}</td>
								<td><f:formatDate type="date" dateStyle="LONG" value="${reservation.fromDate}" /></td>
								<td><f:formatDate type="date" dateStyle="LONG" value="${reservation.toDate}" /></td>
								<td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean" event="delete" class="delete"><s:param name="reservation.id" value="${reservation.id}"/><img src="${pageContext.request.contextPath}/images/remove_icon.png" alt="delete" /></s:link> </td>
								</tr>
						</c:forEach>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
    </s:layout-component>
</s:layout-render>