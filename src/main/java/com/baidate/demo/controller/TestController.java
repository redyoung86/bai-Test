package com.baidate.demo.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.baidate.demo.domain.Test;
import com.baidate.demo.redis.RedisCacheable;
import com.baidate.demo.redis.RedisService;
import com.baidate.demo.result.CodeMsg;
import com.baidate.demo.result.Result;
import com.baidate.demo.result.ResultError;


@RestController
public class TestController {

	@Autowired
	private RedisService redisService;
	
	@RedisCacheable(value = "xulinglin",key="age",seconds=20)
	@RequestMapping(value= "/index1",method = RequestMethod.GET)
	public Result<Test> test1(@RequestParam(value = "age")Integer age,String name,@RequestParam("d")Float d){
		Test test = new Test("小天", age, new Date());
		return Result.success(test);	
	}
	
	@RedisCacheable
	@RequestMapping(value= "/index4")
	public Result<Test> test1(@RequestParam("id")int id){
		Test test = new Test("小林", id, new Date());
//		System.out.println("test1 out id : "+id+" sid : "+sid+" bool : "+ bool+" dob : "+dob);
		return Result.success(test);	
	}
	
	@RequestMapping(value= "/index2")
	public Result<ResultError> test2(){
		return Result.error(CodeMsg.BIND_ERROR);
	}
	
	@RequestMapping (value= "/set")
	public Result<String> set(@RequestParam("id")String id){
		if(StringUtils.isEmpty(id)) {
			return Result.success("空");
		}
		redisService.set(id, id);
		String str = redisService.get(id, String.class);
		return Result.success(str);
	}
	
	@RequestMapping (value= "/set1")
	public Result<String> set1(@RequestParam("id")String id){
		if(StringUtils.isEmpty(id)) {
			return Result.success("空");
		}
		redisService.set(id, id,30);
		String str = redisService.get(id, String.class);
		return Result.success(str);
	}
	
	@RequestMapping (value= "/get")
	public Result<String> get(@RequestParam("id")String id){
		if(StringUtils.isEmpty(id)) {
			return Result.success("空");
		}
		String str = redisService.get(id, String.class);
		return Result.success(str);
	}
}
