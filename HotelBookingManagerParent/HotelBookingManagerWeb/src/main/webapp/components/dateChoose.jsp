<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<script type="text/javascript">
$(function() {
	$( "#datepickerFrom" ).datepicker(
	{ 
		dateFormat: "dd.mm.yy",
		monthNames: ${actionBean.months}
	});
	$( "#datepickerTo" ).datepicker({ 
		dateFormat: "dd.mm.yy",
		monthNames: ${actionBean.months}
	});
});
</script>
<div>
	<div>
		<s:label for="dateFrom" name="dateFrom"/>
	</div>
	<div>
		<s:text id="datepickerFrom" class="date-picker" name="from" />
		<%--
		<s:select id="dateFrom" name="dateInterval.dateFrom">
			<c:forEach var="i" begin="1" end="31">
				<option value="${i}">${i}</option>
			</c:forEach>
		</s:select>
		<s:select id="monthFrom" name="dateInterval.monthFrom">
			<s:options-collection collection="${actionBean.months}" value="index" label="name" />
		</s:select>
		<s:select id="yearFrom" name="dateInterval.yearFrom">
			<c:forEach var="i" begin="2000" end="2020">
				<option value="${i}">${i}</option>
			</c:forEach>
		</s:select>
		--%>
	</div>
</div>
<div>
	<div>
		<s:label for="dateTo" name="dateTo"/>
	</div>
	<div>
		<s:text id="datepickerTo" class="date-picker" name="to" />
		<%--
		<s:select id="dateTo" name="dateInterval.dateTo">
			<c:forEach var="i" begin="1" end="31">
				<option value="${i}">${i}</option>
			</c:forEach>
		</s:select>
		<s:select id="monthTo" name="dateInterval.monthTo">
			<s:options-collection collection="${actionBean.months}" value="index" label="name" />
		</s:select>
		<s:select id="yearTo" name="dateInterval.yearTo">
			<c:forEach var="i" begin="2000" end="2020">
				<option value="${i}">${i}</option>
			</c:forEach>
		</s:select>
		--%>
	</div>
</div>