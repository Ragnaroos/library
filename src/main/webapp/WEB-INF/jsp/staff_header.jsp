<%--
  Created by IntelliJ IDEA.
  User: mary
  Date: 2023/11/18
  Time: 18:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty user}">
    <script>
        window.location.href = "login.html";
    </script>
</c:if>
<nav style="position:fixed;z-index: 999;width: 100%;background-color: black" class="navbar navbar-default"
     role="navigation">
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 8%;margin-right: 1%">
            <a class="navbar-brand" href="staff_main.html" style="font-family: 宋体; font-size: 250%; color: white">管理系统</a>
        </div>
        <div class="collapse navbar-collapse" >
            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: white">
                        图书管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="staff_all_book.html">${user.work_name}的全部图书</a></li>
                        <li class="divider"></li>
                        <li><a href="staff_add_book.html">增加图书</a></li>
                        <li class="divider"></li>
                        <li><a href="staff_add_batchbook.html">批量增加</a></li>
                    </ul>
                </li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: white">
                        借还管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="staff_book_borrow_check.html">借阅审核</a></li>
                        <li class="divider"></li>
                        <li><a href="staff_book_return_check.html">归还审核</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: white">
                        图书流入
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="staff_cirin_apply.html">申请流入</a></li>
                        <li class="divider"></li>
                        <li><a href="staff_cirin_return.html">申请归还</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: white">
                        流出管理
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="staff_cirout_check.html">流出审核</a></li>
                        <li class="divider"></li>
                        <li><a href="staff_cirout_return_check.html">归还审核</a></li>
                    </ul>
                </li>
                <li >
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="color: white">
                        统计分析
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="staff_statistics_bookborrow.html">借阅统计</a></li>
<%--                        <li class="divider"></li>--%>
<%--                        <li><a href="staff_statistics_bookcirculate.html">流出审核</a></li>--%>
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

