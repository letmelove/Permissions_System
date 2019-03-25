package com.shiro.shiroDemo.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shiro.shiroDemo.model.User;

@Controller
public class TestController {
	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	/**
	 * 登录成功跳转的页面
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout() {
		//拿到当前验证主体
		Subject subject = SecurityUtils.getSubject();
		if(null != subject) {
			subject.logout();
		}
		return "login";
	}
	/**
	 * 没有登录时 是不能访问这个接口.
	 * 只有被指定的角色才能访问这个接口
	 * @return
	 */
	@RequestMapping("/admin")
	@ResponseBody
	public String admin() {
		return "admin success";
	}
	
	/**
	 * 拥有指定权限才能访问这个接口
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String edit() {
		return "edit success";
	}
	
	/**
	 * 进行登录验证
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping("/loginUser")
	public String loginUser(@RequestParam("username")String username, @RequestParam("password")String password, HttpSession session) {
		UsernamePasswordToken token =  new UsernamePasswordToken(username,password);
		//拿到shiro的主体
		Subject subject = SecurityUtils.getSubject();
		//shiro认证逻辑
		try {
			//subject提供的登录认证
			subject.login(token);
			//拿到登录用户
			User user = (User)subject.getPrincipal();
			//用户写到session里去
			session.setAttribute("user", user);
			return "index";
		} catch (Exception e) {
			return "login";
		}
	}
	/**
	 *没有对应权限时跳转的页面
	 * @return
	 */
	@RequestMapping("/unauthorized")
	public String unauthorized() {
		return "unauthorized";
	}
}
