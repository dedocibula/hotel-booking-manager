<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" title="Booking Manager - reservations" pageInfo="reservation.jsp">
	<s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean" var="actionBean"/>

    <s:layout-component name="right_content">

		<div class="content_right_section">
			<div class="content_title_01">Reservation Management</div>
			<s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean" event="all">Back to list</s:link>
			<s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean">
				<%@include file="/../dateChoose.jsp" %>
				<div>
					<s:label for="dateFrom">Client:</s:label>
				</div>
				<div>
					<s:select id="client" name="client.id">
						<s:options-collection collection="${actionBean.clients}" value="id" label="lastName" />
					</s:select>
				</div>
				<div>
					<s:label for="dateFrom">Room:</s:label>
				</div>
				<div>
					<s:select id="room" name="room.id">
						<s:options-collection collection="${actionBean.rooms}" value="id" label="pricePerNight" />
					</s:select>
				</div>
				<div class="cleaner_h20">&nbsp;</div>
				<s:submit name="add">Save</s:submit>
			</s:form>
		</div>

    </s:layout-component>
</s:layout-render>