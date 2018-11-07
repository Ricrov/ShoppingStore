package com.store.dev.service;

import com.store.dev.repository.commons.ResultWrapper;
import com.store.dev.repository.entity.UserEntity;

import java.util.List;

public interface UserInfoService {
    ResultWrapper findUserByUsername(UserEntity user);
    ResultWrapper addNewUser(UserEntity user);

}
