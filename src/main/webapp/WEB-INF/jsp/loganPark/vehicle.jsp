<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="vehicleTable" scope="session" type="java.util.List<server.logan_park.view.vehicle.VehicleView>"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<jsp:include page="menu.jsp" />
<table class="table-all-borders">
    <tbody>
    <tr>
        <th>plateNumber</th>
        <th>Name</th>
        <th>OKKO_CARD</th>
        <th>UPG_CARD</th>
    </tr>
    <c:forEach items="${vehicleTable}" var="vehicle">
        <tr>

            <td>${vehicle.plate}</td>
            <td>${vehicle.name}</td>
            <td>
                <form
                        action="${pageContext.request.contextPath}/logan_park/update_vehicle"
                        method="post"
                        enctype="application/x-www-form-urlencoded"
                        id="form${vehicle.plate}_okko">
                    <label>
                        <input class="semafor-input"
                               type="text"
                               name="card"
                               value="${vehicle.okkoCard}"
                               data-status="${vehicle.okkoCard!=null}"
                               onchange="$('#form${vehicle.plate}_okko').submit()">
                    </label>
                    <input type="hidden" name="plate" value=${vehicle.plate}>
                    <input type="hidden" name="station" value="okko">
                </form>
            </td>
            <td>
                <form
                        action="${pageContext.request.contextPath}/logan_park/update_vehicle"
                        method="post"
                        enctype="application/x-www-form-urlencoded"
                        id="form${vehicle.plate}_upg">
                    <label>
                        <input class="semafor-input"
                               type="text"
                               name="card"
                               value="${vehicle.upgCard}"
                               data-status="${vehicle.upgCard!=null}"
                               onchange="$('#form${vehicle.plate}_upg').submit()">
                    </label>
                    <input type="hidden" name="plate" value=${vehicle.plate}>
                    <input type="hidden" name="station" value="upg">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
Добавити автомобіль
<form
        action="${pageContext.request.contextPath}/logan_park/new_vehicle"
        method="post"
        enctype="application/x-www-form-urlencoded">
<table class="table-all-borders">
    <tbody>
    <tr>
        <th>plateNumber</th>
        <th>Name</th>
        <th>OKKO_CARD</th>
        <th>UPG_CARD</th>
    </tr>
        <tr>
            <td> <label><input type="text" name="plate"></label></td>
            <td> <label><input type="text" name="name"></label></td>
            <td> <label><input type="text" name="okkoCard"></label></td>
            <td> <label><input type="text" name="upgCard"></label></td>
        </tr>
    </tbody>
</table>
    <input type="submit">
</form>
</body>
</html>