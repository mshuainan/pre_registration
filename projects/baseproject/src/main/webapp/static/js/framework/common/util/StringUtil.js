StringUtil = {
	/**
	 * 将string格式的数据转换成json
	 * @param {} str 字符串
	 * @return {} json对象
	 */
	stringToJSON : function(str){
		return JSON.parse(str);
	},
	
	jsonToString : function(jsonObj) {
		return JSON.stringify(jsonObj);
	}
};

String.prototype.startWith = function(str) {
	var reg = new RegExp("^" + str);
	return reg.test(this);
}

String.prototype.endWith = function(str) {
	var reg = new RegExp(str + "$");
	return reg.test(this);
}

String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g, "");
}