<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="driverTable" scope="session" type="java.util.List<orm.entity.logan_park.driver.UberDriver>"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<jsp:include page="menu.jsp" />
<table class="table-all-borders">
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