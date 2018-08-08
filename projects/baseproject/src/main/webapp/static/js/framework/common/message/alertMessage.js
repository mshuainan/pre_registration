/**
 * 公用提示信息 普通弹出框形式
 */
function AlertMessage() {

	/**
	 * alert 警告提示
	 * @param message	提示框显示的消息文本(非必填)
	 * @param title		显示在标题面板的标题文本(非必填)
	 */
	this.showWarning = function(message, title) {
		$.messager.alert(title || "操作提示", message, "warning");
	},
	
	/**
	 * alert 错误提示
	 * @param message	提示框显示的消息文本(非必填)
	 * @param title		显示在标题面板的标题文本(非必填)
	 */
	this.showError = function(message, title) {
		$.messager.alert(title || "操作提示", message, "error");
	},
	
	/**
	 * 操作成功提示信息
	 * @param message	提示信息内容
	 */
	this.showSuccess = function(message, title) {
		$.messager.alert(title || "操作提示", message || '操作成功');
	}

}

