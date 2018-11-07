package com.store.dev.controller;

import com.store.dev.repository.dao.UserEntityRepository;
import com.store.dev.repository.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserInfoController{

    @Resource
    private UserEntityRepository userEntityRepository;

    @RequestMapping("/list")
    public List<UserEntity> test() {
        return userEntityRepository.findAll();
    }

}
