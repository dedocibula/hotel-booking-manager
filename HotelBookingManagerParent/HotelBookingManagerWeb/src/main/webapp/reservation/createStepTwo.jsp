<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:message var="pageTitle" key="client.pageTitle"/>

<s:layout-render name="/layout.jsp" title="${pageTitle}" pageInfo="reservation.jsp">
	<s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean" var="actionBean"/>

    <s:layout-component name="right_content">

		<div class="content_right_section">
			<div class="content_title_01"><fmt:message key="reservationManagement"/></div>
			<s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean" event="all"><fmt:message key="backToReservation"/></s:link>
				<div class="cleaner_h20">&nbsp;</div>
			</div>
		<s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean">
			<s:errors/>
			<div class="content_right_section">
                                <p><fmt:message key="createReservationStepTwoDescription"/></p>
				<s:hidden name="from" />
				<s:hidden name="to" />
				<s:hidden name="hotel.id" />
			</div>
			<div class="content_right_2column_box">
				<s:label for="client" class="content_title_03"><fmt:message key="client"/>:</s:label>
				<s:select id="client" name="client.id" size="10" style="width:230px; border-radius:5px;">
					<s:options-collection collection="${actionBean.clients}" value="id"/>
				</s:select>
			</div>
			<div class="content_right_2column_box">
				<s:label for="room" class="content_title_03"><fmt:message key="room"/>:</s:label>
				<s:select id="room" name="room.id" size="10" style="width:230px; border-radius:5px;">
					<s:options-collection collection="${actionBean.rooms}" value="id"/>
				</s:select>
				<div class="cleaner_h40">&nbsp;</div>
			</div>
			<div class="content_right_2column_box">
				<s:submit name="add">Save</s:submit>
					<div class="cleaner_h10">&nbsp;</div>
				</div>
				<div class="content_right_2column_box">
				<s:submit name="create">Go Back</s:submit>
					<div class="cleaner_h10">&nbsp;</div>
				</div>
		</s:form>
    </s:layout-component>
</s:layout-render>