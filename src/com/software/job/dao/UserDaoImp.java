package com.software.job.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.software.job.po.Users;
import com.software.job.vo.UserInfo;

public class UserDaoImp implements UserDao {
	public void add(Users users) {
		Connection conn = null;
		PreparedStatement ps = null;
		conn = DBUtil.getConn();
		String sql="insert into users (uname,pwd,email,phone,age,gender,address,degree,jointime,ip) values(?,?,?,?,?,?,?,?,?,?)";
		//String sql = "insert into users (id,uname,pwd,email,phone,age,gender,address,degree,jointime,ip) values(seq_users.nextval,?,?,?,?,?,?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setObject(1, users.getUname());
			ps.setObject(2, users.getPwd());
			ps.setObject(3, users.getEmail());
			ps.setObject(4, users.getPhone());
			ps.setObject(5, users.getAge());
			ps.setObject(6, users.getGender());
			ps.setObject(7, users.getAddress());
			ps.setObject(8, users.getDegree());
			ps.setObject(9, users.getJoinTime());
			ps.setObject(10, users.getIp());

			ps.executeUpdate();
			System.out.println("一个用户注册成功了!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(null, ps, conn);
		}
	}
	/**
	 * 查找数据库是否有重复的名字
	 */
	public int countUnameNum(String uname) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int unameNum = 0;
		conn = DBUtil.getConn();
		String sql = "select count(*) from users where uname=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setObject(1, uname);
			rs = ps.executeQuery();
			while (rs.next()) {
				unameNum = rs.getInt(1);
			}
			System.out.println("已经查询数据库里面的UNAME字段了");
			return unameNum;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			DBUtil.close(null, ps, conn);
		}
	}

	@Override
	public Users getUserByUnamePwd(String uname, String pwd) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = DBUtil.getConn();
		Users users = null;
		String sql = "select * from users where uname=? and pwd=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, uname);
			ps.setString(2, pwd);
			rs = ps.executeQuery();
			while (rs.next()) {
				users = new Users(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6),
						rs.getInt(7), rs.getString(8), rs.getInt(9), rs.getDate(10), rs.getString(11));
			}
			System.out.println("通过用户名密码查找用户!");
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.close(rs, ps, conn);
		}
	}

	
	@Override
	public List<UserInfo> listAllUsers(int startNum,int endNum) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<UserInfo> list=new ArrayList<UserInfo>();
		conn=DBUtil.getConn();
		//String sql="select * from (select a.*,rownum rn from (select * from users) a)where rn between ? and ?";
		String sql="select * from users limit ?,?";
		try {
			ps=conn.prepareStatement(sql);
			ps.setInt(1, startNum);
			ps.setInt(2, endNum);
			rs=ps.executeQuery();
			while(rs.next()) {
				UserInfo userInfo=new UserInfo(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6),
						rs.getInt(7), rs.getString(8), rs.getInt(9), rs.getDate(10), rs.getString(11));
				list.add(userInfo);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
	}
	
	public int getUsersCount() {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		conn=DBUtil.getConn();
		int count=0;
		String sql="select count(*) from users";
		try {
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				count=rs.getInt(1);
			}
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return count;
		}
	}
	@Override
	public void updateImg(String imgpath, int id) {
		
	}
}
