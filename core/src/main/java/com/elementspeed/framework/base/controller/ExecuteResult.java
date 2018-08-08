package com.elementspeed.framework.base.controller;

import java.util.HashMap;
import java.util.Map;


/**
 *  当controller的方法使用 ResponseBody注解时, 应当返回ExecuteResult
 */
public class ExecuteResult {
	//执行结果
	private boolean success = false;
	//提示信息
	private String message = "";
	//resultMap可暂存执行结果, Object应当是普通POJO.
	//Ajax操作可通过resultMap动态修改页面内容
	private Map<String, ?> resultMap;
	//提供操作的快捷方式，一旦调用该方法，resultMap会被修改为<String,Object>且会清除原来set进来的Map
	private Map<String, Object> resultMapObj;
	
	public ExecuteResult(boolean success) {
		this.success = success;
	}
	
	public ExecuteResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public ExecuteResult(boolean success, Map<String, ?> resultMap, String message) {
		this.success = success;
		this.resultMap = resultMap;
		this.message = message;
	}
	
	public ExecuteResult(boolean success, Map<String, ?> resultMap) {
		this.success = success;
		this.resultMap = resultMap;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, ?> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, ?> resultMap) {
		this.resultMap = resultMap;
	}
	
	/**
	 * 调用该方式，将自动设置resultMap为<String,Object>类型，且重新赋值
	 * @param key
	 * @param value
	 */
	public void putResultMap(String key,Object value){
		if(resultMapObj==null){
			this.resultMapObj = new HashMap<String, Object>();
		}
		this.resultMapObj.put(key, value);
		setResultMap(this.resultMapObj);
	}
	
	/**
	 * 设置返回信息
	 * @param success 成功标记
	 * @param message 返回信息
	 */
	public void setResultInfo(boolean success,String message){
		setSuccess(success);
		setMessage(message);
	}
	
}
