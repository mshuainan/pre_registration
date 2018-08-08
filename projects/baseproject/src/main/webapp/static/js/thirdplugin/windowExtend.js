/** 扩展easyui-window,小于上一层的高宽 */
$.extend($.fn.window.methods, {
	autoSizeMax : function(obj,params){
		var w = $(document).width()-30;
		var h = $(document).height()-80;
		if(params!=null && params.body){
			w = $(document.body).innerWidth()-50;
			h = $(document.body).innerHeight()-40;
		}
		if(params!=null && params.window){
			w = $(window).width()-30;
			h = $(window).height()-20;
		}
		$(obj).window('resize',{
			width : w,
			height : h
		});
		$(obj).window('center');
	}
});