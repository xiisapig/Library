package cn.ypjalt.entity;

public class User {
	private int id;
	private String pwd;
	private String name;
	private String sex;
	private String depart;
	private String classes;
	private String tel;
	private int role;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
    public User(int id, String pwd, String name, String sex, String depart,
			String classes, String tel, int role) {
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.sex = sex;
		this.depart = depart;
		this.classes = classes;
		this.tel = tel;
		this.role = role;
	}
    public  User(){}

	public boolean isRole() {
		return this.role==1;
	}

}
