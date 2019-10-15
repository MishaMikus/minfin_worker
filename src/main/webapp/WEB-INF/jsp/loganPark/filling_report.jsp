<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<jsp:include page="menu.jsp"/>
Історія<br>
<c:forEach items="${fillingTable.weekLinksList}" var="weekLink">
    тиджень ${weekLink.id} : <a href="/logan_park/filling_report/${weekLink.href}">${weekLink.label}</a>
    <br>
</c:forEach>
<br>
Тижнева статистика
<br>
<table class="table-all-borders">
    <tbody>
    <tr>
        <th colspan=2>РАЗОМ ${fillingTable.fillingInfo.weekFilling.amount} грн (${fillingTable.fillingInfo.weekFilling.count} л)</th>
    </tr>
    <tr>
        <th>автомобіль</th>
        <th>грн</th>
        <th>л</th>
    </tr>
    <c:forEach items="${fillingTable.fillingInfo.carDistributedMap}" var="entry">
        <tr>
            <td>${entry.key}</td>
            <td>${entry.value.amount}</td>
            <td>${entry.value.count}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>
По днях
<br>
<table class="table-all-borders">
    <tbody>
    <c:forEach items="${fillingTable.fillingRecordMap}" var="entry">
        <tr>
            <th colspan=8>${entry.key}</th>
        </tr>
        <tr>
            <th>час</th>
            <th>автомобіль</th>
            <th>літрів</th>
            <th>ціна</th>
            <th>ціна/л</th>
            <th>ціна без знижки</th>
            <th>ціна/л без знижки</th>
            <th>адреса</th>
        </tr>
        <c:forEach items="${entry.value}" var="fillingView">
            <tr>
                <td>${fillingView.time}</td>
                <td>${fillingView.car}</td>
                <td>${fillingView.itemAmount}</td>
                <td>${fillingView.amount}</td>
                <td>${fillingView.realPrice}</td>
                <td>${fillingView.amountAndDiscount}</td>
                <td>${fillingView.price}</td>
                <td>${fillingView.address}</td>
            </tr>
        </c:forEach>
    </c:forEach>
    </tbody>
</table>
</body>
</html>