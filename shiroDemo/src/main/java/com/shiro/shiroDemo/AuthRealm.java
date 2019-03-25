package com.shiro.shiroDemo;

import com.shiro.shiroDemo.model.Permissions;
import com.shiro.shiroDemo.model.Role;
import com.shiro.shiroDemo.model.User;
import com.shiro.shiroDemo.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //授权
    @SuppressWarnings("rawtypes") //去掉警告信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    	User user = new User();
    	//相当于从session里获取用户
    	Iterator it = principalCollection.fromRealm(this.getClass().getName()).iterator();
    	while (it.hasNext()) {
          user = (User) it.next();
        }
    	
        List<String> permissionList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        //拿到当前所有的角色
        Set<Role> roleSet = user.getRole();
        //当角色不为空的时候遍历角色
        if(CollectionUtils.isNotEmpty(roleSet)){
            for(Role role : roleSet){
            	roleNameList.add(role.getRname());
                //每一个角色里都能拿到一个存储permission的set
                Set<Permissions> permissionSet = role.getPermissions();
                //当permissionSet不为空的时候遍历权限列表
                if(CollectionUtils.isNotEmpty(permissionSet)){
                    for(Permissions permission : permissionSet){
                        permissionList.add(permission.getName());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionList);
        info.addRoles(roleNameList);
        return info;
    }
    //认证登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //需要将我们传入的token转为UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //取出username
        String username = usernamePasswordToken.getUsername();
        //去数据库里取出对应的用户
        User user = userService.findByUsername(username);

        return new SimpleAuthenticationInfo(user,user.getPassword(),this.getClass().getName());
    }
}
