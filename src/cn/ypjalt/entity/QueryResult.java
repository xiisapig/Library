package cn.ypjalt.entity;

import java.util.List;

public class QueryResult<T> {
	private List<T> resultList;// 查询的结果数据
	private int totalNum;// 总的记录条数
	@SuppressWarnings("unused")
	private int totalPage;// 总的页数
	private int nowPage;// 当前页
	private int num;// 每页显示的记录条数

	public QueryResult() {
		nowPage = 1;
		num = 10;

	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getTotalPage() {
		return this.totalNum % this.num == 0 ? this.totalNum / this.num : this.totalNum / this.num + 1;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
