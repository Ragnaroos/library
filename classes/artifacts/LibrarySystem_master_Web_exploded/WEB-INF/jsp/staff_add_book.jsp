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
            <form action="staff_add_book_do.html" method="post" id="staff_add_book_do" enctype="multipart/form-data">
                <div class="form-group">
                    <label>图书名</label>
                    <input type="text" class="form-control" name="book_name" id="book_name" required>
                </div>
                <div class="form-group">
                    <label>封面</label><br>
                    <!-- 显示头像的矩形框 -->
                    <img id="avatar" src="" alt="封面" class="img-thumbnail" style="width: 150px; height: 150px;">
                    <!-- 选择新头像的输入框 -->
                    <input type="file" class="form-control" name="picture" id="picture" required>
                </div>

                <div class="form-group">
                    <label>出版时间</label>
                    <input type="date" class="form-control" name="publish_time" id="publish_time" required>
                </div>

                <div class="form-group">
                    <label>作者</label>
                    <input type="text" class="form-control" name="writer" id="writer" required>
                </div>

                <div class="form-group">
                    <label>出版社</label>
                    <input type="text" class="form-control" name="publish_house" id="publish_house" required>
                </div>

                <div class="form-group">
                    <label>图书分类</label>
                    <input type="text" class="form-control" name="book_class" id="book_class" required>
                </div>

                <div class="form-group">
                    <label>页数</label>
                    <input type="text" class="form-control" name="pages" id="pages" required>
                </div>

                <div class="form-group">
                    <label>价格</label>
                    <input type="text" class="form-control" name="price" id="price" required>
                </div>


                <div class="form-group">
                    <label>图书状态</label><br>
                    <label class="radio-inline" >
                        <input type="radio" name="book_state" id="public" value="public" ${thisbook.book_state == 'public' ? 'checked' : ''} required> 公开
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="book_state" id="private" value="private" ${thisbook.book_state == 'private' ? 'checked' : ''}> 私密
                    </label>
                </div>

                <div class="form-group">
                    <label for="belongto">所属单位</label>
                    <input type="text" class="form-control" name="belongto" id="belongto" value="${user.work_name}" readonly>
                </div>


                <div class="form-group">
                    <input type="submit" value="确定" class="btn btn-success btn-sm" class="text-left">
                    <a href="staff_all_book.html" class="btn btn-info btn-sm">返回</a>
                </div>

                <script>
                    // JavaScript 用于处理图像预览和文件输入更改
                    document.getElementById('picture').onchange = function (event) {
                        var reader = new FileReader();
                        reader.onload = function (e) {
                            // 当文件被选择后，更新 img 标签的 src 属性以展示新头像
                            document.getElementById('avatar').src = e.target.result;
                        };
                        // 读取选定的文件并将其作为DataURL渲染
                        reader.readAsDataURL(event.target.files[0]);
                    };
                </script>
            </form>
        </div>
    </div>
</div>


</body>
</html>


