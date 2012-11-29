<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<td colspan="100%" class="description">
        <div class="description_div">
                <span class="description_span"><fmt:message key="hotel.address"/>:</span>
                <c:out value="${hotel.contact.address}"/>
        </div>
        <div class="description_div">
            <span class="description_span"><fmt:message key="hotel.city"/>:</span>
                <c:out value="${hotel.contact.city}"/>
        </div>
        <div class="description_div">
                <span class="description_span"><fmt:message key="hotel.country"/>:</span>
                <c:out value="${hotel.contact.country}"/>
        </div>
</td>
