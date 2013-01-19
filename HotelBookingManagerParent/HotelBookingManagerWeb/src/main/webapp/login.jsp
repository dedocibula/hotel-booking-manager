<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<s:layout-render name="/layout.jsp" title="Booking Manager - Login" pageInfo="index.jsp">
    <s:layout-component name="right_content">
        <div class="content_right_section">
            <div class="content_title_01">Welcome to  Hotel Booking Manager</div>
            <p>In order to be able to use this page you need to be logged in.</p>
            <p>Please fill your user details below to log in.</p>
            <p>If you haven't visited our website yet please create your user account by registering.</p>
        </div>
        
        <div class="cleaner_h20">&nbsp;</div>
        <div class="content_right_section">
              <div class="content_title_03">Login with username and password</div>
              <c:if test="${not empty param.loginerror}">
                  <div class="errorblock">
                      Your login attempt was not successful, try again.<br /> Caused :
                      ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                  </div>
              </c:if>
              <form action="<c:url value="j_spring_security_check" />" method="POST">
                    <label for="j_username">User</label>
                    <input type="text" name="j_username" id="j_username"/>
                    <div class="cleaner_h10">&nbsp;</div>
                    
                    <label for="j_password">Password</label>
                    <input type="password" name="j_password" id="j_password"/>
                    
                    <div class="cleaner_h20">&nbsp;</div>
                    <input type="submit" value="Login"/>
            </form>
                
       	  </div>
            
            
	<div class="cleaner_h20">&nbsp;</div>
                        
    </s:layout-component>
</s:layout-render>
