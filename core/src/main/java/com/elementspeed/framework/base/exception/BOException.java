package com.elementspeed.framework.base.exception;

/**
 * 业务异常<br>
 * 
 * 业务校验未通过或业务执行失败时, 应当抛出 BOException
 *
 */
public class BOException extends RuntimeException {
	
	private static final long serialVersionUID = -1951299552619851196L;
	
	private String msgCode;			//异常编码
	private String msgContent;		//异常内容
	private Object[] args = null;	//异常参数, 如果异常为 err_cannotconfirm{0},{1} , args对应{0},{1}

	public BOException(String msgContent) {
		this.msgContent = msgContent;
	}

	public BOException(String msgCode, String msgContent, Object... args) {
		this.msgCode = msgCode;
		this.msgContent = msgContent;
		this.args = args;
	}

	@Override
	public String getMessage() {
		return getMsgContent();
	}
	
	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
}
