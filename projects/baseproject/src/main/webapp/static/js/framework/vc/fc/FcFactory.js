var FcFactory = {
	
	/**
	 * 
	 * @param id	控件ID
	 * @param type	控件类型
	 * 		type 的可选类型：
	 * 			1.input -> 文本框
	 *  		2.select -> 下拉框
	 * 			3.date -> 日期控件
	 * @returns 
	 */
	getInstance : function(id, type) {
		var result;
		switch (type) {
			case 'input':
				result = new TextBox(id);
				break;
			case 'select':
				result = new ComboBox(id);
				break;
			case 'date':
				result = new DateBox(id);
				break;
			default :
				alert('无法识别组件类型：'  + type);
				throw new Error('无法识别组件类型：'  + type);
				break;
		};
		
		return result;
	}
}