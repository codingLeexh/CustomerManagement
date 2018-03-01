<%--
  Created by IntelliJ IDEA.
  User: self-consciousness
  Date: 2018/3/1
  Time: 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>客户列表</title>
</head>
<body>
<h3 align="center" >客户列表</h3>
<table border="1" width="70%" align="center">
    <tr>
        <th>客户姓名</th>
        <th>性别</th>
        <th>手机</th>
        <th>邮箱</th>
        <th>描述</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${pageBean.beanList}" var="customer">
        <tr>
            <td>${customer.name}</td>
            <td>${customer.gender}</td>
            <td>${customer.phone}</td>
            <td>${customer.email}</td>
            <td>${customer.description}</td>
            <td>
                <a href="<c:url value='/CustomerServlet?method=editFindCustomerById&id=${customer.id}'/> ">编辑</a>
                <a href="<c:url value='/CustomerServlet?method=deleteCustomer&id=${customer.id}'/> ">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br/>
<center>
    第${pageBean.pagecode}页/共${pageBean.tatalpage}页
    <a href="${pageBean.url}&pagecode=1">首页</a>
    <c:if test="${pageBean.pagecode>1}">
        <a href="${pageBean.url}&pagecode=${pageBean.pagecode-1}">上一页</a>
    </c:if>

    <c:choose>
        <c:when test="${pageBean.tatalpage<=10}">
            <c:set var="begin" value="1"/>
            <c:set var="end" value="${pageBean.tatalpage}"/>
        </c:when>
        <c:otherwise>
            <c:set var="begin" value="${pageBean.pagecode-5}"/>
            <c:set var="end" value="${pageBean.pagecode+4}"/>
            <%--头溢出--%>
            <c:if test="${begin<1}">
                <c:set var="begin" value="1"/>
                <c:set var="end" value="10"/>
            </c:if>
            <%--尾溢出--%>
            <c:if test="${end>pageBean.tatalpage}">
                <c:set var="end" value="${pageBean.tatalpage}"/>
                <c:set var="begin" value="${pageBean.tatalpage-9}"/>
            </c:if>
        </c:otherwise>
    </c:choose>

    <%--循环遍历页码列表--%>
    <c:forEach var="i" begin="${begin}" end="${end}">
        <c:choose>
            <c:when test="${i eq pageBean.pagecode}">
                [${i}]
            </c:when>
            <c:otherwise>
                <a href="${pageBean.url}&pagecode=${i}">[${i}]</a>
            </c:otherwise>
        </c:choose>

    </c:forEach>


    <c:if test="${pageBean.pagecode<pageBean.tatalpage}">
        <a href="${pageBean.url}&pagecode=${pageBean.pagecode+1}">下一页</a>
    </c:if>
    <a href="${pageBean.url}&pagecode=${pageBean.tatalpage}">尾页</a>

</center>
</body>
</html>
