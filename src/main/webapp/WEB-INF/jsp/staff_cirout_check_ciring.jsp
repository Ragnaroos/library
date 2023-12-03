<%--
  Created by IntelliJ IDEA.
  User: mary
  Date: 2023/11/19
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>图书流出</title>
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

<c:if test="${not empty updateSuccess}">
    <script>alert('${updateSuccess}');</script>
</c:if>
<c:if test="${not empty updateError}">
    <script>alert('${updateError}');</script>
</c:if>

<div class="panel panel-default" style="position:relative;top: 80px;width: 90%;margin-left: 5%">
    <div class="panel-heading">
        <h3 class="panel-title">
            流出审核
        </h3>
    </div>
    <div class="panel-body">
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation"><a href="staff_cirout_check.html">待审核</a></li>
            <li role="presentation" class="active"><a href="staff_cirout_check_ciring.html">流出中</a></li>
        </ul>
        <div class="form-group">
        </div>
        <table class="table table-hover" >
            <thead>
            <tr>
                <th>id</th>
                <th>图书名</th>
                <th>出版时间</th>
                <th>作者</th>
                <th>出版社</th>
                <th>申请人</th>
                <th>申请时间</th>
                <th>申请人单位</th>
                <th>申请理由</th>
                <th>状态</th>



            </tr>
            </thead>
            <tbody>
            <c:forEach items="${books}" var="books" varStatus="status">
                <tr>
                    <td><c:out value="${books.book_id}"></c:out></td>
                    <td><c:out value="${books.book_name}"></c:out></td>
                    <td>
                        <fmt:formatDate value="${books.publish_time}" pattern="yyyy-MM-dd" />
                    </td>
                    <td><c:out value="${books.writer}"></c:out></td>
                    <td><c:out value="${books.publish_house}"></c:out></td>
                    <td>
                        <c:out value="${users[status.index].user_realname}"></c:out>
                    </td>
                    <td>
                        <fmt:formatDate value="${bookCirculates[status.index].cir_time}" pattern="yyyy-MM-dd HH:mm:ss" />
                    </td>
                    <td>
                        <c:out value="${users[status.index].work_name}"></c:out>
                    </td>
                    <td>
                        <c:out value="${bookCirculates[status.index].cir_reason}"></c:out>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${bookCirculates[status.index].cir_state == 'ciring'}">
                                <span style="color: green;"><strong>流出中</strong></span>
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
