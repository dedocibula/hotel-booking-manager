<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:errors/>
<div class="form_row">
        <th><label for="b1">First name:</label></th>
        <td><s:text id="b1" name="client.firstName"/></td>
</div>
<div class="form_row">
        <th><label for="b2">Last name:</label></th>
        <td><s:text id="b2" name="client.lastName"/></td>
</div>
<div class="form_row">
        <th><label for="b3">Phone:</label></th>
        <td><s:text id="b3" name="client.contact.phone"/></td>
</div>
<div class="form_row">
        <th><label for="b4">Email:</label></th>
        <td><s:text id="b4" name="client.contact.email"/></td>
</div>
<div class="form_row">
        <th><label for="b5">Address:</label></th>
        <td><s:text id="b5" name="client.contact.address"/></td>
</div>
<div class="form_row">
        <th><label for="b6">City:</label></th>
        <td><s:text id="b6" name="client.contact.city"/></td>
</div>
<div class="form_row">
        <th><label for="b7">Country:</label></th>
        <td><s:select name="client.contact.country">
                <s:options-collection collection="${actionBean.countries}" style="width:20px;" />
            </s:select></td>
</div>      
</table>