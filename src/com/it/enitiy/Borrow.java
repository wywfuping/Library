package com.it.enitiy;

import java.util.Date;

public class Borrow {
	private Integer id;
	private Integer bid;
	private Integer cid;
	private Date btime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Date getBtime() {
		return btime;
	}
	public void setBtime(Date btime) {
		this.btime = btime;
	}
	
}
