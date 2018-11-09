package com.store.dev.shiro;

import com.store.dev.service.UserLoginService;
import com.store.dev.repository.commons.ResultWrapper;
import com.store.dev.repository.entity.UserEntity;
import com.store.dev.token.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

@Component
public class SimpleRealm extends AuthorizingRealm {

    @Resource
    private UserLoginService userLoginService;

    @Override
    public String getName() {
        return "simpleRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof SimpleUserToken;
    }

    // 后授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 目前暂无授权业务
        /*
        Subject subject = SecurityUtils.getSubject();
        // 获取登录成功时储存的主要信息
        Object principal = principals.getPrimaryPrincipal();
        // 实际上是token
        String jwtToken = (String) principal;

        // 从token信息中获取用户id
        Long userId = JwtUtils.getUserId(jwtToken);

        UserInfo user = userInfoService.findById(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (SysRole sysRole : user.getRoleList()) {
            info.addRole(sysRole.getRole());
            for (SysPermission permission : sysRole.getPermissions()) {
                info.addStringPermission(permission.getPermission());
            }
        }
        return info;
        */
        return null;
    }

    // 先验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = (String)token.getCredentials();
        System.out.println(username + " " + password);
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        ResultWrapper wrapper = userLoginService.login(user);
        if (wrapper.getStatus() == 200) {
            HashMap data = (HashMap) wrapper.getData();
            String jwtToken = JwtUtils.newToken(((Integer)data.get("userId")).longValue());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(jwtToken, password, getName());
            return info;
        }
        throw new AuthenticationException("登录失败");
    }
}
