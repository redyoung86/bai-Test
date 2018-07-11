package com.baidate.demo.result;
/**
 * 
 * @author XiaoTian
 * @date   2018-6-20 16点44分
 */
@SuppressWarnings("unused")
public abstract class ResultError {
	
	//代码
	private int code;
	//说明
	private String msg;
	//数据
	private Object data;
	
	/**
	 * 无参构造器
	 */
	public ResultError() {}
	/**
	 * 
	 * @param code
	 * @param msg
	 */
	public ResultError(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	/**
	 * 
	 * @param code
	 * @param msg
	 * @param data
	 */
	public ResultError(int code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	/**
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}
	/**
	 * 
	 * @param code
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * 
	 * @return
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * 
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * 
	 * @return
	 */
	public Object getData() {
		return data;
	}
	/**
	 * 
	 * @param data
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
}
