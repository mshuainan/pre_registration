package com.elementspeed.framework.common.vc;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 组件类，用于xml模版解析
 * @author sfg0656
 *
 */
public class Fc {
	
	private String id;					//组件id
	private String name;				//组件name
	private String type;				//组件类型 如 radiobox、checkbox
	private String label;				//组件label 
	private String value;				//组件默认值
	private String expend;				//扩展属性
	private String relation;			//关联组件	
	//组件取值范围
	private Map<String, String> range = new LinkedHashMap<String, String>();
	

	public void addRange(String value, String label) {
		range.put(value, label);
	}
	public String getExpend() {
		return expend;
	}
	public void setExpend(String expend) {
		this.expend = expend;
	}
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
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Map<String, String> getRange() {
		return range;
	}
	public void setRange(Map<String, String> range) {
		this.range = range;
	}
	@Override
	public String toString() {
		return "Fc [id=" + id + ", name=" + name + ", type=" + type
				+ ", label=" + label + ", value=" + value 
				+ ", range=" + range + "]";
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
}
