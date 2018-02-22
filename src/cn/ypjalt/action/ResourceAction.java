package cn.ypjalt.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.ypjalt.dao.ResourceDao;
import cn.ypjalt.entity.Resource;
import cn.ypjalt.entity.User;

@SuppressWarnings("serial")
public class ResourceAction extends HttpServlet {

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("Resource servlet启动");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		ResourceDao rdao = new ResourceDao();
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("loginUser");
		if ("menu".equals(action)) {
			List<Resource> list = rdao.getRoot(false, u.getRole());
			Map<Integer, List<Resource>> map = new HashMap<Integer, List<Resource>>();
			for (Resource r : list)
				map.put(r.getId(), rdao.getChildren(r.getId(), false, u.getRole()));
			request.setAttribute("plist", list);
			request.setAttribute("cmap", map);
			request.getRequestDispatcher("../menu.jsp").forward(request, response);
		} else if ("priv".equals(action)) {
			List<Resource> list = rdao.getRoot(true, u.getRole());
			Map<Integer, List<Resource>> map = new HashMap<Integer, List<Resource>>();
			for (Resource r : list)
				map.put(r.getId(), rdao.getChildren(r.getId(), true, u.getRole()));
			request.setAttribute("plist", list);
			request.setAttribute("cmap", map);
			request.getRequestDispatcher("setPriv.jsp").forward(request, response);
		} else if ("issys".equals(action)) {
			String id = request.getParameter("id");
			String issys = request.getParameter("issys");
			rdao.updatePriv(Integer.parseInt(id), issys);
		}
	}
}
