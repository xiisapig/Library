package cn.ypjalt.entity;

public class Resource {
	private Integer id;// int(10) unsigned操作资源id
	private String name;// varchar(100)操作资源名称
	private String description;// varchar(100)描述
	private String type;// varchar(40)类型
	private String address;// varchar(200)相关地址
	private Integer priority;// int(10)层次级别
	private String defunct;// char(1)删除标志Y-删除 N-未删除
	private Resource parent;// int(10)父节点id
	private String display;// char(1)是否在目录树中显示 Y-显示 N-不显示
	private String issys;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getDefunct() {
		return defunct;
	}

	public void setDefunct(String defunct) {
		this.defunct = defunct;
	}

	public Resource getParent() {
		return parent;
	}

	public void setParent(Resource parent) {
		this.parent = parent;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getIssys() {
		return issys;
	}

	public void setIssys(String issys) {
		this.issys = issys;
	}
}
