package com.shiro.shiroDemo;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
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
        bean.setUnauthorizedUrl("/unauthorized");

        //配置请求该如何拦截
        //key是一个正则表达式，表达式代表访问的请求value代表使用什么样的拦截器
        LinkedHashMap<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        //访问index必须是要登录后才能访问的
        filterChainDefinitionMap.put("/index","authc");
        //login不需要任何校验
        filterChainDefinitionMap.put("/login","anon");
        //loginUser不需要身份认证
        filterChainDefinitionMap.put("/loginUser", "anon");
        //admin接口只能指定的角色访问
        filterChainDefinitionMap.put("/admin", "roles[admin]");
        //edit接口只能允许拥有指定的权限的人访问
        filterChainDefinitionMap.put("/edit", "perms[edit]");
        //druid前缀的请求都不拦截
        filterChainDefinitionMap.put("/druid/**", "anon");
        //其他接口只要登录就可以访问
        filterChainDefinitionMap.put("/**", "user");
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
        //把认证相关的东西缓存到内存中
        authRealm.setCacheManager(new MemoryConstrainedCacheManager());
        //给出自己的密码比较器
        authRealm.setCredentialsMatcher(matcher);
        return  authRealm;
    }

    //拿到校验规则
    @Bean("credentialMatcher")
    public  CredentialMatcher credentialMatcher(){
        return new CredentialMatcher();
    }
    
    /**
     * shiro与spring相关联
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager")SecurityManager securityManager) {
    	AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
    	advisor.setSecurityManager(securityManager);
    	return advisor;
    }
    
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    	DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
    	creator.setProxyTargetClass(true);
    	return creator;
    }

}
