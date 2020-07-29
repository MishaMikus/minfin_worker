<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="topnav" id="topnav">
    <a href="/logan_park/new_report_general">Тижневий звіт 2</a>
    <a href="/logan_park/weekly_report_general">Тижневий звіт</a>
    <a href="/logan_park/weekly_report_uber">Тижневий звіт UBER</a>
    <a href="${pageContext.request.contextPath}/logan_park/weekly_report_bolt">Тижневий звіт BOLT</a>
    <a href="${pageContext.request.contextPath}/logan_park/week_report_manual_uber">Ручний тижневий звіт UBER</a>
    <a href="${pageContext.request.contextPath}/logan_park/filling_report">Паливний звіт</a>
    <a href="${pageContext.request.contextPath}/logan_park/driver">Водії</a>
    <a href="${pageContext.request.contextPath}/logan_park/one_time_sms_code">СМС</a>
    <a href="${pageContext.request.contextPath}/logan_park/uber_captcha">Капча</a>
    <a href="${pageContext.request.contextPath}/logan_park/vehicle">Автомобілі</a>
</div>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
    $(document).ready(function(){
        var current = location.pathname;
        $('#topnav a').each(function(){
            var $this = $(this);
            // if the current path is like this link, make it active
            if(current.indexOf($this.attr('href')) !== -1){
                $this.addClass('active');
            }
        })
    })
</script>