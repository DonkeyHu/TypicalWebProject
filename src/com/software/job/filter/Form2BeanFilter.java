package com.software.job.filter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.software.job.util.BeanUtil;

@WebFilter("/*")
public class Form2BeanFilter implements Filter {

    public Form2BeanFilter() {
    }

	
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String className=request.getParameter("formToBean");
		Object obj=null;
		if(className!=null) {
			try {
				Class c=Class.forName(className);
				obj=c.newInstance();
				Field[] f=c.getDeclaredFields();
				for (Field field : f) {
					String fieldName=field.getName();
					Class fieldType=field.getType();
					//这里主要原因是约定好的javaBean属性名称和表单的一致。
					String param=request.getParameter(fieldName);
					if(param!=null) {
						Method m=c.getMethod(BeanUtil.parseSetMethod2(fieldName), fieldType);
						String typeName=fieldType.getName();
						if("int".equals(typeName)||"java.lang.Integer".equals(typeName)) {
							m.invoke(obj, Integer.parseInt(param));
						}else if("long".equals(typeName)||"java.lang.Long".equals(typeName)) {
							m.invoke(obj, Long.parseLong(param));
						}else if("byte".equals(typeName)|| "java.lang.Byte".equals(typeName)) {
							m.invoke(obj, Byte.parseByte(param));
						}else if("short".equals(typeName)|| "java.lang.Short".equals(typeName)) {
							m.invoke(obj, Short.parseShort(param));
						}else if("float".equals(typeName)||"java.lang.Float".equals(typeName)) {
							m.invoke(obj, Float.parseFloat(param));
						}else if("double".equals(typeName)|| "java.lang.Double".equals(typeName)) {
							m.invoke(obj, Double.parseDouble(param));
						}else {
							m.invoke(obj, param);
						}
						
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}	
		}
		request.setAttribute("formBean", obj);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
