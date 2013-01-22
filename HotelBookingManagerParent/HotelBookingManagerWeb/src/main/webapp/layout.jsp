<%--
    Document   : layout
    Created on : 21.11.2012, 2:09:13
    Author     : Marian Rusnak & Andrej Galád
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<s:layout-definition>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><c:out value="${title}" /></title>
        <meta name="keywords" content="Hotel, Room, Reservation, Client, Java, Tomcat, Spring, Hibernate, CSS, XHTML" />
        <meta name="description" content="Booking Manager - website allowing to create reservations all around the globe" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon.ico" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style.css" />
		
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/jquery-ui.css" />
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-ui.js"></script>
        <s:layout-component name="head"/>
    </head>
    <body>
        <div id="templatemo_container">
            <div id="templatemo_header">
                <div id="website_title">
                    <div id="title">
                            Booking Manager
                    </div>
                    <div id="salgon">
                            Book yourself a piece of paradise...</div>
                </div>
            </div> <!-- end of header -->

            <div id="templatemo_banner">
                <sec:authorize access="hasAnyRole('ROLE_USER, ROLE_ADMIN')">
                <div id="templatemo_menu">
                    <ul>
                        <li><s:link href="/index.jsp" class="${(pageInfo == 'index.jsp') ? 'current' : ''}"><fmt:message key="home"/></s:link></li>
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li><s:link href="/clients/" class="${(pageInfo == 'client.jsp') ? 'current' : ''}"><fmt:message key="client"/></s:link></li>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_USER')">
                        <li><s:link href="/users/" class="${(pageInfo == 'user.jsp') ? 'current' : ''}"><fmt:message key="user"/></s:link></li>
                        </sec:authorize>
                        <li><s:link href="/hotels/" class="${(pageInfo == 'hotel.jsp') ? 'current' : ''}"><fmt:message key="hotel"/></s:link></li>
                        <li><s:link href="/reservations/" class="${(pageInfo == 'reservation.jsp') ? 'current' : ''}"><fmt:message key="reservation"/></s:link></li>
                        <li><s:link href="/contact_about.jsp" class="${(pageInfo == 'contact_about.jsp') ? 'current' : ''}"><fmt:message key="contact"/></s:link></li>
                        <li><s:link href="/j_spring_security_logout" class="last">Logout</s:link></li>
                    </ul>
                </div>
                </sec:authorize>
            </div> <!-- end of banner -->

            <div id="templatemo_content">

                <div id="content_left">

                    <s:layout-component name="left_content" />

                    <div class="content_left_section">
                    <div class="content_title_02">Latest News</div>

                        <div class="news_title">[Update] Database</div>
                            <p>
                            We have succesfully managed to link service and presentation layer. Now we are operating
                            on top of functional database. There are still minor glitches that we need to repair like
                            insufficient localization or service error handling but we are possitive that this time
                            we will finish on schedule.
                            </p>
                        <div class="cleaner_h30">&nbsp;</div>
                        
                        <div class="news_title">[Bloopers] After a few hours of head-wrecking...</div>
                            <p>
                            "On this webpage all hotel management is being done.
                            It serves either for regular hotels as well as new hotels willing to book a room in a hotel.
                            Hotels can view their account, alter or delete it or create new.
                            Below is a list of all the hotels that have used this website so far..."
                            </p>
                        <div class="cleaner_h30">&nbsp;</div>

                        <div class="news_title">Database unavailable</div>
                            <p>We might have found possible bug in Java. It seems like application context injection in web.xml might not work because of bad mapping in persistence. We will try to resolve this issue this week.</p>
                        <div class="cleaner_h30">&nbsp;</div>

                    <div class="cleaner_horizontal_divider_01">&nbsp;</div>
                    <div class="cleaner_h30">&nbsp;</div>
                    </div>

                    <a href="http://www.templatemo.com" target="_parent"><img src="${pageContext.request.contextPath}/images/templatemo_special_offer.jpg" alt="special offer" /></a>
                    <div class="cleaner_h30">&nbsp;</div>
                </div> <!-- end of content left -->

                <div id="content_right">

                    <s:layout-component name="right_content"/>

                </div> <!-- end of content right -->

                <div class="cleaner">&nbsp;</div>
            </div> <!-- end of content -->

            <div id="templatemo_footer">
            Copyright © 2024 <a href="#"><strong>PA165 Best Group</strong></a> | Designed by Andrej Galád
                <div style="clear: both; margin-top: 10px;">
                    <a href="http://validator.w3.org/check?uri=referer"><img style="border:0;width:88px;height:31px" src="http://www.w3.org/Icons/valid-xhtml10" alt="Valid XHTML 1.0 Transitional" width="88" height="31" vspace="8" border="0" /></a>
                    <a href="http://jigsaw.w3.org/css-validator/check/referer"><img style="border:0;width:88px;height:31px"  src="http://jigsaw.w3.org/css-validator/images/vcss-blue" alt="Valid CSS!" vspace="8" border="0" /></a>
                </div>
                </div> <!-- end of footer -->
        </div> <!-- end of container -->
        
        <script type="text/javascript">
            (function() {
                $('tbody').children('tr.info').children().wrapInner('<div class="wrapper">');
                var $wrapper = $('div.wrapper');
                var $left_content = $('div#content_left');
                
                if ($('div.validation_error')[0]) {
                    $left_content.show();
                } else {
                    $left_content.css({
                        left: "-200px"
                    });
                    
                    $left_content.animate({
                        opacity: 'show',
                        left: '+=200'
                    }, 1000);
                }
                
                $('tr.main').on('hover', function(e) {
                    if (e.type == 'mouseenter') {
                        $(this).next()
                               .find('div.wrapper')
                               .slideDown(200);
                    } else {
                        $wrapper.slideUp();
                    }
                });
                
                $('a.delete').on('click', function(e) {
                    var answer = confirm('Are you sure that you want to remove this item?');
                    if (!answer) {
                        e.preventDefault();
                    }
                });
                
                $('a.last').on('click', function(e) {
                    var self = this;
                    e.preventDefault();
                    $('<div>Are you sure you want to logout?</div>').dialog({
                        title: "Logout confirmation",
                        position: { my: "center", at: "center", of: "#templatemo_banner" },
                       modal: true,
                       show: { effect: "clip", duration: 300 },
                       hide: { effect: "clip", duration: 100 },
                       buttons: {
                           'Yes': function() {
                               $(this).dialog('close');
                               document.location = self.href;
                           },
                           'No': function() {
                               $(this).dialog('close');
                           }
                       }
                    });
                })
            })();
        </script>
    </body>
</html>
</s:layout-definition>
