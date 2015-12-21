package com.qf.lingshixiaomaio.model;

public class Tags {
	private String title;
	private String color;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Tags(String title, String color) {
		super();
		this.title = title;
		this.color = color;
	}
	
}
