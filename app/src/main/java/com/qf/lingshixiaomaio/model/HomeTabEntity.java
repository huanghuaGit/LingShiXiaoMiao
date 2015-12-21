package com.qf.lingshixiaomaio.model;

public class HomeTabEntity {
	private int id;
	private String title;
	private String desc;
	private String img_url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public HomeTabEntity(int id, String title, String desc, String img_url) {
		super();
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.img_url = img_url;
	}

}
