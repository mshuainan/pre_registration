DateUtil = {
	/**
	 * convert value to yyyy-mm-dd
	 * @param value
	 */
	format : function(value) {
		return !!value ? $.fn.datebox.defaults.formatter(new Date(value)) : "";
	},

	/**
	 * convert value to yyyy-MM-dd hh:mm:ss
	 * @param value
	 */
	format_HMS : function(value) {
		var fmt = 'yyyy-MM-dd hh:mm:ss';
		var date = new Date(value);
		var o = {
			"M+" : date.getMonth() + 1, //月份         
			"d+" : date.getDate(), //日         
			"h+" : date.getHours() % 12 == 0 ? 12 : date.getHours() % 12, //小时         
			"H+" : date.getHours(), //小时         
			"m+" : date.getMinutes(), //分         
			"s+" : date.getSeconds(), //秒         
			"q+" : Math.floor((date.getMonth() + 3) / 3), //季度         
			"S" : date.getMilliseconds()
		};
		var week = {
			"0" : "/u65e5",
			"1" : "/u4e00",
			"2" : "/u4e8c",
			"3" : "/u4e09",
			"4" : "/u56db",
			"5" : "/u4e94",
			"6" : "/u516d"
		};
		if (/(y+)/.test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		}
		if (/(E+)/.test(fmt)) {
			fmt = fmt
					.replace(
							RegExp.$1,
							((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f"
									: "/u5468")
									: "")
									+ week[date.getDay() + ""]);
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(fmt)) {
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
			}
		}
		return fmt;
	},
	parserTime :function (s){
			if($.trim(s)==""){
			return new Date();
			}
			var dt=s.split(" ");
			var d=$.fn.datebox.defaults.parser(dt[0]);
			if(dt.length<2){
			return d;
			}
			
			var _a74=":";
			var tt=dt[1].split(_a74);
			var hour=parseInt(tt[0],10)||0;
			var _a75=parseInt(tt[1],10)||0;
			var _a76=parseInt(tt[2],10)||0;
			return new Date(d.getFullYear(),d.getMonth(),d.getDate(),hour,_a75,_a76);
	},
	
	/**
	 * 获取两个日期之间天数 前包后不包
	 * @param startDate
	 * @param endDate
	 * startDate和endDate是2006-12-18格式  
	 * @returns
	 */
	dateDiff : function(startDate, endDate){    
       var  aDate,  oDate1,  oDate2,  iDays  
       aDate  =  startDate.split("-")  
       oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2006格式  
       aDate  =  endDate.split("-")  
       oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  
       iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数  
       return  iDays;  
   },
   
   /**
	 * 获取两个日期之间所有日期 前包后不包 返回格式："2016-04-02"
	 * @param startDate
	 * @param endDate
	 * startDate和endDate是2006-12-18格式  
	 * @returns
	 */
   getDateBetween : function(startDate, endDate) {
	   
	   var getDate = function(str) {
			var tempDate = new Date();
			var list = str.split("-");
			tempDate.setFullYear(list[0]);
			tempDate.setMonth(list[1] - 1);
			tempDate.setDate(list[2]);
			return tempDate;
		}
		var date1 = getDate(startDate);
		var date2 = getDate(endDate);
		date1.setDate(date1.getDate());
		var content = "";
		while (!(date1.getFullYear() == date2.getFullYear()
				&& date1.getMonth() == date2.getMonth() && date1.getDate() == date2
				.getDate())) {
			var date = date1.getFullYear();
			if(date1.getMonth() + 1 < 10 ) {
				date += "-0" + (date1.getMonth() + 1)
			} else {
				date += "-" + (date1.getMonth() + 1)
			}
			if(date1.getDate() < 10) {
				date += "-0" + date1.getDate();
			} else {
				date += "-" + date1.getDate();
			}
			content += date + ",";
			date1.setDate(date1.getDate() + 1);
		}
		return !!content ? content.substring(0, content.length - 1) : "";
   	},
   	
   	/**
   	 * 获取指定日期前或后几天的日期
   	 * @param date 指定日期
   	 * @param day 天数，正数为指定日期后几天 负数表示指定日期前几天
   	 * @param format 格式化 可不填 默认yyyy-MM-dd
   	 * 			取值示例 yyyy/MM/dd \ yy
   	 * @returns {String}
   	 */
   	getNearDateStr : function(date, day, format) 
   	{ 
   		format = format || 'yyyy-MM-dd';
   		date.setDate(date.getDate() + day);//获取day天后的日期 
   		var y = date.getFullYear(); 
   		var m = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);//获取当前月份的日期，不足10补0
   		var d = date.getDate() < 10 ? "0" + date.getDate() : date.getDate(); //获取当前几号，不足10补0
   		var format = format.replace(/yyyy/, y);
   		format = format.replace(/MM/, m);
   		format = format.replace(/dd/, d);
   		return format;
   	}
}