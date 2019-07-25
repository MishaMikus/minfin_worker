<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="paymentView" scope="session" type="server.logan.park.view.PaymentView"/>
<html>
<body>
<form method="post" action="logan_park/upload_payment_csv" enctype="multipart/form-data">
<input type="file" name="payment_file" >
<input type="submit">
</form>

<c:if test="${!empty sessionScope.paymentView}">

${paymentView.name}
<br>

${paymentView.report}

</c:if>
<a href="https://partners.uber.com/p3/fleet-manager/payments">https://partners.uber.com/p3/fleet-manager/payments</a><br>
<img src="${pageContext.request.contextPath}/resources/images/instruction.png" alt="instruction: 1. save payment to csv file; 2. upload file">
</body>
</html>