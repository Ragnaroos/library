<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${user.user_realname}的主页</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js" ></script>
    <script>
        $(function () {
            $('#header').load('reader_header.html');
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
<div class="modal fade" id="warnProfileModal" tabindex="-1" role="dialog" aria-labelledby="warnProfileLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="warnProfileLabel">
                    提示
                </h4>
            </div>
            <div class="modal-body">
                个人信息不完整！
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="window.location.href='profile.html';">去完善
                </button>
            </div>
        </div>
    </div>
</div>
<c:if test="${empty user.user_realname or
               empty user.phone or
               empty user.address or
               empty user.email }">
    <script>
        $(function () {
            $("#warnProfileModal").modal({
                show: true
            })
        })
    </script>
</c:if>




<c:if test="${empty user}">
    <script>
        window.location.href = "login.html";
    </script>
</c:if>
</body>
</html>
