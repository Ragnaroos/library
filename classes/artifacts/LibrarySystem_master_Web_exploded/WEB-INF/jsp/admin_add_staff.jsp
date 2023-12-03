<%--
  Created by IntelliJ IDEA.
  User: mary
  Date: 2023/11/19
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>添加工作人员</title>
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
<c:if test="${not empty errStaff}">
    <script>alert('${errStaff}');</script>
</c:if>
<c:if test="${not empty sucStaff}">
    <script>alert('${sucStaff}');</script>
</c:if>



<div class="col-xs-6 col-md-offset-3" style="padding-top: 100px;position: relative">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">添加工作人员</h3>
        </div>
        <div class="panel-body">
            <form action="staff_add_do.html" method="post" id="staff_add_do" >
                <div class="input-group" style="padding-top: 20px;">
                    <span  class="input-group-addon">工号</span>
                    <input  type="text" class="form-control" name="work_num" id="work_num" >
                </div>
                <div class="input-group" style="padding-top: 20px;">
                    <span class="input-group-addon">工作单位</span>
                    <select class="form-control" name="work_name" id="work_name" required>
                        <c:forEach items="${works}" var="work">
                            <option value="${work.work_name}">${work.work_name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="input-group" style="padding-top: 20px;">
                    <span class="input-group-addon">真实姓名</span>
                    <input type="text" class="form-control" name="user_realname" id="user_realname"  >
                </div>
                <div class="input-group" style="padding-top: 20px;">
                    <span  class="input-group-addon">性别</span>
                    <label class="radio-inline" style="margin-left: 20px;">
                        <input type="radio" name="sex" id="male" value="男"> 男
                    </label>
                    <label class="radio-inline" style="margin-left: 20px;">
                        <input type="radio" name="sex" id="female" value="女"> 女
                    </label>
                </div>


                <div class="input-group" style="padding-top: 20px;">
                    <span  class="input-group-addon">住址</span>
                    <input type="text" class="form-control" name="address" id="address"  >
                </div>
                <div class="input-group" style="padding-top: 20px;">
                    <span class="input-group-addon">电话</span>
                    <input type="text" class="form-control" name="phone" id="phone"  >
                </div>
                <div class="input-group" style="padding-top: 20px;">
                    <span class="input-group-addon">邮箱</span>
                    <input type="email" class="form-control" name="email" id="email"  >
                </div>
                <div class="input-group" style="padding-top: 20px;">
                </div>
                <input style="align-items: center" type="submit" value="添加" class="btn btn-success btn-sm"
                       class="text-left">
                <script>
                    function mySubmit(flag){
                        return flag;
                    }
                    $("#user_realname").submit(function () {
                        if($("#work_num").val()==''||$("#work_name").val()==''||$("#sex").val()==''||$("#user_realname").val()==''||$("#address").val()==''||$("#phone").val()==''||$("#email").val()==''){
                            alert("请填入完整信息！");
                            return mySubmit(false);
                        }
                    })
                </script>
            </form>
        </div>
    </div>

</div>

</body>
</html>

