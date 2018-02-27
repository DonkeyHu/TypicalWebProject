# TypicalWebProject
## 项目概述：
- 此项目是一个典型的MVC Java Web项目。用jsp+Servlet+javabean进行项目开发，并未用框架，希望从底层的角度，来了解一个MVC项目的构成。
- 后续，可以用Spring+SpringMVC+MyBatis对项目进行重构。
- 目的：主要是了解做项目的流程，练习写代码，并不会过分着重于需求的设计。

## 项目亮点：
- 对Dao层：自己**模拟了Hibernate**，手写了一个小型的ORM框架。原理说到底就是反射+JDBC的封装+Sql语句拼接字符串。
- 对Controller层：**模拟了SpringMVC**，用过滤器+反射，对提交的表单信息封装在JavaBean对象中。
- 手写了一个数据库连接池。
- 令牌机制防止表单重复提交。
- 注册表单的JS验证、Ajax用户名唯一性验证等等。

## 开发环境：
- jdk1.8+Tomcat 9+Mysql 5.7+Eclipse(本人用Oracle也做了一版，上传的程序是用的Mysql版)


## 项目功能模块：
- 用户注册、登录、退出，分页（列出用户），用户个人信息管理

## 项目开发（第一版）：
- Model：数据的封装和处理，javabean、jdbc
- Controller：数据的收集、调用相应的数据处理程序、跳转 ，servlet
- View：控制页面展示 ，jsp+jstl


## 项目流程：

##### 1.建立表结构
- Oracle建表注意[主键增长](https://zhidao.baidu.com/question/358461319.html)问题（本人曾用Oracle对项目进行开发过，此处是当时记录的笔记）
- Oracle数据库不能创建一个schema，只能通过创建用户，同时创建与用户名相同的schema。

##### 2.DAO层

##### 3.建立reg.jsp注册页面（先不增加JS验证）
- 建立处理表单的servlet，用来收集表单数据，构建javabean对象封装数据，调用service。
- 注意：（1）数据类型的转换；（2）得到客户端IP地址：request.getRemoteAddr();(3)Date类型
- 遇到问题：request.getParameter()获得参数中文乱码的问题。原因：Http请求传输数据将URL以ISO-8859-1编码，服务器收到字节流后默认以IOS-8859-1解码成字符流.....

##### 4.增加业务逻辑类 UserService
##### 5.面向借口编程：
- 编程的“开闭”原则，可拓展，尽量不要修改原来已有的代码，增加接口意味着可拓展性方便。

##### 6.增加表单的JS验证（使用正则表达式）
- String类原型里面增加方法：String.prototype.trim=function(
){}
- 正则表达式的判断：if(!re.test(v)){}

##### 7.Ajax增加用户名唯一性验证：
- 异步编程
- 遇到“换行符”问题：我这用 response.getWriter().println(""),我在客户端收到数据进行验证判断总为false，原因是传输数据把“换行符”的数据格式保留下来了......

##### 8.Servlet的重构，仿SpringMVC，对Servlet进行管理，Dispathch Action
- 其实就是加了一个判断语句
- 想想SpringMVC框架为你做的几件事：（1）数据的接收（包括类型转换）；（2）页面的跳转；（3）数据的response输出。

##### 9.增加登录、退出功能
- 登录成功：设置一个Session保存信息
- 退出：request.getSesssion().invalidate()

##### 10.把所有的用户列出来，增加vo类封装数据

##### 11.(1)增加分页
- 关键点在于取数据库里面的记录数，oracle里面[rownum](http://blog.csdn.net/gyb2013/article/details/6900753)的理解。
- PageNation单独封装，原因：UserServiceImp需要返回ListUser和UserCount两个模块的数据，你会选择怎么做？Map or 新建一个对象进行数据的封装。
##### (2).实现分页所用的导航条功能（这里也封装），并重构PageNation类，将其分装成单独模块。
- 错点：EL表达式提取数据其实是用了对象的get（）方法，修改了对象属性名称，但get（）set（）方法名却未改过来，导致提取不出数据(T.T)

##### 12.过滤器入门
- *图片加水印，中文问题，表单数据*

- Filter的init（）方法，一直会被调用，无论是否满足其URL

##### 13.反射机制
- 首先获取Class对象，然后就是得知道怎样调用属性，方法，构造器
- 想想为什么通常创建javaBean带个无参构造器，因为很多框架创建对象都以这种方式。

```
Person p=(Person)con.newInstance();
```
##### 14.通过反射封装JDBC（查找）
- 表和类对应，记录和对象对应，属性和字段对应
- 查询：多个字段多个记录（多个对象--->List）
- 错点：try catch错误打印最好一个对应一个，千万别Exception e 一起打印，否则错误会直接跳转，找不到哪出错了。 
- 第二个有意思的是，Oracle数据库全部字段名设置为大写，会不会有时候与JavaBean的驼峰命名法冲突？初步体会到数据库之间的差异了
- 在通过反射进行查询封装的时候遇到了一个有趣的bug，百思不得其解，而后回来用MySQL数据库连接测试才发现，这原来是连接数据库jar包的问题。其实就是ResultSet里面的getObject()对不同的驱动程序包有不同反应。同样的代码Oracle数据库会报错。关键是下面这两句代码：

```
Method m=c.getMethod(parseSetMethod(fieldName), f.getType());
m.invoke(obj, rs.getObject(fieldName));
```

```
	public static List getRowsFields(String sql, Object[] param, Class c) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = DBUtil.getConn();
		List list = new ArrayList();
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i + 1, param[i]);
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmt=rs.getMetaData();
			while (rs.next()) {
				Object obj=null;
				try {
					obj=c.newInstance();
					for(int j=0;j<rsmt.getColumnCount();j++) {
						String fieldName=rsmt.getColumnName(j+1);
						Field f=c.getDeclaredField(fieldName);
						//关键是这里这两步导致的错误
						Method m=c.getMethod(getSetMethod(fieldName), f.getType());
						m.invoke(obj, rs.getObject(fieldName));
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	private static String getSetMethod(String fieldName) {
		return "set"+fieldName.toUpperCase().substring(0,1)+fieldName.substring(1);
	}
```

```
java.lang.IllegalArgumentException: argument type mismatch
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at com.software.job.dao.DBUtil.getRowsFields(DBUtil.java:106)
	at com.software.job.dao.DBUtil.main(DBUtil.java:135)
```
- Oracle数据库数据类型与Java数据类型的的匹配问题

##### 15.ORM小框架（模拟Hibernate）（增删改）
- 异常一层层往上抛，由DAO层，到Service层，再到Servlet层实现处理，页面跳转。
- 反射+拼接字符串

##### 16.表单提交，通过过滤器+反射封装对象信息
- 目的：是把表单信息封装到javaBean中，自己想想该如何操作。

##### 17.令牌机制防表单重复提交
- 什么叫同一个请求？指的是提交的参数完全一致呗。
- 原理：1.在jsp页面设置了一个Session：

```
<%	
session.setAttribute("token", new Date().getTime());
%>

<input type="hidden" name="token" value="${sessionScope.token}"/>
```
2.filter对时间值进行判断。3.在服务端Servlet中再设置一个相同属性名称的Session对其覆盖。故当提交重复请求的话，时间值不对等。

##### 18.树结构
- 是一种数据结构的思想，关键在于PID，父节点ID
- 获取DOM的Node元素意味着可以获取其元素里面的所有内容

##### 19.手写数据库连接池和动态代理
- Connection里面是socket原理，数据库开启服务，在某个端口监听。
- 这“池（pool）”这概念倒像一种思想，一种缓存思想，“多”的时候用来提高效率。
- 与服务器每一次连接都会产生一个线程？
- 每次new新对象都会花费巨大资源，这一点毋庸置疑
- 用到Tomcat的DBCP连接池技术，引出[JNDI](http://blog.csdn.net/zhaosg198312/article/details/3979435#reply)(Java Naming and Directory Interface)技术，下面是配置代码：  
- JNDI顾名思义就是起接口的作用，数据源中定义好方法（模板），传入参数调用即可。针对外部资源的调用。
  
server.xml里面：

```
  <GlobalNamingResources>
    <Resource auth="Container" description="User database that can be updated and saved" factory="org.apache.catalina.users.MemoryUserDatabaseFactory" name="UserDatabase" pathname="conf/tomcat-users.xml" type="org.apache.catalina.UserDatabase"/>
   <!--maxIdle指如果没有用户连接，最多空出10个连接等待用户连接  --> 
    <Resource name="jdbc/test" type="javax.sql.DataSource" username="HuDongLing" password="123456" driverClassName="oracle.jdbc.driver.OracleDriver" maxIdle="10" maxWail="5000" url="jdbc:oracle:thin:@localhost:1521:xe" maxActive="20" />
  </GlobalNamingResources>
```
context.xml里面：

```
<ResourceLink global="jdbc/test" name="jdbc/test" type="javax.sql.DataSource" />
```
java里面调用：

```
package com.software.job.dbcp;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBCPConn {
	public static Connection getConns() throws Exception{
		 Context context=new InitialContext();
		 DataSource ds=(DataSource) context.lookup("java:comp/env/jdbc/test");
		 return ds.getConnection();
	}
}

```
##### 20.动态代理
- 会写篇博客单独分析

##### 21.增加log4j2
- 三部分主要内容：（1）Logger本身，有一个Root根节点，以及各继承关系子Logger，注意权限级别的排列;（2）appender，log可以输出到控制台或者文件;(3)第三个就是格式的排列了layout，在xml文件或者property文件中。

##### 22.文件上传
- *假设上传10M文件，是先放在内存里，还是写入硬盘上？内存和硬盘是什么概念？* 最后当然是写在硬盘上，但读写硬盘效率极低
- 先了解文件上传的基本过程：客户端是一个个字节将数据提交到服务端，故应该在服务端内存中设置一个缓存，例如：一个1M的缓存，上传满了1M，再将数据写入到硬盘上的一个临时文件上。
- web服务本质就是多线程+网络编程+IO流
- 进度条：涉及AJAX的技术，得知道传到服务端多少数据了，再除以数据总量。
- 图片上传，一般把路径保存
- 文件上传：原理就是IO流，request.getInputStream()，再用输出流输出到服务器。但是，要注意，request是把所有的数据都得到了，故需对数据进行分析，得到其有用的。一般用一些组件包完成。 

##### 23.个人管理中心
