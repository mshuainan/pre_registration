///**
// * 组件的观察者接口, 当一个组件的值/状态/范围改变后, 将影响与之关联的所有组件
// * 
// *  使用示例:
// *  var MyVC = function(){};
//    MyVC.prototype = {
//	    update : function(source){...}
//	}
//	
//	//创建对象
//	var vc = new MyVC();
//	
//	//检验MyVC类的vc对象是否实现了Observable接口
//	Interface.ensureImpelements(vc, Observable);
// */
//var Observable = new Interface('Observable', ['update']);