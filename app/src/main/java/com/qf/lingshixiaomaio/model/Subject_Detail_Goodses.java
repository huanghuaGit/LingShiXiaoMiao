package com.qf.lingshixiaomaio.model;

/**
 * 专题详情列表实体类
 * @author JoshuaJan
 *
 */
public class Subject_Detail_Goodses {
	private int surplus_num;
	private int sub_classify_id;
	private int freight;
	private String img_url;
	private String title;
	private int type;
	private int sold_num;
	private int fav_num;
	private int guide_type;
	private double current;
	private double prime;
	private int id;
	private int time;
	private int status;
	private String desc;
	public int getSurplus_num() {
		return surplus_num;
	}
	public void setSurplus_num(int surplus_num) {
		this.surplus_num = surplus_num;
	}
	public int getSub_classify_id() {
		return sub_classify_id;
	}
	public void setSub_classify_id(int sub_classify_id) {
		this.sub_classify_id = sub_classify_id;
	}
	public int getFreight() {
		return freight;
	}
	public void setFreight(int freight) {
		this.freight = freight;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSold_num() {
		return sold_num;
	}
	public void setSold_num(int sold_num) {
		this.sold_num = sold_num;
	}
	public int getFav_num() {
		return fav_num;
	}
	public void setFav_num(int fav_num) {
		this.fav_num = fav_num;
	}
	public int getGuide_type() {
		return guide_type;
	}
	public void setGuide_type(int guide_type) {
		this.guide_type = guide_type;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Subject_Detail_Goodses(int surplus_num, int sub_classify_id,
			int freight, String img_url, String title, int type, int sold_num,
			int fav_num, int guide_type, double current, double prime, int id,
			int time, int status, String desc) {
		super();
		this.surplus_num = surplus_num;
		this.sub_classify_id = sub_classify_id;
		this.freight = freight;
		this.img_url = img_url;
		this.title = title;
		this.type = type;
		this.sold_num = sold_num;
		this.fav_num = fav_num;
		this.guide_type = guide_type;
		this.current = current;
		this.prime = prime;
		this.id = id;
		this.time = time;
		this.status = status;
		this.desc = desc;
	}

}
