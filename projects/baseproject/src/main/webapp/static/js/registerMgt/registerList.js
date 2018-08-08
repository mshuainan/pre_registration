$(function() {
	/** 初始化注册信息列表 */
	RegisterGrid.init();
});

var RegisterGrid = {
	gridBase : null,
	init : function() {
		var gridParams = new GridParams();
		gridParams.title = '报名列表';
		gridParams.id = 'registerGrid';
		gridParams.multiSelect = true;
		gridParams.toolbar = 'btnToolbar';
		gridParams.url = '/portalroot/newRegister/query';
		gridParams.queryFormId = 'searchForm';
		this.gridBase = new DataGrid(gridParams);
		this.gridBase.init();
		this.bindEvent();
	},	
	bindEvent : function() {
		this.gridBase.addQueryBtnEvent('btnQuery');
		/*$('#btnDelete').click(UserDialog.deleteRows);*/
	},	
	formatter : {
		opteration :  function(value, rowdata, index) {
			return  GridFormatter.operation("deleteRow('" + rowdata.id + "')",'作废', true);
		},
		
		studentGender:function(value){
			if(1 == value){
				return '男';
			}else if(2 == value){
				return '女';
			} else {
				return '未知';
			}
		},
		
		propertyOwner:function(value){
			if(1 == value){
				return '父亲';
			}else if(2 == value){
				return '母亲';
			} else {
				return '';
			}
		},
		
		studentFaceName : function(value, rowdata) {
			return GridFormatter.doDownload(value, rowdata.studentFaceUrl);
		},
		
		propertyFileName : function(value, rowdata) {
			return GridFormatter.doDownload(value, rowdata.propertyFileUrl);
		},
		
		lockStatus : function(value, rowdata, index) {
			if(0 == value || -2 == value || 2 == value ) {
				return "<img src='" + CtxVar.path + GridFormatter.basePath + "hmenu-lock.png' title='" + I18N.getText('common.disable') + "' class='easyui-tooltip'>";
			}
			else if(1 == value || -1 == value) {
				return "<img src='" + CtxVar.path + GridFormatter.basePath + "hmenu-unlock.png' title='" + I18N.getText('common.enable') + "' class='easyui-tooltip'>";
			}
		},
	},
	
};

function deleteRow(rowId) {
	RegisterGrid.gridBase.deleteRowOnRemote(rowId, '/system/usermgt/users/delete');
};

/** 导出注册信息 */
function expRegisterInfo() {
	var selectedRowsIdArr = RegisterGrid.gridBase.getSelectedRowsIdArr();
	console.log(selectedRowsIdArr);
	if(selectedRowsIdArr == null){
		easyUISubimitForExport('searchForm','/portalroot/newRegister/exportByQuery');
	} else{
		var selectedRowsIds = selectedRowsIdArr.join(",");
		exportExcelToUrl('/portalroot/newRegister/exportRegisterInfo/' + selectedRowsIds, true);
	}
};