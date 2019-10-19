<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

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
        <th colspan=2>РАЗОМ ${fillingTable.fillingInfo.weekFilling.amount} грн
            (${fillingTable.fillingInfo.weekFilling.count} л)
        </th>
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
            <th colspan=8>
                    ${entry.key.label}
            </th>
        </tr>
        <tr>
            <th>час</th>
            <th>автомобіль</th>
            <th>літрів</th>
            <th>ціна</th>
            <th>ціна/л</th>
            <th>мережа</th>
            <th>магазин</th>
            <th>адреса</th>
            <th>кілометраж</th>
        </tr>
        <c:forEach items="${entry.value}" var="fillingView">
            <tr>
                <td>${fillingView.time}</td>
                <td>${fillingView.car}</td>
                <td>${fillingView.itemAmount}</td>
                <td>${fillingView.amount}</td>
                <td>${fillingView.realPrice}</td>
                <td>${fillingView.station}</td>
                <td>${fillingView.shop}</td>
                <td>${fillingView.address}</td>
                <td>${fillingView.km}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/logan_park/save_km" method="post" enctype="application/x-www-form-urlencoded">
                        <label>
                            <input type="text" name="km" value="${fillingView.km}">
                        </label>
                        <input type="hidden" name="date" value=${fillingView.date.time}>
                        <input type="submit" value="Save">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </c:forEach>
    </tbody>
</table>
</body>
</html>