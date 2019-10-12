<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<div class="topnav">
    <a href="/logan_park/week_report_manual">Ручний тижневий звіт</a>
    <a href="/logan_park/weekly_report">Тижневий звіт</a>
    <a class="active" href="/logan_park/filling_report">Паливний звіт</a>
    <a href="/logan_park/driver">Водії</a>
    <a href="/logan_park/one_time_sms_code">СМС</a>
    <a href="/logan_park/uber_captcha">Капча</a>
</div>
<%--@elvariable id="fillingTable" type="java.util.List<java.lang.FillingRecord>"--%>
<table class="table-all-borders">
    <tbody>
    <tr>
        <th>дата</th>
        <th>картка</th>
        <th>автомобіль</th>
        <th>літрів</th>
    </tr>

    <c:forEach items="${fillingTable}" var="fillingView">
        <tr>
            <td>${fillingView.date}</td>
            <td>${fillingView.card}</td>
            <td>${fillingView.car}</td>
            <td>${fillingView.itemAmount}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>
</body>
</html>