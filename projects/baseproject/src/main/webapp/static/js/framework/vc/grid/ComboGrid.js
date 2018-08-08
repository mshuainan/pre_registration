/**
 * Combogrid 下拉表格
 * 使用示例:
 * 
 * jsp中: 
 * <sf:input path="materialId" cssClass="easyui-textbox" id="mtrlComboGrid"/>
 * <!-- 下拉表格 -->
	<div id="mtrlComboGridToolbar">
		<form id="mtrlComboGridSearchForm" method="post">
			<div class="div-search">
				品类：<input class="easyui-textbox" name="categoryId" >
				物料编码：<input class="easyui-textbox" maxlength="20" name="materialNo">
				物料名称：<input class="easyui-textbox" maxlength="100" name="materialName">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="btnMtrlCbgQuery"><s:message code="common.btn.query"/></a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="sysReset('mtrlComboGridSearchForm')"><s:message code="common.btn.reset"/></a>
			</div>
		</form>
	</div>
 * 
 * js中:
 * $(function() {
    	MaterialComboGrid.init();
    });
 * 
 * var MaterialComboGrid = {
	comboGrid : null,
	
	init : function(values, texts) {					
		var cbGridParams = new CbGridParams();
		cbGridParams.id = 'mtrlComboGrid';						
		cbGridParams.textField = 'materialName';
		cbGridParams.url = '/mdata/material/query';
		cbGridParams.columns = [[
		     {field:'id', hidden:true},
		     {field:'categoryErp.code', title:'品类编码', width:80, align:'center', 
		    	formatter:function(value, rowdata, index){return rowdata.categoryErp ? rowdata.categoryErp.code : "";}},
		     {field:'categoryErp.name', title:'品类名称', width:100, align:'center', 
		    	formatter:function(value, rowdata, index){return rowdata.categoryErp ? rowdata.categoryErp.name : "";}},
			 {field:'materialNo', title:'物料编码', width:80, align:'center'},
			 {field:'materialName', title:'物料名称', width:250, align:'left'},
			 {field:'specification', title:'规格型号', width:150, align:'center'},
			 {field:'sku', title:'库存单位', width:80, align:'center'},
			 {field:'purUnit', title:'采购单位', width:80, align:'center'},
			 {field:'type', title:'类型', width:80, align:'center'},
			 {field:'moduleFlag', title:'是否组件', width:80, align:'center', formatter:GridFormatter.yesOrNoStatus},
			 {field:'description', title:'描述', width:150, align:'center'},
			 {field:'remark', title:'备注', width:100, align:'center'}
		]];
		cbGridParams.toolbar = 'mtrlComboGridToolbar';
		cbGridParams.queryFormId = 'mtrlComboGridSearchForm';
		cbGridParams.values = values;
		cbGridParams.texts = texts;
		
		this.comboGrid = new ComboGrid(cbGridParams);
		this.comboGrid.init();
		this.comboGrid.addQueryBtnEvent("btnMtrlCbgQuery");
	}
}
 */
function ComboGrid(gridParams) {
	ComboGrid.superclass.constructor.call(this, gridParams);

	this.rowdataArr = [];
	
	this.init = function() {
		if(this.$datagrid)
			return;
		
		var gridParamObj = this.createParamObj();
		gridParamObj.iconCls = null;
		gridParamObj.panelWidth = this.gridParams.panelWidth;
		gridParamObj.panelHeight = this.gridParams.panelHeight;
		gridParamObj.collapsible = false;
		gridParamObj.idField = this.gridParams.idField;
		gridParamObj.textField = this.gridParams.textField;
		gridParamObj.targetId = this.gridParams.targetId;
		gridParamObj.columns = this.gridParams.columns;
		gridParamObj.frozenColumns = this.gridParams.multiSelect ? [[{field : 'ck', checkbox : true}]] : [];
		gridParamObj.multiple = this.gridParams.multiSelect;
		
		this.addListener(gridParamObj);

		this.$datagrid = this.getSourceComponent().combogrid(gridParamObj);
		this.addPagination();
	},
	
	this.addListener = function(gridParamObj) {
		var $this = this;
		
		/**
		 *  idField对应的值是否存在于rowdataArr中 
		 */
		var inRowdataArr = function(rowdata) {
			var idValue = rowdata[$this.gridParams.idField];
			for (var i = 0; i < $this.rowdataArr.length; i++) {
				if ($this.rowdataArr[i][$this.gridParams.idField] == idValue) {
					return i;
				}
			}
			
			return -1;
		};
		
		/**
		 * 根据默认值勾选. 由于需要翻页勾选, 此处需要同时设置setText和setValues
		 */
		gridParamObj.onLoadSuccess = function(data) {
			$this._setValues();
			if($.isFunction($this.gridParams.onLoadSuccess)) {
				$this.gridParams.onLoadSuccess();
			}
		};
        
		/**
		 * 单击行数据时, 首先触发onSelect或onUnselect, 然后触发onClickRow.
		 * 由于可能需要翻页勾选, onClickRow需要覆盖默认实现
		 */
		gridParamObj.onClickRow = function(index, rowdata) {
			$this._setValues();
		};
		
		gridParamObj.onSelect = function(index, rowdata) {
			//如果是多选　&&　选中行在relationArr中不存在, 将其加入到relationArr
			if($this.gridParams.multiSelect && inRowdataArr(rowdata) == -1) 
				$this.rowdataArr.push(rowdata);
			else
				$this.rowdataArr = [rowdata];
			
			$this._setValues();
			$this.execRelate($this);
		};

		gridParamObj.onUnselect = function(index, rowdata) {
			if($this.gridParams.multiSelect) {
				//取消选中时需要将rowdataArr数组中对应的值去掉
				var i = inRowdataArr(rowdata);
				if(i != -1)
					$this.rowdataArr.splice(i, 1);
			}
			
			$this.execRelate($this);
		};
	},
	
	/**
	 * 获取Grid控件
	 */
	this.getGrid = function() {
		return this.$datagrid.combogrid('grid');
	},
	
	/**
	 * 获取源控件
	 */
	this.getSourceComponent = function() {
		return $('#' + this.gridParams.id);
	}
}
extend(ComboGrid, DataGrid);

/**
 * 为组件赋值
 */
ComboGrid.prototype._setValues = function() {
	var textArr = [];
	for(var i = 0; i < this.rowdataArr.length; i++) {
		textArr.push(this.rowdataArr[i][this.gridParams.textField]);
	}
	
	//为ComboGrid赋值, 此处仅需setText, 不能使用直接使用setValues
	this.getSourceComponent().combogrid('setText', textArr.join(','));
	
}

/**
 * CbGridParams参数
 */
function CbGridParams() {
	CbGridParams.superclass.constructor.call(this);
	
	this.panelWidth = '75%';			//下拉面板的宽度
	this.panelHeight = 'auto';			//下拉面板的高度
	this.height = '25';					//默认25
	this.width = '157';
	this.textField = null;				//展示值对应的字段
	this.columns = null;				//数据网格的列（column）的配置对象
	this.values = null;					//组件的值,多值以,分隔
	this.texts = null;					//组件对应的展示, 多展示以,分隔
	this.singleSelect = true;			//默认单选
	this.pageSize = 5;					//每页显示的记录条数
	this.pageList = [5, 10, 15];		//可以设置每页记录条数的列表 
}
extend(CbGridParams, GridParams);