<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="fileName" scope="session" type="java.lang.String"/>
<jsp:useBean id="paymentTable" scope="session" type="java.util.HashMap<java.lang.String,java.util.ArrayList<server.logan.park.view.PaymentView>>"/>
<html>
<body>
<form method="post" action="logan_park/upload_payment_csv" enctype="multipart/form-data">
<input type="file" name="payment_file" >
<input type="submit">
</form>
<c:if test="${!empty sessionScope.paymentTable}">
${fileName}
<br>
<c:forEach items="${paymentTable}" var="entry">
<table border="1">
<tbody>
 <tr>
    <td colspan=6>${entry.key}</td>
 </tr>
<tr>
    <th>зміна</th>
    <th>поїздок</th>
    <th>загалом</th>
    <th>готівка</th>
    <th>зарплата</th>
    <th>решту від готівки</th>
</tr>

<c:forEach items="${entry.value}" var="paymentView">
<tr>
    <td>${paymentView.name}</td>
    <td>${paymentView.count}</td>
    <td>${paymentView.amount}</td>
    <td>${paymentView.cash}</td>
    <td>${paymentView.salary}</td>
    <td>${paymentView.change}</td>
</tr>
</c:forEach>
</tbody>
</table>
<br>
</c:forEach>
</c:if>
<a href="https://partners.uber.com/p3/fleet-manager/payments">https://partners.uber.com/p3/fleet-manager/payments</a><br>
<img src="${pageContext.request.contextPath}/resources/images/instruction.png" alt="instruction: 1. save payment to csv file; 2. upload file">
</body>
</html>