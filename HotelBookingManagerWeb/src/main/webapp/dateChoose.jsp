<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<div>
	<s:select id="dateFrom" name="dateFrom">
		<c:forEach var="i" begin="1" end="31">
			<option value="${i}">${i}</option>
		</c:forEach>
	</s:select>
	<s:select id="monthFrom" name="monthFrom">
		<s:options-collection collection="${actionBean.months}" value="index" label="name" />
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
		<s:options-collection collection="${actionBean.months}" value="index" label="name" />
	</s:select>
	<s:select id="yearTo" name="yearTo">
		<c:forEach var="i" begin="2000" end="2020">
			<option value="${i}">${i}</option>
		</c:forEach>
	</s:select>
</div>