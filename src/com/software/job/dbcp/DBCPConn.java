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
