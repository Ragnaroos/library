<%--
  Created by IntelliJ IDEA.
  User: mary
  Date: 2023/11/19
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<div class="col-xs-6 col-md-offset-3" style="padding-top: 100px;position: relative">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">
                ${thisuser.user_realname}的详细信息
            </h3>
        </div>

        <div class="panel-body">
            <div class="form-group">
                <label>用户名</label>
                <input type="text" class="form-control" name="user_name" id="user_name" value="${thisuser.user_name}" readonly>
            </div>
            <div class="form-group">
                <label>角色</label><br>
                <input type="text" class="form-control" name="cha" id="cha" value="${thischa.character_name}" readonly>
            </div>
            <div class="form-group">
                <label>头像</label><br>
                <!-- 显示头像的矩形框 -->
                <img id="avatar" src="${thisuser.photo_path}" alt="头像" class="img-thumbnail" style="width: 150px; height: 150px;">
            </div>
            <div class="form-group">
                <label>真实姓名</label>
                <input type="text" class="form-control" name="user_realname" id="user_realname" value="${thisuser.user_realname}" readonly>
            </div>

            <div class="form-group">
                <label>联系电话</label>
                <input type="text" class="form-control" name="phone" id="phone" value="${thisuser.phone}" readonly>
            </div>

            <div class="form-group">
                <label>住址</label>
                <input type="text" class="form-control" name="address" id="address" value="${thisuser.address}" readonly>
            </div>

            <div class="form-group">
                <label>邮箱</label>
                <input type="email" class="form-control" name="email" id="email" value="${thisuser.email}" readonly>
            </div>
            <div class="form-group">
                <label>工号</label>
                <input type="text" class="form-control" name="work_num" id="work_num" value="${thisuser.work_num}" readonly>
            </div>
            <div class="form-group">
                <label>单位名称</label>
                <input type="text" class="form-control" name="work_name" id="work_name" value="${thisuser.work_name}" readonly>
            </div>
            <div class="form-group">
                <label>是否注册</label>
                <input type="text" class="form-control" name="isregister" id="isregister" value="${thisuser.isregister}" readonly>
            </div>
            <div class="form-group">
                <label>性别</label><br>
                <label class="radio-inline" >
                    <input type="radio" name="sex" id="male" value="男" ${thisuser.sex == '男' ? 'checked' : ''} readonly> 男
                </label>
                <label class="radio-inline">
                    <input type="radio" name="sex" id="female" value="女" ${thisuser.sex == '女' ? 'checked' : ''}> 女
                </label>
            </div>


            <div class="form-group">
                <label>创建时间</label>
                <div class="form-control">
                    <fmt:formatDate value="${thisuser.created_time}" pattern="yyyy-MM-dd HH:mm:ss" />
                </div>
            </div>


            <div class="form-group">
                <label>上次登录时间</label>
                <div class="form-control">
                    <fmt:formatDate value="${thisuser.lastlogin_time}" pattern="yyyy-MM-dd HH:mm:ss" />
                </div>
            </div>


            <div class="form-group">
                <a href="admin_all_user.html"><button type="button" class="btn btn-info btn-xs">返回</button></a>
            </div>
        </div>
    </div>
</div>


</body>
</html>
