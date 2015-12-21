package com.qf.lingshixiaomaio.model;

public class HomeDropDownBrandsEntity {
	private int id;
	private String title;
	private String img_url;
	private long time;
	private int type ;
	private double current;
	private double prime;
	private int guide_type;

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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getCurrent() {
		return current;
	}

	public void setCurrent(double current) {
		this.current = current;
	}

	public double getPrime() {
		return prime;
	}

	public void setPrime(double prime) {
		this.prime = prime;
	}

	public int getGuide_type() {
		return guide_type;
	}

	public void setGuide_type(int guide_type) {
		this.guide_type = guide_type;
	}

	public HomeDropDownBrandsEntity(int id, String title, String img_url,
			long time, int type, double current, double prime, int guide_type) {
		super();
		this.id = id;
		this.title = title;
		this.img_url = img_url;
		this.time = time;
		this.type = type;
		this.current = current;
		this.prime = prime;
		this.guide_type = guide_type;
	}

}