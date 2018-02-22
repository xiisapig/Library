package cn.ypjalt.entity;

public class Huanshu {
	private Jieshu jieshu;
	private String htime;
	public Huanshu(Jieshu jieshu, String htime) {
		super();
		this.jieshu = jieshu;
		this.htime = htime;
	}
	public Jieshu getJieshu() {
		return jieshu;
	}
	public void setJieshu(Jieshu jieshu) {
		this.jieshu = jieshu;
	}
	public String getHtime() {
		return htime;
	}
	public void setHtime(String htime) {
		this.htime = htime;
	}
	public Huanshu(){}
}
