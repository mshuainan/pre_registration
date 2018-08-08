package com.elementspeed.mapper.system.entity;

import com.elementspeed.framework.base.dao.BaseEntity;

/**
 * 系统菜单
 */
public class SysModules extends BaseEntity {

	private static final long serialVersionUID = 8422272907402134483L;

	private String parentId; // 父模块ID
	private String code; // 模块代码
	private String i18nText; // 模块名称
	private Integer type; // 类型(1：采购商，2:供应商)
	private String url; // 跳转路径
	private String icon; // 图标base64编码
	private Integer mobileFlag; // 移动端菜单标识
	private Integer isLeaf; // 是否叶子节点
	private String renderingParam;

	/** 菜单的顺序号，排序 */
	private Integer menuSn;

	private String beforeId;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getI18nText() {
		return i18nText;
	}

	public void setI18nText(String i18nText) {
		this.i18nText = i18nText;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getMenuSn() {
		return menuSn;
	}

	public void setMenuSn(Integer menuSn) {
		this.menuSn = menuSn;
	}

	public String get_parentId() {
		return getParentId();
	}

	public void set_parentId(String _parentId) {
	}

	public String getBeforeId() {
		return beforeId;
	}

	public void setBeforeId(String beforeId) {
		this.beforeId = beforeId;
	}

	public Integer getMobileFlag() {
		return mobileFlag;
	}

	public void setMobileFlag(Integer mobileFlag) {
		this.mobileFlag = mobileFlag;
	}

	public String getRenderingParam() {
		return renderingParam;
	}

	public void setRenderingParam(String renderingParam) {
		this.renderingParam = renderingParam;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

}
