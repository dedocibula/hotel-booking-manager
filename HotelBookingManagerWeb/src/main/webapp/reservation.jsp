<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" title="Booking Manager - reservations" pageInfo="reservation.jsp">
    <s:layout-component name="content">
		<s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean" var="actionBean"/>
        <s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean">
			<%@include file="dateChoose.jsp" %>
			<div>
				<s:submit name="chooseDate">Submit</s:submit>
			</div>

		</s:form>

		<s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean">
			<div>
				<s:select id="hotel" name="hotel">
					<s:options-collection collection="${actionBean.hotels}" />
				</s:select>
			</div>
			<div>
				<s:submit name="chooseHotel">Submit</s:submit>
			</div>

			<%--<s:select id="monthFrom" name="monthFrom">
				<c:forEach items="${actionBean.months}" var="month">
					<option value="${month}">${month}</option>
					
				</c:forEach>
			</s:select>--%>

		</s:form>

		<table>
			<thead>
				<tr>
					<th>Client</th>
					<th>Room</th>
					<th>From</th>
					<th>To</th>
				</tr>
			</thead>
			<c:forEach items="${reservations}" var="reservation">
				<tr>
					<td>${reservation.client.firstName} ${reservation.client.lastName}</td>
					<td>${reservation.room}</td>
					<td>${reservation.dateFrom}</td>
					<td>${reservation.dateTo}</td>
				</tr>
			</c:forEach>
		</table>
    </s:layout-component>
</s:layout-render>