package com.tw.connect.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.flink.calcite.shaded.com.google.common.cache.Cache;
import org.apache.flink.calcite.shaded.com.google.common.cache.CacheBuilder;

public class LRUCache {

	protected Cache<String, Map<String, Object>> cache;

	public LRUCache() {
		cache = CacheBuilder.newBuilder().maximumSize(Integer.MAX_VALUE)
				.expireAfterWrite(24, TimeUnit.HOURS).build();
	}

	public Map<String, Object> getFromCache(String key) {
		if (cache == null) {
			return null;
		}
		return cache.getIfPresent(key);
	}

	public void putCache(String key, Map<String, Object> value) {
		if (cache == null) {
			return;
		}
		cache.put(key, value);
	}
	
}
