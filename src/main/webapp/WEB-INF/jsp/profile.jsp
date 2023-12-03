<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>修改个人信息</title>
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
<c:if test="${not empty updatePSuccess}">
    <script>alert('${updatePSuccess}');</script>
</c:if>
<c:if test="${not empty updatePError}">
    <script>alert('${updatePError}');</script>
</c:if>



<div class="col-xs-6 col-md-offset-3" style="padding-top: 100px;position: relative">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">修改个人信息</h3>
        </div>
        <div class="panel-body">
            <form action="updateProfile" method="post" id="updateProfile" enctype="multipart/form-data">

                <div class="form-group">
                    <label>用户名</label>
                    <input type="text" class="form-control" name="user_name" id="user_name" value="${user.user_name}" readonly>
                </div>
                <!-- 添加图片上传的输入框 -->
                <div class="form-group">
                    <label for="picture">上传头像</label><br>
                    <!-- 显示头像的矩形框 -->
                    <img id="avatar" src="${user.photo_path}" alt="头像" class="img-thumbnail" style="width: 150px; height: 150px;">
                    <!-- 选择新头像的输入框 -->
                    <input type="file" class="form-control" name="picture" id="picture" required>
                </div>
                <div class="form-group">
                    <label>真实姓名</label>
                    <input type="text" class="form-control" name="user_realname" id="user_realname" value="${user.user_realname}">
                </div>

                <div class="form-group">
                    <label>联系电话</label>
                    <input type="text" class="form-control" name="phone" id="phone" value="${user.phone}">
                </div>

                <div class="form-group">
                    <label>住址</label>
                    <input type="text" class="form-control" name="address" id="address" value="${user.address}">
                </div>

                <div class="form-group">
                    <label>邮箱</label>
                    <input type="email" class="form-control" name="email" id="email" value="${user.email}">
                </div>

                <div class="form-group">
                    <label>性别</label><br>
                    <label class="radio-inline" >
                        <input type="radio" name="sex" id="male" value="男" ${user.sex == '男' ? 'checked' : ''}> 男
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="sex" id="female" value="女" ${user.sex == '女' ? 'checked' : ''}> 女
                    </label>
                </div>


                <div class="form-group">
                    <label>创建时间</label>
                    <input type="text" class="form-control" value="${user.created_time}" readonly>
                </div>

                <div class="form-group">
                    <label>上次登录时间</label>
                    <input type="text" class="form-control" value="${user.lastlogin_time}" readonly>
                </div>

                <input style="align-items: center" type="submit" value="确认修改" class="btn btn-success btn-sm"
                       class="text-left">
            <script>
                function mySubmit(flag){
                    return flag;
                }
                $("#updateProfile").submit(function () {
                    if( $("#user_name").val()==''||$("#user_realname").val()==''||$("#phone").val()==''||$("#address").val()==''||$("#email").val()==''||$("#work_name").val()==''||$("#work_num").val()==''){
                        alert("请填入完整信息！");
                        return mySubmit(false);
                    }
                    // 检查性别单选按钮是否至少有一个被选中
                    if(!$('input[name="sex"]:checked').val()) {
                        alert("请选择性别！");
                        return false; // 阻止表单提交
                    }
                })


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
<c:if test="${empty user}">
    <script>
        window.location.href = "login.html";
    </script>
</c:if>

</body>
</html>