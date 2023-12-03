<%--
  Created by IntelliJ IDEA.
  User: mary
  Date: 2023/11/19
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>全部用户</title>
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

<div class="col-xs-6 col-md-offset-3" style="padding-top: 100px;position: relative">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">
                ${thiswork.work_name}的详细信息
            </h3>
        </div>

        <div class="panel-body">
            <form action="admin_all_work_update_do.html" method="post" id="admin_all_work_update_do" >
                <div class="form-group">
                    <label>单位名称</label>
                    <input type="text" class="form-control" name="work_name" id="work_name" value="${thiswork.work_name}" required>
                </div>

                <div class="form-group">
                    <label>联系人</label>
                    <input type="text" class="form-control" name="contact" id="contact" value="${thiswork.contact}" required>
                </div>

                <div class="form-group">
                    <label>联系电话</label>
                    <input type="text" class="form-control" name="contact_phone" id="contact_phone" value="${thiswork.contact_phone}" required>
                </div>

                <div class="form-group">
                    <label>联系地址</label>
                    <input type="text" class="form-control" name="contact_address" id="contact_address" value="${thiswork.contact_address}" required>
                </div>

                <div class="form-group">
                    <label>邮箱</label>
                    <input type="email" class="form-control" name="contact_email" id="contact_email" value="${thiswork.contact_email}" required>
                </div>

                <div class="form-group">
                    <label>单位性质</label>
                    <input type="text" class="form-control" name="work_nature" id="work_nature" value="${thiswork.work_nature}" required>
                </div>


                <div class="form-group">
                    <input type="submit" value="确定" class="btn btn-success btn-sm" class="text-left">
                    <a href="admin_all_work.html" class="btn btn-info btn-sm">返回</a>
                </div>

            </form>
        </div>
    </div>
</div>


</body>
</html>

