<%@page contentType="text/html; utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <h1>系统主页</h1><br/>
    <h1>欢迎回来 <shiro:principal/> 酱~</h1>
    <shiro:authenticated>
        认证之后展示的内容<br>
    </shiro:authenticated>
    <shiro:notAuthenticated>
        不需要认证就能展示的内容<br>
    </shiro:notAuthenticated>

    <a href="${pageContext.request.contextPath}/user/logout">退出登录</a>
    <ul>
        <shiro:hasAnyRoles name="user, admin">
            <li><a href="">用户管理</a></li>
            <ul>
                <shiro:hasPermission name="user:find:*">
                    <li><a href="">查询用户</a></li>
                </shiro:hasPermission>
                <shiro:hasPermission name="user:create:*">
                    <li><a href="">添加用户</a></li>
                </shiro:hasPermission>
                <shiro:hasPermission name="user:update:*">
                    <li><a href="">修改用户</a></li>
                </shiro:hasPermission>
                <shiro:hasPermission name="order:delete:01">
                    <li><a href="">删除用户</a></li>
                </shiro:hasPermission>
            </ul>
        </shiro:hasAnyRoles>

        <shiro:hasRole name="admin">
            <li><a href="">商品管理</a></li>
            <li><a href="">订单管理</a></li>
            <li><a href="">物流管理</a></li>
        </shiro:hasRole>
    </ul>
</body>
</html>