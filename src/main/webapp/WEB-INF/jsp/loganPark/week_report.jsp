<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>

<form id="updateWeekReportRequestForm" method="post" action="/updateWeekReportRequest">
    <%--@elvariable id="latestUpdateDate" type="java.util.String"--%>
    <%--@elvariable id="latestUpdateDuration" type="java.lang.Long"--%>
        <%--@elvariable id="updateStatus" type="java.lang.Long"--%>
    <button class="btn-update-${updateStatus}" type="submit" form="updateWeekReportRequestForm" value="Submit">
        UPDATE [${latestUpdateDate}] [${latestUpdateDuration/1000} s]
    </button>
</form>

<%--@elvariable id="weekHashLabel" type="java.lang.Integer"--%>
weekHashLabel : ${weekHashLabel}
<br>

<%--@elvariable id="paymentTable" type="java.util.HashMap<java.lang.String,server.logan_park.helper.model.PaymentDriverRecord>"--%>
<%--<c:if test="${!empty sessionScope.paymentTable}">--%>
    <c:forEach items="${paymentTable}" var="entry">
        <table class="table-all-borders" >
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
                <th colspan="3">${entry.value.summary.formula}</th>
                <th colspan="2">Видати на руки : </th>
                <th>${entry.value.summary.salaryWithTips}</th>
                <th>${entry.value.summary.changeWithoutTips}</th>
            </tr>

            </tbody>
        </table>
        <br>
    </c:forEach>
<%--</c:if>--%>

<%--@elvariable id="ownerTable" type="java.util.HashMap<java.lang.String,server.logan_park.helper.model.PaymentOwnerRecord>"--%>
<%--<c:if test="${!empty sessionScope.ownerTable}">--%>
    <c:forEach items="${ownerTable}" var="entry">
        <table class="table-all-borders">
            <tbody>
            <tr>
                <th colspan="6">${entry.key}</th>
                <th colspan="4">На виведення : ${entry.value.ownerPaymentViews.withdraw}</th>
            </tr>
            <tr>
                <th>період</th>
                <th>відсоток комісії</th>
                <th>чистий дохід</th>
                <th>комісія</th>
                <th>безготівка</th>
                <th>кількість поїздок</th>
                <th>дохід</th>
                <th>готівка</th>
                <th>чайові</th>
                <th>промо</th>

            </tr>
                <tr>
                    <td>${entry.value.ownerPaymentViews.dateRangeName}</td>
                    <td>${entry.value.ownerPaymentViews.taxPercentage}%</td>
                    <td>${entry.value.ownerPaymentViews.amountMinusCommission}</td>
                    <td>${entry.value.ownerPaymentViews.commission}</td>
                    <td>${entry.value.ownerPaymentViews.nonCash}</td>
                    <td><div class="block-item-text">
                        <input type="checkbox" hidden class="read-more-state" id="${entry.value.ownerPaymentViews.tripListId}">
                        <div class="read-more-wrap">
                            <button>
                                <label for="${entry.value.ownerPaymentViews.tripListId}" class="read-more-trigger_closed">
                                        ${entry.value.ownerPaymentViews.count}
                                </label>
                                <label for="${entry.value.ownerPaymentViews.tripListId}" class="read-more-trigger_opened">
                                        ${entry.value.ownerPaymentViews.count}
                                </label>
                            </button>
                            <p class="read-more-target">
                                <c:forEach items="${entry.value.ownerPaymentViews.tripList}" var="tripView">
                                    <a href="https://partners.uber.com/p3/payments/trips/${tripView.id}">${tripView.text}</a><br>
                                </c:forEach>
                            </p>
                        </div>
                    </div></td>

                    <td>${entry.value.ownerPaymentViews.amount}</td>
                    <td>${entry.value.ownerPaymentViews.cash}</td>
                    <td>${entry.value.ownerPaymentViews.tips}</td>
                    <td>${entry.value.ownerPaymentViews.promotion}</td>
                </tr>
            </tbody>
        </table>
        <br>
    </c:forEach>
<%--</c:if>--%>

<%--@elvariable id="generalPartnerSummary" type="server.logan_park.helper.model.GeneralPartnerSummary"--%>
<%--<c:if test="${!empty sessionScope.generalPartnerSummary.profit}">--%>

    <table class="table-all-borders">
        <tbody>
        <tr>
            <th>прибуток</th>
            <th>готівка</th>
            <th>прихід</th>
            <th>податок</th>
            <th>виплати</th>
            <th>чистий залишок</th>
        </tr>
        <tr>

            <td>${generalPartnerSummary.profit}</td>
            <td>${generalPartnerSummary.cash}</td>
            <td>${generalPartnerSummary.transfer}</td>
            <td>${generalPartnerSummary.tax}</td>
            <td>${generalPartnerSummary.withdraw}</td>
            <td>${generalPartnerSummary.realProfit}</td>
        </tr>
        </tbody>
    </table>
<%--</c:if>--%>
</body>
</html>