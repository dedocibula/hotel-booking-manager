<%-- 
    Document   : showHotel
    Created on : 22.11.2012, 22:58:03
    Author     : Filip Bogyai
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
 
<s:layout-render name="/layout.jsp" title="Booking Manager - Hotel" pageInfo="showHotel.jsp">
    <s:layout-component name="left_content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" var="actionBean"/>
        
            <div class="content_left_section">
            	<div class="content_title_01">New Hotel</div>
                
                <s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean">
                    <%@include file="formHotel.jsp"%>
                    <div class="cleaner_h20">&nbsp;</div>
                    <s:submit name="add">Add Hotel</s:submit>
                </s:form>
                
                <div class="cleaner">&nbsp;</div>
            </div> <!-- end of booking -->
            
            <div class="cleaner_h30">&nbsp;</div>
            <div class="cleaner_horizontal_divider_01">&nbsp;</div>
            <div class="cleaner_h30">&nbsp;</div>
           
    </s:layout-component>
    
    <s:layout-component name="right_content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" var="actionBean"/>
        
            <div class="content_right_section">
                    <div class="content_title_01">Hotel Management</div>
                    <img src="${pageContext.request.contextPath}/images/templatemo_image_04.jpg" alt="image" />
                    <p>On this webpage all hotel management is being done.</p>
                    <p>It serves either for regular hotels as well as new hotels willing to book a room in a hotel.</p>
                    <p>Hotels can view their account, alter or delete it or create new.</p>
                    <p>Below is a list of all the hotels that have used this website so far...</p>
                </div>
            
                <div class="cleaner_h40">&nbsp;</div>
            
            
                <div class="content_right_section">
                    <div class="content_title_02">All Hotels</div>
                    <c:choose>
                        <c:when test="${empty actionBean.hotels}">
                            <p class="emptyDb">No Hotels so far :(</p>
                        </c:when>
                        <c:otherwise>
                            <table>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>                                   
                                    <th>phone</th>
                                    <th>email</th>
                                    <th>address</th>
                                    <th>city</th>
                                    <th>country</th>
                                    <th>rooms</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                <c:forEach items="${actionBean.hotels}" var="hotel">
                                    <tr>
                                        <td>${hotel.id}</td>
                                        <td><c:out value="${hotel.name}"/></td>                                        
                                        <td><c:out value="${hotel.contact.phone}"/></td>
                                        <td><c:out value="${hotel.contact.email}"/></td>
                                        <td><c:out value="${hotel.contact.address}"/></td>
                                        <td><c:out value="${hotel.contact.city}"/></td>
                                        <td><c:out value="${hotel.contact.country}"/> </td>
                                        <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" event="rooms"><s:param name="hotel.id" value="${hotel.id}"/>Rooms</s:link> </td>
                                        <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" event="edit"><s:param name="hotel.id" value="${hotel.id}"/>Edit</s:link> </td>
                                        <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.HotelsActionBean" event="delete"><s:param name="hotel.id" value="${hotel.id}"/>Delete</s:link> </td>

                                    </tr>
                                </c:forEach> 
                            </table>
                        </c:otherwise>
                    </c:choose>
            </div>
    </s:layout-component>
</s:layout-render>   

