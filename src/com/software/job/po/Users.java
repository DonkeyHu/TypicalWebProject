package com.software.job.po;

import java.sql.Date;

public class Users {
	private int id;
	private String uname;
	private String pwd;
	private String email;
	private String phone;
	private int age;
	private int gender;
	private String address;
	private int degree;
	private Date joinTime;
	private String ip;
	private String imgpath;
	
	public Users() {
		
	}
	
	public Users(int id, String uname, String pwd, String email, String phone, int age, int gender, String address,
			int degree, Date joinTime, String ip) {
		super();
		this.id = id;
		this.uname = uname;
		this.pwd = pwd;
		this.email = email;
		this.phone = phone;
		this.age = age;
		this.gender = gender;
		this.address = address;
		this.degree = degree;
		this.joinTime = joinTime;
		this.ip = ip;
	}

	public Users(String uname, String pwd, String email, String phone, int age, int gender, String address, int degree,
			Date joinTime, String ip) {
		super();
		this.uname = uname;
		this.pwd = pwd;
		this.email = email;
		this.phone = phone;
		this.age = age;
		this.gender = gender;
		this.address = address;
		this.degree = degree;
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
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
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

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	
	
}
