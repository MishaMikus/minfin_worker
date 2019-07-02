<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="transactionTable" scope="session" type="java.util.List<server.views.dashboard.TransactionView>"/>
<jsp:useBean id="lastTransactionView" scope="session" type="server.views.dashboard.TransactionView"/>
<jsp:useBean id="tradeStatus" scope="session" type="server.views.dashboard.TradeStatusView"/>
<jsp:useBean id="dealTable" scope="session" type="java.util.List<server.views.dashboard.DealView>"/>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<body>
<table border="1">
    <a href="/trade">
        <button style="background-color: ${tradeStatus.buttonColor}">${tradeStatus.buttonLabel}</button>
    </a>
    ${tradeStatus.message}
    <tr>
        <th colspan="8">TRANSACTION TABLE</th>
    </tr>
    <tr>
        <th>дата</th>
        <th>транзакція</th>
        <th>курс (грн за 1 дол)</th>
        <th>рух долар</th>
        <th>рух гривні</th>
        <th>каса-долар</th>
        <th>каса-гривня</th>
        <th bgcolor="#d3d3d3"></th>
    </tr>
    <c:if test="${transactionTable!=null}">
        <c:forEach items="${transactionTable}" var="transactionView">
            <tr>
                <td>${transactionView.date}</td>
                <td>${transactionView.transaction}</td>
                <td>${transactionView.course}</td>
                <td>${transactionView.usd_change}</td>
                <td>${transactionView.uah_change}</td>
                <td>${transactionView.usd}</td>
                <td>${transactionView.uah}</td>
                <td bgcolor="#d3d3d3"></td>
            </tr>
        </c:forEach></c:if>
    <c:if test="${lastTransactionView!=null}">
    <tr>
        <td>${lastTransactionView.date}</td>
        <td>${lastTransactionView.transaction}</td>
        <td>${lastTransactionView.course}</td>
        <td>${lastTransactionView.usd_change}</td>
        <td>${lastTransactionView.uah_change}</td>
        <td>${lastTransactionView.usd}</td>
        <td>${lastTransactionView.uah}</td>
        <td>
            <a href="/delete/${lastTransactionView.id}">
                <button style="background-color: lightsalmon">X</button>
            </a>
        </td>
    </tr>
    </c:if>
    <tr>
        <td bgcolor="#d3d3d3"></td>
        <td>
            <label>
                <select>
                    <option value="invest_usd">invest_usd</option>
                    <option value="invest_uah">invest_uah</option>
                    <option value="usd-uah">usd-uah</option>
                    <option value="uah-usd">uah-usd</option>
                    <%--                    invest - course input disabled--%>
                    <%--                    usd-ush - usd_move input dissabled--%>
                    <%--                    uah-usd - uah_move input dissabled--%>
                </select>
            </label>
        </td>
        <td><label>
            <input type="text">
        </label></td>
        <td><label>
            <input type="text">
        </label></td>
        <td><label>
            <input type="text">
        </label></td>
        <td bgcolor="#d3d3d3"></td>
        <td bgcolor="#d3d3d3"></td>
        <td>
            <a href="/add">
                <button style="background-color: lightgreen">+</button>
            </a>
        </td>
    </tr>
</table>
<br>
<table border="1">
    <tr>
        <th colspan="5">DEAL TABLE</th>
    </tr>
    <tr>
        <th>дата</th>
        <th>курс (грн за 1 дол)</th>
        <th>тип</th>
        <th>кількість</th>
    </tr>
    <c:forEach items="${dealTable}" var="dealView">
        <tr>
            <td>${dealView.date}</td>
            <td>${dealView.course}</td>
            <td>${dealView.type}</td>
            <td>${dealView.usd}</td>
        </tr>
    </c:forEach>
</table>


</body>
</html>