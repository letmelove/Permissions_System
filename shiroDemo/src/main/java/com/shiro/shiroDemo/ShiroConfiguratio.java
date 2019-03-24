package com.shiro.shiroDemo;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfiguratio {


    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager manager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);

        //登录的url
        bean.setLoginUrl("login");
        //登录成功的url
        bean.setSuccessUrl("/index");
       //没有权限的时候跳转的url
        bean.setUnauthorizedUrl("/unauth");

        //配置请求该如何拦截
        //key是一个正则表达式，表达式代表访问的请求value代表使用什么样的拦截器
        LinkedHashMap<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        //访问index必须是要登录
        filterChainDefinitionMap.put("/index","authc");
        //login不需要任何校验
        filterChainDefinitionMap.put("/login","anon");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return bean;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        return manager;
    }

    @Bean("authRealm")
    public AuthRealm authRealm(@Qualifier("credentialMatcher")CredentialMatcher matcher){
        AuthRealm authRealm = new AuthRealm();
        //给出自己的密码比较器
        authRealm.setCredentialsMatcher(matcher);
        return  authRealm;
    }

    /**
     * 拿到校验规则
     * @return
     */
    @Bean("credentialMatcher")
    public  CredentialMatcher credentialMatcher(){
        return new CredentialMatcher();
    }


}
