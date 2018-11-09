package com.store.dev.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class SimpleUserToken implements AuthenticationToken {
    private String username;
    private String password;

    public SimpleUserToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return password;
    }
}
