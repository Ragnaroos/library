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
  <title>借还管理</title>
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
    <ul class="nav nav-tabs" role="tablist">
      <li role="presentation" class="active"><a href="staff_book_borrow_check.html">待审核</a></li>
      <li role="presentation"><a href="staff_book_borrow_check_ing.html">借阅中</a></li>
    </ul>
    <div class="form-group">
    </div>
    <table class="table table-hover" >
      <thead>
      <tr>
        <th>id</th>
        <th>图书名</th>
        <th>作者</th>
        <th>出版社</th>
        <th>借阅申请时间</th>
        <th>借阅申请人</th>
        <th>申请理由</th>

        <th>通过</th>
        <th>拒绝</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items="${books}" var="books" varStatus="status">
        <tr>
          <td><c:out value="${books.book_id}"></c:out></td>
          <td><c:out value="${books.book_name}"></c:out></td>
          <td><c:out value="${books.writer}"></c:out></td>
          <td><c:out value="${books.publish_house}"></c:out></td>
          <td>
            <fmt:formatDate value="${bookBorrows[status.index].brw_time}" pattern="yyyy-MM-dd HH:mm:ss" />
          </td>
          <td>
            <c:out value="${users[status.index].user_realname}"></c:out>
          </td>
          <td>
            <c:out value="${bookBorrows[status.index].brw_reason}"></c:out>
          </td>


          <td>
            <a href="staff_book_borrow_check_agree.html?book_id=<c:out value="${books.book_id}"></c:out>">
              <button type="button" class="btn btn-success btn-xs">通过</button>
            </a>
          </td>
          <td>
            <a href="staff_book_borrow_check_disagree.html?book_id=<c:out value="${books.book_id}"></c:out>">
              <button type="button" class="btn btn-danger btn-xs">拒绝</button>
            </a>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>

</body>
</html>
