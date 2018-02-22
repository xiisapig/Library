package cn.ypjalt.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ypjalt.dao.AdviceDao;
import cn.ypjalt.entity.Advice;
import cn.ypjalt.entity.QueryResult;
import cn.ypjalt.entity.User;
import cn.ypjalt.util.ST;

public class AdviceAction extends HttpServlet {
	AdviceDao adviceDao = new AdviceDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("add".equals(action)) {
			this.add(request, response);
		} else if ("show".equals(action)) {
			this.show(request, response);
		} else if ("delete".equals(action)) {
			this.delete(request, response);
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String Str_id = request.getParameter("del_id");// 接收修改和删除的id
		if (Str_id != null) {
			int id = Integer.parseInt(Str_id);
			adviceDao.delete(id);
		}
	}

	private void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		if (loginUser.isRole()) {
			String str_page = request.getParameter("page");
			int page = 1;
			if (ST.isEmpty(str_page))
				page = 1;
			else
				page = Integer.parseInt(str_page);
			QueryResult<Advice> result = adviceDao.queryAll(page);
			request.setAttribute("queryResult", result);
			request.getRequestDispatcher("Advice.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("Advice.jsp").forward(request, response);
		}

	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String addAdvice = request.getParameter("bio");
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		Advice advice = new Advice(loginUser, addAdvice);
		if (adviceDao.insert(advice)) {
			request.setAttribute("message", "提交成功");
		} else {
			request.setAttribute("message", "提交失败");
		}
		request.getRequestDispatcher("Advice.jsp").forward(request, response);

	}

}
