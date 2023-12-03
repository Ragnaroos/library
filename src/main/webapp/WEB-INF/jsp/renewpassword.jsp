<%--
  Created by IntelliJ IDEA.
  User: mary
  Date: 2023/11/18
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>邮箱验证</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
    <script>
        $(function () {
            var cha =  '<c:out value="${cha.character_name}"/>';
            if (cha === "admin") {
                $('#header').load('admin_header.html');
            }
            else if(cha === "staff"){
                $('#header').load('staff_header.html');
            }
            else if(cha === "reader"){
                $('#header').load('reader_header.html');
            }
        })
    </script>
</head>
<body background="img/book2.jpg" style=" background-repeat:no-repeat ;
background-size:100% 100%;
background-attachment: fixed;">

<div id="header"></div>
<c:if test="${not empty vertifyError}">
    <script>alert('${vertifyError}');</script>
</c:if>

<div class="col-xs-6 col-md-offset-3" style="padding-top: 100px;position: relative">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">修改密码-请输入新密码</h3>
        </div>
        <div class="panel-body">
            <form action="updatePassword" method="post" id="updatePassword">

                <div class="form-group">
                    <label>新密码</label>
                    <input type="password" id="newPasswd" name="newPasswd" placeholder="输入新密码"
                           class="form-control">
                </div>

                <div class="form-group">
                    <label>再次确认新密码</label>
                    <input type="password" id="reNewPasswd" name="reNewPasswd" placeholder="再次输入新密码"
                           class="form-control" >
                </div>


                <input style="align-items: center" type="submit" value="确认" class="btn btn-success btn-sm"
                       class="text-left">
                <script>

                    function mySubmit(flag){
                        return flag;
                    }
                    $("#updatePassword").submit(function () {
                        if( $("#newPasswd").val() !== $("#reNewPasswd").val() && $("#newPasswd").val() !== "" && $("#reNewPasswd").val() !== ""){
                            alert("两次输入的密码不一致！");
                            return mySubmit(false);
                        }
                        else if($("#newPasswd").val() === "" || $("#reNewPasswd").val() === ""){
                            alert("密码不能为空！");
                            return mySubmit(false);
                        }
                    })
                </script>
            </form>


        </div>
    </div>
</div>
<c:if test="${empty user}">
    <script>
        window.location.href = "login.html";
    </script>
</c:if>

</body>
</html>

