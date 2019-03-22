package com.shiro.shiroDemo.mapper;

import org.apache.ibatis.annotations.Param;

import com.shiro.shiroDemo.model.User;

public interface UserMapper {
	
	User findByUsername(@Param("username") String username);
}
