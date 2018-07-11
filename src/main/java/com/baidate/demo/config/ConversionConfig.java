package com.baidate.demo.config;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
/** 
 * @author XiaoTian
 */

@SuppressWarnings("unused")
public class ConversionConfig{
	/**
	 * 被转换类型
	 */
	private TypeDescriptor sourceTypeDesc;
	/**
	 * 转换类型
	 */
	private TypeDescriptor typeDescriptor;
	
	private ConversionService serivce = null;
	
	public ConversionConfig() {}
	/**
	 * @param sourceTypeDesc 被转换类型
	 * @param typeDescriptor 转换类型
	 */
	public ConversionConfig(Class<?> sourceTypeDesc,Class<?> typeDescriptor) {
		this.sourceTypeDesc = TypeDescriptor.valueOf(sourceTypeDesc);
		this.typeDescriptor = TypeDescriptor.valueOf(typeDescriptor);
	}

	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		// TODO Auto-generated method stub
		serivce = DefaultConversionService.getSharedInstance();
		Object object = serivce.convert(source,sourceType,targetType);
		return object;
	}
	
	public Object convert(Object source) {
		// TODO Auto-generated method stub
		serivce = DefaultConversionService.getSharedInstance();
		Object object = serivce.convert(source,this.sourceTypeDesc,this.typeDescriptor);
		return object;
	}
	
	public ConversionService getSerivce() {
		return serivce;
	}
	
	public void setSerivce(ConversionService serivce) {
		this.serivce = serivce;
	}
	
	
}
