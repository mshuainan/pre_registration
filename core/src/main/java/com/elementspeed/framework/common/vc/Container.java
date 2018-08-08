package com.elementspeed.framework.common.vc;

import java.util.ArrayList;
import java.util.List;

/**
 * jsp容器类 用于解析xml模版
 * @author sfg0656
 *
 */
public class Container {

	private String id;            	//容器id
	private String name;			//容器name
	private String type;			//容器类型 如 form 、 grid。。。 目前默认form
	private String title;			//容器title
	
	private List<Fc> fcList = new ArrayList<Fc>();   //容器内组件list， 目前一行只一个组件

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Fc> getFcList() {
		return fcList;
	}

	public void setFcList(Fc fc) {
		this.fcList.add(fc);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
