$.extend($.fn.validatebox.defaults.rules, {   
    selectValueRequired: {   
        validator: function(value,param){  
             if (value == "" || value.indexOf('--请选择--') >= 0) {   
                return false;  
             }else {  
                return true;  
             }    
        },   
        message: '该输入项为必选项!'   
    },
    phoneRex: {
        validator: function(value){
        	var reg =/^0{0,1}(13[0-9]|15[0-9]|18[0-9])[0-9]{8}$/;
        	 if(value == ""){
        		 return false;
        	 } else if(isNaN(value)||(value.length != 11)) {
        		return false;
        	 } else if(!reg.test(value)) {
        		 return false;
        	 } else {
        		 return true;
        	 }
	        //var rex=/^1[3-8]+\d{9}$/;
	        //var rex=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	        //区号：前面一个0，后面跟2-3位数字 ： 0\d{2,3}
	        //电话号码：7-8位数字： \d{7,8
	        //分机号：一般都是3位数字： \d{3,}
	         //这样连接起来就是验证电话的正则表达式了：/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/		 
	        //var rex2=/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
//	        if(rex.test(value)/*||rex2.test(value)*/) {
//	          return true;
//	        }else{
//	           return false;
//	        }
        },
        message: '请输入正确电话或手机格式!'
      },
      // 身份证验证
      idCard: {// 验证身份证
          validator: function (value) {
	        	var rex2 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
	  	        if(rex2.test(value)) {
	  	          return true;
	  	        }else{
	  	           return false;
	  	        }
             // return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
          },
          message: '身份证号码格式不正确！'
      },
      /**
       * 适用于新增和查询
       */
     checkDate: { 
    	 validator: function(value, param){ 
    		 var time = param.toString().split(",");
    		 var $timeStart = $(time[0]);
    		 var $timeEnd = $(time[1]);
    		 if(!$timeStart.is('.textbox-f'))
    	     $.parser.parse($timeStart.parent()); 
    		 if(!$timeEnd.is('.textbox-f'))
    		 $.parser.parse($timeEnd.parent());  
    		 var time1 = $timeStart.datetimebox('getText'); 
    		 var time2 = $timeEnd.datetimebox('getText');
    		 var varify = true;
    		 if(!time1=="" && !time2==""){
    			 var d1 = $.fn.datebox.defaults.parser(time1); 
    			 if(time1.split(" ").length==2){
    				 d1 = DateUtil.parserTime(time1); 
    			 }
        		 var d2 = $.fn.datebox.defaults.parser(time2); 
        		 if(time2.split(" ").length==2){
    				 d2 = DateUtil.parserTime(time2); 
    			 }
        		 varify= d2>=d1;
        		 if(!varify){
        			 $(time[1]).datebox('setValue', '').datebox('showPanel');
        		 } 
    		 }
    		 return varify; 
    	 }, 
    	 message: '结束时间不能早于开始时间！' 
	},
	/**
	 * 适用于新增和查询
	 */
	checkDateBetween: { 
		validator: function(value, param){ 
			var times = param.toString().split(",");
			var $timeStart = times[0]==''?null:$(times[0]);
			var $time = times[1]==''?null:$(times[1]);
			var $timeEnd = times[2]==''?null:$(times[2]);
			//判断下本身
			if($time.is('.easyui-datetimebox') && $time.datetimebox('getText').split(" ").length < 2){
				return true;
			}
			if($timeStart!=null && !$timeStart.is('.textbox-f'))
				$.parser.parse($timeStart.parent());  
			if($time!=null && !$time.is('.textbox-f'))
				$.parser.parse($time.parent());  
			if($timeEnd!=null && !$timeEnd.is('.textbox-f'))
				$.parser.parse($timeEnd.parent());  
			var time1 = $timeStart==null?'':$timeStart.datetimebox('getText'); 
			var time = $time==null?'':$time.datetimebox('getText');
			var time2 = $timeEnd==null?'':$timeEnd.datetimebox('getText');
			var varify = true;
			if(!time=="" && (!time1=="" || !time2=="")){
				var d1 = $.fn.datebox.defaults.parser(time1); 
				if(time1.split(" ").length==2){
					d1 = DateUtil.parserTime(time1); 
				}
				var d2 = $.fn.datebox.defaults.parser(time2); 
				if(time2.split(" ").length==2){
					d2 = DateUtil.parserTime(time2); 
				}
				var d = $.fn.datebox.defaults.parser(time); 
				if(time.split(" ").length==2){
					d = DateUtil.parserTime(time); 
				}
				if(!time1==""){
				if(d<=d1){
					$(times[1]).datebox('setValue', '').datebox('showPanel');
					return false;
				}
				}
				if(!time2==""){
					if(d>=d2){
						$(times[2]).datebox('setValue', '').datebox('showPanel');
						return true;
					}
				}
			}
			return true; 
		}, 
		message: '结束时间不能早于开始时间！' 
	},
	
	/**
	 * 适用于修改 当修改操作时 如果日期控件有值 则$(time[0]).datetimebox('getText')报错
	 * 股改为$(time[0]).val()
	 */
	checkDateWithValue: { 
   	 validator: function(value, param){ 
   		 var time = param.toString().split(",");
   		 var time1 = $(time[0]).val(); 
   		 var time2 = $(time[1]).val();
   		 var varify = true;
   		 if(!time1=="" && !time2==""){
   			 var d1 = $.fn.datebox.defaults.parser(time1); 
       		 var d2 = $.fn.datebox.defaults.parser(time2); 
       		 varify= d2>=d1;
       		 if(!varify){
       			 $(time[1]).datebox('setValue', '').datebox('showPanel');
       		 } 
   		 }
   		 return varify; 
   	 }, 
   	 message: '结束时间不能早于开始时间！' 
	},
	
	/**
	 * IP地址验证
	 * author：masn
	 */
    ip: {
          validator: function (value) {
        	  var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/; 
               return ip.test(value);
           },
           message: 'IP地址格式不正确'
       },
}); 