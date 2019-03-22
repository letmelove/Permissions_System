package com.shiro.shiroDemo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shiro.shiroDemo.mapper.UserMapper;
import com.shiro.shiroDemo.model.User;
import com.shiro.shiroDemo.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserMapper userMapper;
	
	@Override
	public User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}

}
