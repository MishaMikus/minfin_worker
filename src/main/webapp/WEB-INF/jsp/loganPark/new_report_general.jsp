<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<jsp:include page="menu.jsp"/>
<br>
Історія<br>
<c:forEach items="${weeklyReport.weekLinksList}" var="weekLink">
    тиджень ${weekLink.id} : <a href="/logan_park/new_report_general/${weekLink.href}">${weekLink.label}</a>
    <br>
</c:forEach>
Водії на автомобілях компанії
<c:forEach items="${weeklyReport.driverStatList}" var="driverStat">
    <table class="table-all-borders">
        <tbody>
        <tr>
            <th colspan=2>${driverStat.driverName}</th>
            <th></th>
            <th></th>
            <th>${driverStat.plan}</th>
            <th>${driverStat.rate}</th>
        </tr>
        <tr>
            <th>таксі</th>
            <th>вал</th>
            <th>готівка</th>
            <th>чайові</th>
            <th>вал_водія</th>
            <th>решту</th>
        </tr>
        <tr>
            <td>BOLT</td>
            <td>${driverStat.boltStat.amount}</td>
            <td>${driverStat.boltStat.cash}</td>
            <td>${driverStat.boltStat.tips}</td>
            <td>${driverStat.boltStat.salary}</td>
            <td>${driverStat.boltStat.change}</td>
        </tr>
        <tr>
            <td>UBER</td>
            <td>${driverStat.uberStat.amount}</td>
            <td>${driverStat.uberStat.cash}</td>
            <td>${driverStat.uberStat.tips}</td>
            <td>${driverStat.uberStat.salary}</td>
            <td>${driverStat.uberStat.change}</td>
        </tr>
        <tr>
            <td>UKLON</td>
            <td>${driverStat.uklonStat.amount}</td>
            <td>${driverStat.uklonStat.cash}</td>
            <td>${driverStat.uklonStat.tips}</td>
            <td>${driverStat.uklonStat.salary}</td>
            <td>${driverStat.uklonStat.change}</td>
        </tr>
        <tr>
            <td>838</td>
            <td>${driverStat.stat838.amount}</td>
            <td>${driverStat.stat838.cash}</td>
            <td>${driverStat.stat838.tips}</td>
            <td>${driverStat.stat838.salary}</td>
            <td>${driverStat.stat838.change}</td>
        </tr>
        <tr>
            <th></th>
            <th>${driverStat.sum.amount}</th>
            <th>${driverStat.sum.cash}</th>
            <td>${driverStat.sum.tips}</td>
            <th>${driverStat.sum.salary}</th>
            <th>${driverStat.sum.change}</th>
        </tr>
        </tbody>
    </table>
    <br>
</c:forEach>

Партнери
<br>
<c:forEach items="${weeklyReport.driverOwnerStatMap}" var="entry">
    ${entry.key}

    <table class="table-all-borders">
        <tbody>
        <tr>
            <th></th>
            <th></th>
            <th colspan="3">убер</th>
            <th colspan="3">болт</th>
            <th colspan="3">уклон</th>
            <th colspan="5">разом</th>
        </tr>
        <tr>
            <th>Партнер</th>
            <th>Водій</th>

            <th>вал</th>
            <th>готівка</th>
            <th>чайові</th>

            <th>вал</th>
            <th>готівка</th>
            <th>чайові</th>

            <th>вал</th>
            <th>готівка</th>
            <th>чайові</th>

            <th>вал</th>
            <th>готівка</th>
            <th>чайові</th>

            <th>комісія</th>
            <th>на виведення</th>
        </tr>
        <c:forEach items="${entry.value}" var="value">
            <tr>

                <td>${value.partner}</td>
                <td>${value.name}</td>

                <td>${value.uber_stat.amount}</td>
                <td>${value.uber_stat.cash}</td>
                <td>${value.uber_stat.tips}</td>

                <td>${value.bolt_stat.amount}</td>
                <td>${value.bolt_stat.cash}</td>
                <td>${value.bolt_stat.tips}</td>

                <td>${value.uklon_stat.amount}</td>
                <td>${value.uklon_stat.cash}</td>
                <td>${value.uklon_stat.tips}</td>

                <td>${value.general_stat.amount}</td>
                <td>${value.general_stat.cash}</td>
                <td>${value.general_stat.tips}</td>
                <td>${value.general_stat.commission}</td>
                <th>${value.general_stat.withdraw}</th>

            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
</c:forEach>

</body>
</html>