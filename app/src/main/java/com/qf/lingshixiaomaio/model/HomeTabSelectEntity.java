package com.qf.lingshixiaomaio.model;

public class HomeTabSelectEntity {
	private int id;
	private String title;

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

	public HomeTabSelectEntity(int id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

}
