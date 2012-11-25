<%-- 
    Document   : formHotel
    Created on : 22.11.2012, 23:12:42
    Author     : Filip Bogyai
--%>

<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:errors/>
<div class="form_row">
        <th><label for="b2">Name:</label></th>
        <td><s:text id="b2" name="hotel.name"/></td>
</div>
<div class="form_row">
        <th><label for="b3">Phone:</label></th>
        <td><s:text id="b3" name="hotel.contact.phone"/></td>
</div>
<div class="form_row">
        <th><label for="b4">Email:</label></th>
        <td><s:text id="b4" name="hotel.contact.email"/></td>
</div>
<div class="form_row">
        <th><label for="b5">Address:</label></th>
        <td><s:text id="b5" name="hotel.contact.address"/></td>
</div>
<div class="form_row">
        <th><label for="b6">City:</label></th>
        <td><s:text id="b6" name="hotel.contact.city"/></td>
</div>
<div class="form_row">
        <th><label for="b7">Country:</label></th>
        <td><s:text id="b7" name="hotel.contact.country"/></td>
</div>      
</table>
