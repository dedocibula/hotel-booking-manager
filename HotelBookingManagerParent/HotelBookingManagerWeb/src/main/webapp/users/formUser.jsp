<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<s:errors/>
<div class="form_row">
        <s:label for="b0" name="user.password"/>
        <s:password id="b0" name="user.password"/>
</div>
<div class="cleaner_horizontal_divider_01">&nbsp;</div>
<div class="form_row">
        <s:label for="b1" name="client.firstName"/>
        <s:text id="b1" name="user.client.firstName"/>
</div>
<div class="form_row">
        <s:label for="b2" name="client.lastName"/>
        <s:text id="b2" name="user.client.lastName"/>
</div>
<div class="form_row">
        <s:label for="b3" name="client.phone"/>
        <s:text id="b3" name="user.client.contact.phone"/>
</div>
<div class="form_row">
        <s:label for="b4" name="client.email"/>
        <s:text id="b4" name="user.client.contact.email"/>
</div>
<div class="form_row">
        <s:label for="b5" name="client.address"/>
        <s:text id="b5" name="user.client.contact.address"/>
</div>
<div class="form_row">
        <s:label for="b6" name="client.city"/>
        <s:text id="b6" name="user.client.contact.city"/>
</div>
<div class="form_row">
        <s:label for="b7" name="client.country"/>
        <s:select name="user.client.contact.country">
                <s:options-collection collection="${actionBean.countries}" style="width:20px;" />
            </s:select>
</div>
