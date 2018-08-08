package com.elementspeed.common.file.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Extra implements Serializable {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Extra() {
		super();
	}

	public Extra(String id) {
		super();
		this.id = id;
	}

}
