<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:useBean id="fileId" scope="session" type="java.lang.String"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<div class="topnav">
    <a href="/logan_park/week_report_manual">Ручний тижневий звіт</a>
    <a href="/logan_park/weekly_report">Тижневий звіт</a>
    <a href="/logan_park/filling_report">Паливний звіт</a>
    <a href="/logan_park/driver">Водії</a>
    <a href="/logan_park/one_time_sms_code">СМС</a>
    <a class="active" href="/logan_park/uber_captcha">Капча</a>
</div>
<img src="uber_captcha/img/${fileId}" alt="captcha">
</body>
</html>