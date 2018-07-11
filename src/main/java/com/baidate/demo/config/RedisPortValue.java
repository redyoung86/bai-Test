package com.baidate.demo.config;

public class RedisPortValue {
	/**
	 * value值
	 */
	private String value;
	/**
	 * key值
	 */
	private String key;
	/**
	 * 时间
	 */
	private int seconds;
	/**
	 * 是否存在
	 */
	private boolean required;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}
}
