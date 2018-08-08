package com.elementspeed.tool.excel.common;

import java.util.List;
import java.util.Map;

public class SheetHeadInfo {
	
	private int headRows ;   //标题总共占用几行
	private Map<Integer, List<SheetHeadTemplate>> headTemplates;
	
	public int getHeadRows() {
		return headRows;
	}
	public void setHeadRows(int headRows) {
		this.headRows = headRows;
	}
	public Map<Integer, List<SheetHeadTemplate>> getHeadTemplates() {
		return headTemplates;
	}
	public void setHeadTemplates(Map<Integer, List<SheetHeadTemplate>> headTemplates) {
		this.headTemplates = headTemplates;
	}
	
}
