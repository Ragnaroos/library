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

<c:if test="${not empty updateSuccess}">
    <script>alert('${updateSuccess}');</script>
</c:if>
<c:if test="${not empty updateError}">
    <script>alert('${updateError}');</script>
</c:if>

<div class="panel panel-default" style="position:relative;top: 80px;width: 90%;margin-left: 5%">
    <div class="panel-heading">
        <h3 class="panel-title">
            搜索结果
        </h3>
    </div>
    <div class="panel-body">
        <form method="post" action="queryuser.html" class="form-inline"  id="searchform">
            <div class="form-group">
                <input type="text" class="form-control" name="searchWord" placeholder="搜索用户名/单位/住址...">
            </div>
            <button type="submit" class="btn btn-default">搜索</button>
        </form>
        <div class="form-group">
        </div>
        <table class="table table-hover" >
            <thead>
            <tr>
                <th>id</th>
                <th>真实姓名</th>
                <th>账号状态</th>
                <th>联系电话</th>
                <th>住址</th>
                <th>邮箱</th>
                <th>工号</th>
                <th>工作单位</th>
                <th>性别</th>

                <th>详情</th>
                <th>编辑</th>
                <th>禁用</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="users">
                <tr>
                    <td><c:out value="${users.user_id}"></c:out></td>
                    <td><c:out value="${users.user_realname}"></c:out></td>
                    <td>
                        <c:choose>
                            <c:when test="${users.user_state == 'usable'}">
                                <span style="color: green;"><strong>可用</strong></span>
                            </c:when>
                            <c:when test="${users.user_state == 'unusable'}">
                                <span style="color: red;"><strong>不可用</strong></span>
                            </c:when>
                        </c:choose>
                    </td>
                    <td><c:out value="${users.phone}"></c:out></td>
                    <td><c:out value="${users.address}"></c:out></td>
                    <td><c:out value="${users.email}"></c:out></td>
                    <td><c:out value="${users.work_num}"></c:out></td>
                    <td><c:out value="${users.work_name}"></c:out></td>
                    <td><c:out value="${users.sex}"></c:out></td>


                    <td><a href="admin_all_user_info.html?user_name=<c:out value="${users.user_name}"></c:out>"><button type="button" class="btn btn-primary btn-xs">详情</button></a></td>
                    <td><a href="admin_all_user_update.html?user_name=<c:out value="${users.user_name}"></c:out>"><button type="button" class="btn btn-info btn-xs">编辑</button></a></td>
                    <td><a href="admin_all_user_deleteone.html?user_name=<c:out value="${users.user_name}"></c:out>"><button type="button" class="btn btn-danger btn-xs">禁用</button></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="form-group">
            <a href="admin_all_user.html"><button type="button" class="btn btn-info btn-xs">返回</button></a>
        </div>
    </div>
</div>

</body>
</html>

