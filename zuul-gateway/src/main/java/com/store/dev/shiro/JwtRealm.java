package com.store.dev.shiro;

import com.store.dev.token.JwtUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.springframework.stereotype.Component;

@Component
public class JwtRealm extends SimpleRealm{
    @Override
    public String getName() {
        return "jwtRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String jwtToken = (String) token.getPrincipal();
        try {
            String newToken = JwtUtils.autoRequire(jwtToken);
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(newToken, newToken, getName());
            return info;
        } catch (Exception e) {
            throw new AuthenticationException("请重新登录");
        }
    }
}
