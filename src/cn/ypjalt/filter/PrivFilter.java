package cn.ypjalt.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.ypjalt.dao.ResourceDao;
import cn.ypjalt.entity.Resource;
import cn.ypjalt.entity.User;

public class PrivFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse response, FilterChain fc)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("loginUser");
		if (u == null)
			fc.doFilter(request, response);
		else {
			// 判断当前访问的地址是否受控
			String uri = request.getRequestURI();// gpa/student/StudentAction?action=delete
			String action = request.getParameter("action");
			// 拼成一个完成的地址
			int index = uri.indexOf("?");
			if (index >= 0)
				uri = uri.substring(0, index);
			index = uri.lastIndexOf("/");
			if (index >= 0)
				uri = uri.substring(index + 1);
			uri = uri + "?action=" + action;// UserAction?action=delete
			uri.replaceAll("UI", "");
			boolean flag = false;
			ResourceDao rdao = new ResourceDao();
			List<Resource> list = rdao.getManagerResourceList();
			for (Resource r : list) {// student/StudentAction?action=delete
				if (r.getAddress() != null && r.getAddress().replaceAll("UI", "").endsWith(uri)) {
					flag = true;
					break;
				}
			}
			// 如果受控则需要判断身份
			if (!flag || (flag && u.isRole())) {
				fc.doFilter(request, response);
			} else {
				request.setAttribute("message", "没有权限");
				request.getRequestDispatcher("../login.jsp").forward(request, response);// 返回权限提示的页面
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("权限过滤器启动");

	}

}
