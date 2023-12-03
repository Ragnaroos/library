<%@ page contentType="text/html;charset=UTF-8"  language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>首页</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
    <script src="js/js.cookie.js"></script>
    <style>
        #login{
           height: 50%;
            width: 28%;
            margin-left: auto;
            margin-right: auto;
            margin-top: 5%;
            display: block;
            position: center;
        }

        .form-group {
            margin-bottom: 0;
        }
        * {
            padding:0;
            margin:0;
        }
    </style>
</head>
<body background="img/book2.jpg" style=" background-repeat:no-repeat ;
background-size:100% 100%;
background-attachment: fixed;">
<c:if test="${!empty error}">
    <script>
            alert("${error}");
            window.location.href="login.html";
    </script>
</c:if>
<h2 style="text-align: center; color: white; font-family: '宋体'; font-size: 500%">图书借阅管理系统</h2>

<div class="panel panel-default" id="login">
    <div class="panel-heading" style="background-color: #fff">
        <h3 class="panel-title">用户登录</h3>
    </div>
    <div class="panel-body">
        <div class="form-group">
            <label for="id">用户名</label>
            <input type="text" class="form-control" id="id" placeholder="请输入用户名">
        </div>
        <div class="form-group">
            <label for="passwd">密码</label>
            <input type="password" class="form-control" id="passwd" placeholder="请输入密码">
        </div>
        <div class="form-group">
            <label for="role">角色</label>
            <select class="form-control" id="role">
                <option value="admin">管理员</option>
                <option value="staff">工作人员</option>
                <option value="reader">读者</option>
            </select>
        </div>

        <div class="text-left" style="margin-bottom: 10px;">
            <a href="reader_register.html" style="margin-right: 15px;">读者注册</a>
            <a href="find_password.html" style="margin-right: 15px;">找回密码</a>
        </div>
        <p style="text-align: right;color: red;position: absolute" id="info"></p><br/>
        <button id="loginButton"  class="btn btn-primary  btn-block">登陆
        </button>
    </div>
</div>
    <script>
        // 记住登录信息
        function rememberLogin(username, password, checked) {
            Cookies.set('loginStatus', {
                username: username,
                password: password,
                remember: checked
            }, {expires: 30, path: ''})
        }
        // 若选择记住登录信息，则进入页面时设置登录信息
        function setLoginStatus() {
            var loginStatusText = Cookies.get('loginStatus')
            if (loginStatusText) {
                var loginStatus
                try {
                    loginStatus = JSON.parse(loginStatusText);
                    $('#id').val(loginStatus.username);
                    $('#passwd').val(loginStatus.password);
                    $("#remember").prop('checked',true);
                } catch (__) {}
            }
        }

        // 设置登录信息
        setLoginStatus();
        $("#loginButton").click(function () {
            var id =$("#id").val();
            var passwd=$("#passwd").val();
            var remember=$("#remember").prop('checked');
            if (id == '') {
                $("#info").text("提示:账号不能为空");
            }
            else if( passwd ==''){
                $("#info").text("提示:密码不能为空");
            }
            else {
                $.ajax({
                    type: "POST",
                    url: "/api/loginCheck",
                    data: {
                        id:id ,
                        passwd: passwd
                    },
                    dataType: "json",
                    success: function(data) {
                        if (data.stateCode.trim() === "0") {
                            $("#info").text("提示:账号或密码错误！");
                        } else if (data.stateCode.trim() === "1") {
                            $("#info").text("提示:账号不可用！请联系管理员");
                        } else if (data.stateCode.trim() === "2") {
                            $("#info").text("提示:管理员登录成功！");
                            window.location.href="/admin_main.html";
                        } else if (data.stateCode.trim() === "3") {
                            $("#info").text("提示:工作人员登录成功！");
                            window.location.href="/staff_main.html";
                        }
                        else if (data.stateCode.trim() === "4") {
                            if(remember){
                                rememberLogin(id,passwd,remember);
                            }else {
                                Cookies.remove('loginStatus');
                            }
                            $("#info").text("提示:登陆成功，跳转中...");
                            window.location.href="/reader_main.html";

                        }
                    }
                });
            }
        })

    </script>
</div>

</body>
</html>
