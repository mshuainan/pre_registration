/**
 * grid单元格渲染辅助函数
 */
var GridFormatter = {
	
	basePath : '/static/js/jquery-easyui-1.4.5/themes/icons/',
	
	statusValue : {
		REFUSE : -1,
		NO : 0,
		YES : 1,
		PART : 2,
		WAIT : 3,
		// 取消或作废状态
		CANCLE : 9
	},
	
	/**
	 * 操作列渲染, 返回超链接
	 * @param clickFunStr	onclick函数字符串
	 * @param text			操作列显示的文字
	 * @parma isShow		是否显示 默认显示
	 * @param isDisabled	是否不可交互, 默认false
	 * @returns {String}
	 * <br>
	 * <li>示例1 : GridFormatter.operation("CmpMgtDialog.openEdit(" + index + ")", "修改");
	 * <li>示例2 :　GridFormatter.operation("CmpMgtDialog.openEdit(" + index + ")", "修改") 
				+ GridFormatter.operation("CmpMgtDialog.openEdit('" + rowdata.id + "')", "删除");
	 */
	operation : function(clickFunStr, text, isShow, isDisabled) {
		if(isShow != undefined && !isShow) {
			return this.operationNull();
		} else if(isDisabled) {
			return '<div style="display: inline-block; margin-left:8px; margin-right:8px;">' + text + '</div>';			
		} else {
			return '<div style="display: inline-block; margin-left:8px; margin-right:8px;">\
						<a href="javascript:;" onclick="' + clickFunStr + '">' + text + '</a>\
					</div>';			
		}	
	},
	
	/**
	 * 操作列渲染, 返回超链接,可以传入自定义class
	 * @param clickFunStr	onclick函数字符串
	 * @param text			操作列显示的文字
	 * @parma isShow		是否显示 默认显示
	 * @param isDisabled	是否不可交互, 默认false
	 * @returns {String}
	 * <br>
	 * <li>示例1 : GridFormatter.operation("CmpMgtDialog.openEdit(" + index + ")", "修改");
	 * <li>示例2 :　GridFormatter.operation("CmpMgtDialog.openEdit(" + index + ")", "修改") 
				+ GridFormatter.operation("CmpMgtDialog.openEdit('" + rowdata.id + "')", "删除");
	 */
	operation2 : function(clickFunStr, text, isShow, isDisabled,classStyle) {
		
		var className = '';
		if(classStyle != undefined && classStyle!=''){
			className = 'class="'+classStyle+'"';
		}
		
		if(isShow != undefined && !isShow) {
			return this.operationNull();
		}
		else if(isDisabled) {
			return '<div '+className+' style="display: inline-block; margin-left:8px; margin-right:8px;">' + text + '</div>';			
		}
		else {
			return '<div '+className+' style="display: inline-block; margin-left:8px; margin-right:8px;">\
			<a href="javascript:;" onclick="' + clickFunStr + '">' + text + '</a>\
			</div>';			
		}	
	},
	
	/**
	 * 操作列渲染, 返回超链接，以新窗口打开
	 * @param url
	 * @param text
	 * @returns {String}
	 */
	operationNewPage : function(url, text) {
		if (text == null || text.length == 0)
			return "";
		return '<div style="display: inline-block; margin-left:8px; margin-right:8px;">\
			<a href="' + getSecurityUrl(url) + '" target="_blank">' + text + '</a>\
		</div>';
	},
	
	/**
	 * 操作列渲染
	 * @param id 
	 * @param menuButtonParams
	 */
	menuButton : function(id, menuButtonParams) {
		var result = '<a href="#" class="easyui-menubutton" data-options="menu:\'#' + id + '\',iconCls:\'icon-edit\'">操作</a>' ;
		result += '<div id="' + id + '" style="width:150px;">';
		for(var i = 0 ; i < menuButtonParams.length; i++){
			if(typeof menuButtonParams[i] === 'string'){
				result += '<div class="menu-sep"></div>';
			} else {
				result += '<div data-options="iconCls:\'' + menuButtonParams[i].icon + '\'" onclick="' + menuButtonParams[i].onclick + '">' + menuButtonParams[i].text + '</div>';
			}
		}
		result += '</div>'; 
		return result; 
	},

	/**
	 * 用于内容过长时显示全部内容
	 * @param value
	 * @param row
	 * @param index
	 * @returns {String}
	 */
	remarkFormater : function(value, row, index) {
		var content = '';
		var abValue = value + '';
		if (value != undefined) {
			content = '<div href="javascript:;"  title="' + abValue
				+ '" class="easyui-tooltip">' + abValue + '</div>';
		}
		return content;
	},
	
	/**
	 * 操作列渲染, 直接返回 '--', 当操作列不允许交互时使用
	 */
	operationNull : function() {
		return '<div style="display: inline-block; margin-left:8px; margin-right:8px;">--</div>';
	},
	
	/**
	 * 超链接渲染, 跳转到url指向的视图
	 * @param value
	 * @param url
	 * @parma isShow		是否显示 默认显示
	 * @returns {String}
	 */
	toView : function(value, url, isShow) {
		if(isShow != undefined && !isShow) {
			return this.operationNull();
		}
		
		return '<a href="javascript:;" onclick="sysToUrl(\'' + url + '\')">' + value + '</a>'
	},
	
	/**
	 * 日期格式化
	 * @param cellValue
	 */
	dateFormat : function(cellValue) {
		return DateUtil.format(cellValue);
	},

	/**
	 * 日期格式化-yyyy-MM-dd hh:mm:ss
	 * @param cellValue
	 */
	dateFormat_HMS : function(cellValue) {
		return DateUtil.format_HMS(cellValue);
	},
	
	/**
	 * **率格式化
	 */
	rateFormat : function(value) {
		if(value == null || value == "") return "";
		return value + "%";
	},
	
	/**
	 * **率格式化,需乘以100
	 */
	rate100Format : function(value) {
		if(value == null || value == "") return "";
		return value*100 + "%";
	},
	
	/**
	 * 单元格状态渲染, 根据cellValue将状态渲染为图标
	 * @param cellValue 单元格的值
	 * @param titles	{}类型, 值对应的titles, render函数能够识别的属性共5个: no, yes, part, refuse, wait
	 * 
	 * 使用示例:
	 * GridFormatter.status(cellValue, {yes:'已确认', no:'未确认', part:'部分确认', refuse:'驳回'});
	 */		
	status : function(cellValue, titles) {
		if(!titles)
			return '';
		
		//未**
		if(0 == cellValue) {
			return "<img src='" + CtxVar.path + this.basePath + "clear.png' title='" + titles.no + "' class='easyui-tooltip'>";
		}
		//已**
		else if(1 == cellValue) {
			return "<img src='" + CtxVar.path + this.basePath + "ok2.png' title='" + titles.yes + "' class='easyui-tooltip'>";
		}
		//部分**
		else if(2 == cellValue) {
			return "<img src='" + CtxVar.path + this.basePath + "half-done.png' title='" + titles.part + "' class='easyui-tooltip'>";
		}
		//驳回
		else if(-1 == cellValue) {
			return "<img src='" + CtxVar.path + this.basePath + "cancel.png' title='" + titles.refuse + "' class='easyui-tooltip'>";
		}
		//待**
		else if(3 == cellValue) {
			return "<img src='" + CtxVar.path + this.basePath + "wait.png' title='" + titles.wait + "' class='easyui-tooltip'>";
		}
		else if(4 == cellValue) {
			return "<img src='" + CtxVar.path + this.basePath + "ok.png' title='" + titles.freeCheck + "' class='easyui-tooltip'>";
		}
		else {
			return cellValue;
		}
	},
	
	/**
	 * 简单状态(是/否)
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	yesOrNoStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'否', yes:'是'});
	},
	/**
	 * 是否上下架
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	marketableStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未上架', yes:'已上架'});
	},
	
	/**
	 * 缺失状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	absenceStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'缺失', yes:'完善'});
	},
	
	/**
	 * 简单状态(有/无)
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	havaOrNotStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'无', yes:'有'});
	},
	
	/**
	 * 发布状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	publishStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未发布', yes:'已发布'});
	},
	
	/**
	 * 审核状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	checkStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未开始', wait:"待审核", yes:'通过', refuse:"驳回", part:"部分审核"});
	},
	
	/**
	 * 审核状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	winStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未开始', wait:"待授标", yes:'中标',refuse:'未中标'});
	},
	
	/**
	 * 另外一种审核状态 不包括待审核选项
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	checkStatus2 : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未审核', yes:'审核通过', refuse:"审核驳回",wait:'待审核'});
	},
	
	/**
	 * 验证状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	verifierStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未开始', yes:'通过', refuse:"驳回",wait:'待验证'});
	},
	
	/**
	 * 部署状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	deployStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未部署', yes:'已部署',wait:'待部署'});
	},
	/**
	 * ERP 同步状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	erpSynStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未同步', yes:'同步成功', refuse:"同步失败"});
	},
	/**
	 * 确认状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	confirmStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未确认', yes:'已确认', part:"部分确认", refuse:"驳回", wait:'待确认'});
	},
	
	/**
	 * 响应状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	applicationStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未应标', yes:'已应标', refuse:"拒绝"});
	},
	
	/**
	 * 缴纳状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	payStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未缴纳', yes:'已缴纳'});
	},
	
	/**
	 * 关闭状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	closeStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未关闭', yes:'已关闭'});
	},
	
	/**
	 * 驳回状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	refuseStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未驳回', yes:'已驳回'});
	},
	
	/**
	 * 完成状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	completeStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未完成', yes:'已完成', part:"部分完成"});
	},
	
	/**
	 * 完成审核状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	completeCheckStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未完成', yes:'已完成', wait:'待审核',refuse:'驳回'});
	},
	
	/**
	 * 完成审核状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	completeActiveStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未完成', yes:'已完成', part:'进行中',refuse:'失败'});
	},
	
	/**
	 * 执行状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	executingState : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未执行', yes:'已完成', part:"部分完成"});
	},
	
	/**
	 * 激活状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	activateStatus : function(value, rowdata, index) {
		return  GridFormatter.status(value, {no:'未激活', yes:'已激活'});
	},
	
	/**
	 * 是否有效
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	validFlag : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'已取消', yes:'有效'});
	},
	/**
	 * 是否取消（问题反馈）
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	cancelStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'有效', yes:'已取消'});
	},
	/**
	 * 有效状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	validStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未生效', yes:'已生效'});
	},
	
	/**
	 * 反馈状态
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	feedbackStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未反馈', yes:'已反馈', part:'部分反馈', wait:'待反馈'});
	},
	/**
	 * 反馈状态（带驳回 问题状态）
	 * @param value
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	planFeedbackStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未反馈', yes:'已反馈', refuse:'驳回', wait:'待反馈'});
	},
	
	/**
	 * 启用、禁用状态
	 * @param value 1: 启用  0：禁用
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	lockStatus : function(value, rowdata, index) {
		if(0 == value) {
			return "<img src='" + CtxVar.path + GridFormatter.basePath + "hmenu-lock.png' title='禁用' class='easyui-tooltip'>";
		}
		else if(1 == value) {
			return "<img src='" + CtxVar.path + GridFormatter.basePath + "hmenu-unlock.png' title='启用' class='easyui-tooltip'>";
		}
	},
	/**
	 * 锁定状态
	 * @param value 1: 锁定  0：未锁定
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	lockedStatus : function(value, rowdata, index) {
		if(1 == value) {
			return "<img src='" + CtxVar.path + GridFormatter.basePath + "hmenu-lock.png' title='锁定' class='easyui-tooltip'>";
		}
		else if(0 == value) {
			return "<img src='" + CtxVar.path + GridFormatter.basePath + "hmenu-unlock.png' title='未锁定' class='easyui-tooltip'>";
		}
	},
	
	/**
	 * 启用、禁用状态
	 * @param value 0: 未禁用  1：已禁用
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	disableStatus : function(value, rowdata, index) {
		if(1 == value) {
			return "<img src='" + CtxVar.path + GridFormatter.basePath + "hmenu-lock.png' title='禁用' class='easyui-tooltip'>";
		}
		else if(0 == value) {
			return "<img src='" + CtxVar.path + GridFormatter.basePath + "hmenu-unlock.png' title='未禁用' class='easyui-tooltip'>";
		}
	},
	
	/**
	 * 合同签订状态
	 * @param value 0: 未签  3：签订中 1：已签
	 * @param rowdata
	 * @param index
	 * @returns
	 */
	contractStatus : function(value, rowdata, index) {
		return GridFormatter.status(value, {no:'未签',wait:'签订中',yes:'已签'});
	},
	
	/**
	 * 文件下载
	 * @param value
	 * @param url
	 * @returns {String}
	 */
	doDownload : function(value, url) {
		return value ? '<a href="javascript:;" onclick="FileUtil.download(\'' + url + '\')">' + value + '</a>' : '';
	},
	
	numFmt2 : function(v,r,i){
		if(v!=null && v!=''){
			return parseFloat(v).toFixed(2);
		}
		return r;
	},
	numFmt3 : function(v,r,i){
		if(v!=null && v!=''){
			return parseFloat(v).toFixed(3);
		}
		return r;
	},
	numFmt4 : function(v,r,i){
		if(v!=null && v!=''){
			return parseFloat(v).toFixed(4);
		}
		return r;
	}
}