package com.software.job.dbcp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

public class ProxyInvocation implements InvocationHandler{
	private Connection conn;
	private DBCP pool;
	public ProxyInvocation() {
		
	}
	
	public ProxyInvocation(Connection c,DBCP p) {
		this.conn=c;
		this.pool=p;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object obj=null;
		if("close".equals(method.getName())) {
			close();
		}else {
			obj=method.invoke(conn,args);
		}
		return obj;
	}
	
	public void close() {
		System.out.println("将连接返回连接池");
		pool.closeConn(conn);
	}
}
