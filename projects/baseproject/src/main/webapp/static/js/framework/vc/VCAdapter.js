/**
 * 组件适配器, 实现了Observable接口
 * @param vcId 组件id
 * 
 * 使用示例:
 * id对应easyUI组件id
 * var myVCAdapter = new VCAdapter('id');
 * myVCAdapter.update = function(sourceVC) {
 * 		do something...
 * };
 */
function VCAdapter(vcId) {
	this.vc = $('#' + vcId);
	this.isEasyUI = true;
	
	try { 
		this.vc.textbox('options');
	} catch (e) {
		this.isEasyUI = false;
	}
}

VCAdapter.prototype = {

	/**
	 * Observable接口的方法
	 * @param sourceVC	源组件(被观察者)
	 */
	update : function(sourceVC) {
		
	},
	
	/**
	 * 为组件赋值
	 * @param value
	 */
	setValue : function(value) {
		//jquery easyui组件
		if(this.isEasyUI) {
			this.vc.textbox('setValue', value); 
		}
		//普通组件
		else {
			this.vc.val(value);
		}
	},
	
	getValue : function() {
		if(this.isEasyUI) {
			return this.vc.textbox('getValue');
		}
		else {
			return this.vc.val();
		}
	},
	
	getText : function() {
		if(this.isEasyUI) {
			return this.vc.textbox('getText');
		}
		else {
			return this.vc.val();
		}
	}
};