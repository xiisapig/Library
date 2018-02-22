package cn.ypjalt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ypjalt.entity.Book;
import cn.ypjalt.entity.Jieshu;
import cn.ypjalt.entity.QueryResult;
import cn.ypjalt.entity.User;
import cn.ypjalt.util.DBUtil;
import cn.ypjalt.util.ST;

public class JieshuDao extends DBUtil {
	public boolean insert(Book b, User s) {
		Connection conn = super.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = "insert into jieshu values(?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, s.getId());
			pst.setString(2, s.getName());
			pst.setInt(3, b.getBid());
			pst.setString(4, b.getBname());
			pst.setString(5, ST.getTime());
			int flag = pst.executeUpdate();
			if (flag > 0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.close(conn, pst, rs);
		}
		return false;
	}

	public Jieshu queryById(int id, int bid) {
		Connection conn = super.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select * from jieshu where id='" + id + "'and bid= ' " + bid + " ' ";
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
				Jieshu jieshu = new Jieshu();
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setName(rs.getString("name"));
				Book b = new Book();
				b.setBid(rs.getInt("bid"));
				b.setBname(rs.getString("bname"));
				String jtime = rs.getString("jtime");
				jieshu = new Jieshu(u, b, jtime);
				return jieshu;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.close(conn, pst, rs);
		}
		return null;
	}

	// 查找所有用户
	public QueryResult<Jieshu> queryAll(int page, int id, int roleId) {
		QueryResult<Jieshu> result = new QueryResult<Jieshu>();
		result.setNowPage(page);// result设置总页数
		int pageSize = result.getNum();// 每页页数
		Connection conn = super.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Jieshu> list = new ArrayList<Jieshu>();
			if (roleId == 1) {
				ps = conn.prepareStatement("select count(*) from jieshu ");
			} else {
				ps = conn.prepareStatement("select count(*) from jieshu where id='" + id + "'");
			}
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
			// 4
			if (page > pages)
				page = pages;
			if (page < 1)
				page = 1;
			// 5
			int begin = (page - 1) * pageSize;
			int end = page * pageSize;
			String sql = null;
			if (roleId == 1) {
				// sql = "select * from (select rownum rn,u.* from"
				// + "(select * from Jieshu order by id )u)"
				// + "where rn between ? and ?";
				sql = "select * from Jieshu order by id limit ?,?";
			} else {
				// sql = "select * from (select rownum rn,u.* from"
				// + "(select * from Jieshu where id='" + id
				// + "' order by id )u)" + "where rn between ? and ?";
				sql = "select * from Jieshu where id='" + id + "' order by id  limit ?,?";
			}
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

	private List<Jieshu> set(ResultSet rs, List<Jieshu> list) {
		// TODO Auto-generated method stub
		try {
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setName(rs.getString("name"));
				Book b = new Book();
				b.setBid(rs.getInt("bid"));
				b.setBname(rs.getString("bname"));
				String jtime = rs.getString("jtime");
				Jieshu j = new Jieshu(u, b, jtime);
				list.add(j);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	// 按名字找出用户
	public QueryResult<Jieshu> queryByName(int page, String name, int id, int roleId) {
		/*
		 * 1查找有多少条数据 2通过每页有多少条数据，知道有多少页数据 3如果查找的页数小于的1的话，让page=1
		 * 4如果查找的页数大于最大的，让page=最大页数 5找到起始值和结束值
		 */
		QueryResult<Jieshu> result = new QueryResult<Jieshu>();
		result.setNowPage(page);// result设置总页数
		int pageSize = result.getNum();// 每页页数
		Connection conn = super.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Jieshu> list = new ArrayList<Jieshu>();
			if (roleId == 1)
				ps = conn.prepareStatement("select count(*) from Jieshu where  name like ?");
			else
				ps = conn.prepareStatement("select count(*) from Jieshu where  id='" + id + "' and name like ?");
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
			if (page < 1)
				page = 1;
			// 4
			// 5
			int begin = (page - 1) * pageSize;
			int end = page * pageSize;
			String sql;
			if (roleId == 1) {
				// sql = "select * from (select rownum rn,u.* from"
				// + "(select * from Jieshu where name like ? order by id )u)"
				// + "where rn between ? and ?";
				sql = "select * from Jieshu where name like ? order by id limit ?,?";
			} else {
				// sql = "select * from (select rownum rn,u.* from"
				// + "(select * from Jieshu where id='" + id
				// + "' and name like ? order by id )u)"
				// + "where rn between ? and ?";
				sql = "select * from Jieshu where  id='" + id + "' and name like ? order by id limit ?,?";
			}
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

	public boolean delete(int id, int bid) {
		String sql = "delete  from jieshu where id='" + id + "'and bid= ' " + bid + " ' ";
		return super.executeSql(sql);
	}

}
