/**
 *  DataList extends DataGrid
 */
function DataList(gridParams) {
	DataList.superclass.constructor.call(this, gridParams);

	this.init = function() {
		if(this.$datagrid)
			return;
		
		var gridParamObj = this.createParamObj();
		gridParamObj.valueField = this.gridParams.valueField,
		gridParamObj.textField = this.gridParams.textField,
		gridParamObj.checkbox = this.gridParams.checkbox;
		gridParamObj.frozenColumns = null;
		gridParamObj.textFormatter = this.gridParams.textFormatter;
		
		this.$datagrid = $('#' + this.gridParams.id).datalist(gridParamObj);
		this.$datagrid.datagrid('clearChecked');
	}
}
extend(DataList, DataGrid);

/**
 * DataList参数
 */
function DataListParams() {
	DataListParams.superclass.constructor.call(this);
	
	this.valueField = 'id';				//The field value name to bind to the row.
	this.textField = 'id';				//The field name to display on the row.
	this.checkbox = true;				//true to display checkbox for each rows
	this.width = null;
	this.height = null;
	this.pagination = false;			//如果为true，则在DataGrid控件底部显示分页工具栏。
	this.showHeader = false;
	this.collapsible = false;
	/*
	 * 	function(value,row,index)
	 * The text field formatter function, takes three parameters:
		value: the field value.
		row: the row record data.
		index: the row index.
	 */
	this.textFormatter = null;
}
extend(DataListParams, GridParams);