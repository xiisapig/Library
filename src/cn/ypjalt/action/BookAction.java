package cn.ypjalt.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ypjalt.dao.BookDao;
import cn.ypjalt.dao.JieshuDao;
import cn.ypjalt.entity.Book;
import cn.ypjalt.entity.QueryResult;
import cn.ypjalt.entity.User;
import cn.ypjalt.util.ST;

public class BookAction extends HttpServlet {
	BookDao bDao = new BookDao();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("searchUI".equals(action)) {
			this.searchUI(request, response);
		} else if ("addUI".equals(action)) {
			this.addUI(request, response);
		} else if ("add".equals(action)) {
			this.add(request, response);
		} else if ("modifyUI".equals(action)) {
			this.modifyUI(request, response);
		} else if ("modify".equals(action)) {
			this.modify(request, response);
		} else if ("delete".equals(action)) {
			this.delete(request, response);
		} else if ("jieshu".equals(action)) {
			this.jieshu(request, response);
		}

	}

	private void jieshu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int bid = Integer.parseInt(request.getParameter("id"));
		Book book = bDao.queryById(bid);
		JieshuDao jdDao = new JieshuDao();
		if (jdDao.insert(book, loginUser)) {
			int nowBstore = book.getBstore() - 1;
			book.setBstore(nowBstore);
			bDao.update(book);
			request.setAttribute("message", "借书成功");
		} else {
			request.setAttribute("message", "不要太贪心哦，一人只能借一本");
		}
		this.searchUI(request, response);

	}

	// 删除用户
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String Str_id = request.getParameter("del_id");// 接收修改和删除的id
		if (Str_id != null) {
			int id = Integer.parseInt(Str_id);
			bDao.delete(id);
		}
	}

	private void modifyUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("modify_id"));
		Book book = bDao.queryById(id);
		request.setAttribute("modifyBook", book);
		request.getRequestDispatcher("modify.jsp").forward(request, response);

	}

	private void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bid = Integer.parseInt(request.getParameter("bid"));
		String bname = request.getParameter("bname");
		String bauthor = request.getParameter("bauthor");
		String btype = request.getParameter("btype");
		int bprice = Integer.parseInt(request.getParameter("bprice"));
		String bpublisher = request.getParameter("bpublisher");
		int bstore = Integer.parseInt(request.getParameter("bstore"));
		Book b = new Book(bid, bname, bauthor, btype, bprice, bpublisher, bstore);
		request.setAttribute("modifyBook", b);
		if (bDao.update(b))
			request.setAttribute("message", "图书修改成功");
		else
			request.setAttribute("message", "图书修改失败");
		request.getRequestDispatcher("modify.jsp").forward(request, response);

	}

	private void addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("add.jsp").forward(request, response);

	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// int bid=Integer.parseInt(request.getParameter("bid"));
		String bname = request.getParameter("bname");
		String bauthor = request.getParameter("bauthor");
		String btype = request.getParameter("btype");
		int bprice = Integer.parseInt(request.getParameter("bprice"));
		String bpublisher = request.getParameter("bpublisher");
		int bstore = Integer.parseInt(request.getParameter("bstore"));
		Book b = new Book(0, bname, bauthor, btype, bprice, bpublisher, bstore);
		BookDao bDao = new BookDao();
		if (bDao.insert(b)) {
			request.setAttribute("message", "图书添加成功");
		} else {
			request.setAttribute("message", "图书添加失败");
		}
		request.getRequestDispatcher("add.jsp").forward(request, response);

	}

	// 准备数据，跳到搜索用户界面
	private void searchUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		// 接收查询关键字
		String name = request.getParameter("name");// 根据姓名模糊查询
		String pub = request.getParameter("pub");// 根据姓名模糊查询
		String author = request.getParameter("author");// 根据姓名模糊查询
		// 如果id name同时为空，表示是通过目录链接进行查询
		String str_page = request.getParameter("page");
		QueryResult<Book> result = new QueryResult<Book>();
		int page = 1;
		if (ST.isEmpty(str_page))
			page = 1;
		else
			page = Integer.parseInt(str_page);
		if (ST.isEmpty(name) && ST.isEmpty(pub) && ST.isEmpty(author)) {
			result = bDao.queryAll(page);
		} else if (!ST.isEmpty(name)) { // 根据ID进行查询
			result = bDao.queryByName(page, name);
		} else if (!ST.isEmpty(author)) {// 根据姓名进行查询
			result = bDao.queryByAuthor(page, author);
		} else if (!ST.isEmpty(pub)) {// 根据姓名进行查询
			result = bDao.queryByPub(page, pub);
		}
		request.setAttribute("bname", name);
		request.setAttribute("bauthor", author);
		request.setAttribute("pub", pub);
		request.setAttribute("queryResult", result);
		request.getRequestDispatcher("SearchUI.jsp").forward(request, response);
	}

}
