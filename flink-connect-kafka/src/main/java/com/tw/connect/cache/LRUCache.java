package com.tw.connect.cache;

import java.util.concurrent.TimeUnit;

import org.apache.flink.calcite.shaded.com.google.common.cache.Cache;
import org.apache.flink.calcite.shaded.com.google.common.cache.CacheBuilder;


public class LRUCache{

	protected Cache<String, String> cache;
	
	public LRUCache(){
		cache = CacheBuilder.newBuilder()
                .maximumSize(Integer.MAX_VALUE)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build();
	}
	
	    public String getFromCache(String key) {
	        if(cache == null){
	            return null;
	        }
	        return cache.getIfPresent(key);
	    }

	    public void putCache(String key, String value) {
	        if(cache == null){
	            return;
	        }
	        cache.put(key, value);
	    }
}
