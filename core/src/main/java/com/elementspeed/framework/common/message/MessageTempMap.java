package com.elementspeed.framework.common.message;

import java.util.HashMap;
import java.util.Map;

public class MessageTempMap implements MessageTemp {

	private static Map<String, Object> map = new HashMap<String, Object>();
	
	@Override
	public Object get(String key) {
		return map.get(key);
	}

	@Override
	public void put(String key, Object value) {
		map.put(key, value);
	}

	@Override
	public void remove(String key) {
		if(map.containsKey(key)) {
			map.remove(key);
		}
	}

	@Override
	public void removeAll() {
		map.clear();
	}

}
