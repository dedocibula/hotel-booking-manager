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
			<s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean" event="all">Back to reservation list</s:link>
				<div class="cleaner_h20">&nbsp;</div>
			<s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean">
				<s:errors/>
				<div class="content_right_section">
					<p>Please select a hotel you want to book room in as well as dates when would you like to make your reservation.</p>
					<%@include file="/../components/dateChoose.jsp" %>
					<div>
						<s:label for="hotel"><fmt:message key="hotel"/>:</s:label>
						</div>
						<div>
						<s:select id="hotel" name="hotel.id" size="10" style="width:230px;">
							<s:options-collection collection="${actionBean.hotels}" value="id" />
						</s:select>
					</div>
					<div class="cleaner_h40">&nbsp;</div>
				</div>
				<div class="content_right_section">
					<div class="cleaner_h20">&nbsp;</div>
					<s:submit name="createContinue">Continue</s:submit>
						<div class="cleaner_h40">&nbsp;</div>
					</div>
			</s:form>
		</div>

    </s:layout-component>
</s:layout-render>