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

</body>
</html>