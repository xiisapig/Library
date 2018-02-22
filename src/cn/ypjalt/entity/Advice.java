package cn.ypjalt.entity;

public class Advice {
	private User user;
	private String advice;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public Advice(User user, String advice) {
		this.user = user;
		this.advice = advice;
	}
	public Advice(){}

}
