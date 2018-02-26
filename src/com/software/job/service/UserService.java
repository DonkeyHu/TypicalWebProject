package com.software.job.service;


import com.software.job.po.Users;
import com.software.job.util.PageNation;

public interface UserService {
	public void register(Users users);
	public boolean checkName(String uname);
	public Users login(String uname,String pwd);
	public PageNation pageNation(String pageNum);
}
