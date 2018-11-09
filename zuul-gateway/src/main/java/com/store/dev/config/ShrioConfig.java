package com.store.dev.config;

import com.store.dev.shiro.JwtRealm;
import com.store.dev.shiro.SimpleRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Shrio配置文件
 * CREATED BY X.
 */
@Configuration
public class ShrioConfig {
    @Resource
    private SimpleRealm simpleRealm;
    @Resource
    private JwtRealm jwtRealm;

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(simpleRealm);
        realms.add(jwtRealm);
        securityManager.setRealms(realms);
        securityManager.setSessionManager(new DefaultSessionManager());
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }
}
