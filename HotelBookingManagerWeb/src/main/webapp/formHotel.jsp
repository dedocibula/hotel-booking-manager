<%-- 
    Document   : formHotel
    Created on : 22.11.2012, 23:12:42
    Author     : Filip Bogyai
--%>

<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:errors/>
<table>
    <tr>
        <th><s:label for="b1" name="hotel.name"/></th>
        <td><s:text id="b1" name="hotel.name"/></td>
    </tr>
    <tr>
        <th><s:label for="b2" name="hotel.contact.phone"/></th>
        <td><s:text id="b2" name="hotel.contact.phone"/></td>
    </tr>
    <tr>
        <th><s:label for="b3" name="hotel.contact.email"/></th>
        <td><s:text id="b3" name="hotel.contact.email"/></td>
    </tr>
    <tr>
        <th><s:label for="b4" name="hotel.contact.address"/></th>
        <td><s:text id="b4" name="hotel.contact.address"/></td>
    </tr>
    <tr>
        <th><s:label for="b5" name="hotel.contact.city"/></th>
        <td><s:text id="b5" name="hotel.contact.city"/></td>
    </tr>
    <tr>
        <th><s:label for="b6" name="hotel.contact.country"/></th>
        <td><s:text id="b6" name="hotel.contact.country"/></td>
    </tr>       
<%--<tr>
        <th><s:label for="b7" name="hotel.rooms"/></th>
        <td><s:text id="b7" name="hotel.rooms"/></td>
    </tr>     --%>
</table>
