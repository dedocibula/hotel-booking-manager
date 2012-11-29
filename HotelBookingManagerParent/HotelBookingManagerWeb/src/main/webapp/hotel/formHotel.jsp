<%--
    Document   : formHotel
    Created on : 22.11.2012, 23:12:42
    Author     : Filip Bogyai
--%>

<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<s:errors/>
<div class="form_row">
        <th><s:label for="b2" name="hotel.name"/></th>
        <td><s:text id="b2" name="hotel.name"/></td>
</div>
<div class="form_row">
        <th><s:label for="b3" name="hotel.phone"/></th>
        <td><s:text id="b3" name="hotel.contact.phone"/></td>
</div>
<div class="form_row">
        <th><s:label for="b4" name="hotel.email"/></th>
        <td><s:text id="b4" name="hotel.contact.email"/></td>
</div>
<div class="form_row">
        <th><s:label for="b5" name="hotel.address"/></th>
        <td><s:text id="b5" name="hotel.contact.address"/></td>
</div>
<div class="form_row">
        <th><s:label for="b6" name="hotel.city"/></th>
        <td><s:text id="b6" name="hotel.contact.city"/></td>
</div>
<div class="form_row">
        <th><s:label for="b7" name="hotel.country"/></th>
        <td><s:select name="hotel.contact.country">
            <s:options-collection collection="${actionBean.countries}" style="width:20px;" />
            </s:select></td>
</div>
</table>
