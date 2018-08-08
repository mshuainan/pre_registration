/**
 * datagrid对象.
 * @param gridParams datagrid参数对象
 * 使用示例:
 *  var gridParams = new GridParams();
	gridParams.title = '公司管理';
	gridParams.id = 'companyDgrid';
	gridParams.toolbar = 'btnToolbar';
	gridParams.url = '/system/compmgt/getListByPage';
	gridBase.queryFormId = 'companyForm';
	
	var gridBase = new DataGrid(gridParams);
	gridBase.init();
	gridBase.addQueryBtnEvent('btnQuery');
 */
function DataGrid(gridParams) {
	//对话框参数集合
	this.gridParams = gridParams;
	//jquery easy ui datagrid句柄
	this.$datagrid = null;
	//列头的contextMenu
	this.cmenu;
	
	//初始化datagrid
	this.init = function() {
		if(this.$datagrid)
			return;
		
		this.$datagrid = $('#' + this.gridParams.id).datagrid(this.createParamObj());
		this.addPagination();
		this.addEnterListener();
		this.$datagrid.datagrid('clearChecked');
	},
	
	/**
	 * 创建datagrid参数
	 */
	this.createParamObj = function() {
		$this = this;
		var toolbar = null;
		if(!!this.gridParams.toolbar)
			toolbar = '#' + this.gridParams.toolbar;
		
		return {
			//fit:true,
			ctrlSelect : true,
			checkOnSelect : false,								//	If true, the checkbox is checked/unchecked when the user clicks on a row. If false, the checkbox is only checked/unchecked when the user clicks exactly on the checkbox
			title : this.gridParams.title,
			iconCls : this.gridParams.iconCls,
			width : this.gridParams.width,						//控件宽度
			height : this.gridParams.height,					//控件高度
			nowrap : true,										//True to display data in one line. Set to true can improve loading performance.
			striped : true,										//设置为true将交替显示行背景。  
			idField : this.gridParams.idField,					//指示哪个字段是标识字段。
			collapsible : this.gridParams.collapsible,			//显示可折叠按钮
			fitColumns : false,									//水平滚动条。
			rownumbers : this.gridParams.rownumbers,			//如果为true，则显示一个行号列。 
			showHeader : this.gridParams.showHeader,			//Defines if to show row header.
			remoteSort : this.gridParams.remoteSort,			//定义从服务器对数据进行排序。 
			sortName : this.gridParams.sortName ,				//定义排序字段
			sortOrder : this.gridParams.sortOrder,				//定义排序类型 asc：升序 desc：倒序
			multiSort : this.gridParams.multiSort,				//定义是否按多个字段排序
			loadMsg : null,										//在从远程站点加载数据的时候显示提示消息。
			toolbar : toolbar,									//顶部工具栏的DataGrid面板
			data : this.gridParams.data,
			url : !!this.gridParams.url && !this.gridParams.lazyInit ? CtxVar.path + this.gridParams.url : null,
			method : this.gridParams.method || 'POST',
			queryParams : this._getQueryParam(),
			pagination : this.gridParams.pagination,			//如果为true，则在DataGrid控件底部显示分页工具栏。
			pageSize : this.gridParams.pageSize,				//每页显示的记录条数
			pageList : this.gridParams.pageList,				//可以设置每页记录条数的列表   
			//如果为true，则只允许选择一行。
			singleSelect : this.gridParams.singleSelect || !this.gridParams.multiSelect,
			//同列属性，但是这些列将会被冻结在左侧。
			frozenColumns : this.gridParams.hasCheckbox && ((this.gridParams.singleSelect || this.gridParams.multiSelect)) ? 
				[[{	field : 'ck', checkbox : true}]] : [],
			rowStyler : this.gridParams.rowStyler,				//行样式
			showFooter : this.gridParams.showFooter,			//是否显示列表foot
			
			//列头右键菜单
			onHeaderContextMenu : function(e, field) {
				e.preventDefault();
				if (!$this.cmenu) {
					$this.createColumnMenu();
				}
				$this.cmenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			},
			onLoadSuccess : this.gridParams.onLoadSuccess,
			onClickCell : this.gridParams.onClickCell,
			onClickRow : this.gridParams.onClickRow,
			onDblClickRow : this.gridParams.onDblClickRow,
			onCheckAll : this.gridParams.onCheckAll,
			onCheck : this.gridParams.onCheck
			
		};
	},
	
	/**
	 * 获取Grid控件
	 */
	this.getGrid = function() {
		return this.$datagrid;
	},
	
	/**
	 * 添加分页控件
	 */
	this.addPagination = function() {
		//分页
		if (this.gridParams.pagination) {
			var p = this.getGrid().datagrid('getPager');
			
			if('links' == AppProp.PaginationType) {
				$(p).pagination({
					total : this.gridParams.total, // 总记录数
					pageNumber : this.gridParams.pageNumber,
					//将翻页控件改为Pagination Links型式
					layout:['list','sep','first','prev','links','next','last','sep','refresh']
				});
			}
			else {
				$(p).pagination({
					total : this.gridParams.total, // 总记录数
					pageNumber : this.gridParams.pageNumber
				});
			}
		}
	},
	
	/**
	 * 添加回车监听
	 */
	this.addEnterListener = function() {
		var gridBase  = this;
		document.onkeydown = function(e) { 
		    var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		    	gridBase.query();
		    }
		}
	},
	
	/**
	 * 创建表头右键菜单
	 */
	this.createColumnMenu = function(){
		$this = this;
		
		$this.cmenu = $('<div/>').appendTo('body');
		$this.cmenu.menu({
			onClick: function(item){
				if (item.iconCls == 'icon-ok2') {
					$this.getGrid().datagrid('hideColumn', item.name);
					$this.cmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-empty'
					});
				} 
				else {
					$this.getGrid().datagrid('showColumn', item.name);
					$this.cmenu.menu('setIcon', {
						target: item.target,
						iconCls: 'icon-ok2'
					});
				}
			}
		});
		var fields = $this.getGrid().datagrid('getColumnFields');
		for (var i = 0; i < fields.length; i++) {
			var field = fields[i];
			var col = $this.getGrid().datagrid('getColumnOption', field);
			if(col.hidden)
				continue;
			
			$this.cmenu.menu('appendItem', {
				text : col.title,
				name : field,
				iconCls : 'icon-ok2'
			});
		}
	}
}

/**
 * 为表单的查询按钮设置查询方法, 当点击查询时, 根据表单中的条件执行查询
 * 作为一种快捷方式 addQueryBtnEvent 为查询按钮添加click事件
 * @param btnId
 * @param validateFun	查询前执行的前台校验函数, 该函数应当返回bool类型
 */
DataGrid.prototype.addQueryBtnEvent = function(btnId, validateFun) {
	var gridBase = this;
	$('#' + btnId).click(function() {
		//执行自定义前台校验
		if($.isFunction(validateFun)) {
			//..
		}
		
		gridBase.query();
	});
};

/**
 * 获取查询表格用的参数
 * 1. 如果仅设置了gridParams.queryParams, 则返回gridParams.queryParams;
 * 2. 如果设置了关联表单, 则返回表单数据的json对象
 * @returns
 */
DataGrid.prototype._getQueryParam = function() {
	return this.gridParams.queryFormId ?  
			$('#' + this.gridParams.queryFormId).form2json2() : this.gridParams.queryParams;
} 

/**
 * 执行查询操作, 加载并显示第一页的行
 * 1. 如果仅设置了gridParams.queryParams, 则根据gridParams.queryParams查询;
 * 2. 如果设置了关联表单, 则根据表单中的条件查询.
 */
DataGrid.prototype.query = function() {
	if(this.gridParams.lazyInit) {
		this.getGrid().datagrid({
			queryParams: this._getQueryParam(),
			url: getSecurityUrl(this.gridParams.url)
		});
	} else {
		this.getGrid().datagrid('load', this._getQueryParam());
	} 
}

/**
 * 重新加载行，就像 load 方法一样，但是保持在当前页
 */
DataGrid.prototype.reload = function() {
	this.getGrid().datagrid('clearSelections');
	this.getGrid().datagrid('clearChecked');
	this.getGrid().datagrid('reload', this._getQueryParam());
}

/**
 * Load local data, the old rows will be removed.
 */
DataGrid.prototype.loadData = function(data) {
	this.getGrid().datagrid('loadData', data);
}

/**
 * datagrid插入一个新行,  新行的索引是0. 如果rowdata的idField在表格中已存在, 则不能插入.
 * @param rowdata 行数据
 * @param callback 回调函数
 */
DataGrid.prototype.insertRow = function(rowdata, callback) {
	if(rowdata == null)
		return;
	
	var rows = this.getRows();
	for(var i = 0; i < rows.length; i++) {
		if(rows[i][this.gridParams.idField] == rowdata[this.gridParams.idField]) {
			return;
		}
	}
	
	this.getGrid().datagrid('insertRow', {
		index : 0,
		row : rowdata
	});
	
	if($.isFunction(callback)) {
		callback();
	}
}

/**
 * 插入多行数据
 * @param rows
 * @param callback
 */
DataGrid.prototype.insertRows = function(rows, callback) {
	if(rows == null || rows.length == 0)
		return;
	
	for(var i = 0; i < rows.length; i++) {
		this.insertRow(rows[i]);
	}
}

/**
 * 判断表格中的rowdata.colName列是否已经存在colValue的值
 * @param rowdata
 * @param colName
 * @param colValue
 * @returns {Boolean}
 */
DataGrid.prototype.isExist = function(colName, colValue) {
	var rows = this.getRows();
	for(var i = 0; i < rows.length; i++) {
		if(rows[i][colName] == colValue) {
			return true;
		}
	}
	
	return false;
}

/**
 * 获取表格中的所有数据
 * @returns
 */
DataGrid.prototype.getRows = function() {
	return this.getGrid().datagrid('getRows');
}

/**
 * 获取表格中所有行的ID
 */
DataGrid.prototype.getRowsIdArr = function() {
	var rows = this.getRows();
	if(rows.length == 0)
		return null
	var ids = [];
	for(var i = 0; i < rows.length; i++) {
		ids.push(rows[i].id);
	}
	return ids;
}

/**
 * 获取表格在当前页的行数
 * @returns
 */
DataGrid.prototype.getRowCount = function() {
	return this.getRows().length;
}

/**
 * 根据id获取行数据
 * @param id id字段的值
 * @returns 
 */
DataGrid.prototype.getRowData = function(id) {
	var rows = this.getRows();
	return rows[this._getRowIndex(id)];
}

/**
 * 根据 id 字段值获取行索引
 * @param id	id字段的值
 */
DataGrid.prototype._getRowIndex = function(id) {
	return this.getGrid().datagrid('getRowIndex', id);
}

/**
 * 更新datagrid指定id的行数据
 * @param id id字段的值
 * @param rowdata 行数据
 * @param callback 回调函数
 */
DataGrid.prototype.updateRow = function(id, rowdata, callback) {
	this.getGrid().datagrid('updateRow', {
		index : this._getRowIndex(id),
		row : rowdata
	});
	
	if($.isFunction(callback)) {
		callback();
	}
}

/**
 * 更新指定的行数据
 * @param rows
 */
DataGrid.prototype.updateSelectedRows = function(rows) {
	if($.isArray(rows)) {
		for(var i = 0; i < rows.length; i++) {
			this.updateRow(rows[i][this.gridParams.idField], rows[i]);
		}
	}
}

/**
 * 移除所有数据
 */
DataGrid.prototype.removeAll = function() {
	this.loadData([]);
}

/**
 * 删除指定的行数据
 * @param rows	行数据
 */
DataGrid.prototype.deleteRows = function(rows) {
	if($.isArray(rows)) {
		var idArr = [];
		for(var i = 0; i < rows.length; i++) {
			idArr.push(rows[i][this.gridParams.idField]);
		}
		
		for(var i = 0; i < idArr.length; i++) {
			this.deleteRow(idArr[i]);
		}
	}
}

/**
 * 删除指定id的行
 * @param id	id字段的值
 */
DataGrid.prototype.deleteRow = function(id) {
	this.getGrid().datagrid('deleteRow', this._getRowIndex(id));
}

/**
 * 删除选中行, 如果没有任何选中, 弹出提示
 */
DataGrid.prototype.deleteSelectedRowsWithMsg = function() {
	var rows = this.getSelectedRowsWithMsg();
	if(rows.length > 0)
		this.deleteRows(rows);
}

/**
 * 删除选中行
 */
DataGrid.prototype.deleteSelectedRows = function() {
	var rows = this.getSelectedRows();
	if(rows.length > 0)
		this.deleteRows(rows);
}

/**
 * deleteRowOnRemote提供了一种根据ID后台删除行数据的快捷方式. 
 * 该方法将执行ajaxExec(url + '/' + id), 成功后将该行从表格中移除.
 */
DataGrid.prototype.deleteRowOnRemote = function(id, url, methodType,callback) {
	var gridBase = this;
	Msg.confirm(
		function(result) {
			if(result) {
				ajaxExec(url + '/' + id, null, function() {
					gridBase.deleteRow(id);
					if(callback){
						callback();
					}
				}, true, methodType);
			}
		});
}

/**
 * 获取选中行
 * @returns {Array}
 */
DataGrid.prototype.getSelectedRows = function() {
	var selections = this.getGrid().datagrid('getChecked');
	return  $.isArray(selections) ? selections : null;
}

/**
 * 获取选中行带提示信息, 如果没有选中行, 弹出提示信息并返回null
 * @returns {Array}
 */
DataGrid.prototype.getSelectedRowsWithMsg = function() {
	var rows = this.getSelectedRows();
	if(rows.length == 0) {
		Msg.showWarning('请选择记录');
	}
	return rows;
}

/**
 * 获取选中行的id数组
 * @returns {Array}
 */
DataGrid.prototype.getSelectedRowsIdArr = function() {
	var rows = this.getSelectedRows();
	if(rows.length == 0) {
		return null
	}
	var ids = [];
	for(var i = 0; i < rows.length; i++) {
		ids.push(rows[i].id);
	}
	return ids;
}

/**
 * 获取选中行的id数组带提示信息,如果没有选中行, 弹出提示信息并返回null
 * @returns {Array}
 */
DataGrid.prototype.getSelectedRowsIdArrWithMsg = function() {
	var arr = this.getSelectedRowsIdArr();
	if(arr == null){
		Msg.showWarning('请选择记录');
	}
	return arr;
}

/**
 * 获取选中行的id字符串
 * @returns {String}
 */
DataGrid.prototype.getSelectedRowsIdStr = function() {
	var arr = this.getSelectedRowsIdArr();
	return  $.isArray(arr) ? arr.join(',') : null;
}

/**
 * 获取选中行的id字符串带提示信息,如果没有选中行, 弹出提示信息并返回null
 * @returns {String}
 */
DataGrid.prototype.getSelectedRowsIdStrWithMsg = function() {
	var arr = this.getSelectedRowsIdArrWithMsg();
	return $.isArray(arr) ? arr.join(',') : null;
}

/**
 * 处理关联控件
 */
DataGrid.prototype.execRelate = function() {
	//改变关联组件
	for(var i = 0; i < this.gridParams.relationArr.length; i++) {
		//检验是否实现了Observable接口
		Interface.ensureImpelements(this.gridParams.relationArr[i], Observable);
		this.gridParams.relationArr[i].update(this);
	}
}

/**
 * 设置表格可编辑
 */
DataGrid.prototype.setEditable = function() {
	this.getGrid().datagrid('enableCellEditing').datagrid('gotoCell');
}

DataGrid.getRowById = function(dg,id){
	var row = null;
	var rows = dg.datagrid('getRows');
	$.each(rows,function(i,n){
		if(n.id==id){
			row = n;
			return false;
		}
	});
	return row;
}

/**
 * datagrid参数
 */
function GridParams(){
	//datagrid配置项
	this.width = '100%';				//控件宽度
//	this.height = 'auto';			//控件高度
	this.height = $(window).height();	//控件高度
	this.iconCls = 'icon-list';
	this.id = '';						//datagrid id
	this.title = '';					//在面板头部显示的标题文本。
	this.toolbar = '';					//顶部工具栏的DataGrid面板
	this.idField = 'id';				//Indicate which field is an identity field.
	this.url = null;					//一个URL从远程站点请求数据。
	this.pagination = true;				//是否显示分页工具栏, 默认true
	this.singleSelect = false;			//如果为true，则只允许选择一行。
	this.multiSelect = false;			//如果为true，则允许选择多行。
	this.hasCheckbox = true;			//是否有checkbox列, 仅当singleSelect或multiSelect为true时, 该属性才生效
	this.remoteSort = true;				//定义从服务器对数据进行排序
	//分页配置项
	this.total = 0;						//默认总记录条数
	this.pageNumber = 1;				//默认当前页号
	this.pageSize = 15;					//每页显示的记录条数
	this.pageList = [15, 30, 50];		//可以设置每页记录条数的列表 
	this.queryFormId = '';				//用于查询grid的表单ID
	this.queryParams = null;			//object 当请求远程数据时，发送的额外参数{}
	this.relationArr = [];				//comboGridParam的关联组件, 其中的元素应当是VCAdapter类型, 或实现了Observable接口
	this.collapsible = true;			//是否显示折叠按钮,
	this.rownumbers = true;				//如果为true，则显示一个行号列。 
	this.showHeader = true;				//Defines if to show row header.
	this.lazyInit = false; 				//懒加载 layzeInit == true: 初始化时不执行查询
	this.sortName = '';					//默认排序字段
	this.sortOrder = '';				//默认按升序排序
	this.multiSort = false;				//多个字段排序
	this.method = '';
	/*
	 * The data to be loaded.
		Code example:
		$('#dg').datagrid({
			data: [
				{f1:'value11', f2:'value12'},
				{f1:'value21', f2:'value22'}
			]
		});
	 */
	this.data = [];
	this.showFooter = false;			//显示底部附件信息，例如汇总等
	this.onLoadSuccess =  function(){};
	this.onClickCell = function(){};
	this.onClickRow = function(){};
	this.onDblClickRow = function(){};
	this.onCheckAll = function(){};
}
