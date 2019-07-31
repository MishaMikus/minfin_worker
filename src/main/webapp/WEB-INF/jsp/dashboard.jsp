<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="transactionTable" scope="session" type="java.util.List<server.dashboard.view.TransactionView>"/>
<jsp:useBean id="lastTransactionView" scope="session" type="server.dashboard.view.TransactionView"/>
<jsp:useBean id="tradeStatus" scope="session" type="server.dashboard.view.TradeStatusView"/>
<jsp:useBean id="dealTable" scope="session" type="java.util.List<server.dashboard.view.DealView>"/>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<body>
<a href="trade">
    <button style="background-color: ${tradeStatus.buttonColor}">${tradeStatus.buttonLabel}</button>
</a>
<br>
${tradeStatus.message}
<form method="post" action="add" id="addForm">
    <table border="1">
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
            </c:forEach>
        </c:if>

        <c:if test="${lastTransactionView.id!=null}">
            <tr>
                <td>${lastTransactionView.date}</td>
                <td>${lastTransactionView.transaction}</td>
                <td>${lastTransactionView.course}</td>
                <td>${lastTransactionView.usd_change}</td>
                <td>${lastTransactionView.uah_change}</td>
                <td>${lastTransactionView.usd}</td>
                <td>${lastTransactionView.uah}</td>
                <td>
                    <div role="button" onclick="window.location='delete/${lastTransactionView.id}'"
                         style="background-color: lightsalmon">del
                    </div>
                </td>
            </tr>
        </c:if>

        <tr>
            <td bgcolor="#d3d3d3"></td>
            <td>
                <label>
                    <select name="type">
                        <option value="invest_usd">invest_usd</option>
                        <option value="invest_uah">invest_uah</option>
                        <option value="usd-uah">usd-uah</option>
                        <option value="uah-usd">uah-usd</option>
                    </select>
                </label>
            </td>
            <td><label>
                <input placeholder="26.1" name="course" type="text">
            </label></td>
            <td><label>
                <input placeholder="-1000" name="usd_action" type="text">
            </label></td>
            <td><label>
                <input placeholder="+26100" name="uah_action" type="text">
            </label></td>
            <td bgcolor="#d3d3d3"></td>
            <td bgcolor="#d3d3d3"></td>
            <td>
                <div role="button" onclick="document.getElementById('addForm').submit();"
                     style="background-color: lightgreen">add
                </div>
            </td>
        </tr>
    </table>
</form>
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
        <th>переглядів</th>
        <th>статус</th>
    </tr>

    <c:forEach items="${dealTable}" var="dealView">
        <tr>
            <td>${dealView.date}</td>
            <td>${dealView.course}</td>
            <td>${dealView.type}</td>
            <td>${dealView.usd}</td>
            <td>${dealView.views}</td>
            <td>${dealView.status}</td>
        </tr>
    </c:forEach>
</table>

<form method="post" action="price" id="priceForm">
    <label>
        <input name="price_sell" type="text" value="${price_sell}">
        <input name="price_buy" type="text" value="${price_buy}">
    </label>
    <div role="button" onclick="document.getElementById('priceForm').submit();">
        update price
    </div>
</form>

</body>
</html>