<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>修改密码</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
    <script>
        $(function () {
            var cha =  '<c:out value="${cha.character_name}"/>';
            if (cha === "admin") {
                $('#header').load('admin_header.html');
            }
            else if(cha === "staff"){
                $('#header').load('staff_header.html');
            }
            else if(cha === "reader"){
                $('#header').load('reader_header.html');
            }
        })
    </script>
</head>
<body background="img/book2.jpg" style=" background-repeat:no-repeat ;
background-size:100% 100%;
background-attachment: fixed;">

<div id="header"></div>
<script type="text/javascript">
    function redirectToProfile() {
        window.location.href = "profile.html";
    }
</script>

<c:if test="${empty user.email}">
    <script type="text/javascript">
        alert('电子邮件地址为空，请完善您的个人资料。');
        redirectToProfile();
    </script>
</c:if>

<c:if test="${not empty vertifyPError}">
    <script>alert('${verifyPError}');</script>
</c:if>

<div class="col-xs-6 col-md-offset-3" style="padding-top: 100px;position: relative">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">修改密码-请先输入旧密码</h3>
        </div>
        <div class="panel-body">
            <form action="verifyPassword" method="post" id="verifyPassword">

                <div class="form-group">
                    <label>用户名</label>
                    <input type="text" class="form-control" name="user_name" id="user_name" value="${user.user_name}" readonly>
                </div>

                <div class="form-group">
                    <label>旧密码</label>
                    <input type="password" class="form-control" name="password" id="password">
                </div>

                <input style="align-items: center" type="submit" value="进入邮箱验证" class="btn btn-success btn-sm"
                       class="text-left">
                <script>

                    function mySubmit(flag){
                        return flag;
                    }
                    $("#verifyPassword").submit(function () {
                        if( $($("#password").val()==''){
                            alert("请填入密码！");
                            return mySubmit(false);
                        }
                    })
                </script>
            </form>


        </div>
    </div>
</div>
<c:if test="${empty user}">
    <script>
        window.location.href = "login.html";
    </script>
</c:if>

</body>
</html>
