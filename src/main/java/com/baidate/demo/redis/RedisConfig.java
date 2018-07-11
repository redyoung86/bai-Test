package com.baidate.demo.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * 
 * @author XiaoTian
 *
 */
@Component
@ConfigurationProperties(prefix="redis")
public class RedisConfig {
	private String host;
	private int port;
	private int timeout;//秒
	private String password;
	private int poolMaxTotal;
	private int poolMaxIdle;
	private int poolMaxWait;//秒
	/**
	 * 
	 * @return String
	 */
	public String getHost() {
		return host;
	}
	/**
	 * 
	 * @param String host
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * 
	 * @return int port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * 
	 * @param int port
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * 
	 * @return int timeout
	 */
	public int getTimeout() {
		return timeout;
	}
	/**
	 * 
	 * @param timeout
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 
	 * @return
	 */
	public int getPoolMaxTotal() {
		return poolMaxTotal;
	}
	/**
	 * 
	 * @param poolMaxTotal
	 */
	public void setPoolMaxTotal(int poolMaxTotal) {
		this.poolMaxTotal = poolMaxTotal;
	}
	/**
	 * 
	 * @return
	 */
	public int getPoolMaxIdle() {
		return poolMaxIdle;
	}
	/**
	 * 
	 * @param poolMaxIdle
	 */
	public void setPoolMaxIdle(int poolMaxIdle) {
		this.poolMaxIdle = poolMaxIdle;
	}
	/**
	 * 
	 * @return
	 */
	public int getPoolMaxWait() {
		return poolMaxWait;
	}
	/**
	 * 
	 * @param poolMaxWait
	 */
	public void setPoolMaxWait(int poolMaxWait) {
		this.poolMaxWait = poolMaxWait;
	}
}
