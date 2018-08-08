package com.elementspeed.framework.common.message;

/**
 * 临时存储机制，默认用session实现， 可根据具体业务对方法重写
 * @author sfg0656
 *
 */
public interface MessageTemp {

	/**
	 * 
	 * @param key
	 * @return
	 */
	Object get(String key);
	
	/**
	 * 
	 * @param key
	 * @param vlaue
	 */
	void put(String key, Object vlaue);
	
	/**
	 * 
	 * @param key
	 */
	void remove(String key);
	
	/**
	 * 
	 */
	void removeAll();

}
