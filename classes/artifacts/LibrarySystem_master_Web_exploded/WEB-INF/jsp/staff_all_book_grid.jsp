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

  </div>
</div>

</body>
</html>
