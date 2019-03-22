package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyUserService myUserService;
	
	/**
	 * 忽略静态资源
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**","/css/**","/images/**");
	}
	/**
	 * 拦截哪些请求。以及请求该怎么处理
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			//项目访问主路径不验证
			.antMatchers("/").permitAll()
			//其他请求全部需要经过验证
			.anyRequest().authenticated()
			.and()
			//支持注销允许访问
			.logout().permitAll()
			.and()
			//支持表单登录
			.formLogin();
		//关闭默认的csrf验证	
		http.csrf().disable();
	}
	/**
	 * 登录验证
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//security提供的基于内存的验证
		
		auth.inMemoryAuthentication()
			.passwordEncoder(new BCryptPasswordEncoder())
			.withUser("admin")
			.password(new BCryptPasswordEncoder()
					.encode("123456"))
			.roles("ADMIN");
		
		auth.inMemoryAuthentication()
			.passwordEncoder(new BCryptPasswordEncoder())
			.withUser("demo")
			.password(new BCryptPasswordEncoder()
					.encode("demo"))
			.roles("USER");

		
		
//		auth.userDetailsService(myUserService).passwordEncoder(new MyPasswordEncoder());
//		
//		//使用默认的数据库验证
//		auth.jdbcAuthentication()
//			.usersByUsernameQuery("")
//			//根据权限去查询
//			.authoritiesByUsernameQuery("")
//			//密码认证
//			.passwordEncoder(new MyPasswordEncoder());
		
	}
	
}
