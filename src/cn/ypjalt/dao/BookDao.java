package cn.ypjalt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ypjalt.entity.Book;
import cn.ypjalt.entity.QueryResult;
import cn.ypjalt.util.DBUtil;

public class BookDao extends DBUtil {
	public boolean insert(Book b) {
		Connection conn = super.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = "insert into book(bname,bauthor,btype,bprice,bpublisher,bstore) values(?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			// pst.setInt(1, b.getBid());
			pst.setString(1, b.getBname());
			pst.setString(2, b.getBauthor());
			pst.setString(3, b.getBtype());
			pst.setInt(4, b.getBprice());
			pst.setString(5, b.getBpublisher());
			pst.setInt(6, b.getBstore());
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

	public boolean delete(int ID) {
		String sql = "delete  from book where bid='" + ID + "'";
		return super.executeSql(sql);
	}

	public boolean update(Book b) {
		Connection conn = super.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			String sql = "update book set bname=?,bauthor=?, btype=?, bprice=?,  bpublisher=?, bstore=? where bid=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, b.getBname());
			pst.setString(2, b.getBauthor());
			pst.setString(3, b.getBtype());
			pst.setInt(4, b.getBprice());
			pst.setString(5, b.getBpublisher());
			pst.setInt(6, b.getBstore());
			pst.setInt(7, b.getBid());
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

	public Book queryById(int id) {
		Connection conn = super.openConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "select * from book where bid=" + id + "";
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			Book b = new Book();
			if (rs.next()) {
				b.setBid(rs.getInt("bid"));
				b.setBname(rs.getString("bname"));
				b.setBauthor(rs.getString("bauthor"));
				b.setBtype(rs.getString("btype"));
				b.setBprice(rs.getInt("bprice"));
				b.setBpublisher(rs.getString("bpublisher"));
				b.setBstore(rs.getInt("bstore"));
			}
			return b;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.close(conn, pst, rs);
		}
		return null;
	}

	private List<Book> set(ResultSet rs, List<Book> list) {
		try {
			while (rs.next()) {
				Book b = new Book();
				b.setBid(rs.getInt("bid"));
				b.setBname(rs.getString("bname"));
				b.setBauthor(rs.getString("bauthor"));
				b.setBtype(rs.getString("btype"));
				b.setBprice(rs.getInt("bprice"));
				b.setBpublisher(rs.getString("bpublisher"));
				b.setBstore(rs.getInt("bstore"));
				list.add(b);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查找所有用户
	public QueryResult<Book> queryAll(int page) {
		/*
		 * 1查找有多少条数据 2通过每页有多少条数据，知道有多少页数据 3如果查找的页数小于的1的话，让page=1
		 * 4如果查找的页数大于最大的，让page=最大页数 5找到起始值和结束值
		 */
		QueryResult<Book> result = new QueryResult<Book>();
		result.setNowPage(page);// result设置总页数
		int pageSize = result.getNum();// 每页页数
		Connection conn = super.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Book> list = new ArrayList<Book>();
			ps = conn.prepareStatement("select count(*) from book");
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
			if (page > pages)
				page = pages;
			// 3
			if (page < 1)
				page = 1;
			// 4
			// 5
			int begin = (page - 1) * pageSize;
			int end = page * pageSize;
			// String sql="select * from (select rownum rn,u.* from"+
			// "(select * from book order by bid )u)"+
			// "where rn between ? and ?";
			String sql = "select * from book order by bid limit ?,?";
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
	public QueryResult<Book> queryByName(int page, String name) {
		/*
		 * 1查找有多少条数据 2通过每页有多少条数据，知道有多少页数据 3如果查找的页数小于的1的话，让page=1
		 * 4如果查找的页数大于最大的，让page=最大页数 5找到起始值和结束值
		 */
		QueryResult<Book> result = new QueryResult<Book>();
		result.setNowPage(page);// result设置总页数
		int pageSize = result.getNum();// 每页页数
		Connection conn = super.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Book> list = new ArrayList<Book>();
			ps = conn.prepareStatement("select count(*) from book where bname like ?");
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
			// "(select * from book where bname like ? order by bid )u)"+
			// "where rn between ? and ?";
			String sql = "select * from book where bname like ?  order by bid limit ?,?";
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
	public QueryResult<Book> queryByAuthor(int page, String author) {
		QueryResult<Book> result = new QueryResult<Book>();
		result.setNowPage(page);// result设置总页数
		int pageSize = result.getNum();// 每页页数
		Connection conn = super.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Book> list = new ArrayList<Book>();
			ps = conn.prepareStatement("select count(*) from book where bauthor like ?");
			ps.setString(1, "%" + author + "%");
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
			// "(select * from book where bauthor like ? order by bid )u)"+
			// "where rn between ? and ?";
			String sql = "select * from book where bauthor like ?  order by bid limit ?,?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + author + "%");
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

	public QueryResult<Book> queryByPub(int page, String pub) {
		QueryResult<Book> result = new QueryResult<Book>();
		result.setNowPage(page);// result设置总页数
		int pageSize = result.getNum();// 每页页数
		Connection conn = super.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Book> list = new ArrayList<Book>();
			ps = conn.prepareStatement("select count(*) from book where BPUBLISHER like ?");
			ps.setString(1, "%" + pub + "%");
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
			// "(select * from book where BPUBLISHER like ? order by bid )u)"+
			// "where rn between ? and ?";
			String sql = "select * from book where BPUBLISHER like ?  order by bid limit ?,?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + pub + "%");
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

}
