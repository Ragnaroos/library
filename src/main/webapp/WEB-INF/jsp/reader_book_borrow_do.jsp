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
  <title>图书借还</title>
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

<div class="col-xs-6 col-md-offset-3" style="padding-top: 100px;position: relative">
  <div class="panel panel-primary">
    <div class="panel-heading">
      <h3 class="panel-title">
        发起借阅
      </h3>
    </div>

    <div class="panel-body">
      <form action="reader_book_borrow_submit.html?book_id=${thisbook.book_id}" method="post" id="reader_book_borrow_submit" enctype="multipart/form-data">
        <div class="form-group">
          <label>图书名</label>
          <input type="text" class="form-control" name="book_name" id="book_name" value="${thisbook.book_name}" readonly>
        </div>
        <div class="form-group">
          <label>封面</label><br>
          <!-- 显示头像的矩形框 -->
          <img id="avatar" src="${thisbook.photo_path}" alt="封面" class="img-thumbnail" style="width: 150px; height: 150px;">
        </div>

        <div class="form-group">
          <label for="belongto">所属单位</label>
          <input type="text" class="form-control" name="belongto" id="belongto" value="${user.work_name}" readonly>
        </div>


        <div class="form-group">
          <label for="belongto">借阅理由</label>
          <input type="text" class="form-control" name="brw_reason" id="brw_reason" required>
        </div>

        <div class="form-group">
          <input type="submit" value="确定" class="btn btn-success btn-sm" class="text-left">
          <a href="staff_all_book.html" class="btn btn-info btn-sm">返回</a>
        </div>

      </form>
    </div>
  </div>
</div>


</body>
</html>


