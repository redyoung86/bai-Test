package com.baidate.demo.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.baidate.demo.redis.RedisCacheable;
import com.baidate.demo.redis.RedisService;
/**
 * @author XiaoTian
 * @date 2018年7月9号6点02分
 */
@SuppressWarnings({"unused","null"})
public class RedisRunConfig extends Config {

	private Method method = null;
	
	private RedisRunConfig() {}
	
	private RedisRunConfig(Method method) {
		this.method = method;
	}
	
	public RedisPortValue annotations(Method method) {
		// TODO Auto-generated method stub
		RedisCacheable redisCacheable = method.getAnnotation(RedisCacheable.class);
		RedisPortValue redisPortValue = new RedisPortValue();
		redisPortValue.setRequired(redisCacheable == null ? false : true);
		if (!redisPortValue.isRequired()) {
			return redisPortValue;
		}
		redisPortValue.setKey(redisCacheable.key());
		redisPortValue.setSeconds(redisCacheable.seconds());
		redisPortValue.setValue(redisCacheable.value());
		return redisPortValue;
	}
	/**
	 * @param method
	 * @return
	 */
	public static RedisPortValue annotation(RedisRunConfig redisRunConfig) {
		return redisRunConfig.annotations(redisRunConfig.getMethod());
	}
	/**
	 * @param method
	 * @param request
	 * @return Object
	 * {ClassConfig  className:全类名,method:方法,args:参数}
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	private Object mannerOfExecution(Method method,HttpServletRequest request,RedisService redisService) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
 		Object object = null;
 		RedisPortValue redisPortValue = RedisRunConfig.annotation(new RedisRunConfig(method));
		if(redisPortValue.isRequired()) {
			String key = redisPortValue.getKey();
			String value = requestModelMap(request,key);
			String methodName = getkey(null == redisPortValue.getValue() 
					|| redisPortValue.getValue().length() == 0 ? method.getName() 
							: redisPortValue.getValue(),key,value);
			//获取缓存
			String redisValue = redisService.get(methodName);
			if(null == redisValue) {
				String className = method.getDeclaringClass().getName();
				Object []args = null;
				ClassConfig classConfig = new ClassConfig();
				classConfig.setClassName(className);
				classConfig.setMethod(method);
				classConfig.setArgs(args);
				object = Config.getObject(classConfig,request);
				//设置缓存
				redisService.set(methodName, object, redisPortValue.getSeconds());
			}else {
				object = redisValue;
			}	
		}
		return object;
	}
	/**
	 * @param method
	 * @param request
	 * @return Object
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static Object mannerOfExecutions(Method method,HttpServletRequest request,RedisService redisService) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return new RedisRunConfig().mannerOfExecution(method,request,redisService);
	}
	/**
	 * @return Method
	 */
	public Method getMethod() {
		return method;
	}
	
	private String getkey(String str1,String ...str2) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(str1);
		for (String string : str2) {
			if (null != string && string.length() > 0) {
				stringBuffer.append("_").append(string);
			} 
		}	
		return stringBuffer.toString();
	}
	
	@SuppressWarnings("rawtypes")
	private String requestModelMap(HttpServletRequest request,String str1) {
		Enumeration em = request.getParameterNames();
		String value = null;
		while (em.hasMoreElements()) {
		    String key = (String) em.nextElement();  
		    if (key.equals(str1)) {
		    	value = request.getParameter(key);
		    	break;
			}
		}
		return value;
	}
}
