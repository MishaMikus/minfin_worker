<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<jsp:include page="menu.jsp" />
<table class="table-all-borders">
    <tbody>
    <tr>
        <th>дата</th>
        <th>автомобіль</th>
        <th>літрів</th>
        <th>ціна</th>
        <th>ціна/л</th>
        <th>адреса</th>
    </tr>

    <c:forEach items="${fillingTable}" var="fillingView">
        <tr>
            <td>${fillingView.date}</td>
            <td>${fillingView.car}</td>
            <td>${fillingView.itemAmount}</td>
            <td>${fillingView.amount}</td>
            <td>${fillingView.price}</td>
            <td>${fillingView.address}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>
</body>
</html>