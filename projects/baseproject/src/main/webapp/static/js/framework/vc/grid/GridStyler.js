/**
 * grid单元格样式渲染辅助函数
 * 
 * 示例:
 * <th data-options="field:'sampleNumber', width:'90', align:'center', sortable:'true'"
					styler='GridStyler.setHighLight'>打样数量</th>
 */
var GridStyler = {
	/**
	 * 高亮显示
	 * @param value
	 * @param rowdata
	 * @param index
	 * @param isHighLight	boolean, 是否设置高亮
	 * @returns {String}
	 */
	setHighLight : function(value, rowdata, index, isHighLight) {
		if(isHighLight)
			return 'background-color:#ffee00;color:red;';
	},

	setEditor : function(value, rowdata, index) {
		return 'border-width:1px;border-color:#000000';
	}
}