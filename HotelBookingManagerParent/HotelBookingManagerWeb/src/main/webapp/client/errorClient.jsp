<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:message var="pageTitle" key="client.pageTitle"/>
<s:layout-render name="/layout.jsp" title="${pageTitle}" pageInfo="client.jsp">
    <s:layout-component name="left_content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ClientsActionBean" var="actionBean"/>

            <div class="content_left_section">
            </div> <!-- end of booking -->

            <div class="cleaner_h30">&nbsp;</div>
            <div class="cleaner_horizontal_divider_01">&nbsp;</div>
            <div class="cleaner_h30">&nbsp;</div>

    </s:layout-component>

    <s:layout-component name="right_content">

            <div class="content_right_section">
                <p><fmt:message key="clientDeleteError"/></p>
                <s:link href="/client/client.jsp" class="${(pageInfo == 'client.jsp') ? 'current' : ''}"><fmt:message key="backToClients"/></s:link>
            </div>
    </s:layout-component>
</s:layout-render>