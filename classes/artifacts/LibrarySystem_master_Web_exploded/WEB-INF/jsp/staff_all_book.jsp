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

<c:if test="${not empty updateSuccess}">
    <script>alert('${updateSuccess}');</script>
</c:if>
<c:if test="${not empty updateError}">
    <script>alert('${updateError}');</script>
</c:if>

<div class="panel panel-default" style="position:relative;top: 80px;width: 90%;margin-left: 5%">
    <div class="panel-heading">
        <h3 class="panel-title">
            ${user.work_name}单位图书
        </h3>
    </div>
    <div class="panel-body">
        <form method="post" action="staff_querybook.html" class="form-inline"  id="searchform">
            <div class="form-group">
                <input type="text" class="form-control" name="searchWord" placeholder="搜索书名/作者/出版社...">
            </div>
            <button type="submit" class="btn btn-default">搜索</button>
        </form>
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
                <th>分类</th>
                <th>页数</th>
                <th>价格</th>
                <th>所属单位</th>
                <th>状态</th>

                <th>详情</th>
                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${books}" var="books">
                <tr>
                    <td><c:out value="${books.book_id}"></c:out></td>
                    <td><c:out value="${books.book_name}"></c:out></td>
                    <td>
                        <fmt:formatDate value="${books.publish_time}" pattern="yyyy-MM-dd" />
                    </td>
                    <td><c:out value="${books.writer}"></c:out></td>
                    <td><c:out value="${books.publish_house}"></c:out></td>
                    <td><c:out value="${books.book_class}"></c:out></td>
                    <td><c:out value="${books.pages}"></c:out></td>
                    <td><c:out value="${books.price}"></c:out></td>
                    <td><c:out value="${books.belongto}"></c:out></td>
                    <td>
                        <c:choose>
                            <c:when test="${books.book_state == 'public'}">
                                <span style="color: green;"><strong>公开</strong></span>
                            </c:when>
                            <c:when test="${books.book_state == 'private'}">
                                <span style="color: red;"><strong>私密</strong></span>
                            </c:when>
                        </c:choose>
                    </td>

                    <td><a href="staff_all_book_info.html?book_id=<c:out value="${books.book_id}"></c:out>"><button type="button" class="btn btn-primary btn-xs">详情</button></a></td>
                    <td><a href="staff_all_book_update.html?book_id=<c:out value="${books.book_id}"></c:out>"><button type="button" class="btn btn-info btn-xs">编辑</button></a></td>
                    <td><a href="staff_all_book_deleteone.html?book_id=<c:out value="${books.book_id}"></c:out>"><button type="button" class="btn btn-danger btn-xs">删除</button></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
