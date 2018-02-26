package com.software.job.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.software.job.dao.UserDao;
import com.software.job.dao.UserDaoImp2;
import com.software.job.po.Users;
import com.software.job.util.ImageUtil;



@WebServlet("/UpFileServlet")
public class UpFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao=new UserDaoImp2();
    public UpFileServlet() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tempDirectory="C:/t/";
		String fileDirectory="C:/a/";
		int sizeThreshold=1024 * 10; //这里设置缓存的大小10k
		File repositoryFile=new File(tempDirectory);
		FileItemFactory factory=new DiskFileItemFactory(sizeThreshold,repositoryFile);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(1024 * 1024 * 500);//设置最大的文件大小
		try {
			//解析请求，这里是关键，从流里面读取想要的文件数据
			List items = upload.parseRequest(request);
			Iterator  iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();//FileItem就表示一个表单域
				//这里isFormField方法用来判断是否是普通表单域(即非File表单域)
				if (item.isFormField()) {
					System.out.println("******"+item.getFieldName());//返回表单域的名字
					System.out.println("******"+item.getString("utf-8"));//返回表单域的值
				} else {
					String fieldName = item.getFieldName();
				    String fileName = item.getName();//这里是指文件名，eg:baifu.png
				    System.out.println("***"+fieldName);
				    System.out.println("***"+fileName);
				    //下面截字符串的作用就是防止文件名重复
				    String fileType=fileName.substring(fileName.lastIndexOf("."));
				    long date=new Date().getTime();
				    File uploadedFile = new File(fileDirectory+date+fileType);
				    item.write(uploadedFile);
				    
				    System.out.println(this.getServletContext().getRealPath("/img"));
				    
				    ImageUtil.toSmall(uploadedFile, new File(this.getServletContext().getRealPath("/img")+"/"+date+".gif"), 100, 100);
				    Users users=(Users)request.getSession().getAttribute("user");
				    userDao.updateImg(date+".gif", users.getId());
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
