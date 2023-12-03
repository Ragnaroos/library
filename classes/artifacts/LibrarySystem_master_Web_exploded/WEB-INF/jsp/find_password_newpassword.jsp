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
        <h3 class="panel-title" style="color: #fff;">输入新密码</h3>
    </div>
    <div class="panel-body">


        <div class="form-group">
            <label>密码</label>
            <input type="password" id="newPasswd" name="newPasswd" placeholder="输入密码"
                   class="form-control">
        </div>

        <div class="form-group">
            <label>确认密码</label>
            <input type="password" id="reNewPasswd" name="reNewPasswd" placeholder="再次输入密码"
                   class="form-control" >
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
        var passwd = $("#newPasswd").val();

        if( $("#newPasswd").val() !== $("#reNewPasswd").val() && $("#newPasswd").val() !== "" && $("#reNewPasswd").val() !== "" ){
            $("#info").text("提示：两次输入的密码不同！");
        }
        else if($("#newPasswd").val() === "" || $("#reNewPasswd").val() === ""){
            $("#info").text("提示：密码不能为空！");
        }
        else {
            $("#info").text("");
            $.ajax({
                type: "POST",
                url: "/api/password_new",
                data: {
                    newPasswd: passwd
                },
                dataType: "json",
                success: function(data) {
                    if (data.stateCode.trim() === "0") {
                        $("#info").text("提示:修改失败！");
                    } else if (data.stateCode.trim() === "1") {
                        $("#info").text("提示:修改成功！");
                        window.location.href="/reader_main.html";
                    }
                }
            });
        }
    })
</script>


</body>
</html>

