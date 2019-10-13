<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<jsp:include page="menu.jsp" />
<%--@elvariable id="smsReceiver" type="server.logan_park.view.one_time_sms_reseiver.model.SMSReceiverFormModel"--%>
<form:form method="POST" action="/one_time_sms_code" modelAttribute="smsReceiver">
    <form:label path="code">SMS-code</form:label>
    <form:input path="code" />
    <input type="submit" value="Submit" />
</form:form>
</body>
</html>