package com.baidate.demo.result;

/**
 * 
 * @author XiaoTian
 * @date  2018-6-20 16点44分
 */
public class CodeMsg extends ResultError {
	
	//代码
	private int code;
	//说明
	private String msg;
	//数据
	private Object data;
	
	//通用的错误码
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
	public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
	
	/**
	 * 
	 */
	private CodeMsg( ) { super(); }
	/**		
	 * 
	 * @param code
	 * @param msg
	 */
	private CodeMsg( int code,String msg ) {
		this.code = code;
		this.msg = msg;
		this.data = "null";
	}
	
	public int getCode() {
		return code;
	}
	/**
	 * @return String msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * 
	 * @param args
	 * @return
	 */
	public CodeMsg fillArgs(Object... args) {
		int code = this.code;
		String message = String.format(this.msg, args);
		return new CodeMsg(code, message);
	}
	/**
	 * @return Object data
	 */
	public Object getData() {
		return data;
	}
	/**
	 * toString() 输出
	 */
	@Override
	public String toString() {
		return "CodeMsg [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
		
}
