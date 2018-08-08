//表单验证规则
$.extend($.fn.validatebox.defaults.rules, {
	//最大长度
    maxLength: {    
        validator: function(value, param) {    
            return value.length <= param[0];    
        },    
        message: '请输入不超过  {0} 个字符'   
    },
    //姓名
    nameValidator : {    
        validator: function(value, param) {    
        	var re = /^[\u4e00-\u9fa5a-z]+$/gi;
        	return re.test(value);
        },    
        message: '姓名不能有特殊字符'   
    },
    //数字
    number: {  
        validator: function (value) {  
            var reg =/^[0-9]*$/;  
            return reg.test(value);  
        },  
        message: '请输入数字'  
    },
    //密码校验
    passwordValidator: {
    	  validator: function (value) {      		
    		if(value.length < 6 || value.length > 30){       				
    				return false;
    			}
    		if (!/[a-zA-Z]/.test(value)){    	
    			return false;
    		}    
    		if (!/[0-9]/.test(value)){    	
    			return false;
    		} 
                 return true;  
          },  
          message: '密码长度必须在6-30之间，包含字母及数字，且前后不能为空格'
    },
    //移动手机号码验证  
    mobile: {
        validator: function (value) {  
            var reg = /^1[2|3|4|5|6|7|8|9]\d{9}$/;  
            return reg.test(value);  
        },  
        message: '请输入正确的手机号码'  
    }, 
    eqPassword : { 
    	validator : function(value, param) {  
    	return value == $(param[0]).val();   
    	},   
    	message : '密码不一致！'   
    },
    //ajax验证姓名是否存在
    ajaxValidatorName:{
    	validator: function(value) {
    		url = CtxVar.path + '/demo/load/table/demo2/ajaxValidateName';
    		var result = $.ajax({
				    		url : url,
				    		type : "post",
				    		async : false,
				    		dataType : "json",
				    		data : {"name":value}
			    		}).responseText;
    		return result == 'true';
    	},
    	message : '输入的信息已存在!' 
    },
    taxCodeValidator : {
    	validator : function (value, param) {
    		//var res = /^[0-9]{14}[A-Z0-9]$/ ;
    		if(value.length == 15) 
    			return value.substring(6) == ($(param[0]).val() + $(param[1]).val());
    		return false;
    	},
    	message : '税务登记证格式错误'
    }
});  