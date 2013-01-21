<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
<s:layout-render name="/layout.jsp" title="Booking Manager - Home" pageInfo="index.jsp">
    <s:layout-component name="right_content">
        <sec:authorize access="hasRole('ROLE_USER')">
        <div class="content_right_section">
        	
                <div class="content_title_01">Welcome to  Hotel Booking Manager</div>
                <img src="images/templatemo_image_01.jpg" alt="image" />
                <p>Feel like you had enough? Feel like your life no longer satisfies you? Invest in your recreation now. Enjoy new countries that you didn't know before. Travel, explore, have fun. This website is everything you need.</p>
                
              <p>Now everything you ever craved for is possible. You are just few clicks away from your dream destination. Take your time and explore everything this page has to offer.</p>
              <p>We believe that you will find everything you need. Feel free to refer about this page to your colleagues, family, love ones, friends... Let them enjoy themselves as much as you are.</p>
       	  </div>
            
            
			<div class="cleaner_h20">&nbsp;</div>
                        
            <div class="content_right_section">
        	
                <div class="content_title_02">Services we offer</div>
                <p>Here you can see the list of everything this page has to offer.</p>
                <ul>
                	<li>Finding a hotel</li>
                        <li>Creating your account</li>
                        <li>Picking a room</li>
                        <li>Making a reservation</li>
                        <li>And so much more...</li>
                </ul> 
                <div class="cleaner_h10">&nbsp;</div>
       	  </div>
               
            
            <div class="cleaner_h20">&nbsp;</div>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <div class="content_right_section">
                    <div class="content_title_01">Welcome Administrator</div>
                <img src="images/templatemo_image_01.jpg" alt="image" />
                <p>Your account allows you to manage client, hotels, rooms and reservations on this webpage.</p>
                </div>
                <div class="cleaner_h10">&nbsp;</div>
                <div class="content_right_section">
                <p>List of your competencies:</p>
                <ul>
                    <li>Edit and delete client profiles</li>
                    <li>Add new hotels, edit existing ones, delete them</li>
                    <li>Add new rooms to hotels, edit existing ones, delete them</li>
                    <li>Create new reservations for clients or delete existing ones</li>
                </ul>
                </div>
            
            
			<div class="cleaner_h20">&nbsp;</div>
            </sec:authorize>
            
            <div class="content_right_section">
                <div class="content_title_02">Gallery of hottest places</div>
                    <div class="gallery_box">
                    	<img src="images/templatemo_image_02.jpg" alt="image" />
                        <a href="#"></a>
                    </div>
                    <div class="gallery_box">
	                    <img src="images/templatemo_image_03.jpg" alt="image" />
                        <a href="#"></a>
                    </div>
                    <div class="gallery_box">
	                    <img src="images/templatemo_image_04.jpg" alt="image" />
                        <a href="#"></a>
                    </div>
                    <div class="gallery_box">
                    	<img src="images/templatemo_image_05.jpg" alt="image" />
                        <a href="#"></a>
                    </div>
                    <div class="gallery_box">
                    	<img src="images/templatemo_image_06.jpg" alt="image" />
                        <a href="#"></a>
                    </div>
                    
                    <div class="cleaner">&nbsp;</div>
			</div>                    
        	<div class="cleaner_h20">&nbsp;</div>
    </s:layout-component>
</s:layout-render>
