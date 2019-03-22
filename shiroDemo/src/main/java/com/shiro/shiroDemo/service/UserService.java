package com.shiro.shiroDemo.service;



import com.shiro.shiroDemo.model.User;

public interface UserService {
	
	User findByUsername(String username);
}
