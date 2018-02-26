package com.software.job.service;

import java.util.List;

import com.software.job.dao.UserDao;
import com.software.job.dao.UserDaoImp;
import com.software.job.dao.UserDaoImp2;
import com.software.job.po.Users;
import com.software.job.util.PageNation;
import com.software.job.vo.UserInfo;

public class UserServiceImp implements UserService{
	private UserDao userDao=new UserDaoImp2();
	
	public UserServiceImp() {	
		
	}
	
	public void register(Users users){
		userDao.add(users);
	}

	public boolean checkName(String uname) {
		int unameNum=userDao.countUnameNum(uname);
		if(unameNum>0) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public Users login(String uname, String pwd) {
		 return userDao.getUserByUnamePwd(uname,pwd);
	}
	/*
	 * 实现分页功能
	 * @see com.software.job.service.UserService#listUsers()
	 */
	@Override
	public PageNation pageNation(String pageNum) {
		int rowNum=userDao.getUsersCount();
		PageNation p=new PageNation(pageNum,10,rowNum);
		//List<UserInfo> listUser=userDao.listAllUsers(p.getStartRow(), p.getStartRow()+9);
		List<UserInfo> listUser=userDao.listAllUsers(p.getStartRow(), p.getSize());
		p.setList(listUser);
		return p;
	}
	
}
