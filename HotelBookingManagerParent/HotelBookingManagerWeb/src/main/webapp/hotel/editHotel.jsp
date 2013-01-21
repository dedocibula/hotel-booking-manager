<%--
    Document   : editHotel
    Created on : 22.11.2012, 22:58:14
    Author     : Filip Bogyai
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:message var="pageTitle" key="hotel.pageTitle"/>
<s:layout-render name="/layout.jsp" title="${pageTitle}" pageInfo="hotel.jsp">
    <s:layout-component name="right_content">
       <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" var="actionBean"/>

            <div class="content_right_section">
                <div class="content_title_02"><fmt:message key="changeHotel"/></div>
                <p><fmt:message key="changeHotelDescription"/></p>

                <div class="cleaner">&nbsp;</div>
            </div>

            <div class="cleaner_h40">&nbsp;</div>

            <s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean">
                <div class="content_right_section">
                    <div class="content_title_03"><fmt:message key="attributesHotel"/> ${actionBean.hotel.name}:</div>

                        <s:hidden name="hotel.id"/>
                        <s:hidden name="hotel.rooms"/>
                        <%@include file="formHotel.jsp"%>
                        <div class="cleaner_h20">&nbsp;</div>

                    <div class="cleaner">&nbsp;</div>
                </div> <!-- end of editing -->

                <div class="content_right_2column_box">
                    <s:submit name="save"/>
                    <div class="cleaner_h10">&nbsp;</div>
                </div>

                <div class="content_right_2column_box">
                    <div class="rc_btn_02"/><s:link href="/hotels/hotelAll"><fmt:message key="hotelAll"/></s:link></div>
                    <div class="cleaner_h10">&nbsp;</div>
                </div>
            </s:form>

    </s:layout-component>
</s:layout-render>
