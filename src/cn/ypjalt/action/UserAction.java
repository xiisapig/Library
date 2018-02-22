package cn.ypjalt.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ypjalt.dao.UserDao;
import cn.ypjalt.entity.QueryResult;
import cn.ypjalt.entity.User;
import cn.ypjalt.util.ST;

public class UserAction extends HttpServlet {
	UserDao uDao = new UserDao();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action.equals("login")) {
			this.login(request, response);
		} else if ("searchUI".equals(action)) {
			this.searchUI(request, response);
		} else if ("modify_pwd".equals(action)) {
			this.modify_pwd(request, response);
		} else if ("modify_pwdUI".equals(action)) {
			this.modify_pwdUI(request, response);
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
		}

	}

	// 删除用户
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String Str_id = request.getParameter("del_id");// 接收修改和删除的id
		if (Str_id != null) {
			int id = Integer.parseInt(Str_id);
			uDao.delete(id);
		}
	}

	// 修改个人资料
	private void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));// 接收修改和删除的id
		User u = uDao.queryById(id).getResultList().get(0);
		String pwd = u.getPwd();
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String depart = request.getParameter("depart");
		String classes = request.getParameter("classes");
		String tel = request.getParameter("tel");
		u = new User(id, pwd, name, sex, depart, classes, tel, 0);
		request.setAttribute("modifyUser", u);
		if (uDao.update(u) == true)
			request.setAttribute("message", "修改成功");
		else
			request.setAttribute("message", "修改失败");
		request.getRequestDispatcher("modify.jsp").forward(request, response);

	}

	// 接收登录用户，跳到修改界面
	private void modifyUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("loginUser");
		int id;
		if (request.getParameter("modify_id") == null) {
			id = u.getId();
		} else {
			id = Integer.parseInt(request.getParameter("modify_id"));// 接收修改和删除的id
		}
		u = uDao.queryById(id).getResultList().get(0);
		request.setAttribute("modifyUser", u);
		request.getRequestDispatcher("modify.jsp").forward(request, response);

	}

	// 注册用户
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String pwd = request.getParameter("pwd2");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String depart = request.getParameter("depart");
		String classes = request.getParameter("classes");
		String tel = request.getParameter("tel");
		User user = new User(id, pwd, name, sex, depart, classes, tel, 0);
		if (uDao.insert(user)) {
			request.setAttribute("message", "注册成功");
		} else {
			request.setAttribute("message", "注册失败");
		}
		request.getRequestDispatcher("add.jsp").forward(request, response);
	}

	// 跳到注册页面
	private void addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("add.jsp").forward(request, response);

	}

	// 接收登录用户，跳到修改个人密码界面
	private void modify_pwdUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("modify_pwd.jsp").forward(request, response);

	}

	// 修改密码
	private void modify_pwd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String new_pwd = request.getParameter("new_pwd1");
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("loginUser");
		u.setPwd(new_pwd);
		if (uDao.update(u) == true) {
			request.setAttribute("message", "修改成功,请使用新密码登录");
			request.getRequestDispatcher("../login.jsp").forward(request, response);
		} else {
			request.setAttribute("message", "修改失败");
			request.getRequestDispatcher("modify_pwd.jsp").forward(request, response);
		}
	}

	// 准备数据，跳到搜索用户界面
	private void searchUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDao uDao = new UserDao();
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		// 接收查询关键字
		String Str_id = request.getParameter("id");// 根据用户名查询
		String name = request.getParameter("name");// 根据姓名模糊查询
		// 如果id name同时为空，表示是通过目录链接进行查询
		String str_page = request.getParameter("page");
		QueryResult<User> result = new QueryResult<User>();
		int page = 1;
		if (ST.isEmpty(str_page))
			page = 1;
		else
			page = Integer.parseInt(str_page);
		List<User> userList = new ArrayList<User>();
		if (loginUser.isRole()) {// 如果是管理员，显示所有人员信息
			if (ST.isEmpty(Str_id) && ST.isEmpty(name)) {
				result = uDao.queryAll(page);
			} else if (!ST.isEmpty(Str_id)) { // 根据ID进行查询
				int id = Integer.parseInt(Str_id);
				result = uDao.queryByID(page, id);
				request.setAttribute("id", id);
			} else if (!ST.isEmpty(name)) {// 根据姓名进行查询
				result = uDao.queryByName(page, name);
				// if(ST.page(result, page))//第一次查询名字的时候page>大于总共页数
				// result = uDao.queryByName(name, 1L);
				request.setAttribute("name", name);
			}
		} else {// 学生，显示自己信息
			result = uDao.queryById(loginUser.getId());
		}
		//
		request.setAttribute("queryResult", result);
		request.getRequestDispatcher("SearchUI.jsp").forward(request, response);

	}

	// 接收登录信息
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String pwd = request.getParameter("password");
		User user = new User();
		user.setId(id);
		user.setPwd(pwd);
		if (uDao.login(user)) {// 判断用户名或密码是否正确
			user = uDao.queryById(id).getResultList().get(0);
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", user);
			String vcode = request.getParameter("vcode");
			String obj = (String) session.getAttribute("code");
			if (vcode.equalsIgnoreCase(obj)) {
				request.getRequestDispatcher("../index.jsp").forward(request, response);
			} else {
				request.setAttribute("message", "验证码错误");
				request.setAttribute("userId", id);
				request.getRequestDispatcher("../login.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("message", "用户名或密码错误");
			request.setAttribute("userId", id);
			request.getRequestDispatcher("../login.jsp").forward(request, response);
		}
	}

}
