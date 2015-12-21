package com.qf.lingshixiaomaio.model;

/**
 * 专题信息实体类
 * @author JoshuaJan
 *
 */
public class Subject_Info {
	private int id;
	private String desc;
	private String title;
	private String img_url;
	private int img_w;
	private int img_h;
	private int hotindex;
	private int share_num;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
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
	public int getHotindex() {
		return hotindex;
	}
	public void setHotindex(int hotindex) {
		this.hotindex = hotindex;
	}
	public int getShare_num() {
		return share_num;
	}
	public void setShare_num(int share_num) {
		this.share_num = share_num;
	}
	
	public Subject_Info(int id, String desc, String title, String img_url,
			int img_w, int img_h, int hotindex, int share_num) {
		super();
		this.id = id;
		this.desc = desc;
		this.title = title;
		this.img_url = img_url;
		this.img_w = img_w;
		this.img_h = img_h;
		this.hotindex = hotindex;
		this.share_num = share_num;
	}
	@Override
	public String toString() {
		return "Subject_Info [id=" + id + ", desc=" + desc + ", title=" + title
				+ ", img_url=" + img_url + ", img_w=" + img_w + ", img_h="
				+ img_h + ", hotindex=" + hotindex + ", share_num=" + share_num
				+ "]";
	}
	
}
