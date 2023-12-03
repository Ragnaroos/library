<%@ page contentType="text/html;charset=UTF-8"  %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>管理主页</title>
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
<c:if test="${not empty vertifySucc}">
    <script>alert('${vertifySucc}');</script>
</c:if>


<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    提示
                </h4>
            </div>
            <div class="modal-body">
                管理员登陆成功！
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">进入主页
                </button>
            </div>
        </div>
    </div>
</div>
<c:if test="${!empty user && !myModelShow }">
    <script>
        $(function () {
            $("#myModal").modal({
                show: true
            })
        })
        $.post('/mymodal-view-status', function() {
            // 设置会话属性为true，表示用户已经看到了模态框
            <% session.setAttribute("myModelShow", true); %>
        });
    </script>
</c:if>


<c:if test="${empty user}">
    <script>
        window.location.href = "login.html";
    </script>
</c:if>

</body>
</html>
