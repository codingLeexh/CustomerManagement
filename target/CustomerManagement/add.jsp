<%--
  Created by IntelliJ IDEA.
  User: self-consciousness
  Date: 2018/3/1
  Time: 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>添加客户信息</title>
</head>
<body>
<h3 align="center">添加客户</h3>

<form action="<c:url value="/CustomerServlet"/> " method="post">
    <input type="hidden" name="method" value="addCustomer"/>

    <table bgcolor="#5f9ea0" border="0" align="center" width="40%" style="margin: auto">
        <tr>
            <td width="100px">客户名称</td>
            <td width="40%">
                <input type="text" name="name"/>
            </td>
        </tr>
        <tr>
            <td>客户性别</td>
            <td>
                <input type="radio" name="gender" value="male" id="male"/>
                <label for="male">男</label>
                <input type="radio" name="gender" value="female" id="female"/>
                <label for="female">女</label>
            </td>
        </tr>
        <tr>
            <td>手机</td>
            <td>
                <input type="text" name="phone" id="phone"/>
            </td>
        </tr>
        <tr>
            <td>邮箱</td>
            <td>
                <input type="text" name="email" id="email"/>
            </td>
        </tr>
        <tr>
            <td>描述</td>
            <td>
                <textarea rows="5" cols="30" name="description"></textarea>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" name="submit" value="提交"/>
                <input type="reset" name="reset" value="重置"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
