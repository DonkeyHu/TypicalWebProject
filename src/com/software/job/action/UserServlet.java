package com.software.job.action;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;

import com.software.job.po.Users;
import com.software.job.service.UserService;
import com.software.job.service.UserServiceImp;
import com.software.job.util.PageNation;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserServiceImp();
	org.apache.logging.log4j.Logger logger = LogManager.getLogger("mylog");

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method=request.getParameter("method").trim();
		if("reg".equals(method)) {
			register(request,response);
		}else if("checkName".equals(method)) {
			checkName(request,response);
		}else if("login".equals(method)){
			login(request,response);
		}else if("exit".equals(method)) {
			exit(request,response);
		}else if("listUsers".equals(method)) {
			listUsers(request,response);
		}
	}

	private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		logger.info("将所有的用户数据查出");
		String pageNum=request.getParameter("pageNum");
		PageNation p=userService.pageNation(pageNum);
		request.setAttribute("pageNation",p);
		request.getRequestDispatcher("list_user_all.jsp").forward(request, response);
		return;
	}

	private void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		request.setCharacterEncoding("UTF-8");
		System.out.println(request.getParameter("address").trim());
		
		String uname = request.getParameter("uname").trim();
		String pwd = request.getParameter("pwd").trim();
		String email = request.getParameter("email").trim();
		String phone = request.getParameter("phone").trim();
		int age = Integer.parseInt(request.getParameter("age").trim());
		int gender = Integer.parseInt(request.getParameter("gender").trim());
		String address = request.getParameter("address").trim();
		int degree = Integer.parseInt(request.getParameter("degree").trim());
		java.sql.Date joinTime = new Date(new java.util.Date().getTime());
		String ip = request.getRemoteAddr();
		System.out.println(address);
		Users users = new Users(uname, pwd, email, phone, age, gender, address, degree, joinTime, ip);
		userService.register(users);
		request.getRequestDispatcher("reg_ok.jsp").forward(request, response);
		*/
		
		//第二版本：用过滤器+反射来保存表单信息；
		
		request.getSession().setAttribute("token", new java.util.Date().getTime());
		String ip = request.getRemoteAddr();
		java.sql.Date joinTime = new Date(new java.util.Date().getTime());
		Users users=(Users)request.getAttribute("formBean");
		users.setIp(ip);
		users.setJoinTime(joinTime);
		userService.register(users);
		request.getRequestDispatcher("reg_ok.jsp").forward(request, response);
		
	}

	private void checkName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uname = request.getParameter("uname").trim();
		boolean flag = userService.checkName(uname);
		response.getWriter().print(flag);
	}
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uname = request.getParameter("uname").trim();
		String pwd=request.getParameter("pwd").trim();
		Users users=userService.login(uname, pwd);
		if(users==null) {
			request.setAttribute("LoginError", "用户名或密码错误,请重新登入！");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}else {
			request.getSession().setAttribute("user", users);
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
	}
	private void exit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		response.sendRedirect("index.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
