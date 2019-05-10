package com.tw.consumer.redis;

public enum RedisOperation {
	/**
	 * SET + EXPIRE
	 */
	setex,
	/**
	 * set
	 */
	set,
	/**
	 * SET if Not eXists
	 */
	setnx,
	/**
	 * Add the specified member to the set value stored at key. 
	 */
	sadd
}
