<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:message var="pageTitle" key="client.pageTitle"/>
<s:layout-render name="/layout.jsp" title="${pageTitle}" pageInfo="client.jsp">
    <s:layout-component name="right_content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ClientsActionBean" var="actionBean"/>

            <div class="content_right_section">
                    <div class="content_title_01"><fmt:message key="clientManagement"/></div>
                    <img src="${pageContext.request.contextPath}/images/templatemo_image_04.jpg" alt="image" />
                    <fmt:message key="clientManagementDescription"/>
                </div>

                <div class="cleaner_h40">&nbsp;</div>


                <div class="content_right_section">
                    <div class="content_title_02"><fmt:message key="allClients"/></div>
                    <c:choose>
                        <c:when test="${empty actionBean.clients}">
                            <p class="emptyDb"><fmt:message key="noClients"/></p>
                        </c:when>
                        <c:otherwise>
                            <table id="table">
                                <thead>
                                    <th><fmt:message key="id"/></th>
                                    <th><fmt:message key="client.firstName"/></th>
                                    <th><fmt:message key="client.lastName"/></th>
                                    <th><fmt:message key="client.phone"/></th>
                                    <th><fmt:message key="client.email"/></th>
                                    <th></th>
                                    <th></th>
                                </thead>
                                <c:forEach items="${actionBean.clients}" var="client">
                                    <tr class="main">
                                        <td>${client.id}</td>
                                        <td><c:out value="${client.firstName}"/></td>
                                        <td><c:out value="${client.lastName}"/></td>
                                        <td><c:out value="${client.contact.phone}"/></td>
                                        <td><c:out value="${client.contact.email}"/></td>
                                        <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ClientsActionBean" event="edit"><s:param name="client.id" value="${client.id}"/><img src="${pageContext.request.contextPath}/images/edit_icon.png" alt="edit" /></s:link> </td>
                                        <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ClientsActionBean" event="delete" class="delete"><s:param name="client.id" value="${client.id}"/><img src="${pageContext.request.contextPath}/images/remove_icon.png" alt="delete" /></s:link> </td>

                                    </tr>
                                    <tr class="info">
                                        <%@include file="clientInfo.jsp" %>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:otherwise>
                    </c:choose>
            </div>
    </s:layout-component>
</s:layout-render>
