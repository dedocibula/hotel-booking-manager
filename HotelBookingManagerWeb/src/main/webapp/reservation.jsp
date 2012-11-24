<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" title="Booking Manager - reservations" pageInfo="reservation.jsp">
    <s:layout-component name="content">
		<s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean" var="actionBean"/>
        <s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ReservationsActionBean" action="choseDate">
			<div>
				<s:select id="dateFrom" name="dateFrom">
					<c:forEach var="i" begin="1" end="31">
						<option value="${i}">${i}</option>
					</c:forEach>
				</s:select>
				<s:select id="monthFrom" name="monthFrom">
					<c:forEach var="i" begin="0" end="11">
						<option value="${i}">${i}</option>
					</c:forEach>
				</s:select>
				<s:select id="yearFrom" name="yearFrom">
					<c:forEach var="i" begin="2000" end="2020">
						<option value="${i}">${i}</option>
					</c:forEach>
				</s:select>
			</div>
			<div>
				<s:select id="dateTo" name="dateTo">
					<c:forEach var="i" begin="1" end="31">
						<option value="${i}">${i}</option>
					</c:forEach>
				</s:select>
				<s:select id="monthTo" name="monthTo">
					<c:forEach var="i" begin="0" end="11">
						<option value="${i}">${i}</option>
					</c:forEach>
				</s:select>
				<s:select id="yearTo" name="yearTo">
					<c:forEach var="i" begin="2000" end="2020">
						<option value="${i}">${i}</option>
					</c:forEach>
				</s:select>
			</div>
			<%--<s:select id="monthFrom" name="monthFrom">
				<c:forEach items="${actionBean.months}" var="month">
					<option value="${month}">${month}</option>
					
				</c:forEach>
			</s:select>--%>

		</s:form>
    </s:layout-component>
</s:layout-render>