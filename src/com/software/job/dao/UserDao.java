package com.software.job.dao;

import java.util.List;

import com.software.job.po.Users;
import com.software.job.vo.UserInfo;

public interface UserDao {
	public void add(Users users);
	public int countUnameNum(String uname);
	public Users getUserByUnamePwd(String uname,String pwd);
	public List<UserInfo> listAllUsers(int startNum,int endNum);
	public int getUsersCount();
	public void updateImg(String imgpath,int id);
}
