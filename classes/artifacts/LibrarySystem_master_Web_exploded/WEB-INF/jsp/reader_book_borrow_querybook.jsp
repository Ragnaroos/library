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
      $('#header').load('reader_header.html');
    })
  </script>
</head>
<body background="img/book2.jpg" style=" background-repeat:no-repeat ;
background-size:100% 100%;
background-attachment: fixed;">
<div id="header"></div>

<c:if test="${cha.character_name ne 'reader'}">
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
    <form method="post" action="reader_book_borrow_querybook.html" class="form-inline"  id="searchform">
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
        <th>是否可借</th>

        <th>借阅</th>
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
          <td><c:out value="${books.book_class}"></c:out></td>
          <td><c:out value="${books.pages}"></c:out></td>
          <td><c:out value="${books.price}"></c:out></td>
          <td><c:out value="${books.belongto}"></c:out></td>
          <td>
            <c:choose>
              <c:when test="${bookBorrows[status.index].isbrwed == 'no'}">
                <span style="color: green;">可借</span>
              </c:when>
              <c:when test="${bookBorrows[status.index].isbrwed == 'yes'}">
                <span style="color: red;"><strong>在借</strong></span>
              </c:when>
            </c:choose>
          </td>

          <td>
            <a href="reader_book_borrow_do.html?book_id=${books.book_id}">
              <button type="borrowbutton" class="btn btn-info btn-xs" ${bookBorrows[status.index].isbrwed == 'yes' ? 'disabled' : ''}>借阅</button>
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
