package com.justbon.lps.core;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @author xiesc
 * @date 2020年8月3日
 * @version 1.0.0
 * @Description: TODO
 */
public class LRUCache<T> {

	protected Cache<String, T> cache;

	public LRUCache() {
		cache = CacheBuilder.newBuilder().maximumSize(10000)
				.expireAfterWrite(24, TimeUnit.HOURS).build();
	}

	public T getFromCache(String key) {
		if (cache == null) {
			return null;
		}
		return cache.getIfPresent(key);
	}

	public void putCache(String key, T value) {
		if (cache == null) {
			return;
		}
		cache.put(key, value);
	}
}
