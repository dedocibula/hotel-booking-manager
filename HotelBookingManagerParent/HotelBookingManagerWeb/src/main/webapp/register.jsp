<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<s:layout-render name="/layout.jsp" title= "Booking Manager - Register" pageInfo="index.jsp">
    <s:layout-component name="right_content">
       <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.UsersActionBean" var="actionBean"/>

            <div class="content_right_section">
                <div class="content_title_02"><fmt:message key="newUser"/></div>
                <p><fmt:message key="newUserDescription"/></p>

                <div class="cleaner">&nbsp;</div>
            </div>

            <div class="cleaner_h40">&nbsp;</div>

            <s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.UsersActionBean" action="/users/add/">
                <div class="content_right_section">
                    <%@include file="users/formUser.jsp"%>
                    <div class="cleaner_h20">&nbsp;</div>

                    <div class="cleaner">&nbsp;</div>
                </div> <!-- end of editing -->

                <div class="content_right_2column_box">
                    <s:submit name="add"/>
                    <div class="cleaner_h10">&nbsp;</div>
                </div>

                <div class="content_right_2column_box">
                    <div class="rc_btn_02"/><s:link href="/login.jsp"><fmt:message key="userDetails"/></s:link></div>
                    <div class="cleaner_h10">&nbsp;</div>
                </div>
            </s:form>

    </s:layout-component>
</s:layout-render>
