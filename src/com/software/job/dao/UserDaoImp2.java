package com.software.job.dao;


import java.util.ArrayList;
import java.util.List;

import com.software.job.po.Users;
import com.software.job.vo.UserInfo;

public class UserDaoImp2 implements UserDao {
	public void add(Users users) {
		DBUtil.add(users);
	}
	/**
	 * 查找数据库是否有重复的名字
	 */
	public int countUnameNum(String uname) {
		String sql = "select count(*) from users where uname=?";
		//这里用于Oracle数据库
		//BigDecimal i=(BigDecimal)DBUtil.getValue(sql, new Object[] {uname});
		Long i=(Long)DBUtil.getValue(sql, new Object[] {uname});
		return i.intValue();
	}

	@Override
	public Users getUserByUnamePwd(String uname, String pwd) {
		String sql = "select * from users where uname=? and pwd=?";
		return (Users) DBUtil.getRowFields(sql, new Object[] {uname,pwd}, Users.class);
	}

	
	@Override
	public List<UserInfo> listAllUsers(int startNum,int endNum) {
		//String sql="select * from (select a.*,rownum rn from (select * from users) a)where rn between ? and ?";
		String sql="select * from users limit ?,?";
		List<UserInfo> list=new ArrayList<>();
		List<Users> listUser=DBUtil.getRowsFields(sql, new Object[] {startNum,endNum}, Users.class);
		//这里把User转为UserInfo，DBUtil.getRowsFields()还不能类型转换
		for (Users users : listUser) {
			UserInfo ui=new UserInfo(users);
			list.add(ui);
		}
		return list;
	}
	
	public int getUsersCount() {
		String sql="select count(*) from users";
		Long i=(Long)DBUtil.getValue(sql, null);
		return i.intValue();
	}
	
	public void updateImg(String imgpath,int id) {
		String sql="update users set imgpath=? where id=?";
		DBUtil.updateData(sql, new Object[] {imgpath,id});
	}
}
