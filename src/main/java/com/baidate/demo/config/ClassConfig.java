package com.baidate.demo.config;

import java.lang.reflect.Method;

public class ClassConfig {
	/**
	 * 全类名 
	 */
	private String className;
	/**
	 * 方法
	 */
	private Method method;
	/**
	 * 参数
	 */
	private Object[] args;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	
}
