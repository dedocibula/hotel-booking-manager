<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:errors/>
<div class="form_row">
        <th><s:label for="b1" name="client.firstName"/></th>
        <td><s:text id="b1" name="client.firstName"/></td>
</div>
<div class="form_row">
        <th><s:label for="b2" name="client.lastName"/></th>
        <td><s:text id="b2" name="client.lastName"/></td>
</div>
<div class="form_row">
        <th><s:label for="b3" name="client.phone"/></th>
        <td><s:text id="b3" name="client.contact.phone"/></td>
</div>
<div class="form_row">
        <th><s:label for="b4" name="client.email"/></th>
        <td><s:text id="b4" name="client.contact.email"/></td>
</div>
<div class="form_row">
        <th><s:label for="b5" name="client.address"/></th>
        <td><s:text id="b5" name="client.contact.address"/></td>
</div>
<div class="form_row">
        <th><s:label for="b6" name="client.city"/></th>
        <td><s:text id="b6" name="client.contact.city"/></td>
</div>
<div class="form_row">
        <th><s:label for="b7" name="client.country"/></th>
        <td><s:select name="client.contact.country">
                <s:options-collection collection="${actionBean.countries}" style="width:20px;" />
            </s:select></td>
</div>
</table>