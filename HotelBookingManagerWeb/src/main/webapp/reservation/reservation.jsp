<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" title="Booking Manager - reservations" pageInfo="reservation.jsp">
	<s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean" var="actionBean"/>
	<s:layout-component name="left_content">

		<div class="content_left_section">
			<div class="content_title_01">Select date interval</div>

			<s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean">
				<%@include file="/../dateChoose.jsp" %>
				<div class="cleaner_h20">&nbsp;</div>
				<s:submit name="chooseDate">Submit</s:submit>
			</s:form>

			<div class="cleaner">&nbsp;</div>
		</div>

		<div class="cleaner_h30">&nbsp;</div>
		<div class="cleaner_horizontal_divider_01">&nbsp;</div>
		<div class="cleaner_h30">&nbsp;</div>

    </s:layout-component>

    <s:layout-component name="right_content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean" var="actionBean"/>

		<div class="content_right_section">
			<div class="content_title_01">Reservation Management</div>
			<img src="${pageContext.request.contextPath}/images/templatemo_image_04.jpg" alt="image" />
			<p>On this webpage all reservation management is being done.</p>
		</div>

		<div class="cleaner_h40">&nbsp;</div>


		<div class="content_right_section">
			<div class="content_title_02">Reservations</div>
			<c:out value="${actionBean.dateInterval}" />
			<c:choose>
				<c:when test="${empty actionBean.reservations}">
					<p class="emptyDb">No reservations match search criteria.</p>
				</c:when>
				<c:otherwise>
					<s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean">
						<div>
							<s:select id="hotel" name="hotel">
								<s:options-collection collection="${actionBean.hotels}" value="id" label="name" />
							</s:select>
						</div>
						<div>
							<s:submit name="chooseHotel">Submit</s:submit>
						</div>

					</s:form>

					<table>
						<thead>
							<tr>
								<th>Client</th>
								<th>Room</th>
								<th>From</th>
								<th>To</th>
								<th>Actions</th>
							</tr>
						</thead>
						<c:forEach items="${reservations}" var="reservation">
							<tr>
								<td>${reservation.client.firstName} ${reservation.client.lastName}</td>
								<td>${reservation.room}</td>
								<td>${reservation.dateFrom}</td>
								<td>${reservation.dateTo}</td>
								<td>
									<s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean" event="edit">
										<s:param name="reservation.id" value="${reservation.id}"/>
										Edit
									</s:link> 
									<s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean" event="delete">
										<s:param name="reservation.id" value="${reservation.id}"/>
										Delete
									</s:link>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
    </s:layout-component>
</s:layout-render>