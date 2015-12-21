package com.qf.lingshixiaomaio.model;

import java.util.List;

/**
 * 美味详情实体类
 * 
 * @author ZL
 * 
 */
public class ItemDetailEntity {
	// 美味详情头顶布局实体参数
	private int id;
	private String title;
	private int types;
	private int guide_type;
	private int sold_num;
	private int surplus_num;
	private double current;
	private double prime;
	private long server_time;
	private List<Tags> tag_list;
	private List<String> image_list;

	// 美味详情中间图片布局实体参数（details）
	private String details;

	// 美味详情的美味信息和喵亲口碑评论部分布局参数
	private String arguments_title;
	private List<Infos> infos_list;

	private int total_num;
	private List<CommentsEntity> list_Comments;

	// 美味详情底部点击加入购物车后弹出的布局的实体参数(Kinds)
	private int kinds_total_id;// (总id)
	private String kinds_total_title;// 口味(总标题)
	private int kinds_id;
	private String kings_title;// (樱桃味)
	private double kinds_current;
	private double kinds_prime;
	private int kinds_surplus_num;

	// 美味详情底部猜你喜欢布局参数
	private int guess_love_id;
	private String guess_love_title;
	private int guess_love_type;
	private int guess_love_guide_type;
	private int guess_love_status;
	private int guess_love_sold_num;
	private int guess_love_surplus_num;
	private double guess_love_current;
	private double guess_love_img_url;
	private long guess_love_time;
	private String guess_love_tag_title;

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

	public int getTypes() {
		return types;
	}

	public void setTypes(int types) {
		this.types = types;
	}

	public int getGuide_type() {
		return guide_type;
	}

	public void setGuide_type(int guide_type) {
		this.guide_type = guide_type;
	}

	public int getSold_num() {
		return sold_num;
	}

	public void setSold_num(int sold_num) {
		this.sold_num = sold_num;
	}

	public int getSurplus_num() {
		return surplus_num;
	}

	public void setSurplus_num(int surplus_num) {
		this.surplus_num = surplus_num;
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

	public long getServer_time() {
		return server_time;
	}

	public void setServer_time(long server_time) {
		this.server_time = server_time;
	}

	public List<Tags> getTag_list() {
		return tag_list;
	}

	public void setTag_list(List<Tags> tag_list) {
		this.tag_list = tag_list;
	}

	public List<String> getImage_list() {
		return image_list;
	}

	public void setImage_list(List<String> image_list) {
		this.image_list = image_list;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getTotal_num() {
		return total_num;
	}

	public void setTotal_num(int total_num) {
		this.total_num = total_num;
	}

	public List<CommentsEntity> getList_Comments() {
		return list_Comments;
	}

	public void setList_Comments(List<CommentsEntity> list_Comments) {
		this.list_Comments = list_Comments;
	}

	public int getKinds_total_id() {
		return kinds_total_id;
	}

	public void setKinds_total_id(int kinds_total_id) {
		this.kinds_total_id = kinds_total_id;
	}

	public String getKinds_total_title() {
		return kinds_total_title;
	}

	public void setKinds_total_title(String kinds_total_title) {
		this.kinds_total_title = kinds_total_title;
	}

	public int getKinds_id() {
		return kinds_id;
	}

	public void setKinds_id(int kinds_id) {
		this.kinds_id = kinds_id;
	}

	public String getKings_title() {
		return kings_title;
	}

	public void setKings_title(String kings_title) {
		this.kings_title = kings_title;
	}

	public double getKinds_current() {
		return kinds_current;
	}

	public void setKinds_current(double kinds_current) {
		this.kinds_current = kinds_current;
	}

	public double getKinds_prime() {
		return kinds_prime;
	}

	public void setKinds_prime(double kinds_prime) {
		this.kinds_prime = kinds_prime;
	}

	public int getKinds_surplus_num() {
		return kinds_surplus_num;
	}

	public void setKinds_surplus_num(int kinds_surplus_num) {
		this.kinds_surplus_num = kinds_surplus_num;
	}

	public String getArguments_title() {
		return arguments_title;
	}

	public void setArguments_title(String arguments_title) {
		this.arguments_title = arguments_title;
	}

	public List<Infos> getInfos_list() {
		return infos_list;
	}

	public void setInfos_list(List<Infos> infos_list) {
		this.infos_list = infos_list;
	}

	public int getGuess_love_id() {
		return guess_love_id;
	}

	public void setGuess_love_id(int guess_love_id) {
		this.guess_love_id = guess_love_id;
	}

	public String getGuess_love_title() {
		return guess_love_title;
	}

	public void setGuess_love_title(String guess_love_title) {
		this.guess_love_title = guess_love_title;
	}

	public int getGuess_love_type() {
		return guess_love_type;
	}

	public void setGuess_love_type(int guess_love_type) {
		this.guess_love_type = guess_love_type;
	}

	public int getGuess_love_guide_type() {
		return guess_love_guide_type;
	}

	public void setGuess_love_guide_type(int guess_love_guide_type) {
		this.guess_love_guide_type = guess_love_guide_type;
	}

	public int getGuess_love_status() {
		return guess_love_status;
	}

	public void setGuess_love_status(int guess_love_status) {
		this.guess_love_status = guess_love_status;
	}

	public int getGuess_love_sold_num() {
		return guess_love_sold_num;
	}

	public void setGuess_love_sold_num(int guess_love_sold_num) {
		this.guess_love_sold_num = guess_love_sold_num;
	}

	public int getGuess_love_surplus_num() {
		return guess_love_surplus_num;
	}

	public void setGuess_love_surplus_num(int guess_love_surplus_num) {
		this.guess_love_surplus_num = guess_love_surplus_num;
	}

	public double getGuess_love_current() {
		return guess_love_current;
	}

	public void setGuess_love_current(double guess_love_current) {
		this.guess_love_current = guess_love_current;
	}

	public double getGuess_love_img_url() {
		return guess_love_img_url;
	}

	public void setGuess_love_img_url(double guess_love_img_url) {
		this.guess_love_img_url = guess_love_img_url;
	}

	public long getGuess_love_time() {
		return guess_love_time;
	}

	public void setGuess_love_time(long guess_love_time) {
		this.guess_love_time = guess_love_time;
	}

	public String getGuess_love_tag_title() {
		return guess_love_tag_title;
	}

	public void setGuess_love_tag_title(String guess_love_tag_title) {
		this.guess_love_tag_title = guess_love_tag_title;
	}

	// 美味详情头顶布局
	public ItemDetailEntity(int id, String title, int types, int guide_type,
			int sold_num, int surplus_num, double current, double prime,
			long server_time, List<Tags> tag_list, List<String> image_list) {
		super();
		this.id = id;
		this.title = title;
		this.types = types;
		this.guide_type = guide_type;
		this.sold_num = sold_num;
		this.surplus_num = surplus_num;
		this.current = current;
		this.prime = prime;
		this.server_time = server_time;

		this.tag_list = tag_list;
		this.image_list = image_list;
	}

	// 美味详情中间图片布局
	public ItemDetailEntity(String details) {
		super();
		this.details = details;
	}

	// 美味详情的美味信息布局参数
	public ItemDetailEntity(int types, int guide_type, String arguments_title,
			List<Infos> infos_list) {
		super();
		this.arguments_title = arguments_title;
		this.infos_list = infos_list;
		this.types = types;
		this.guide_type = guide_type;

	}

	// 美味详情底部点击加入购物车后弹出的布局
	public ItemDetailEntity(int kinds_total_id, String kinds_total_title,
			int kinds_id, String kings_title, double kinds_current,
			double kinds_prime, int kinds_surplus_num) {
		super();
		this.kinds_total_id = kinds_total_id;
		this.kinds_total_title = kinds_total_title;
		this.kinds_id = kinds_id;
		this.kings_title = kings_title;
		this.kinds_current = kinds_current;
		this.kinds_prime = kinds_prime;
		this.kinds_surplus_num = kinds_surplus_num;
	}

	// 美味详情底部猜你喜欢布局参数
	public ItemDetailEntity(int guess_love_id, String guess_love_title,
			int guess_love_type, int guess_love_guide_type,
			int guess_love_status, int guess_love_sold_num,
			int guess_love_surplus_num, double guess_love_current,
			double guess_love_img_url, long guess_love_time,
			String guess_love_tag_title) {
		super();
		this.guess_love_id = guess_love_id;
		this.guess_love_title = guess_love_title;
		this.guess_love_type = guess_love_type;
		this.guess_love_guide_type = guess_love_guide_type;
		this.guess_love_status = guess_love_status;
		this.guess_love_sold_num = guess_love_sold_num;
		this.guess_love_surplus_num = guess_love_surplus_num;
		this.guess_love_current = guess_love_current;
		this.guess_love_img_url = guess_love_img_url;
		this.guess_love_time = guess_love_time;
		this.guess_love_tag_title = guess_love_tag_title;
	}

	// 美味详情评论
	public ItemDetailEntity(int total_num, List<CommentsEntity> list_Comments) {
		super();
		this.total_num = total_num;
		this.list_Comments = list_Comments;
	}

	// 优惠action部分
	private String icon_title;
	private String activity_title;
	private int action_type;
	private String action_info;

	public String getIcon_title() {
		return icon_title;
	}

	public void setIcon_title(String icon_title) {
		this.icon_title = icon_title;
	}

	public String getActivity_title() {
		return activity_title;
	}

	public void setActivity_title(String activity_title) {
		this.activity_title = activity_title;
	}

	public int getAction_type() {
		return action_type;
	}

	public void setAction_type(int action_type) {
		this.action_type = action_type;
	}

	public String getAction_info() {
		return action_info;
	}

	public void setAction_info(String action_info) {
		this.action_info = action_info;
	}

	public ItemDetailEntity(int id, String title, int types, int guide_type,
			int sold_num, int surplus_num, double current, double prime,
			long server_time, List<Tags> tag_list, List<String> image_list,
			String icon_title, String activity_title, int action_type,
			String action_info) {
		super();
		this.id = id;
		this.title = title;
		this.types = types;
		this.guide_type = guide_type;
		this.sold_num = sold_num;
		this.surplus_num = surplus_num;
		this.current = current;
		this.prime = prime;
		this.server_time = server_time;

		this.tag_list = tag_list;
		this.image_list = image_list;

		this.icon_title = icon_title;
		this.activity_title = activity_title;
		this.action_type = action_type;
		this.action_info = action_info;
	}

	// 购物车实体类
	private String image_url;

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	private int select_type;
	
	public int getSelect_type() {
		return select_type;
	}

	public void setSelect_type(int select_type) {
		this.select_type = select_type;
	}

	public ItemDetailEntity(String image_url, String title, double current,
			double prime,int select_type) {
		this.title = title;
		this.current = current;
		this.prime = prime;
		this.image_url = image_url;
		this.select_type = select_type;
	}

}
