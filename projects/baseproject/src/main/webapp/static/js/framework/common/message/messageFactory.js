/**
 * 信息窗口工厂类  生产不同的信息窗口样式
 */
var MsgFactory = {
		
	create : function (msgWindowType) {
		if(msgWindowType == "alert"){
			return new AlertMessage();
		} else {
			var msg = new ShowMessage();
			msg.init();
			return msg;
		}
	}
}