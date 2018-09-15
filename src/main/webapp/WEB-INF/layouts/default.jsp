<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html >
<html>
<head>
    <title>报表系统</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <link href="webjars/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="webjars/bootstrap/3.3.5/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
    <link href="webjars/bootstrap3-dialog/1.34.4/dist/css/bootstrap-dialog.min.css" rel="stylesheet" type="text/css">
    <link href="webjars/nprogress/0.2.0/nprogress.css" rel="stylesheet" type="text/css"/>
    <link href="js/plugin/datePicker/themes/default.css" rel="stylesheet" type="text/css"/>
    <link href="js/plugin/datePicker/themes/default.date.css" rel="stylesheet" type="text/css"/>
    <link href="js/plugin/timePicker/css/timePicker.css" rel="stylesheet" type="text/css"/>
    <link href="css/default.css" rel="stylesheet" type="text/css">
    <script src="webjars/jquery/2.1.4/jquery.js"></script>
    <script src="webjars/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="webjars/bootstrap3-dialog/1.34.4/dist/js/bootstrap-dialog.min.js"></script>
    <script src="webjars/nprogress/0.2.0/nprogress.js"></script>
    <script src="js/plugin/datePicker/legacy.js"></script>
    <script src="js/plugin/datePicker/picker.js"></script>
    <script src="js/plugin/datePicker/picker.date.js"></script>
    <script src="js/plugin/timePicker/js/rocketHelpers.js"></script>
    <script src="js/plugin/timePicker/js/timePicker.js"></script>
    <script type="text/javascript">
        function isExit() {
            if (confirm('确定注销登录吗?')) {
                window.location.href = "/logout";
                return false;
            }
        }
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('ul.nav li').hover(function () {
                $(this).find('.dropdown-menu:first').stop(true, true).delay(50).fadeIn();
            }, function () {
                $(this).find('.dropdown-menu:first').stop(true, true).delay(50).fadeOut();
            });
            $('ul.nav li').click(function () {
                $('ul.nav li').removeClass("active");
                $(this).addClass("active");
            });

            if (location.hash.indexOf("#/admin/") > 0) {
                var start = location.hash.indexOf("/", 7);
                var end = location.hash.indexOf("/", start + 1);
                var activeLi = location.hash.substring(start + 1, end);
                $('ul.nav li').removeClass("active");
                $('#' + activeLi).addClass("active");
            }

            $("#myreport").load(function () {
                var mainheight = $(this).contents().find("body").height() + 30;
                $(this).height(mainheight);
            });
        });
    </script>
</head>
<body>
<%@ include file="/WEB-INF/views/menu.jsp" %>
<%@ include file="/WEB-INF/layouts/header.jsp" %>
<iframe src="" id="myreport" name="myreport" frameborder="0" width="100%" scrolling="no" align="" height="800"></iframe>
<%@ include file="/WEB-INF/layouts/footer.jsp" %>
<script src="js/support/utils.js" type="text/javascript"></script>
</body>
</html>