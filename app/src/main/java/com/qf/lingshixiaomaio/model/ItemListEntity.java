package com.qf.lingshixiaomaio.model;

public class ItemListEntity {
	private int id;
	private String title;
	private int sold_num;
	private String img_url;
	private double current;
	private double prime;
	private String tag_title;
	private int fav_num;
	private long time;

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public int getSold_num() {
		return sold_num;
	}

	public void setSold_num(int sold_num) {
		this.sold_num = sold_num;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
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

	public String getTag_title() {
		return tag_title;
	}

	public void setTag_title(String tag_title) {
		this.tag_title = tag_title;
	}

	public int getFav_num() {
		return fav_num;
	}

	public void setFav_num(int fav_num) {
		this.fav_num = fav_num;
	}

	public ItemListEntity(int id, String title, int sold_num, String img_url,
			double current, double prime, String tag_title, int fav_num,
			String desc, long time) {
		super();
		this.id = id;
		this.title = title;
		this.sold_num = sold_num;
		this.img_url = img_url;
		this.current = current;
		this.desc = desc;
		this.prime = prime;
		this.tag_title = tag_title;
		this.fav_num = fav_num;
		this.time = time;

	}

	// 美味详情底部布局参数
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getGuide_type() {
		return guide_type;
	}

	public void setGuide_type(int guide_type) {
		this.guide_type = guide_type;
	}

	private int guide_type;

	public ItemListEntity(int id, String title, int sold_num, String img_url,
			double current, double prime, String tag_title, int fav_num,
			String desc, long time, int type, int guide_type) {
		super();
		this.id = id;
		this.title = title;
		this.sold_num = sold_num;
		this.img_url = img_url;
		this.current = current;
		this.desc = desc;
		this.prime = prime;
		this.tag_title = tag_title;
		this.fav_num = fav_num;
		this.time = time;
		this.type = type;
		this.guide_type = guide_type;

	}

	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	// 打折商品列表
	public ItemListEntity(int id, String title, int sold_num, String img_url,
			double current, double prime, String tag_title, int fav_num,
			long time, int status) {
		super();
		this.id = id;
		this.title = title;
		this.sold_num = sold_num;
		this.img_url = img_url;
		this.current = current;
		this.status = status;
		this.prime = prime;
		this.tag_title = tag_title;
		this.fav_num = fav_num;
		this.time = time;

	}

	

	// 商品收藏的对象
	private float current1;
	public float getCurrent1() {
		return current1;
	}

	public void setCurrent1(float current1) {
		this.current1 = current1;
	}

	public float getPrime1() {
		return prime1;
	}

	public void setPrime1(float prime1) {
		this.prime1 = prime1;
	}

	private float prime1;
	
	
	public ItemListEntity(int id, String title, String img_url, float current1,
			float prime1) {
		super();
		this.id = id;
		this.title = title;
		this.img_url = img_url;
		this.current1 = current1;
		this.prime1 = prime1;

	}

}
