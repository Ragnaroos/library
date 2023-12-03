<%--
  Created by IntelliJ IDEA.
  User: mary
  Date: 2023/11/21
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>全部图书</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
    <script>
        $(function () {
            $('#header').load('staff_header.html');
        })
    </script>
</head>

<body background="img/book2.jpg" style=" background-repeat:no-repeat ;
background-size:100% 100%;
background-attachment: fixed;">
<div id="header"></div>
<c:if test="${cha.character_name ne 'staff'}">
    <script>
        window.location.href = "login.html";
    </script>
</c:if>

<div class="col-xs-6 col-md-offset-3" style="padding-top: 100px;position: relative">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">
                添加图书
            </h3>
        </div>

        <div class="panel-body">
            <form method="POST" action="/uploadCSV.html" enctype="multipart/form-data">
                <div class="form-group">
                    <label>上传文件（仅支持CSV）</label>
                    <input type="file" class="form-control" name="file" accept=".csv" required>
                </div>

                <div class="form-group">
                    <input type="submit" value="确定" class="btn btn-success btn-sm" class="text-left">
                    <a href="staff_main.html" class="btn btn-info btn-sm">返回</a>
                </div>
            </form>
        </div>
    </div>
</div>


</body>
</html>


