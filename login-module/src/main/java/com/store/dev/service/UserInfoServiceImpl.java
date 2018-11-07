package com.store.dev.service;

import com.store.dev.repository.commons.ResultWrapper;
import com.store.dev.repository.dao.UserEntityRepository;
import com.store.dev.repository.entity.UserEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserInfoServiceImpl implements UserInfoService{
    @Resource
    private UserEntityRepository userEntityRepository;

    @Override
    public ResultWrapper findUserByUsername(UserEntity user) {
        UserEntity userInfo = userEntityRepository.findByUsername(user.getUsername());
        if (userInfo != null && userInfo.getPassword().equals(user.getPassword())) {
            return ResultWrapper.success(userInfo);
        }
        return ResultWrapper.error(401, "账号/密码错误");
    }

    @Override
    public ResultWrapper addNewUser(UserEntity user) {
        Date created = new Date();
        user.setCreated(created);
        user.setUpdated(created);
        // 账户重复时, 使用统一异常处理
        UserEntity save = userEntityRepository.save(user);
        if (save != null) {
            return ResultWrapper.success(save);
        }
        return ResultWrapper.error(400, "注册失败");
    }
}
