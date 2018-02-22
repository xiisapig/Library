package cn.ypjalt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.ypjalt.entity.User;

public class LoginFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse response, FilterChain fc)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("loginUser");
		String uri = request.getRequestURI();
		if (u == null) {
			// 如果当前正在进行的是登录操作，则放行
			String action = request.getParameter("action");
			if ("login".equals(action))
				fc.doFilter(request, response);
			else {
				request.setAttribute("message", "请先登录");
				request.getRequestDispatcher("../login.jsp").forward(request, response);
			}
		} else
			fc.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("登录过滤器启动");

	}

}
