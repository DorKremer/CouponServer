package com.dor.coupons.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class CacheController {

	private Map<String, Object> dataMap;

	public CacheController() {
		this.dataMap = new ConcurrentHashMap<>();
	}

	public void put(String key, Object value) {
		this.dataMap.put(key, value);
	}

	public Object get(String key) {
		return this.dataMap.get(key);
	}
	
	public void remove(String key) {
		this.dataMap.remove(key);
	}
	
	public void findKey(long id) {
		
	}

}
