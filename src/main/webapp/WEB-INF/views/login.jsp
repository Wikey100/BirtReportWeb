<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    <link href="webjars/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <style>
        body, h1, h2, h3 {
            font-family: "微软雅黑", "Helvetica Neue", Helvetica, Arial, sans-serif;
        }

        .login_page {
            background: url(images/login/login_bg.png) #fcfcfc;
        }

        .login_logo {
            text-align: center;
            margin: 30px auto 30px;
        }

        .login_logo h1 {
            text-shadow: 0 2px 1px #ccc;
            color: #444;
            font-weight: bold;
        }

        .login_logo h1 small {
            display: block;
            line-height: 160%;
        }

        .login_top {
            clear: both;
            height: 10%;
        }

        .login_content {
            margin: 30px auto 30px;
            background-color: #fefefe;
            max-width: 520px;
            border: 1px solid #c4c4c4;
            height: auto;
            border-radius: 5px;
            -moz-border-radius: 5px;
            -webkit-border-radius: 5px;
            -webkit-box-shadow: 0 0 1px 1px rgba(0, 0, 0, 0.1);
            -moz-box-shadow: 0 0 1px 1px rgba(0, 0, 0, 0.1);
            box-shadow: 0 0 1px 1px rgba(0, 0, 0, 0.1);
            padding: 0 20px 0 20px;
        }

        .login_content .form-title {
            padding-bottom: 10px;
            color: #666;
            font-weight: bold;
        }
    </style>

</head>
<body class="login_page" style='display: none'>
<div id="wrap">
    <div class="container">
        <div class="login_top"></div>
        <div class="login_logo">
            <h1>报表系统</h1>
        </div>
        <div class="login_content tooltip-show popover-show">
            <form id="loginForm" class="login-form" action="${pageContext.servletContext.contextPath}/login" method="post">
                <h3 class="form-title">用户登录</h3>
                <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                    <div class="alert alert-danger">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        登录失败，请重试.
                    </div>
                </c:if>
                <div class="alert alert-danger" id="username_tip" style="display: none">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    请输入账号
                </div>
                <div class="alert alert-danger" id="password_tip" style="display: none">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    请输入密码
                </div>
                <div class="form-group ">
                    <div class="input-group input-group-lg">
                        <div class="input-group-addon"><span class="glyphicon glyphicon-user"></span> 帐号：</div>
                        <label for="username"></label><input type="text" id='username' name='username' class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group input-group-lg">
                        <label class="input-group-addon"><span class="glyphicon glyphicon-lock"></span> 密码：</label>
                        <label for="password"></label><input type="password" id='password' name='password'
                                                             class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <button id="submitBtn" onclick="return false;" class="btn btn-primary btn-lg btn-block"><span
                            class="glyphicon glyphicon-ok"></span> 登 录
                    </button>
                </div>
            </form>

        </div>
    </div>
</div>
<script src="webjars/jquery/2.1.4/jquery.js" type="text/javascript"></script>
<script src="webjars/bootstrap/3.3.5/js/bootstrap.min.js" type="text/javascript"></script>
<script src="webjars/nprogress/0.2.0/nprogress.js"></script>
<script src="js/support/utils.js" type="text/javascript"></script>
<script type="text/javascript">
    $('body').show();
    utils.loading();

    $('#submitBtn').on('click', function() {
        if ($('#username').val() == '') {
            $('#username_tip').show();
            return false;
        } else if ($('#password').val() == '') {
            $('#username_tip').hide();
            $('#password_tip').show();
            return false;
        }
        utils.loading();
        $('#loginForm').submit();
    });

</script>
</body>
</html>