package com.qf.lingshixiaomaio.model;

public class Infos {
	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Infos(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

}
