package com.qf.lingshixiaomaio.model;
//评论实体类
public class CommentsEntity {
	private int comments_id;
	private String comments_img_url;
	private String nickname;
	private String content;
	public int getComments_id() {
		return comments_id;
	}
	public void setComments_id(int comments_id) {
		this.comments_id = comments_id;
	}
	public String getComments_img_url() {
		return comments_img_url;
	}
	public void setComments_img_url(String comments_img_url) {
		this.comments_img_url = comments_img_url;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public CommentsEntity(int comments_id, String comments_img_url, String nickname,
			String content) {
		super();
		this.comments_id = comments_id;
		this.comments_img_url = comments_img_url;
		this.nickname = nickname;
		this.content = content;
	}
	
}
