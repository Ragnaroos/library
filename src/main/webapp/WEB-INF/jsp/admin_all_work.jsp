<%--
  Created by IntelliJ IDEA.
  User: mary
  Date: 2023/11/19
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>全部工作单位</title>
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

<c:if test="${not empty updateSuccess}">
    <script>alert('${updateSuccess}');</script>
</c:if>
<c:if test="${not empty updateError}">
    <script>alert('${updateError}');</script>
</c:if>

<div class="panel panel-default" style="position:relative;top: 80px;width: 90%;margin-left: 5%">
    <div class="panel-heading">
        <h3 class="panel-title">
            全部工作单位
        </h3>
    </div>
    <div class="panel-body">
        <form method="post" action="querywork.html" class="form-inline"  id="searchform">
            <div class="form-group">
                <input type="text" class="form-control" name="searchWord" placeholder="搜索联系人/单位名称/联系地址...">
            </div>
            <button type="submit" class="btn btn-default">搜索</button>
        </form>
        <div class="form-group">
        </div>
        <table class="table table-hover" >
            <thead>
            <tr>
                <th>id</th>
                <th>单位名称</th>
                <th>联系人</th>
                <th>联系电话</th>
                <th>联系地址</th>
                <th>邮箱</th>
                <th>单位性质</th>

                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${works}" var="works">
                <tr>
                    <td><c:out value="${works.work_id}"></c:out></td>
                    <td><c:out value="${works.work_name}"></c:out></td>
                    <td><c:out value="${works.contact}"></c:out></td>
                    <td><c:out value="${works.contact_phone}"></c:out></td>
                    <td><c:out value="${works.contact_address}"></c:out></td>
                    <td><c:out value="${works.contact_email}"></c:out></td>
                    <td><c:out value="${works.work_nature}"></c:out></td>

                    <td><a href="admin_all_work_update.html?work_name=<c:out value="${works.work_name}"></c:out>"><button type="button" class="btn btn-info btn-xs">编辑</button></a></td>
                    <td><a href="admin_all_work_deleteone.html?work_name=<c:out value="${works.work_name}"></c:out>"><button type="button" class="btn btn-danger btn-xs">删除</button></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
