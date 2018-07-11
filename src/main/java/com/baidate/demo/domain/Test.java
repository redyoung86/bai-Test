package com.baidate.demo.domain;

import java.util.Date;

public class Test {
	private String name;
	private int age;
	private Date date;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Test [name=" + name + ", age=" + age + ", date=" + date + "]";
	}
	public Test(String name, int age, Date date) {
		super();
		this.name = name;
		this.age = age;
		this.date = date;
	}
	
}
