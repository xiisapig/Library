package cn.ypjalt.entity;

import java.sql.Time;

public class Jieshu {
	private User user;
	private Book book;
	private String jtime;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Book getBook() {
		return book;
	}
	public String getJtime() {
		return jtime;
	}
	public void setJtime(String jtime) {
		this.jtime = jtime;
	}
	public void setBook(Book book) {
		this.book = book;
	}

	public Jieshu(User user, Book book, String jtime) {
		super();
		this.user = user;
		this.book = book;
		this.jtime = jtime;
	}
	public Jieshu(){}

}
