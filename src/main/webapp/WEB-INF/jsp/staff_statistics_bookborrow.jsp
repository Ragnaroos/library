<%--
  Created by IntelliJ IDEA.
  User: mary
  Date: 2023/11/19
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>统计分析</title>
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
            借阅统计
        </h3>
    </div>
    <div class="panel-body">
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="staff_statistics_bookborrow.html">近三年</a></li>
            <li role="presentation"><a href="staff_statistics_bookborrow_month.html">每月</a></li>
        </ul>
        <div class="form-group">
        </div>
        <div class="form-group" style="display: flex; justify-content:  center;">
            <label style="margin-right: 10px;">单位图书总数：${myWorkBookNum}     </label>
            <label>当前在库图书数：${myWorkBookNowNum}</label>
        </div>
        <canvas id="borrowStatsChart"></canvas>
    </div>
</div>

</body>
<script>
    // 假定booksBorrowed是一个包含近三年借书统计的数组，格式如下：
    const booksBorrowedJson = '<c:out value="${booksBorrowedJson}" escapeXml="false"/>';
    const booksBorrowed = JSON.parse(booksBorrowedJson);
    // 这个数组应从后端获取，并在JavaScript中初始化

    document.addEventListener('DOMContentLoaded', (event) => {
        // 等页面加载完成后执行
        const ctx = document.getElementById('borrowStatsChart').getContext('2d');
        const borrowStatsChart = new Chart(ctx, {
            type: 'bar', // 图表类型
            data: {
                labels: booksBorrowed.map(item => item.year), // X轴标签
                datasets: [{
                    label: '年度借书数量', // 数据集标签
                    data: booksBorrowed.map(item => item.count), // Y轴数据
                    backgroundColor: 'rgba(0, 123, 255, 0.5)', // 柱状图颜色
                    borderColor: 'rgba(0, 123, 255, 1)', // 柱状图边框颜色
                    borderWidth: 1 // 柱状图边框宽度
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true // Y轴起始值为0
                    }
                },
                plugins: {
                    legend: {
                        display: true // 是否显示图例
                    }
                }
            }
        });
    });
</script>
</html>

