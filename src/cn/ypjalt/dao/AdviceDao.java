package cn.ypjalt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ypjalt.entity.Advice;
import cn.ypjalt.entity.QueryResult;
import cn.ypjalt.entity.User;
import cn.ypjalt.util.DBUtil;

public class AdviceDao extends DBUtil {
	public boolean insert(Advice ad) {
		Connection conn = super.openConnection();
		PreparedStatement pstm = null;
		String sql = "insert into jianyi values(?,?,?)";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, ad.getUser().getId());
			pstm.setString(2, ad.getUser().getName());
			pstm.setString(3, ad.getAdvice());
			return pstm.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.close(conn, pstm, null);
		}
		return false;
	}

	public QueryResult<Advice> queryAll(int page) {
		/*
		 * 1查找有多少条数据 2通过每页有多少条数据，知道有多少页数据 3如果查找的页数小于的1的话，让page=1
		 * 4如果查找的页数大于最大的，让page=最大页数 5找到起始值和结束值
		 */
		QueryResult<Advice> result = new QueryResult<Advice>();
		result.setNowPage(page);// result设置总页数
		int pageSize = result.getNum();// 每页页数
		Connection conn = super.openConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<Advice> list = new ArrayList<Advice>();
			ps = conn.prepareStatement("select count(*) from jianyi");
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
			// 5
			int begin = (page - 1) * pageSize;
			int end = page * pageSize;
			// String sql="select * from (select rownum rn,u.* from"+
			// "(select * from jianyi order by id )u)"+
			// "where rn between ? and ?";
			String sql = "select * from jianyi order by id ";
			sql += " limit ? , ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, begin);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				Advice advice = new Advice(user, rs.getString("advice"));
				list.add(advice);
			}
			result.setResultList(list);
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean delete(int ID) {
		String sql = "delete  from jianyi where id='" + ID + "'";
		return super.executeSql(sql);
	}

}
