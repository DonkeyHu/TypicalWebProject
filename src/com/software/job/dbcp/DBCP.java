package com.software.job.dbcp;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.software.job.dao.DBUtil;

public class DBCP {
	private List<Connection> pool;
	private static final int MIN_SIZE=50;
	private static final int MAX_SIZE=100;
	
	public DBCP() {
		init();
	}
	
	private void init() {
		if(pool==null) {
			pool=new ArrayList<Connection>();
		}
		for(int i=0;i<MIN_SIZE;i++) {
			Connection conn=DBUtil.getConn();
			pool.add(conn);
		}
	}
	
	public Connection getConn() {
		Connection c=pool.get(pool.size()-1);
		pool.remove(c);
		ProxyInvocation pi=new ProxyInvocation(c,this);
		return (Connection) Proxy.newProxyInstance(DBCP.class.getClassLoader(),new Class[] {Connection.class},pi);
	}
	
	public void closeConn(Connection c) {
		pool.add(c);
	}
}
