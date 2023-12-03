<%--
  Created by IntelliJ IDEA.
  User: mary
  Date: 2023/11/19
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>添加单位</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
    <script>
        $(function () {
            $('#header').load('admin_header.html');
        })
    </script>
</head>
<body background="img/book2.jpg" style=" background-repeat:no-repeat ;
background-size:100% 100%;
background-attachment: fixed;">

<div id="header"></div>

<c:if test="${cha.character_name ne 'admin'}">
    <script>
        window.location.href = "login.html";
    </script>
</c:if>
<c:if test="${not empty err}">
    <script>alert('${err}');</script>
</c:if>
<c:if test="${not empty suc}">
    <script>alert('${suc}');</script>
</c:if>



<div class="col-xs-6 col-md-offset-3" style="padding-top: 100px;position: relative">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">添加单位</h3>
        </div>
        <div class="panel-body">
            <form action="work_add_do.html" method="post" id="work_add_do" >

                <div class="input-group" style="padding-top: 20px;">
                    <span class="input-group-addon">工作单位</span>
                    <input type="text" class="form-control" name="work_name" id="work_name"  required>
                </div>

                <div class="input-group" style="padding-top: 20px;">
                    <span class="input-group-addon">联系人</span>
                    <input type="text" class="form-control" name="contact" id="contact" required>
                </div>

                <div class="input-group" style="padding-top: 20px;">
                    <span class="input-group-addon">联系电话</span>
                    <input type="text" class="form-control" name="contact_phone" id="contact_phone" required>
                </div>

                <div class="input-group" style="padding-top: 20px;">
                    <span class="input-group-addon">电子邮箱</span>
                    <input type="email" class="form-control" name="contact_email" id="contact_email" required>
                </div>

                <div class="input-group" style="padding-top: 20px;">
                    <span class="input-group-addon">联系地址</span>
                    <input type="text" class="form-control" name="contact_address" id="contact_address" required>
                </div>

                <div class="input-group" style="padding-top: 20px;">
                    <span class="input-group-addon">单位性质</span>
                    <input type="text" class="form-control" name="work_nature" id="work_nature" required>
                </div>

                <div class="input-group" style="padding-top: 20px;">
                </div>
                <input style="align-items: center" type="submit" value="添加" class="btn btn-success btn-sm"
                       class="text-left">
            </form>
        </div>
    </div>

</div>

</body>
</html>

