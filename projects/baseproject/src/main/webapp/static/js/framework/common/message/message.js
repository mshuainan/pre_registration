/**
 * 公用提示信息
 */
var Msg = {
	
	messageHandler : MsgFactory.create(AppProp.msgType),
		
	/**
	 * 确认提示
	 * @param callback	回调函数,callback(b)：当用户点击按钮后触发的回调函数，如果点击OK则给回调函数参数b为true;如果点击cancel则为false
	 * @param message	确认消息窗口显示的消息文本(非必填)
	 * @param title     显示在标题面板的标题文本(非必填)
	 * 
	 * 使用示例:
	 * 	Msg.confirm(myFun);
	 * 	....
		function myFun(result) {
			if(result) {
				alert("ok")
			} else {
				alert("failure")
			}
		}
	 */
	confirm : function(callback, message, title) {
		$.messager.confirm(title || I18N.getText('common.operation.tip'), message || I18N.getText('common.operation.isConfirm'), callback);
		//去掉右上角的关闭符号
		//$(".panel-tool-close").css("display","none");
	},
	
	/**
	 * 警告提示
	 * @param message	提示框显示的消息文本(非必填)
	 * @param title		显示在标题面板的标题文本(非必填)
	 */
	showWarning : function(message, title) {
		this.messageHandler.showWarning(message, title);
	},
	
	/**
	 * 错误提示
	 * @param message	提示框显示的消息文本(非必填)
	 * @param title		显示在标题面板的标题文本(非必填)
	 */
	showError : function(message, title) {
		this.messageHandler.showError(message || '失败！', title);
	},
	
	/**
	 * 操作成功提示信息
	 * @param message	提示信息内容
	 */
	showSuccess : function(message) {
		this.messageHandler.showSuccess(message || '成功！');
	}
}