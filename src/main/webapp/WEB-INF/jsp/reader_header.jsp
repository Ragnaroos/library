<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty user}">
    <script>
        window.location.href = "login.html";
    </script>
</c:if>
<nav class="navbar navbar-default" role="navigation" style="background-color:#fff">
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 8%;margin-right: 1%">
            <a class="navbar-brand " href="reader_main.html"><p class="text-primary" style="font-family: 宋体; font-size: 200%; ">我的图书馆</p></a>
        </div>
        <div class="collapse navbar-collapse" id="example-navbar-collapse">
            <ul class="nav navbar-nav navbar-left">
                <li>
                    <a href="reader_all_book.html">
                        全部图书
                    </a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        图书借还
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="reader_book_borrow.html">图书借阅</a></li>
                        <li class="divider"></li>
                        <li><a href="reader_book_return.html">图书归还</a></li>
                    </ul>
                </li>
                <li>
                    <a href="repassword.html" >
                        密码修改
                    </a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" >
                        <img src="${user.photo_path}" alt="头像" style="width: 30px; height: 30px; border-radius: 50%; margin-right: 5px;">
                        ${user.user_realname}，已登陆 <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="profile.html">个人信息修改</a></li>
                    </ul>
                </li>
                <li><a href="login.html">退出</a></li>
            </ul>
        </div>
    </div>
</nav>
