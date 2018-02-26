package com.software.job.dao;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.software.job.dbcp.DBCPConn;
import com.software.job.po.Users;
import com.software.job.util.BeanUtil;

public class DBUtil {
	static Properties prop=null;
	static String driverclass=null;
	static String url=null;
	static String uname=null;
	static String pwd=null;
	
	static {
		prop=new Properties();
		try {
			prop.load(DBUtil.class.getClassLoader().getResourceAsStream("db.properties"));
			driverclass=prop.getProperty("MySqlDriverClass");
			url=prop.getProperty("MySqlURL");
			uname=prop.getProperty("MySqlUname");
			pwd=prop.getProperty("MySqlPwd");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConn() {
		Connection conn=null;
		try {
			Class.forName(driverclass);
			conn=DriverManager.getConnection(url, uname, pwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//第二版：使用Tomcat的DBCP连接池
	/*
	public static Connection getConn() {
		try {
			return DBCPConn.getConns();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	*/
	public static void close(ResultSet rs,Statement stat,Connection conn) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(stat!=null) {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 利用反射进行JDBC封装,这里是查询多个字段，多个记录，封装成对象，加入到集合里
	 * @param sql
	 * @param param
	 * @param c
	 * @return
	 */
	public static List getRowsFields(String sql, Object[] param, Class c) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = DBUtil.getConn();
		List list = new ArrayList();
		try {
			ps = conn.prepareStatement(sql);
			if(param!=null) {
				for (int i = 0; i < param.length; i++) {
					ps.setObject(i + 1, param[i]);
				}
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmt=rs.getMetaData();
			while (rs.next()) {
				Object obj=null;
				try {
					obj=c.newInstance();
					for(int j=0;j<rsmt.getColumnCount();j++) {
						String fieldName=rsmt.getColumnName(j+1);
						Field f=null;
						f=c.getDeclaredField(fieldName);
						Method m=c.getMethod(BeanUtil.parseSetMethod2(fieldName), f.getType());
						m.invoke(obj, rs.getObject(j+1));
						/*
						 * 一下是针对Oracle数据库而言：
						 *
						//这里又是因为Oracle数据库分页的ROWNUM字段造成的判断条件
						if(!"RN".equals(fieldName)) {
							//这里我多加一个对JOINTIME属性的判断，因为实在不清楚Oracle字段名与java驼峰命名冲突了怎么办
							if("JOINTIME".equals(fieldName)) {
								  f=c.getDeclaredField("joinTime");
							}else {
								  f=c.getDeclaredField(fieldName.toLowerCase());
							}
							//关键是这里这两步导致的错误
							Method m=c.getMethod(BeanUtil.parseSetMethod(fieldName), f.getType());
							String typeName=f.getType().getName();
							//这里可用判断解决类型匹配问题！！
							if("int".equals(typeName)||"java.lang.Integer".equals(typeName)) {
								m.invoke(obj, rs.getInt(fieldName));
							}else if("long".equals(typeName)||"java.lang.Long".equals(typeName)) {
								m.invoke(obj, rs.getLong(fieldName));
							}else if("byte".equals(typeName)|| "java.lang.Byte".equals(typeName)) {
								m.invoke(obj, rs.getByte(fieldName));
							}else if("short".equals(typeName)|| "java.lang.Short".equals(typeName)) {
								m.invoke(obj, rs.getShort(fieldName));
							}else if("float".equals(typeName)||"java.lang.Float".equals(typeName)) {
								m.invoke(obj, rs.getFloat(fieldName));
							}else if("double".equals(typeName)|| "java.lang.Double".equals(typeName)) {
								m.invoke(obj, rs.getDouble(fieldName));
							}else if("Date".equals(typeName)||"java.sql.Date".equals(typeName)) {
								m.invoke(obj, rs.getDate(fieldName));
							}else {
								m.invoke(obj, rs.getString(fieldName));
							}
						}
						*/
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
		}finally {
			DBUtil.close(rs, ps, conn);
		}
		return list;
	}
	
	public static Object getRowFields(String sql, Object[] param, Class c) {
		List list=getRowsFields( sql,  param, c);
		if(list!=null && list.size()>0 ) {
			return list.get(0); 
		}else {
			return null;
		}
	}
	public static Object getValue(String sql,Object[] param) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = DBUtil.getConn();
		Object obj=null;
		try {
			ps = conn.prepareStatement(sql);
			if(param!=null) {
				for (int i = 0; i < param.length; i++) {
					ps.setObject(i + 1, param[i]);
				}
			}	
			rs = ps.executeQuery();
			while (rs.next()) {
				obj=rs.getObject(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(rs, ps, conn);
		}
		return obj;
	}
	public static int updateData(String sql,Object...param) {
		Connection conn=null;
		PreparedStatement ps=null;
		int numResult=0;
		conn=DBUtil.getConn();
		try {
			ps=conn.prepareStatement(sql);
			if(param!=null) {
				for(int i=0;i<param.length;i++) {
					ps.setObject(i+1, param[i]);
				}
			}
			numResult=ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(null, ps, conn);
		}
		return numResult;
	}
	//模拟一个ORM映射,这里是针对Oracle数据库的字符串拼凑。这里是加判断来控制所需要添加的字段
	public static void add(Object obj) {
		StringBuilder sb=new StringBuilder("insert into ");
		StringBuilder sb2=new StringBuilder();
		String className=obj.getClass().getName().toLowerCase();
		sb.append(className.substring(className.lastIndexOf(".")+1));
		sb.append(" (");
		Field[] f=obj.getClass().getDeclaredFields();
		List param=new ArrayList();
		for(int i=0;i<f.length;i++) {
			if(!"id".equalsIgnoreCase(f[i].getName())) {
				sb.append(f[i].getName());
				try {
					Method m=obj.getClass().getMethod(BeanUtil.parseGetMethod(f[i].getName()));
					param.add(m.invoke(obj));
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				sb2.append("?");
				if(i<f.length-1) {
					sb.append(",");
					sb2.append(",");
				}
			}
		}
		/*
		 * 一下是针对Oracle数据库的字符串拼接 
		 *
		for(int i=0;i<f.length;i++) {
				sb.append(f[i].getName());
				System.out.println("-------"+f[i].getName());
				try {
					if(!"id".equalsIgnoreCase(f[i].getName())) {
						Method m=obj.getClass().getMethod(BeanUtil.parseGetMethod(f[i].getName()));
						param.add(m.invoke(obj));
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				if("id".equalsIgnoreCase(f[i].getName())) {
					sb2.append("seq_users.nextval");	
				}else {
					sb2.append("?");
				}
				if(i<f.length-1) {
					sb.append(",");
					sb2.append(",");
				}
		}
		*/
		sb.append(") values ("+sb2+")");
		System.out.println("Hu's Hiberate:"+sb);
		updateData(sb.toString(), param.toArray());
	}
	
	public static Object getObjById(int id,Class c) {
		String sql="select * from "+c.getSimpleName()+" where id=?";
		return getRowFields(sql, new Object[] {id}, c);
	}
	
	public static void update(Object obj) {
		StringBuilder sb=new StringBuilder("update "+obj.getClass().getSimpleName().toLowerCase()+" ");
		sb.append("set ");
		Field[] f=obj.getClass().getDeclaredFields();
		List param=new ArrayList();
		for(int i=0;i<f.length;i++) {
			if(!"id".equalsIgnoreCase(f[i].getName())) {
				sb.append(f[i].getName()+"=?");
				if(i<f.length-1) {
					sb.append(",");
				}
				try {
					Method m=obj.getClass().getMethod(BeanUtil.parseGetMethod(f[i].getName()));
					param.add(m.invoke(obj));
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			
		}
		sb.append(" where id=?");
		try {
			Method m1=obj.getClass().getMethod(BeanUtil.parseGetMethod("id"));
			param.add(m1.invoke(obj));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println("Hu's Hiberate:"+sb);
		updateData(sb.toString(), param.toArray());
	}
	
	public static void addOrUpdate(Object obj) {
		int num=0;
		try {
			Method m=obj.getClass().getMethod(BeanUtil.parseGetMethod("id"));
			num=(Integer) m.invoke(obj);
			Long i=(Long)getValue("select count(*) from "+obj.getClass().getSimpleName()+" where id=?", new Object[] {num});
			if(i.intValue()>0) {
				update(obj);
			}else {
				add(obj);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	public static void delete(Object obj) {
		int id=0;
		try {
			Method m=obj.getClass().getMethod(BeanUtil.parseGetMethod("id"));
			id=(Integer) m.invoke(obj);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		String sql="delete from "+obj.getClass().getSimpleName()+" where id=?";
		updateData(sql, id);
		System.out.println("删除ID为"+id+"的用户！");
	}
	
	public static void main(String[] args) {
//		System.out.println(parseSetMethod("UNAME"));
//		List list=getRowsFields("select * from users where id>?",new Object[] {7},Users.class);
//		System.out.println(list.size());
//		Users u=(Users) getRowFields("select * from users where id=?", new Object[] {7}, Users.class);
//		System.out.println(u);
//		BigDecimal i=(BigDecimal) getValue("select count(*) from users",null);
//		System.out.println(i);
		java.sql.Date joinTime = new Date(new java.util.Date().getTime());
		Users users = new Users("zhangdan", "qwqwqw", "21234423@qq.com", "13135226159", 22, 1, "Beijing", 5, joinTime, "191.123.1.1");
		users.setId(5);
//		update(users);
//		addOrUpdate(users);
		delete(users);
		
	}
}
