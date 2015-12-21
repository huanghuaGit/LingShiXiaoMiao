package com.qf.lingshixiaomaio.model;
/**
 * 专题头部实体类
 * @author JoshuaJan
 *
 */
public class Subject_Top {
	private int id;
	private String img_url;
	private int img_w;
	private int img_h;
	private int type;
	private String info;

	public Subject_Top(int id, String img_url, int img_w, int img_h,
			int type, String info) {
		super();
		this.id = id;
		this.img_url = img_url;
		this.img_w = img_w;
		this.img_h = img_h;
		this.type = type;
		this.info = info;
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

	public int getImg_w() {
		return img_w;
	}

	public void setImg_w(int img_w) {
		this.img_w = img_w;
	}

	public int getImg_h() {
		return img_h;
	}

	public void setImg_h(int img_h) {
		this.img_h = img_h;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "Subject_Top [id=" + id + ", img_url="
				+ img_url + ", img_w=" + img_w + ", img_h=" + img_h + ", type="
				+ type + ", info=" + info + "]";
	}

}
