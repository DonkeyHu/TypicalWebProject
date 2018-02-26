<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.software.job.dbcp.*,java.sql.*" %>

<% 
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	conn = DBCPConn.getConns();
	String sql = "select * from users ";
	try {
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			String s=rs.getString("UNAME");
			System.out.println(s);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		conn.close();
	}


%>