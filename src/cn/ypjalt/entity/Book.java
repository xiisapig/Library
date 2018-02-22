package cn.ypjalt.entity;

public class Book {
	private int bid;
	private String bname;
	private String bauthor;
	private String btype;
	private int bprice;
	private String bpublisher;
	private int bstore;
	public Book(int bid, String bname, String bauthor, String btype,
			int bprice, String bpublisher, int bstore) {
		super();
		this.bid = bid;
		this.bname = bname;
		this.bauthor = bauthor;
		this.btype = btype;
		this.bprice = bprice;
		this.bpublisher = bpublisher;
		this.bstore = bstore;
	}
	public Book(){}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getBauthor() {
		return bauthor;
	}
	public void setBauthor(String bauthor) {
		this.bauthor = bauthor;
	}
	public String getBtype() {
		return btype;
	}
	public void setBtype(String btype) {
		this.btype = btype;
	}
	public int getBprice() {
		return bprice;
	}
	public void setBprice(int bprice) {
		this.bprice = bprice;
	}
	public String getBpublisher() {
		return bpublisher;
	}
	public void setBpublisher(String bpublisher) {
		this.bpublisher = bpublisher;
	}
	public int getBstore() {
		return bstore;
	}
	public void setBstore(int bstore) {
		this.bstore = bstore;
	}
}
