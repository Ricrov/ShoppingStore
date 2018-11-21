package com.store.dev.service;

import com.store.dev.repository.commons.ResultWrapper;
import com.store.dev.repository.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 跨服务调用
 * CREATED BY X.
 */
@FeignClient(value = "login-module")
public interface UserLoginService {
    @PostMapping("/user/login")
    ResultWrapper login(UserEntity user);
}
