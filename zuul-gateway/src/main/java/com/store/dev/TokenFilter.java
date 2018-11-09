package com.store.dev;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.store.dev.repository.entity.UserEntity;
import com.store.dev.shiro.JwtToken;
import com.store.dev.shiro.SimpleUserToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * 身份检验, 分发路由
 * CREATED BY X.
 */
public class TokenFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        System.out.println("--->>> TokenFilter {},{}" + request.getMethod() + request.getRequestURL().toString());
        try {
            if (request.getServletPath().startsWith("/login-module/user/login")) {
                try {
                    // 通过字符流获得登录用户账号密码
                    BufferedReader reader = request.getReader();
                    ObjectMapper om = new ObjectMapper();
                    UserEntity user = om.readValue(reader, UserEntity.class);
                    SecurityUtils.getSubject().login(new SimpleUserToken(user.getUsername(), user.getPassword()));
                    String newToken = (String) SecurityUtils.getSubject().getPrincipal();
                    response.addHeader("token", newToken);
                    // 对该请求进行路由
                    ctx.setSendZuulResponse(true);
                    ctx.setResponseStatusCode(200);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (request.getServletPath().startsWith("/login-module/user/register")) {
                // 用户注册时, 直接放行, 注册成功后(此过程不分配token)需要返回登录页面
                ctx.setSendZuulResponse(true);
                ctx.setResponseStatusCode(200);
            } else {
                String token = request.getHeader("token");
                JwtToken jwtToken = new JwtToken(token);
                SecurityUtils.getSubject().login(jwtToken);
                String newToken = (String) SecurityUtils.getSubject().getPrincipal();
                response.addHeader("token", newToken);
                // 对该请求进行路由
                ctx.setSendZuulResponse(true);
                ctx.setResponseStatusCode(200);
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            // 对该请求不进行路由
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("token is bad");
        }
        return null;
    }
}
