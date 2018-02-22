package cn.ypjalt.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ypjalt.dao.BookDao;
import cn.ypjalt.dao.HuanShuDao;
import cn.ypjalt.dao.JieshuDao;
import cn.ypjalt.entity.Book;
import cn.ypjalt.entity.Huanshu;
import cn.ypjalt.entity.Jieshu;
import cn.ypjalt.entity.QueryResult;
import cn.ypjalt.entity.User;
import cn.ypjalt.util.ST;

public class JieshuAction extends HttpServlet {
	JieshuDao jDao = new JieshuDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("searchUI".equals(action)) {
			this.searchUI(request, response);
		} else if ("add".equals(action)) {
			this.add(request, response);
		} else if ("huanshuUI".equals(action)) {
			this.huanshuUI(request, response);
		} else if ("delete".equals(action)) {
			this.delete(request, response);
		}

	}

	private void huanshuUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		// 接收查询关键字
		String name = request.getParameter("name");// 根据姓名模糊查询
		// 如果id name同时为空，表示是通过目录链接进行查询
		String str_page = request.getParameter("page");
		QueryResult<Huanshu> result = new QueryResult<Huanshu>();
		HuanShuDao hsDao = new HuanShuDao();
		int page = 1;
		if (ST.isEmpty(str_page))
			page = 1;
		else
			page = Integer.parseInt(str_page);
		if (ST.isEmpty(name)) {
			result = hsDao.queryAll(page);
		} else if (!ST.isEmpty(name)) { // 根据ID进行查询
			result = hsDao.queryByName(page, name);
		}
		request.setAttribute("name", name);
		request.setAttribute("queryResult", result);
		request.getRequestDispatcher("huanshu.jsp").forward(request, response);
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		int bid = Integer.parseInt(request.getParameter("bid"));
		HuanShuDao hsShuDao = new HuanShuDao();
		Jieshu jieshu = jDao.queryById(id, bid);
		hsShuDao.insert(jieshu);
		if (jDao.delete(id, bid)) {// 还书成功，书本数量加1，还书表增加一条数据
			BookDao bDao = new BookDao();
			Book book = bDao.queryById(bid);
			int nowBstore = book.getBstore() + 1;
			book.setBstore(nowBstore);
			bDao.update(book);

		}

	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		BookDao bDao = new BookDao();
		User loginUser = (User) session.getAttribute("loginUser");
		int bid = Integer.parseInt(request.getParameter("id"));
		Book book = bDao.queryById(bid);
		JieshuDao jdDao = new JieshuDao();
		if (jdDao.insert(book, loginUser)) {
			int nowBstore = book.getBstore() - 1;
			book.setBstore(nowBstore);
			bDao.update(book);
		}

	}

	private void searchUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		// 接收查询关键字
		String name = request.getParameter("name");// 根据姓名模糊查询
		// 如果id name同时为空，表示是通过目录链接进行查询
		String str_page = request.getParameter("page");
		QueryResult<Jieshu> result = new QueryResult<Jieshu>();
		int page = 1;
		if (ST.isEmpty(str_page))
			page = 1;
		else
			page = Integer.parseInt(str_page);
		if (ST.isEmpty(name)) {
			result = jDao.queryAll(page, loginUser.getId(), loginUser.getRole());
		} else if (!ST.isEmpty(name)) { // 根据ID进行查询
			result = jDao.queryByName(page, name, loginUser.getId(), loginUser.getRole());
		}
		request.setAttribute("name", name);
		request.setAttribute("queryResult", result);
		request.getRequestDispatcher("jieshu.jsp").forward(request, response);

	}

}
