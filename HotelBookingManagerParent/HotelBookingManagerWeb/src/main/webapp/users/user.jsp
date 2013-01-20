<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:message var="pageTitle" key="user.pageTitle"/>
<s:layout-render name="/layout.jsp" title="${pageTitle}" pageInfo="user.jsp">

    <s:layout-component name="right_content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.UsersActionBean" var="actionBean"/>

            <div class="content_right_section">
                    <div class="content_title_01"><fmt:message key="userManagement"/></div>
                    <img src="${pageContext.request.contextPath}/images/templatemo_image_04.jpg" alt="image" />
                    <fmt:message key="userManagementDescription"/>
                </div>

                <div class="cleaner_h40">&nbsp;</div>


                <div class="content_right_section">
                    <div class="content_title_02"><fmt:message key="userInfo"/></div>
                        <c:set var="user" value="${actionBean.logged}" />
                        </div>
                        
                        <div class="content_right_2column_box details_header">
                            <fmt:message key="user.userName"/>:
                        </div>
                        <div class="content_right_2column_box">
                            <c:out value="${user.username}" />
                        </div>
                        <div class="cleaner_h20">&nbsp;</div>
                        
                        <div class="cleaner_horizontal_divider_01">&nbsp;</div>
                        <div class="cleaner_h20">&nbsp;</div>
                            
                        <div class="content_right_2column_box details_header">
                            <fmt:message key="client.firstName"/>:
                        </div>
                        <div class="content_right_2column_box">
                            <c:out value="${user.client.firstName}" />
                        </div>
                        <div class="cleaner_h20">&nbsp;</div>
                        
                        <div class="content_right_2column_box details_header">
                            <fmt:message key="client.lastName"/>:
                        </div>
                        <div class="content_right_2column_box">
                            <c:out value="${user.client.lastName}" />
                        </div>
                        <div class="cleaner_h20">&nbsp;</div>
                        
                        <div class="content_right_2column_box details_header">
                            <fmt:message key="client.phone"/>:
                        </div>
                        <div class="content_right_2column_box">
                            <c:out value="${user.client.contact.phone}" />
                        </div>
                        <div class="cleaner_h20">&nbsp;</div>
                        
                        <div class="content_right_2column_box details_header">
                            <fmt:message key="client.email"/>:
                        </div>
                        <div class="content_right_2column_box">
                            <c:out value="${user.client.contact.email}" />
                        </div>
                        <div class="cleaner_h20">&nbsp;</div>
                        
                        <div class="content_right_2column_box details_header">
                            <fmt:message key="client.address"/>:
                        </div>
                        <div class="content_right_2column_box">
                            <c:out value="${user.client.contact.address}" />
                        </div>
                        <div class="cleaner_h20">&nbsp;</div>
                        
                        <div class="content_right_2column_box details_header">
                            <fmt:message key="client.city"/>:
                        </div>
                        <div class="content_right_2column_box">
                            <c:out value="${user.client.contact.city}" />
                        </div>
                        <div class="cleaner_h20">&nbsp;</div>
                        
                        <div class="content_right_2column_box details_header">
                            <fmt:message key="client.country"/>:
                        </div>
                        <div class="content_right_2column_box">
                            <c:out value="${user.client.contact.country}" />
                        </div>
                        <div class="cleaner_h20">&nbsp;</div>
                        
                        <div class="cleaner_horizontal_divider_01">&nbsp;</div>
                        <div class="cleaner_h20">&nbsp;</div>
                        
                        <div class="rc_btn_02"><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.UsersActionBean" event="edit"><s:param name="user.id" value="${user.id}"/><fmt:message key="edit"/></s:link></div>
                       
    </s:layout-component>
</s:layout-render>
