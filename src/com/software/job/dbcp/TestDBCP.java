package com.software.job.dbcp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.software.job.dao.DBUtil;

public class TestDBCP {
	private DBCP dbcp=new DBCP();
	public void test1() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = dbcp.getConn();
		String sql = "select * from users ";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String s=rs.getString("uname");
				System.out.println(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void test2() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = DBUtil.getConn();
		String sql = "select * from users";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String s=rs.getString("uname");
				System.out.println(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(null, ps, conn);
		}
	}
	public static void main(String[] args) {
		long d1=System.currentTimeMillis();
		TestDBCP td=new TestDBCP();
		for(int i=0;i<30;i++) {
			td.test2();
		}
		long d2=System.currentTimeMillis();
		System.out.println(d2-d1);
	}
	
}
