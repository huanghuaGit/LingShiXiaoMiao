package com.qf.lingshixiaomaio.model;

public class HomeHeadEntity {
	private int id;
	private String img_url;
	private String info;
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public HomeHeadEntity(int id, String img_url, String info,int type) {
		super();
		this.id = id;
		this.img_url = img_url;
		this.info = info;
		this.type = type;
	}

}
