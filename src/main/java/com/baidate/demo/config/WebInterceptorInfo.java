package com.baidate.demo.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidate.demo.redis.RedisCacheable;
import com.baidate.demo.redis.RedisService;
import com.baidate.demo.util.DESHelper;
import com.fasterxml.jackson.core.JsonParser;

/**
 * @author XiaoTian
 * @date 2018年7月9号6点02分
 */
@SuppressWarnings("unused")
@Component
public class WebInterceptorInfo {
	/**
	 * 输出
	 */
	private PrintWriter out = null;
	@Resource
	private RedisService redisService;

	/**
	 * 方法进行校验
	 * 
	 * @param handler
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean runMethod(Object handler, HttpServletRequest request, HttpServletResponse response) {
		boolean bool = false;
		// 将handler强转为HandlerMethod,handler就是HandlerMethod
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 从方法处理器中获取出要调用的方法
		Method method = handlerMethod.getMethod();
		Object object = null;
		try {
			object = RedisRunConfig.mannerOfExecutions(method, request, redisService);
			bool = isObject(response, object);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			bool = true;
			e.printStackTrace();
		}
		return bool;
	}

	/**
	 * 写出
	 * 
	 * @param response
	 * @param object
	 */
	private void IoJsonOut(HttpServletResponse response, Object object) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {
			out = response.getWriter();
			String value = object.toString();
			out.append(value);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 输出为Json格式
	 * 
	 * @param response
	 * @param object
	 * @return
	 */
	private boolean isObject(HttpServletResponse response, Object object) {
		if (null == object || object.equals("")) {
			return true;
		} else {
			if (object instanceof String) {
				if (isJson(object)) {
					IoJsonOut(response, JSONArray.toJSONString(object));
				} else {
					IoJsonOut(response, object);
				}
			} else {
				IoJsonOut(response, JSONArray.toJSONString(object));
			}
			return false;
		}
	}

	/**
	 * 判断是否为Json格式
	 * 
	 * @param content
	 * @return
	 */
	private boolean isJson(Object content) {
		try {
			JSONObject jsonStr = JSONObject.parseObject(JSONArray.toJSONString(content));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
