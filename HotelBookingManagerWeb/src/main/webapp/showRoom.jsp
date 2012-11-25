<%-- 
    Document   : hotelRooms
    Created on : 24.11.2012, 19:03:41
    Author     : Filip Bogyai
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
 
<s:layout-render name="/layout.jsp" title="Booking Manager - Room" pageInfo="showRoom.jsp">
    <s:layout-component name="left_content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" var="actionBean"/>
        
            <div class="content_left_section">
            	<div class="content_title_01">New Room</div>
                
                <s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean">
                    <%@include file="formRoom.jsp"%>
                    <div class="cleaner_h20">&nbsp;</div>
                    <s:submit name="add">Add Room</s:submit>
                </s:form>
                
                <div class="cleaner">&nbsp;</div>
            </div> <!-- end of booking -->
            
            <div class="cleaner_h30">&nbsp;</div>
            <div class="cleaner_horizontal_divider_01">&nbsp;</div>
            <div class="cleaner_h30">&nbsp;</div>
           
    </s:layout-component>
    
    <s:layout-component name="right_content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" var="actionBean"/>
        
            <div class="content_right_section">
                    <div class="content_title_01">Room Management</div>
                    <img src="${pageContext.request.contextPath}/images/templatemo_image_04.jpg" alt="image" />
                    <p>On this webpage all hotel management is being done.</p>
                    <p>It serves either for regular hotels as well as new hotels willing to book a room in a hotel.</p>
                    <p>Rooms can view their account, alter or delete it or create new.</p>
                    <p>Below is a list of all the hotels that have used this website so far...</p>
                </div>
            
                <div class="cleaner_h40">&nbsp;</div>
            
            
                <div class="content_right_section">
                    <div class="content_title_02">All Rooms</div>
                    <c:choose>
                        <c:when test="${empty actionBean.hotelRooms}">
                            <p class="emptyDb">No Rooms so far :(</p>
                        </c:when>
                        <c:otherwise>
                            <table>
                                <tr>
                                    <th>id</th>
                                    <th>price</th>  
                                    <th>hotel </th>    
                                    
                                    <th></th>
                                    <th></th>
                                </tr>
                                <c:forEach items="${actionBean.hotelRooms}" var="room">
                                    <tr>
                                        <td>${room.id}</td>
                                             <td><c:out value="${room.pricePerNight}"/></td>
                                             <td><c:out value="${room.hotel.name}"/></td>                   
                                             <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" event="delete"><s:param name="room.id" value="${room.id}"/>smazat</s:link> </td>
                                             <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.RoomsActionBean" event="edit"><s:param name="room.id" value="${room.id}"/>edit</s:link> </td>
                                    </tr>
                                </c:forEach> 
                            </table>
                        </c:otherwise>
                    </c:choose>
            </div>
    </s:layout-component>
</s:layout-render>   
                
