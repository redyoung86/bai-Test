package com.baidate.demo.config;

import java.lang.reflect.Type;

import org.springframework.web.bind.annotation.ValueConstants;

import com.alibaba.druid.util.StringUtils;

/**
 * @author XiaoTian
 */
public class ParameterConfig {
	/**
	 * 参数序号
	 */
	private int no;
	/**
	 * 参数类型
	 */
	private Class<?> ParameterTypes;
	/**
	 * 参数名称
	 */
	private String name;
	/**
	 * 参数 @RequestParam 注解 Value
	 */
	private String requestParamValue;
	/**
	 * @RequestParam 注解 类型
	 */
	private Type parameterizedType;
	/**
	 * 参数是否为空
	 */
	private boolean required;
	/**
	 * 默认值
	 */
	private String defaultValue;
	
	public String getDefaultValue() {
		return StringUtils.isEmpty(defaultValue)?null:defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Class<?> getParameterTypes() {
		return ParameterTypes instanceof Class ? ParameterTypes : null;
	}

	public void setParameterTypes(Class<?> parameterTypes) {
		ParameterTypes = parameterTypes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRequestParamValue() {
		return requestParamValue;
	}

	public void setRequestParamValue(String requestParamValue) {
		this.requestParamValue = requestParamValue;
	}

	public Type getParameterizedType() {
		return parameterizedType;
	}

	public void setParameterizedType(Type parameterizedType) {
		this.parameterizedType = parameterizedType;
	}

}
