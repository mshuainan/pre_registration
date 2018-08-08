/**
 * easyui 的datebox控件
 */
function DateBox(id) {
	DateBox.superclass.constructor.call(this, id);
	
	/**
	 * 同 jquery的val方法
	 */
	this.val = function(value) {
		if(!!value) 
			this.vc.datebox('setValue', value);
		else
			return this.vc.datebox('getValue');
	};
	
	this.setValidator = function(validateFun) {
		this.vc.datebox('calendar').calendar({
	        validator: validateFun
	    });
	};
}
extend(DateBox, Fc);

/**
 * 日期验证
 */
var DateValidator = {
	/**
	 * 将当前日期以前的日期设置为不可选
	 */
	gtToday : function(date) {
		var now = new Date();
        var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
        return d1 <= date ;	
	},

	/**
	 * 将当前日期以后的日期设置为不可选
	 */
	ltToday : function(date) {
		var now = new Date();
	    var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
	    return d1 >= date ;	
	}
	
//	dateArea : function(startId, endId) {
//		var start = new DateBox(startId);
//		var end = new DateBox(endId);
//		
//		start.vc.datebox('calendar').calendar({
//	        validator: function(date) {
//	    		var endDate = end.val();
//	    		if(!!endDate) {
//	    			var arr = endDate.split('-');
//	    			var d1 = new Date(arr[0], arr[1], arr[2]);
//	    			return d1 >= date;
//	    		}
//	    		else {
//	    			return true;
//	    		}
//
//	    	}
//	    });
//	}
}

