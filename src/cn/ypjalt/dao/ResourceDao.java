package cn.ypjalt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.ypjalt.entity.Resource;
import cn.ypjalt.util.DBUtil;

public class ResourceDao extends DBUtil {
	public ResourceDao() {
	}

	public Resource queryById(Integer id) {
		Connection conn = super.openConnection();
		String sql = "select * from resource where id=?";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				Resource r = new Resource();
				r.setId(id);
				r.setName(rs.getString("name"));
				r.setDescription(rs.getString("description"));
				r.setType(rs.getString("type"));
				r.setAddress(rs.getString("address"));
				r.setPriority(rs.getInt("priority"));
				Integer parentId = rs.getInt("parentId");
				if (parentId <= 0)
					r.setParent(null);
				else
					r.setParent(this.queryById(parentId));
				r.setDisplay(rs.getString("display"));
				r.setIssys(rs.getString("issys"));
				r.setDefunct(rs.getString("defunct"));
				return r;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.close(conn, pst, rs);
		}
		return null;
	}

	public List<Resource> getManagerResourceList() {
		Connection conn = super.openConnection();
		String sql = "select * from resource where issys='Y' and defunct='N'";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			return this.getListByRs(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.close(conn, pst, rs);
		}
		return null;
	}

	// display true 全部查询 false只查询能够显示在目录中的
	public List<Resource> getRoot(boolean display, int role) {
		Connection conn = super.openConnection();
		String sql = "select * from resource where parentId<0 " + (display ? "" : "and display='Y'")
				+ " and defunct='N' " + (role == 1 ? "" : "and issys='N'") + "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			return this.getListByRs(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.close(conn, pst, rs);
		}
		return null;
	}

	private List<Resource> getListByRs(ResultSet rs) {
		List<Resource> list = new ArrayList<Resource>();
		try {
			while (rs.next()) {
				Resource r = new Resource();
				r.setId(rs.getInt("id"));
				r.setName(rs.getString("name"));
				r.setDescription(rs.getString("description"));
				r.setType(rs.getString("type"));
				r.setAddress(rs.getString("address"));
				r.setPriority(rs.getInt("priority"));
				Integer parentId = rs.getInt("parentId");
				if (parentId <= 0)
					r.setParent(null);
				else
					r.setParent(this.queryById(parentId));
				r.setDisplay(rs.getString("display"));
				r.setIssys(rs.getString("issys"));
				r.setDefunct(rs.getString("defunct"));
				list.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Resource> getChildren(Integer id, boolean display, int role) {
		Connection conn = super.openConnection();
		String sql = "select * from resource where parentId=? " + (display ? "" : "and display='Y'")
				+ " and defunct='N'" + (role == 1 ? "" : "and issys='N'") + "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			return this.getListByRs(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			super.close(conn, pst, rs);
		}
		return null;
	}

	public boolean updatePriv(Integer id, String issys) {
		Connection conn = super.openConnection();
		try {
			conn.setAutoCommit(false);
			String sql = "update resource set issys='" + issys + "' where id='" + id + "'";
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

}
