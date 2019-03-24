package com.shiro.shiroDemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.shiro.shiroDemo.mapper"})
public class ShiroDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShiroDemoApplication.class, args);
	}

}
