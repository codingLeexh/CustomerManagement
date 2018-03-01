# 客户信息管理系统

> 作者：[codinglixh](http://codinglixh.cn)

---

## 介绍

> 这是一个入门级的Web应用程序，简单的jsp+servlet+javabean+jdbc.因为使用到了一些常用的简单SQL查询，及分页，所以简单介绍下项目构建，与分页逻辑。

`以下是这个web程序主要概况：`

- 添加客户信息
![image](https://raw.githubusercontent.com/codingLeexh/MarkDownPhotos/master/img/%E6%B7%BB%E5%8A%A0%E7%94%A8%E6%88%B7.jpg)

- 客户信息详细
![image](https://raw.githubusercontent.com/codingLeexh/MarkDownPhotos/master/img/%E6%9F%A5%E8%AF%A2%E6%95%B0%E6%8D%AE.jpg)

- 搜索用户
![image](https://raw.githubusercontent.com/codingLeexh/MarkDownPhotos/master/img/%E9%AB%98%E7%BA%A7%E6%90%9C%E7%B4%A2.jpg)

- 编辑信息
![image](https://raw.githubusercontent.com/codingLeexh/MarkDownPhotos/master/img/%E7%BC%96%E8%BE%91%E5%AE%A2%E6%88%B7.jpg)

---

## 项目开发详解

#### 开发环境

工具
```aidl
   JDK1.8、IDEA2017、Tomcat8.5、Mysql5.6、Maven
```
环境
```aidl
    windows
```

#### 项目搭建

> 数据库表创建，借助开发工具傻瓜式创建就好

`给出表字段：`

```aidl
   id 
   name
   gender
   phone
   email
   description  
```
![image](https://raw.githubusercontent.com/codingLeexh/MarkDownPhotos/master/img/%E6%95%B0%E6%8D%AE%E5%BA%93%E8%A1%A8.png)

1.IDEA新建Maven项目，配置pom.xml;

pom.xml加入依赖
```aidl
<dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>

    <!--  导入依赖   -->

    <!-- https://mvnrepository.com/artifact/com.mchange/c3p0 -->
    <dependency>
      <groupId>com.mchange</groupId>
      <artifactId>c3p0</artifactId>
      <version>0.9.5.2</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/com.mchange/mchange-commons-java -->
    <dependency>
      <groupId>com.mchange</groupId>
      <artifactId>mchange-commons-java</artifactId>
      <version>0.2.11</version>
    </dependency>


    <!-- https://mvnrepository.com/artifact/commons-beanutils/commons-beanutils -->
    <dependency>
      <groupId>commons-beanutils</groupId>
      <artifactId>commons-beanutils</artifactId>
      <version>1.9.3</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/commons-dbutils/commons-dbutils -->
    <dependency>
      <groupId>commons-dbutils</groupId>
      <artifactId>commons-dbutils</artifactId>
      <version>1.6</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/commons-logging/commons-logging -->
    <dependency>
      <groupId>commons-logging</groupId>
      <artifactId>commons-logging</artifactId>
      <version>1.2</version>
    </dependency>


    <!-- https://mvnrepository.com/artifact/javax.servlet/jstl -->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>jstl</artifactId>
      <version>1.2</version>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.40</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
    </dependency>

  </dependencies>
```

2.手动导入封装了DbUtil的小工具itcast-tools-1.4.jar(这个包的别人写的，maven中央仓库不存在。网上可下载)

`WEB-INF下新建lib文件夹用于存放第三方jar，将itcast-tools-1.4.jar复制进来。`

`file -> Project Structure -> Libraries -> + -> CustomerManagement -> Webapp -> WEB-INF -> lib -> itcast-tools-1.4.jar`确定，保存。

3.建包

**项目结构**：

![image](https://raw.githubusercontent.com/codingLeexh/MarkDownPhotos/master/img/QQ%E6%88%AA%E5%9B%BE20180301133201.png)

4.创建客户类Customer

```aidl
package entity;

/**
 * @author Lixinhua
 * @email codingLixh@163.com
 * @date 2018/2/28 21:29
 */
public class Customer {
    private String id;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

```

`toString()方法可以省略，但要知道它的`[作用](http://www.runoob.com/java/java-string-tostring.html)

5.创建分页类PageBean

```aidl
package entity;

import java.util.List;

/**
 * @author Lixinhua
 * @email codingLixh@163.com
 * @date 2018/2/28 21:34
 */
public class PageBean<Object> {
    private int pagecode;//当前页码page code
    private int tatalrecords;//总纪录数tatal records
    private int pagerecords;//每页纪录数page records
    private List<Object> beanList;//当前页的纪录
    private String url;//跳转链接

    public int getPagecode() {
        return pagecode;
    }

    public void setPagecode(int pagecode) {
        this.pagecode = pagecode;
    }

    public int getTatalrecords() {
        return tatalrecords;
    }

    public void setTatalrecords(int tatalrecords) {
        this.tatalrecords = tatalrecords;
    }

    public int getPagerecords() {
        return pagerecords;
    }

    public void setPagerecords(int pagerecords) {
        this.pagerecords = pagerecords;
    }

    public List<Object> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<Object> beanList) {
        this.beanList = beanList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTatalpage() {
        int tatalpage = tatalrecords / pagerecords; //总页数
        return tatalrecords % pagerecords == 0 ? tatalpage : tatalpage + 1;
    }
}

```

`简单介绍分页逻辑：`
 
```aidl

int tatalpage(页数)= tatalrecords(总记录数) / pagerecords(单页的记录数)；

return tatalrecords % pagerecords == 0 ? tatalpage : tatalpage + 1;

等同于：

 int tatalpage = tatalrecords / pagerecords; //总页数
 if(tatalrecords % pagerecords == 0){
    return tatalpage;
 }else{
    return tatalpage + 1;
 }
```

6.创建Dao

```aidl
package dao;

import cn.itcast.jdbc.TxQueryRunner;
import entity.Customer;
import entity.PageBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lixinhua
 * @email codingLixh@163.com
 * @date 2018/2/28 21:45
 */
//数据访问层
public class CustomerDao {

    private QueryRunner queryRunner =new TxQueryRunner();//创建一个作为数据库查询的对象


    /**
     * 添加用户
     * @param customer
     */
    public void addCustomer(Customer customer){
        try {//捕获异常
            String sql="insert into t_customer values(?,?,?,?,?,?)";//定义SQL语句
            Object[] params={customer.getId(),customer.getName(),customer.getGender(),customer.getPhone(),customer.getEmail(),customer.getDescription()};//定义一个存放客户信息的数组
            queryRunner.update(sql,params);//调用QueryRunner的update方法，详情可查看update源码
        } catch (SQLException e) {
            throw new RuntimeException(e);// 抛出异常
        }
    }

    /**
     * 分页查询所有数据
     * @param pagecode
     * @param pagerecords
     * @return
     */
    public PageBean<Customer> findAllCustomer(int pagecode,int pagerecords){


        try {
            PageBean<Customer> pageBean =new PageBean<>();//创建一个页面对象
            pageBean.setPagecode(pagecode);//将定义的pagecode赋值给PageBean中的pagecode
            pageBean.setPagerecords(pagerecords);//将定义的pagerecords赋值给PageBean中的pagerecords

            String sql ="select count(*) from t_customer";//定义查询SQL查询所有数据
            Number number =(Number) queryRunner.query(sql,new ScalarHandler<>());//创建一个number存放查到的数据
            int tatalrecords=number.intValue();//声明tatalrecords(总数据)等于number.intValue()(查到的总数据)
            pageBean.setTatalrecords(tatalrecords);//将定义的tatalrecords赋值给PageBean中的tatalrecords

            //限制每次查询的记录数是每页规定显示的记录数，并且按名字排序
            sql="select * from t_customer order by name limit ?,?";
            Object[] params={(pagecode-1)*pagerecords,pagerecords};
            List<Customer> beanList =queryRunner.query(sql,new BeanListHandler<>(Customer.class),params);
            pageBean.setBeanList(beanList);//当前页的记录
            return pageBean;//返回结果集
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按ID查询用户
     * @param id
     * @return
     */
    public Customer findCustomerById(String id){
        //条件SQL，按Id查询
        String sql="select * from t_customer where id=?";
        try {
            //返回被查询的ID所对应的用户信息。
            return queryRunner.query(sql,new BeanHandler<>(Customer.class),id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改指定用户信息
     * @param customer
     */
    public void editCustomer(Customer customer){
        try {
            //更新SQL，指定ID所对应的用户,跟添加数据一样
            String sql="update t_customer set name=?,gender=?,phone=?,email=?,description=? where id=?";
            Object[] params={customer.getName(),customer.getGender(),customer.getPhone(),customer.getEmail(),customer.getDescription(),customer.getId()};
            queryRunner.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除指定用户
     * @param id
     */
    public void deleteCustomer(String id){
        String sql = "delete from t_customer where id=?";
        try {
            queryRunner.update(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 条件查询
     * 高级搜索
     * 数据分页
     * @param customer
     * @param pagecode
     * @param pagerecords
     * @return
     */
    public PageBean<Customer> query(Customer customer,int pagecode,int pagerecords){



        try {
            PageBean<Customer> pageBean =new PageBean<>();//创建一个页面对象
            pageBean.setPagecode(pagecode);//将定义的pagecode赋值给PageBean中的pagecode
            pageBean.setPagerecords(pagerecords);//将定义的pagerecords赋值给PageBean中的pagerecords

            StringBuilder cntSql = new StringBuilder("select count(*) from t_customer ");   
            //有兴趣可以去看看StringBuilder详解，这里简单介绍功能：声明的变量可以重复赋值：[StringBuilder a="a";a.append("b");输出a="ab"]
            StringBuilder whereSql=new StringBuilder(" where 1=1 ");//sql语句的一种优化，当然不是最好的那种
            List<Object> params =new ArrayList<>();
            String name=customer.getName();
            
            //以下逻辑，简单明了
            
            if (name != null && !name.trim().isEmpty()) {
                whereSql.append("and name like ?");
                params.add("%"+name+"%");
            }
            String gender = customer.getGender();
            if (gender != null && !gender.trim().isEmpty()) {
                whereSql.append("and gender=?");
                params.add(gender);
            }

            String phone = customer.getPhone();
            if (phone != null && !phone.trim().isEmpty()) {
                whereSql.append("and phone like ?");
                params.add("%"+phone+"%");
            }

            String email = customer.getEmail();
            if (email != null && !email.trim().isEmpty()) {
                whereSql.append("and email like ?");
                params.add("%"+email+"%");
            }
            
            //把符合条件的用户数据全部查出来
            Number number=queryRunner.query(cntSql.append(whereSql).toString(),new ScalarHandler<>(),params.toArray());
            int tatalrecords=number.intValue();
            pageBean.setTatalrecords(tatalrecords);

            StringBuilder sql=new StringBuilder("select * from t_customer ");
            StringBuilder limitSql=new StringBuilder(" limit ?,?");//分页

            params.add((pagecode-1)* pagerecords);//页面总数
            params.add(pagerecords);

            //将从条件查询获得的数据进行分页
            List<Customer> beanList=queryRunner.query(sql.append(whereSql).append(limitSql).toString(),new BeanListHandler<Customer>(Customer.class),params.toArray());
            pageBean.setBeanList(beanList);
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

```
`注意看注释`

7.service 
`业务逻辑，看似不重要，其实最重要`

```aidl
package service;

import dao.CustomerDao;
import entity.Customer;
import entity.PageBean;

/**
 * @author Lixinhua
 * @email codingLixh@163.com
 * @date 2018/3/1 8:27
 */

//业务逻辑层
public class CustomerService {
    //注入Dao层
    private CustomerDao customerDao =new CustomerDao();


    //重写dao方法

    /**
     * 添加用户
     * @param customer
     */
    public void addCustomer(Customer customer){
        customerDao.addCustomer(customer);
    }

    /**
     * 分页查询所有数据
     * @param pagecode
     * @param pagerecords
     * @return
     */
    public PageBean<Customer> findAllCustomer(int pagecode,int pagerecords){
        return customerDao.findAllCustomer(pagecode,pagerecords);
    }

    /**
     * 按ID查询用户
     * @param id
     * @return
     */
    public Customer findCustomerById(String id){
        return customerDao.findCustomerById(id);
    }

    /**
     * 修改指定用户信息
     * @param customer
     */
    public void editCustomer(Customer customer){
        customerDao.editCustomer(customer);
    }

    /**
     * 删除指定用户
     * @param id
     */
    public void deleteCustomerById(String id){
        customerDao.deleteCustomer(id);
    }

    /**
     * 条件查询
     * 高级搜索
     * 数据分页
     * @param customer
     * @param pagecode
     * @param pagerecords
     * @return
     */
    public PageBean<Customer> query(Customer customer,int pagecode,int pagerecords){
        return customerDao.query(customer,pagecode,pagerecords);
    }
}

```

8.servlet

`接收前端请求，并返回结果`

```aidl
package servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import entity.Customer;
import entity.PageBean;
import service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Lixinhua
 * @email codingLixh@163.com
 * @date 2018/3/1 9:11
 */
//控制层
public class CustomerServlet extends BaseServlet {

    //注入service层
    private CustomerService customerService = new CustomerService();

    //获取当前页码
    private int getPageCode(HttpServletRequest request) {
        String value = request.getParameter("pagecode");
        if (value == null || value.trim().isEmpty()) {
            //返回当前页为第一页
            return 1;
        }
        //否则返回具体页数
        return Integer.parseInt(value);
    }

    //获取分页的页码URL
    private String getUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String queryString = request.getQueryString();
        if (queryString.contains("&pagecode")) {
            int index = queryString.lastIndexOf("pagecode");
            queryString = queryString.substring(0, index);
        }
        return contextPath + servletPath + "?" + queryString;
    }

    //字符格式转换
    private Customer encoding(Customer customer) throws UnsupportedEncodingException {
        String name = customer.getName();
        String gender = customer.getGender();
        String phone = customer.getPhone();
        String email = customer.getEmail();
        if (name != null && !name.trim().isEmpty()) {
            name = new String(name.getBytes("ISO-8859-1"), "utf-8");
            customer.setName(name);
        }
        if (gender != null && !gender.trim().isEmpty()) {
            gender = new String(gender.getBytes("ISO-8859-1"), "utf-8");
            customer.setGender(gender);
        }
        if (phone != null && !phone.trim().isEmpty()) {
            phone = new String(phone.getBytes("ISO-8859-1"), "utf-8");
            customer.setPhone(phone);
        }
        if (email != null && !email.trim().isEmpty()) {
            email = new String(email.getBytes("ISO-8859-1"), "utf-8");
            customer.setEmail(email);
        }
        return customer;
    }


    /**
     * 添加
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String addCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //得到封装好的用户
        Customer customer = CommonUtils.toBean(request.getParameterMap(), Customer.class);
        //将用户的id用uuid随机生成的数表示
        customer.setId(CommonUtils.uuid());
        if (customer != null) {
            customerService.addCustomer(customer);
            request.setAttribute("msg", "恭喜，成功添加客户");
            return "/msg.jsp";
        }
        request.setAttribute("msg", "error，添加客户异常");
        return "/msg.jsp";
    }

    /**
     * 查询
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAllCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取页面传递的pc
        int pagecode = getPageCode(request);
        //设定每页显示数据总数,每页只显示十条数据
        int pagerecords = 10;

        PageBean<Customer> pageBean = customerService.findAllCustomer(pagecode, pagerecords);
        pageBean.setUrl(getUrl(request));
        request.setAttribute("pageBean", pageBean);

        return "f:/list.jsp";
    }

    /**
     * 编辑
     *
     * @param request
     * @param response
     * @return
     */
    public String editFindCustomerById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户ID
        String id = request.getParameter("id");
        Customer customer = customerService.findCustomerById(id);

        request.setAttribute("customer", customer);

        return "/edit.jsp";
    }

    /**
     * 修改
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String editCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = CommonUtils.toBean(request.getParameterMap(), Customer.class);
        if (customer != null) {
            customerService.editCustomer(customer);
            request.setAttribute("msg", "编辑客户信息成功！");
            return "/msg.jsp";
        }
        request.setAttribute("msg", "编辑客户信息失败！");
        return "/msg.jsp";
    }

    /**
     * 删除
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户ID
        String id = request.getParameter("id");
        if (id != null) {
            customerService.deleteCustomerById(id);
            request.setAttribute("msg", "恭喜，删除客户成功");
            return "/msg.jsp";
        }
        request.setAttribute("msg", "error，删除客户失败");
        return "/msg.jsp";
    }

    public String query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到封装好的用户
        Customer customer = CommonUtils.toBean(request.getParameterMap(), Customer.class);
        //转换字符格式
        customer =encoding(customer);

        //获取当前页码
        int pagecode= getPageCode(request);
        //规定每页记录数
        int pagerecords = 10;
        //获取页面数据
        PageBean<Customer> pageBean =customerService.query(customer,pagecode,pagerecords);
        if(pageBean!=null){
            //获取页面URL
            pageBean.setUrl(getUrl(request));
            request.setAttribute("pageBean",pageBean);
            return "/list.jsp";
        }
        request.setAttribute("msg", "查询数据异常");
        return "/msg.jsp";
    }
}

```
9.创建数据库连接配置文件c3p0-config.xml

```aidl
<?xml version="1.0" encoding="utf-8" ?>
<c3p0-config>
    <default-config>
        <property name="jdbcUrl">jdbc:mysql://localhost:3306/你创建的数据库名?useSSL=false</property>
        <property name="driverClass">com.mysql.jdbc.Driver</property>
        <property name="user">登录名</property>
        <property name="password">密码</property>
        <property name="acquireIncrement">3</property>
        <property name="initialPoolSize">10</property>
        <property name="minPoolSize">2</property>
        <property name="maxPoolSize">10</property>
    </default-config>
</c3p0-config>
```
`useSSL=false貌似是数据库的安全保护机制，不启用`

10.创建测试类，插入数据

```aidl
package Test;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import dao.CustomerDao;
import entity.Customer;
import org.apache.commons.dbutils.QueryRunner;

/**
 * @author Lixinhua
 * @email codingLixh@163.com
 * @date 2018/3/1 11:24
 */
public class test {
    QueryRunner qr=new TxQueryRunner();
    public static void main(String[] args)
    {
        CustomerDao customerDao=new CustomerDao();

        for (int i=0;i<1000;i++)
        {
            Customer customer=new Customer();
            customer.setId(CommonUtils.uuid());
            customer.setName("customer"+i);
            customer.setGender(i%2==0?"male":"female");
            customer.setPhone("13476"+i);
            customer.setEmail("customer"+i+"@163.com");
            customer.setDescription("helle world");

            customerDao.addCustomer(customer);
        }
    }
}

```

`运行这个类，打开数据库开发工具，查看插入数据是否成功`

11.配置web.xml

```aidl
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
    
    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>
    <servlet>
        <servlet-name>CustomerServlet</servlet-name>
        <servlet-class>servlet.CustomerServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>CustomerServlet</servlet-name>
        <url-pattern>/CustomerServlet</url-pattern>
    </servlet-mapping>
</web-app>
```
- 到此，后台代码开发完成。

![image](https://raw.githubusercontent.com/codingLeexh/MarkDownPhotos/master/img/customer%E5%AE%8C%E6%95%B4%E7%BB%93%E6%9E%84.png)

#### 前台代码展示

- add.jsp

```aidl
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

```

- edit.jsp

```aidl
<%--
  Created by IntelliJ IDEA.
  User: self-consciousness
  Date: 2018/3/1
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>编辑客户信息</title>
</head>
<body>
<h3 align="center">编辑客户</h3>
<form action="<c:url value='/CustomerServlet'/>" method="post" >
    <input type="hidden" name="method" value="editFindCustomerById"/>
    <input type="hidden" name="id" value="${customer.id}"/>
    <table bgcolor="#5f9ea0" border="0" align="center" width="40%" style="margin: auto">
        <tr>
            <td width="100px">客户名称</td>
            <td width="40%">
                <input type="text" name="name" value="${customer.name}"/>
            </td>
        </tr>
        <tr>
            <td>客户性别</td>
            <td>
                <input type="radio" name="gender" value="male" id="male" <c:if test="${customer.gender eq 'male'}"/>checked="checked"/>
                <label for="male">男</label>
                <input type="radio" name="gender" value="female" id="female" <c:if test="${customer.gender eq 'female'}"/> checked="checked"/>
                <label for="female">女</label>
            </td>
        </tr>
        <tr>
            <td>手机</td>
            <td>
                <input type="text" name="phone" id="phone" value="${customer.phone}"/>
            </td>
        </tr>
        <tr>
            <td>邮箱</td>
            <td>
                <input type="text" name="email" id="email" value="${customer.email}"/>
            </td>
        </tr>
        <tr>
            <td>描述</td>
            <td>
                <textarea rows="5" cols="30" name="description">${customer.description}</textarea>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" name="submit" value="编辑客户"/>
                <input type="reset" name="reset" value="重置"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>

```

- frame.jsp

```aidl
<%--
  Created by IntelliJ IDEA.
  User: self-consciousness
  Date: 2018/2/28
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>主页</title>
</head>
<frameset rows="20%,*">
    <frame src="<c:url value='/top.jsp'/>" name="top"/>
    <frame src="<c:url value='/index.jsp'/>" name="main"/>
</frameset>
</html>

```
- index.jsp

```aidl
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:forward page="/frame.jsp"/>
```

- list.jsp

```aidl
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

```

- msg.jsp

```aidl
<%--
  Created by IntelliJ IDEA.
  User: self-consciousness
  Date: 2018/3/1
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1 style="color:green;" align="center">${msg}</h1>

</body>
</html>

```

- query.jsp

```aidl
<%--
  Created by IntelliJ IDEA.
  User: self-consciousness
  Date: 2018/3/1
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>高级搜索</title>
</head>
<body>
<h3 align="center">高级搜索</h3>
<form action="<c:url value="/CustomerServlet"/>" method="get">
    <input type="hidden" name="method" value="query">
    <table border="0" align="center" width="40%" style="margin-left: 100px">
        <tr>
            <td width="100px">客户名称</td>
            <td width="40%">
                <input type="text" name="name">
            </td>
        </tr>
        <tr>
            <td>客户性别</td>
            <td>
                <select name="gender">
                    <option value="">==请选择性别==</option>
                    <option value="male">male</option>
                    <option value="female">female</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>手机</td>
            <td>
                <input type="text" name="phone"/>
            </td>
        </tr>
        <tr>
            <td>邮箱</td>
            <td>
                <input type="text" name="email"/>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>

                <input type="submit" value="搜索"/>
                <input type="reset" value="重置"/>
            </td>
        </tr>

    </table>
</form>

</body>
</html>

```

- top.jsp

```aidl
<%--
  Created by IntelliJ IDEA.
  User: self-consciousness
  Date: 2018/2/28
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <!-- 他的作用是为本页面所有的表单和超链接指定显示内容的框架-->
    <base target="main">
    <title>首页</title>
</head>
<body style="text-align: center;">
<h1>客户关系管理系统</h1>
<a href="<c:url value='/add.jsp'/>">添加客户</a>
<a href="<c:url value='/CustomerServlet?method=findAllCustomer'/>">查询客户</a>
<a href="<c:url value='/query.jsp'/>">高级搜索</a>

</body>
</html>

```

---

### 项目到此结束了，祝你码的成功，学的愉快~

> 一个基层的web项目是程序员的必经之路。知识在于沉淀，学习要脚踏实地。