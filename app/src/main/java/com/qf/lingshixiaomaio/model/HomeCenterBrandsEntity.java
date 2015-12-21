package com.qf.lingshixiaomaio.model;

public class HomeCenterBrandsEntity {
	private int id;
	private String title;
	private String img_url;
	private String discount;
	private long time;

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

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public HomeCenterBrandsEntity(int id, String title, String img_url,
			String discount, long time) {
		super();
		this.id = id;
		this.title = title;
		this.img_url = img_url;
		this.discount = discount;
		this.time = time;
	}

}
