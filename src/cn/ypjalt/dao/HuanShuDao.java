package cn.ypjalt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ypjalt.entity.Book;
import cn.ypjalt.entity.Huanshu;
import cn.ypjalt.entity.Jieshu;
import cn.ypjalt.entity.QueryResult;
import cn.ypjalt.entity.User;
import cn.ypjalt.util.DBUtil;
import cn.ypjalt.util.ST;

public class HuanShuDao extends DBUtil {
	public boolean insert(Jieshu s) {
		Connection conn = super.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = "insert into huanshu values(?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, s.getUser().getId());
			pst.setString(2, s.getUser().getName());
			pst.setInt(3, s.getBook().getBid());
			pst.setString(4, s.getBook().getBname());
			pst.setString(5, s.getJtime());
			pst.setString(6, ST.getTime());
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

	private List<Huanshu> set(ResultSet rs, List<Huanshu> list) {
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
				Huanshu huanshu = new Huanshu(j, rs.getString("htime"));
				list.add(huanshu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	// 按名字找出用户
	public QueryResult<Huanshu> queryByName(int page, String name) {

		QueryResult<Huanshu> result = new QueryResult<Huanshu>();
		result.setNowPage(page);// result设置总页数
		int pageSize = result.getNum();// 每页页数
		Connection conn = super.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Huanshu> list = new ArrayList<Huanshu>();
			ps = conn.prepareStatement("select count(*) from Huanshu where name like ?");
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
			// 4
			if (page > pages)
				page = pages;
			if (page < 1)
				page = 1;
			// 5
			int begin = (page - 1) * pageSize;
			int end = page * pageSize;
			// String sql="select * from (select rownum rn,u.* from"+
			// "(select * from Huanshu where name like ? order by id )u)"+
			// "where rn between ? and ?";
			String sql = "select * from Huanshu where name like ? order by id";
			sql += " limit ?,?";
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

	public QueryResult<Huanshu> queryAll(int page) {
		QueryResult<Huanshu> result = new QueryResult<Huanshu>();
		result.setNowPage(page);// result设置总页数
		int pageSize = result.getNum();// 每页页数
		Connection conn = super.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Huanshu> list = new ArrayList<Huanshu>();
			ps = conn.prepareStatement("select count(*) from Huanshu");
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
			// 5
			if (page < 1)
				page = 1;
			// 4
			int begin = (page - 1) * pageSize;
			int end = page * pageSize;
			// String sql="select * from (select rownum rn,u.* from"+
			// "(select * from Huanshu order by id )u)"+
			// "where rn between ? and ?";
			String sql = "select * from Huanshu  order by id";
			sql += " limit ?,?";
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

}
