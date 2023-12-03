<%--
  Created by IntelliJ IDEA.
  User: mary
  Date: 2023/11/18
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>找回密码</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
    <script src="js/js.cookie.js"></script>
    <style>
        #login{
            height: 50%;
            width: 28%;
            margin-left: auto;
            margin-right: auto;
            margin-top: 5%;
            display: block;
            position: center;
        }

        .form-group {
            margin-bottom: 0;
        }
        * {
            padding:0;
            margin:0;
        }
    </style>
</head>
<body background="img/book2.jpg" style=" background-repeat:no-repeat ;
background-size:100% 100%;
background-attachment: fixed;">
<h2 style="text-align: center; color: white; font-family: '宋体'; font-size: 500%">图书借阅管理系统</h2>

<div class="panel panel-default" id="login">
    <div class="panel-heading" style="background-color: #2e6da4">
        <h3 class="panel-title" style="color: #fff;">输入用户名</h3>
    </div>
    <div class="panel-body">
        <div class="form-group">
            <label for="user_name">账号</label>
            <input type="text" class="form-control" id="user_name" placeholder="请输入账号">
        </div>

        <p style="text-align: right;color: red;position: absolute" id="info"></p><br/>

        <div class="form-group d-flex justify-content-around">
            <button id="registerButton" class="btn btn-primary" style="width: 48%;">确定</button>
            <a href="login.html"><button type="button" class="btn btn-secondary" style="width: 48%;">返回</button></a>
        </div>
    </div>
</div>
<script>
    $("#registerButton").click(function () {
        var user_name =$("#user_name").val();

        if (user_name == '') {
            $("#info").text("提示:账号不能为空");
        }
        else {
            $("#info").text("");
            $.ajax({
                type: "POST",
                url: "/api/user_name_check",
                data: {
                    user_name:user_name
                },
                dataType: "json",
                success: function(data) {
                    if (data.stateCode.trim() === "0") {
                        $("#info").text("提示:用户不存在！");
                    } else if (data.stateCode.trim() === "1") {
                        $("#info").text("提示:用户没有填写邮箱！");
                    } else if (data.stateCode.trim() === "2") {
                        $("#info").text("提示:验证码已发送！");
                        window.location.href="/find_password_verifytoken.html";
                    }
                }
            });
        }
    })
</script>


</body>
</html>

