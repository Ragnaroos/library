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

<div class="col-xs-6 col-md-offset-3" style="padding-top: 100px;position: relative">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">
                ${thisbook.book_name}的详细信息
            </h3>
        </div>

        <div class="panel-body">
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
                <label>出版时间</label>
                <input type="text" class="form-control" name="publish_time" id="publish_time" value="${thisbook.publish_time}" readonly>
            </div>

            <div class="form-group">
                <label>作者</label>
                <input type="text" class="form-control" name="writer" id="writer" value="${thisbook.writer}" readonly>
            </div>

            <div class="form-group">
                <label>出版社</label>
                <input type="text" class="form-control" name="publish_house" id="publish_house" value="${thisbook.publish_house}" readonly>
            </div>

            <div class="form-group">
                <label>图书分类</label>
                <input type="text" class="form-control" name="book_class" id="book_class" value="${thisbook.book_class}" readonly>
            </div>

            <div class="form-group">
                <label>页数</label>
                <input type="text" class="form-control" name="pages" id="pages" value="${thisbook.pages}" readonly>
            </div>

            <div class="form-group">
                <label>价格</label>
                <input type="text" class="form-control" name="price" id="price" value="${thisbook.price}" readonly>
            </div>


            <div class="form-group">
                <label>图书状态</label>
                <input type="text" class="form-control" name="book_state" id="book_state" value="${thisbook.book_state}" readonly>
            </div>

            <div class="form-group">
                <label>所属单位</label>
                <input type="text" class="form-control" name="belongto" id="belongto" value="${thisbook.belongto}" readonly>
            </div>

            <div class="form-group">
                <label>是否在借</label>
                <label class="radio-inline" >
                    <input type="radio" name="isbrwed" id="yes" value="yes" ${thisbookBorrow.isbrwed == 'yes' ? 'checked' : ''} readonly> 是
                </label>
                <label class="radio-inline">
                    <input type="radio" name="isbrwed" id="no" value="no" ${thisbookBorrow.isbrwed == 'no' ? 'checked' : ''} readonly> 否
                </label>
            </div>


            <div class="form-group">
                <a href="reader_all_book.html"><button type="button" class="btn btn-info btn-xs">返回</button></a>
            </div>
        </div>
    </div>
</div>


</body>
</html>
