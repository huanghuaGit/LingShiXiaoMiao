package com.qf.lingshixiaomaio.model;

import java.util.List;

/**
 * 专题详情实体类
 * @author JoshuaJan
 *
 */
public class Subject_Detail {
	private String img_url;
	private int id;
	private String title;
	private int hotindex;
	private String desc;
	private int share_num;
	public Subject_Detail(String img_url, int id, String title, int hotindex,
			String desc, int share_num) {
		super();
		this.img_url = img_url;
		this.id = id;
		this.title = title;
		this.hotindex = hotindex;
		this.desc = desc;
		this.share_num = share_num;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
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
	public int getHotindex() {
		return hotindex;
	}
	public void setHotindex(int hotindex) {
		this.hotindex = hotindex;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getShare_num() {
		return share_num;
	}
	public void setShare_num(int share_num) {
		this.share_num = share_num;
	}
	@Override
	public String toString() {
		return "Subject_Detail [img_url=" + img_url + ", id=" + id + ", title="
				+ title + ", hotindex=" + hotindex + ", desc=" + desc
				+ ", share_num=" + share_num + "]";
	}


}
