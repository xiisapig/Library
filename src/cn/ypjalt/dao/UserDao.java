package cn.ypjalt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ypjalt.entity.QueryResult;
import cn.ypjalt.entity.User;
import cn.ypjalt.util.DBUtil;

public class UserDao extends DBUtil {
	public boolean login(User s) {
		Connection conn = super.openConnection();
		String sql = "select pwd from users where id=?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, s.getId());
			rs = pst.executeQuery();
			if (rs.next())
				if (s.getPwd().equals(rs.getString("pwd")))
					return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.close(conn, pst, rs);
		}
		return false;
	}

	public QueryResult<User> queryById(int id) {
		Connection conn = super.openConnection();
		QueryResult<User> result = new QueryResult<User>();
		String sql = "select * from users where id=?";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			List<User> list = new ArrayList<User>();
			result.setResultList(this.set(rs, list));
			result.setTotalNum(list.size());
			result.setNowPage(list.size());
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.close(conn, pstm, rs);
		}
		return null;
	}

	private List<User> set(ResultSet rs, List<User> list) {
		try {
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setPwd(rs.getString("pwd"));
				u.setName(rs.getString("name"));
				u.setSex(rs.getString("sex"));
				u.setDepart(rs.getString("depart"));
				u.setClasses(rs.getString("class"));
				u.setTel(rs.getString("tel"));
				u.setRole(rs.getInt("role"));
				list.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public boolean insert(User u) {
		Connection conn = super.openConnection();
		PreparedStatement pstm = null;
		String sql = "insert into users values(?,?,?,?,?,?,?,?)";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, u.getId());
			pstm.setString(2, u.getPwd());
			pstm.setString(3, u.getName());
			pstm.setString(4, u.getSex());
			pstm.setString(5, u.getDepart());
			pstm.setString(6, u.getClasses());
			pstm.setString(7, u.getTel());
			pstm.setInt(8, u.getRole());
			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.close(conn, pstm, null);
		}
		return false;
	}

	// 更新
	public boolean update(User u) {
		Connection conn = super.openConnection();
		try {
			conn.setAutoCommit(false);
			String sql = "update users set pwd='" + u.getPwd() + "',name='" + u.getName() + "',sex='" + u.getSex()
					+ "',depart='" + u.getDepart() + "' ,class='" + u.getClasses() + "',tel='" + u.getTel()
					+ "' where id='" + u.getId() + "'";
			super.executeSql(sql);
			conn.commit();
			return true;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			super.close(conn, null, null);
		}
		return false;
	}

	// 删除
	public boolean delete(int ID) {
		String sql = "delete  from users where id='" + ID + "'";
		return super.executeSql(sql);
	}

	// 查找所有用户
	public QueryResult<User> queryAll(int page) {
		/*
		 * 1查找有多少条数据 2通过每页有多少条数据，知道有多少页数据 3如果查找的页数小于的1的话，让page=1
		 * 4如果查找的页数大于最大的，让page=最大页数 5找到起始值和结束值
		 */
		QueryResult<User> result = new QueryResult<User>();
		result.setNowPage(page);// result设置总页数
		int pageSize = result.getNum();// 每页页数
		Connection conn = super.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<User> list = new ArrayList<User>();
			ps = conn.prepareStatement("select count(*) from users");
			rs = ps.executeQuery();
			// 计算有多少条数据
			int total = -1;// 记录数据
			if (rs.next())
				total = rs.getInt(1);
			result.setTotalNum(total);// 总共数据数字
			// 2
			int mod = total % pageSize;
			int pages = -1;// 总页数
			if (mod == 0)
				pages = total / pageSize;
			else
				pages = total / pageSize + 1;
			result.setTotalPage(pages);// 总页数
			// 3
			if (page > pages)
				page = pages;
			if (page < 1)
				page = 1;
			// 4
			// 5
			int begin = (page - 1) * pageSize;
			int end = page * pageSize;
			// String sql="select * from (select rownum rn,u.* from"+
			// "(select * from users order by id )u)"+
			// "where rn between ? and ?";
			String sql = "select * from users ";
			sql += " LIMIT ?,?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, begin);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			result.setResultList(this.set(rs, list));
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.close(conn, ps, rs);
		}
		return null;
	}

	// 按名字找出用户
	public QueryResult<User> queryByName(int page, String name) {
		/*
		 * 1查找有多少条数据 2通过每页有多少条数据，知道有多少页数据 3如果查找的页数小于的1的话，让page=1
		 * 4如果查找的页数大于最大的，让page=最大页数 5找到起始值和结束值
		 */
		QueryResult<User> result = new QueryResult<User>();
		result.setNowPage(page);// result设置总页数
		int pageSize = result.getNum();// 每页页数
		Connection conn = super.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<User> list = new ArrayList<User>();
			ps = conn.prepareStatement("select count(*) from users where name like ?");
			ps.setString(1, "%" + name + "%");
			rs = ps.executeQuery();
			// 计算有多少条数据
			int total = -1;// 记录数据
			if (rs.next())
				total = rs.getInt(1);
			result.setTotalNum(total);// 总共数据数字
			// 2
			int mod = total % pageSize;
			int pages = -1;// 总页数
			if (mod == 0)
				pages = total / pageSize;
			else
				pages = total / pageSize + 1;
			result.setTotalPage(pages);// 总页数
			// 3
			if (page > pages)
				page = pages;
			// 4
			if (page < 1)
				page = 1;
			// 5
			int begin = (page - 1) * pageSize;
			int end = pageSize;
			// String sql="select * from (select rownum rn,u.* from"+
			// "(select * from users where name like ? order by id )u)"+
			// "where rn between ? and ?";
			String sql = "select * from users where name like ? order by id";
			sql += " LIMIT ?,?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, begin);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			result.setResultList(this.set(rs, list));
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.close(conn, ps, rs);
		}
		return null;
	}

	// 按ID找出用户
	public QueryResult<User> queryByID(int page, int id) {
		/*
		 * 1查找有多少条数据 2通过每页有多少条数据，知道有多少页数据 3如果查找的页数小于的1的话，让page=1
		 * 4如果查找的页数大于最大的，让page=最大页数 5找到起始值和结束值
		 */
		QueryResult<User> result = new QueryResult<User>();
		result.setNowPage(page);// result设置总页数
		int pageSize = result.getNum();// 每页页数
		Connection conn = super.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<User> list = new ArrayList<User>();
			ps = conn.prepareStatement("select count(*) from users where id =?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			// 计算有多少条数据
			int total = -1;// 记录数据
			if (rs.next())
				total = rs.getInt(1);
			result.setTotalNum(total);// 总共数据数字
			// 2
			int mod = total % pageSize;
			int pages = -1;// 总页数
			if (mod == 0)
				pages = total / pageSize;
			else
				pages = total / pageSize + 1;
			result.setTotalPage(pages);// 总页数
			// 3
			if (page > pages)
				page = pages;
			if (page < 1)
				page = 1;
			// 4
			// 5
			int begin = (page - 1) * pageSize;
			int end = page * pageSize;
			// String sql="select * from (select rownum rn,u.* from"+
			// "(select * from users where id=? order by id )u)"+
			// "where rn between ? and ?";
			String sql = "select * from users  where id=? order by id";
			// sql += "LIMIT ?,?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			// ps.setInt(2,begin);
			// ps.setInt(3,end);
			rs = ps.executeQuery();
			result.setResultList(this.set(rs, list));
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.close(conn, ps, rs);
		}
		return null;
	}

}
