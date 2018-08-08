package com.elementspeed.framework.common.vc;

/**
 * 选择节点组件类
 * @author pengj
 * @下午12:05:46
 * @com.elementspeed.framework.common.vc.SelectNode
 */
public class SelectNode {
	/** 字段名称 */
	private String text;
	/** 字段值 */
	private String value;
	
	public SelectNode(){};
	
	public SelectNode(String value, String text) {
		this.value = value;
		this.text = text;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
