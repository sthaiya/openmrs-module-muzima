<spring:htmlEscape defaultHtmlEscape="true" />
<ul id="menu">
	<li class="first">
        <a href="${pageContext.request.contextPath}/admin">
            <spring:message code="admin.title.short" />
        </a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/module/muzima/view.list#/datasource">
            <spring:message code="muzima.view.source" />
        </a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/module/muzima/view.list#/queue">
            <spring:message code="muzima.view.queue" />
        </a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/module/muzima/view.list#/error">
            <spring:message code="muzima.view.error" />
        </a>
    </li>
	<!-- Add further links here -->
</ul>
<h2>
	<spring:message code="muzimaconsultation.title" />
</h2>
