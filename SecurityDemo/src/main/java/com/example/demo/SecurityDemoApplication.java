package com.example.demo;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
//支持自动配置。像有时引入了spring data jpa 但却没有配置数据源的时候就可以@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableAutoConfiguration
//使用role验证。有此注解@PreAuthorize注解才会生效
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityDemoApplication.class, args);
	}
	@RequestMapping("/")
	public String home() {
		return "hello spring boot";
	}
	
	@RequestMapping("/hello")
	public String hello() {
		return "hello word";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/roleAuth")
	public String role() {
		return "admin auth";
	}
	
	//指定 id必须要小于10,用户名必须为当前用户
	@PreAuthorize("#id<10 or principal.username.equals(#username)")
	//方法调用完后进行权限检查。(这里是检查返回值是否为偶数)
	@PostAuthorize("returnObject%2==0")
	@RequestMapping("/test")
	public Integer test(Integer id, String username) {
		return 0;
	}
	
	@PreFilter("filterObject%2==0")
	@PostFilter("filterObject%4==0")
	@RequestMapping("/test2")
	public List<Integer> test2(List<Integer> idList) {
		return idList;
	}
}
