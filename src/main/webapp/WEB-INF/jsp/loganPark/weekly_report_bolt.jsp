<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<jsp:include page="menu.jsp"/>
<%--@elvariable id="weeklyReportBolt" type="server.logan_park.view.weekly_report_bolt.model.WeeklyReportBolt"--%>

<c:forEach items="${weeklyReportBolt.driverStatList}" var="driverStat">
    <table border="1">
        <tbody>
        <tr>
            <th colspan=2>${driverStat.driverName}</th>
            <th colspan=2>${driverStat.plan}</th>
            <th>${driverStat.rate}</th>
        </tr>
        <tr>
            <th>зміна</th>
            <th>вал</th>
            <th>готівка</th>
            <th>зарплата</th>
            <th>решту</th>
        </tr>

        <c:forEach items="${driverStat.workoutList}" var="workout">
            <tr>
                <td>${workout.name}</td>
                <td>${workout.amount}</td>
                <td>${workout.cash}</td>
                <td>${workout.salary}</td>
                <td>${workout.change}</td>
            </tr>
        </c:forEach>
        <tr>
            <th>${driverStat.workoutCount}</th>
            <th>${driverStat.amount}</th>
            <th>${driverStat.cash}</th>
            <th>${driverStat.salary}</th>
            <th>${driverStat.change}</th>
        </tr>
        </tbody>
    </table>
    <br>
</c:forEach>

<table border="1">
    <tbody>
    <tr>
        <th>Загалький вал компанії</th>
        <th>Чистий дохід компанії</th>
    </tr>
    <tr>
        <th>${weeklyReportBolt.generalAmount}</th>
        <th>${weeklyReportBolt.generalProfit}</th>
    </tr>
    </tbody>
</table>
</body>
</html>