/**
 * 所有细粒度组件的基类
 */
function Fc(id) {
	this.vc = $('#' + id);
	
	/**
	 * 同 jquery的val方法
	 */
	this.val = function(value) {
		if(!!value) 
			this.vc.textbox('setValue', value);
		else
			return this.vc.textbox('getValue');
	};
	
	this.clear = function() {
		this.vc.textbox('clear');
	}

	this.setDisable = function(isDisable) {
		if(isDisable)
			this.vc.textbox('disable');
		else
			this.vc.textbox('enable');
	};
	
	this.setReadonly = function(isReadonly) {
		this.vc.textbox('readonly', isReadonly);
	};
	
	/**
	 * 设置文本框宽度
	 */
	this.setWidth = function(width) {
		this.vc.textbox('resize', width);
	};
	
	/**
	 * 
	 */
	this.getText = function() {
		return this.vc.textbox('getText');
	};
}