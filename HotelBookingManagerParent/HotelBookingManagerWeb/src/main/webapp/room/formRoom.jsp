<%--
    Document   : formRoom
    Created on : 24.11.2012, 19:16:15
    Author     : Filip Bogyai
--%>

<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<s:errors/>
<div class="form_row">
        <th><s:label for="b2" name="room.roomType"/></th>
        <td><s:select id="b2" name="room.roomType">
                <s:options-enumeration enum="cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.RoomType" />
            </s:select></td>
</div>
<div class="form_row">
        <th><s:label for="b3" name="room.pricePerNight"/></th>
        <td><s:text id="b3" name="room.pricePerNight"/></td>
</div>
