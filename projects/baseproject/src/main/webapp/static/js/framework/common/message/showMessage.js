/**
 * 公用提示信息，一定时间后自动关闭
 */
function ShowMessage() {
	
	this.init = function() {
		this.hideMessages();
		this.addClickEvent();
	}
	
	/**
	 * 警告提示
	 * @param message	提示框显示的消息文本(非必填)
	 * @param title		显示在标题面板的标题文本(非必填)
	 */
	this.showWarning = function(message, title) {
		this.showMessage('warning', message, false);
	},
	
	/**
	 * 错误提示
	 * @param message	提示框显示的消息文本(非必填)
	 * @param title		显示在标题面板的标题文本(非必填)
	 */
	this.showError = function(message, title) {
		this.showMessage('error', message, false);
	},
	
	/**
	 * 成功提示
	 * @param message	提示框显示的消息文本(非必填)
	 */
	this.showSuccess = function(message) {
		this.showMessage('success', message);
	},
	
	/**
	 * 隐藏信息提示窗口
	 */
	this.hideMessages = function() {
		$.each($('.message'), function(index, messge) {
			$(this).hide("normal");
		});
	},

	/**
	 * 显示信息
	 * @param type 信息类型 warning、error、success、info
	 * @param message 
	 * @param isSuccess 是否success类型  默认true
	 */
	this.showMessage = function(type, message, isSuccess) {
		$('.' + type).show("normal");
		$('.' + type + ' > p').html(message);
		$('.' + type).animate({bottom:"0"}, 500);
		if(isSuccess || typeof(isSuccess) == "undefined" && AppProp.timeout != '0') {
			setTimeout(this.hideMessages, AppProp.timeout);
		}
	},
	
	/**
	 * 为信息展示框绑定点击事件  点击后展示框立即隐藏
	 */
	this.addClickEvent = function() {
		$('.message').click(function() {			  
			$(this).hide("normal");
		});
	}
}