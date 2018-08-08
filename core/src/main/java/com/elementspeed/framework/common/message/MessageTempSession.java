package com.elementspeed.framework.common.message;

import javax.servlet.http.HttpSession;

import com.elementspeed.framework.common.util.SpringContextUtil;

/**
 * 临时存储机制
 * @author sfg0656
 *
 */
public class MessageTempSession implements MessageTemp {
	
	private HttpSession session = SpringContextUtil.getHttpSession();

	@Override
	public Object get(String key) {
		return session.getAttribute(key);
	}

	@Override
	public void put(String key, Object value) {
		session.setAttribute(key, value);
	}

	@Override
	public void remove(String key) {
		session.removeAttribute(key);
	}

	@Override
	public void removeAll() {
		session.invalidate();
	}
	
}
