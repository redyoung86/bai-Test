package com.baidate.demo.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyEditorRegistrySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.druid.util.StringUtils;

@SuppressWarnings({"unused","rawtypes"})
public class Config {
	
	protected Config() {}
	
	protected static final Map<String, ParameterConfig> modelMap = new ConcurrentHashMap<String, ParameterConfig>();

	private final ConversionConfig conversionConfig = new ConversionConfig();
	/**
	 * 
	 * @param className
	 *            全类名
	 * @param method
	 *            运行的方法
	 * @param args
	 *            Object任意参数
	 * @return Object
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static Object getObject(ClassConfig classConfig,HttpServletRequest request) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return new Config().invoke(classConfig,request);
	}

	/**
	 * 
	 * @param className
	 *            全类名
	 * @param method
	 *            运行的方法
	 * @param args
	 *            Object任意参数
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	private Object invoke(ClassConfig classConfig,HttpServletRequest request) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// 加载当前类
		Class<?> clazz = Class.forName(classConfig.getClassName());
		//实现类
		Object obj = clazz.newInstance();
		Method callMethod = null;
		callMethod = classConfig.getMethod();
		callMethod.setAccessible(true);
		//获取参数类型
		Class<?>[] clazzs = callMethod.getParameterTypes();
		//获取方法参数和名称
		Parameter[] parameters = callMethod.getParameters();
		//参数进行存储
		setMap(clazzs,parameters,callMethod);
		Object[] args = null;
		if (null != classConfig.getArgs()) {
			args = classConfig.getArgs();
		}else {
			//遍历所有请求的参数
			Map<String, Object> map = new ConcurrentHashMap<String, Object>();
			Enumeration em = request.getParameterNames();
			while (em.hasMoreElements()) {
			    String key = (String) em.nextElement();
			    String value = request.getParameter(key);
			    map.put(key, value);
			}
			args = new Object[callMethod.getParameterTypes().length];
			for (int i = 0; i < args.length; i++) {
				ParameterConfig parameterConfig = modelMap.get(ParameterName(callMethod, i));
				String requestParamValue = parameterConfig.getRequestParamValue();	
				if (!parameterConfig.isRequired()) {
					args[i] = parameterConfig.getDefaultValue();
				}else if (requestParamValue != null) {
					Object value = map.get(requestParamValue);
					args[i] = conversionConfig.convert(value,TypeDescriptor.valueOf(String.class),TypeDescriptor.valueOf(parameterConfig.getParameterTypes()));
				}else {
					args[i] = null;
				}
			}
		}
		obj = (Object) callMethod.invoke(obj, args);
		return obj;
	}
	
	public void setMap(Class<?>[] clazz,Parameter[] parameters,Method method) {
		try {
			if (clazz.length == parameters.length) {
				for (int i = 0; i < clazz.length; i++) {
					ParameterConfig parameterConfig = new ParameterConfig();
					parameterConfig.setNo(i);
					parameterConfig.setParameterTypes(clazz[i]);
					parameterConfig.setName(parameters[i].getName());
					parameterConfig.setParameterizedType(parameters[i].getParameterizedType());
					RequestParam[] requestParam = parameters[i].getDeclaredAnnotationsByType(RequestParam.class);
					for (int j = 0; j < requestParam.length; j++) {
						RequestParam annotation = requestParam[j];
						parameterConfig.setRequestParamValue(annotation.value());
						parameterConfig.setDefaultValue(annotation.defaultValue());
						parameterConfig.setRequired(annotation.required());
					}
					modelMap.put(ParameterName(method, i), parameterConfig);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new IllegalArgumentException();
		}
	}
	/**
	 * key zhi
	 * @param method
	 * @param i
	 * @return
	 */
	private String ParameterName(Method method, int i) {
		return new StringBuffer().append("Method_modelMap_\"").append(method.getName() + "()\"_Parameter_" + i)
				.toString();
	}

}
