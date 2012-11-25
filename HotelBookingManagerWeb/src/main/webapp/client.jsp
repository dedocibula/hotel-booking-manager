<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
 
<s:layout-render name="/layout.jsp" title="Booking Manager - Client" pageInfo="client.jsp">
    <s:layout-component name="content">
        <s:useActionBean beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ClientsActionBean" var="actionBean"/>
        <div id="content_left">
            <div class="content_left_section">
            	<div class="content_title_01">New Client</div>
                
                <s:form beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ClientsActionBean">
                    <%@include file="formClient.jsp"%>
                    <div class="cleaner_h20">&nbsp;</div>
                    <s:submit name="add">Add Client</s:submit>
                </s:form>
                
                <div class="cleaner">&nbsp;</div>
            </div> <!-- end of booking -->
            
            <div class="cleaner_h30">&nbsp;</div>
            <div class="cleaner_horizontal_divider_01">&nbsp;</div>
            <div class="cleaner_h30">&nbsp;</div>
             
            <div class="content_left_section">
            <div class="content_title_02">Latest News</div>
                
                <div class="news_title">Database unavailable</div>
                    <p>We might have found possible bug in Java. It seems like application context injection in web.xml might not work because of bad mapping in persistence. We will try to resolve this issue this week.</p>
                <div class="cleaner_h30">&nbsp;</div>

            <div class="cleaner_horizontal_divider_01">&nbsp;</div>
            <div class="cleaner_h30">&nbsp;</div>
            </div>
            
            <a href="http://www.templatemo.com" target="_parent"><img src="images/templatemo_special_offer.jpg" alt="special offer" /></a>
            <div class="cleaner_h30">&nbsp;</div>
        </div> <!-- end of content left -->
        
        <div id="content_right">
        
        	<div class="content_right_section">
                    <div class="content_title_01">Client Management</div>
                    <img src="images/templatemo_image_04.jpg" alt="image" />
                    <p>On this webpage all client management is being done.</p>
                    <p>It serves either for regular clients as well as new clients willing to book a room in a hotel.</p>
                    <p>Clients can view their account, alter or delete it or create new.</p>
                    <p>Below is a list of all the clients that have used this website so far...</p>
                </div>
            
                <div class="cleaner_h40">&nbsp;</div>
            
            
                <div class="content_right_section">
                    <div class="content_title_02">All Clients</div>
                    <c:choose>
                        <c:when test="${empty actionBean.clients}">
                            <p class="emptyDb">No Clients so far :(</p>
                        </c:when>
                        <c:otherwise>
                            <table>
                                <tr>
                                    <th>id</th>
                                    <th>first name</th>
                                    <th>last name</th>
                                    <th>phone</th>
                                    <th>email</th>
                                    <th>address</th>
                                    <th>city</th>
                                    <th>country</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                <c:forEach items="${actionBean.clients}" var="client">
                                    <tr>
                                        <td>${client.id}</td>
                                        <td><c:out value="${client.firstName}"/></td>
                                        <td><c:out value="${client.lastName}"/></td>
                                        <td><c:out value="${client.contact.phone}"/></td>
                                        <td><c:out value="${client.contact.email}"/></td>
                                        <td><c:out value="${client.contact.address}"/></td>
                                        <td><c:out value="${client.contact.city}"/></td>
                                        <td><c:out value="${client.contact.country}"/> </td>
                                        <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ClientsActionBean" event="edit"><s:param name="client.id" value="${client.id}"/>Edit</s:link> </td>
                                        <td><s:link beanclass="cz.fi.muni.pa165.hotelbookingmanagerweb.ClientsActionBean" event="delete"><s:param name="client.id" value="${client.id}"/>Delete</s:link> </td>

                                    </tr>
                                </c:forEach> 
                            </table>
                        </c:otherwise>
                    </c:choose>
        </div> <!-- end of content right -->
           
    </s:layout-component>
</s:layout-render>
