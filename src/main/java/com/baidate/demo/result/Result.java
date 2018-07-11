package com.baidate.demo.result;
/**
 * 
 * @author XiaoTian
 *
 * @param <T>
 * @date  2018-6-20 16点44分
 */
@SuppressWarnings("unchecked")
public class Result<T> {
	
	private int code;
	private String msg;
	private T data;
	
	/**
	 *  成功时候的调用
	 * */
	public static <T> Result<T> success(T data){
		if (null == data) {
			data = (T) "null";
		}
		return new Result<T>(data);
	}
	
	/**
	 *  失败时候的调用
	 * */
	public static  <T> Result<T> error(ResultError resultError){
		return new Result<T>(resultError);
	}
	/**
	 * 
	 * @param resultError
	 */
	private Result(ResultError resultError) {
		this.data =  (T) resultError.getData();
		this.code = resultError.getCode();
		this.msg = resultError.getMsg();
	}
	/**
	 * 
	 * @param data
	 */
	private Result(T data) {
		this.code = 200;
		this.msg = "success";
		this.data = data;
	}
	/**
	 * 
	 * @param code
	 * @param msg
	 */
	private Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	/**
	 * 
	 * @param codeMsg
	 */
	private Result(CodeMsg codeMsg) {
		if(codeMsg != null) {
			this.code = codeMsg.getCode();
			this.msg = codeMsg.getMsg();
		}
	}
	/**
	 * 
	 * @return int code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * 
	 * @return String msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * 
	 * @return T data
	 */
	public T getData() {
		return data;
	}
}
