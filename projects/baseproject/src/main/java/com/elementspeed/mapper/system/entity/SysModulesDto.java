package com.elementspeed.mapper.system.entity;

import java.util.LinkedList;
import java.util.List;

import com.elementspeed.framework.common.util.StringUtil;

public class SysModulesDto extends SysModules {

	private static final long serialVersionUID = 3607751735554929419L;
	
	private boolean checked; //该树节点是否选中
	private String parentName;
	
	private List<SysModulesDto> children = new LinkedList<SysModulesDto>();

	public void addChild(SysModulesDto module) {
		children.add(module);
	}
	public List<SysModulesDto> getChildren() {
		return children;
	}

	public void setChildren(List<SysModulesDto> children) {
		this.children = children;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	/**
	 *  是否是一级菜单
	 * @return
	 */
	public boolean isFirstLevel() {
		return StringUtil.isEmpty(getParentId());
	}

}
