<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty user}">
    <script>
        window.location.href = "login.html";
    </script>
</c:if>
<nav style="position:fixed;z-index: 999;width: 100%;background-color: #25c6fc" class="navbar navbar-default"
     role="navigation">
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 8%;margin-right: 1%">
            <a class="navbar-brand" href="admin_main.html" style="font-family: 宋体; font-size: 250%; color: white">管理系统</a>
        </div>
        <div class="collapse navbar-collapse" >
            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: white">
                        图书管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="admin_all_book.html">全部图书</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: white">
                        用户管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="admin_all_user.html">全部用户</a></li>
                        <li class="divider"></li>
                        <li><a href="admin_add_staff.html">增加工作人员</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: white">
                        单位管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="admin_all_work.html">全部单位</a></li>
                        <li class="divider"></li>
                        <li><a href="admin_add_work.html">增加单位</a></li>
                    </ul>
                </li>
                <li >
                    <a href="repassword.html" style="color: white">
                        密码修改
                    </a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: white">
                        <img src="${user.photo_path}" alt="头像" style="width: 30px; height: 30px; border-radius: 50%; margin-right: 5px;">
                        ${user.user_realname}，已登陆 <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="profile.html">个人信息修改</a></li>
                    </ul>
                </li>
                <li><a href="logout.html" style="color: white">退出</a></li>
            </ul>
        </div>
    </div>
</nav>
