package com.software.job.vo;

import java.sql.Date;

import com.software.job.po.Users;
/*
 * 此javaBean为了方便展示页面数据，故和数据库类型不一样
 */
public class UserInfo {
	private int id;
	private String uname;
	private String pwd;
	private String email;
	private String phone;
	private int age;
	private String gender;//数据类型改变
	private String address;
	private String degree;//数据类型改变
	private Date joinTime;
	private String ip;
	
	public UserInfo() {
		
	}
	public UserInfo(Users users) {
		super();
		this.id = users.getId();
		this.uname = users.getUname();
		this.pwd = users.getPwd();
		this.email = users.getEmail();
		this.phone = users.getPhone();
		this.age = users.getAge();
		setGender(users.getGender());
		this.address = users.getAddress();
		setDegree(users.getDegree());
		this.joinTime = users.getJoinTime();
		this.ip = users.getIp();
	}
	public UserInfo(int id, String uname, String pwd, String email, String phone, int age, int gender, String address,
			int degree, Date joinTime, String ip) {
		super();
		this.id = id;
		this.uname = uname;
		this.pwd = pwd;
		this.email = email;
		this.phone = phone;
		this.age = age;
		setGender(gender);
		this.address = address;
		setDegree(degree);
		this.joinTime = joinTime;
		this.ip = ip;
	}

	public UserInfo(String uname, String pwd, String email, String phone, int age, int gender, String address, int degree,
			Date joinTime, String ip) {
		super();
		this.uname = uname;
		this.pwd = pwd;
		this.email = email;
		this.phone = phone;
		this.age = age;
		setGender(gender);
		this.address = address;
		setDegree(degree);
		this.joinTime = joinTime;
		this.ip = ip;
	}
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(int gender) {
		if(gender==0) {
			this.gender = "男";
	    }else {
	    	this.gender="女";
	    }
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		switch (degree) {
		case 0:
			this.degree = "高中";
			break;
		case 1:
			this.degree = "专科";
			break;
		case 2:
			this.degree = "本科";
			break;
		case 3:
			this.degree = "硕士";
			break;
		case 4:
			this.degree = "博士";
			break;
		}
	}
	public Date getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
