<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:message var="pageTitle" key="client.pageTitle"/>
<s:layout-render name="/layout.jsp" title="${pageTitle}" pageInfo="client.jsp">
    <s:layout-component name="right_content">
            <div class="content_right_section">

                    <div class="content_title_01"><fmt:message key="errorMessageHeader1"/></div>

                    <p><fmt:message key="errorMessageDescription"/></p>

                    <div class="cleaner_h10">&nbsp;</div>

                    <div class="content_title_03"><fmt:message key="errorMessageHeader2"/></div>
                    <fmt:message key="clientDeleteError"/>
            </div>                   
            <div class="cleaner_h20">&nbsp;</div>
            <div class="rc_btn_01"><s:link href="/client/client.jsp"><fmt:message key="backToClients"/></s:link></div>
    </s:layout-component>
</s:layout-render>