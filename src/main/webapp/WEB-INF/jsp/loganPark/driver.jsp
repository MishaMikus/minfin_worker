<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="driverTable" scope="session" type="java.util.List<orm.entity.uber.driver.UberDriver>"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<div class="topnav">
    <a href="/logan_park/week_report_manual">Ручний тижневий звіт</a>
    <a href="/logan_park/weekly_report">Тижневий звіт</a>
    <a href="/logan_park/filling_report">Паливний звіт</a>
    <a class="active" href="/logan_park/driver">Водії</a>
    <a href="/logan_park/one_time_sms_code">СМС</a>
    <a href="/logan_park/uber_captcha">Капча</a>
</div>
<table border="1">
    <tbody>
    <tr>
        <th>id</th>
        <th>driverUUID</th>
        <th>driverType</th>
        <th>name</th>
    </tr>
    <c:forEach items="${driverTable}" var="driver">
        <tr>

            <td>${driver.id}</td>
            <td>${driver.driverUUID}</td>
            <td>${driver.driverType}</td>
            <td>${driver.name}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>