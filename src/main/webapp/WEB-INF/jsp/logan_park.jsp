<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="fileName" scope="session" type="java.lang.String"/>
<jsp:useBean id="paymentTable" scope="session"
             type="java.util.HashMap<java.lang.String,server.logan.park.service.PaymentDriverRecord>"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
<form method="post" action="logan_park/upload_payment_csv" enctype="multipart/form-data">
    <input type="file" name="payment_file">
    <input type="submit">
</form>
<c:if test="${!empty sessionScope.paymentTable}">
    ${fileName}
    <br>
    <c:forEach items="${paymentTable}" var="entry">
        <table border="1">
            <tbody>
            <tr>
                <th colspan=10>${entry.key}</th>
                <th>${entry.value.summary.rate}</th>
            </tr>
            <tr>
                <th>зміна</th>
                <th>старт</th>
                <th>фініш</th>
                <th>час(год)</th>
                <th>поїздок</th>
                <th>загалом</th>
                <th>чайові</th>
                <th>промо</th>
                <th>готівка</th>
                <th>зарплата</th>
                <th>решту від готівки</th>
            </tr>

            <c:forEach items="${entry.value.recordList}" var="paymentView">
                <tr>
                    <td>${paymentView.name}</td>
                    <td>${paymentView.start}</td>
                    <td>${paymentView.end}</td>
                    <td>${paymentView.duration}</td>
                    <td>

                        <div class="block-item-text">
                            <input type="checkbox" hidden class="read-more-state" id="${paymentView.tripListId}">
                            <div class="read-more-wrap">
                                <button>
                                    <label for="${paymentView.tripListId}" class="read-more-trigger_closed">
                                            ${paymentView.count}
                                    </label>
                                    <label for="${paymentView.tripListId}" class="read-more-trigger_opened">
                                            ${paymentView.count}
                                    </label>
                                </button>
                                <p class="read-more-target">
                                    <c:forEach items="${paymentView.tripList}" var="tripView">
                                        <a href="https://partners.uber.com/p3/payments/trips/${tripView.id}">${tripView.text}</a><br>
                                    </c:forEach>
                                </p>
                            </div>
                        </div>

                    </td>
                    <td>${paymentView.amount}</td>
                    <td>${paymentView.tips}</td>
                    <td>${paymentView.promotion}</td>
                    <td>${paymentView.cash}</td>
                    <td>${paymentView.salary}</td>
                    <td>${paymentView.change}</td>
                </tr>
            </c:forEach>
            <tr>
                <th colspan="3">Разом</th>
                <th>${entry.value.summary.duration}</th>
                <th>${entry.value.summary.count}</th>
                <th>${entry.value.summary.amount}</th>
                <th>${entry.value.summary.tips}</th>
                <th>${entry.value.summary.promotion}</th>
                <th>${entry.value.summary.cash}</th>
                <th>${entry.value.summary.salary}</th>
                <th>${entry.value.summary.change}</th>
            </tr>

            <tr>
                <th>грн за годину</th>
                <th>${entry.value.summary.uahPerHour}</th>
                <th>гривень за поїздку</th>
                <th>${entry.value.summary.uahPerTrip}</th>
                <th colspan="3"></th>
                <th colspan="2">Видати на руки : </th>
                <th>${entry.value.summary.salaryWithTips}</th>
                <th>${entry.value.summary.changeWithoutTips}</th>
            </tr>

            </tbody>
        </table>
        <br>
    </c:forEach>
</c:if>
<a href="https://partners.uber.com/p3/fleet-manager/payments">https://partners.uber.com/p3/fleet-manager/payments</a><br>
<img src="${pageContext.request.contextPath}/resources/images/instruction.png"
     alt="instruction: 1. save payment to csv file; 2. upload file">
</body>
</html>