package com.baidate.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@SuppressWarnings("unused")
public class RedisService {
	
	@Autowired
	JedisPool jedisPool;
	
	/**
	 * 获取当个对象
	 * */
	public <T> T get( String key,  Class<T> clazz) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			 //生成真正的key
			 String  str = jedis.get(key);
			 T t = null;
			 if (null == str) {
				t = stringToBean(str, clazz);
			 }else {
				 t = (T) str;
			 }
			 return t;
		 }finally {
			  returnToPool(jedis);
		 }
	}

	public String get(String key) {
		Jedis jedis = null;
		try {
			 jedis =  jedisPool.getResource();
			 //生成真正的key
			 String  str = jedis.get(key);
			 return str;
		 }finally {
			  returnToPool(jedis);
		 }
	}
	/**
	 * 设置对象
	 * */
	public <T> boolean set(String key, T value) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			 String str = beanToString(value);
			 if(str == null || str.length() <= 0) {
				 return false;
			 }
			 jedis.set(key, str);
			 
			 return true;
		 }finally {
			  returnToPool(jedis);
		 }
	}
	
	/**
	 * 设置对象
	 * */
	public <T> boolean set(String key, T value,int seconds) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			 String str = beanToString(value);
			 if(str == null || str.length() <= 0) {
				 return false;
			 }
			 if(seconds <= 0) {
				 jedis.set(key, str);
			 }else {
				 jedis.setex(key, seconds, str);
			 }
			 return true;
		 }finally {
			  returnToPool(jedis);
		 }
	}
	
	public static <T> String beanToString(T value) {
		if(value == null) {
			return null;
		}
		Class<?> clazz = value.getClass();
		if(clazz == int.class || clazz == Integer.class) {
			 return ""+value;
		}else if(clazz == String.class) {
			 return (String)value;
		}else if(clazz == long.class || clazz == Long.class) {
			return ""+value;
		}else if(clazz == double.class || clazz == Double.class){
			return ""+value;
		}else {
			return JSON.toJSONString(value);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T stringToBean(String str, Class<T> clazz) {
		if(str == null || str.length() <= 0 || clazz == null) {
			 return null;
		}
		if(clazz == int.class || clazz == Integer.class) {
			 return (T)Integer.valueOf(str);
		}else if(clazz == String.class) {
			 return (T)str;
		}else if(clazz == long.class || clazz == Long.class) {
			return  (T)Long.valueOf(str);
		}else if(clazz == double.class || clazz == Double.class){
			return  (T)Double.valueOf(str);
		}else{
			return JSON.toJavaObject(JSON.parseObject(str), clazz);
		}
	}
	/**
	 * 是否存在不等于空就关闭
	 * @param jedis
	 */
	private void returnToPool(Jedis jedis) {
		 if(jedis != null) {
			 jedis.close();
		 }
	}
}
