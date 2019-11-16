<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<jsp:include page="menu.jsp"/>
<jsp:useBean id="fillingTable" scope="request" type="server.logan_park.view.filling_report.model.FillingTable"/>
Історія<br>
<c:forEach items="${fillingTable.weekLinksList}" var="weekLink">
    тиджень ${weekLink.id} : <a href="/logan_park/filling_report/${weekLink.href}">${weekLink.label}</a>
    <br>
</c:forEach>

Залишки<br>
<table class="table-all-borders">
    <tbody>
    <tr>
        <th>OKKO</th>
        <th>${fillingTable.okkoLeftover} грн</th>
        <th>UPG</th>
        <th>${fillingTable.upgLeftover} грн</th>
    </tr>
    </tbody>
</table>

Тижнева статистика<br>
<table class="table-all-borders">
    <tbody>
    <tr>
        <th colspan=2>РАЗОМ ${fillingTable.fillingInfo.weekFilling.amount} грн
            (${fillingTable.fillingInfo.weekFilling.count} л)
        </th>
    </tr>
    <c:forEach items="${fillingTable.fillingInfo.carDistributedMap}" var="entry">
        <tr>
            <td colspan="5"></td>
        </tr>
        <tr>
            <th>${entry.key}</th>
            <th>${entry.value.amount} грн</th>
            <th>${entry.value.count} л</th>
        </tr>

        <tr>
            <th>початок</th>
            <th>кінець</th>
            <th>пробіг км</th>
            <th>витрати л</th>
            <th>розхід л/(100 км)</th>
        </tr>
        <c:forEach items="${entry.value.fuelCostsList}" var="fuelCosts">
            <tr>
                <td>${fuelCosts.dateLabelStart.label}</td>
                <td>${fuelCosts.dateLabelEnd.label}</td>
                <td>${fuelCosts.distance}</td>
                <td>${fuelCosts.gasAmount}</td>
                <td>${fuelCosts.cost}</td>
            </tr>
        </c:forEach>
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
                <td>
                    <form
                            action="${pageContext.request.contextPath}/logan_park/save_km"
                            method="post"
                            enctype="application/x-www-form-urlencoded"
                            id="form${fillingView.date.time}">
                        <label>
                            <input class="semafor-input"
                                   type="text"
                                   name="km"
                                   value="${fillingView.km}"
                                   data-status="${fillingView.km!=null}"
                                   onchange="$('#form${fillingView.date.time}').submit()">
                        </label>
                        <input type="hidden" name="date" value=${fillingView.date.time}>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </c:forEach>
    </tbody>
</table>
</body>
</html>