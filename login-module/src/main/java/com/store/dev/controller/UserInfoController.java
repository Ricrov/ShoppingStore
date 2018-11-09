package com.store.dev.controller;

import com.store.dev.repository.commons.ResultWrapper;
import com.store.dev.repository.dao.UserEntityRepository;
import com.store.dev.repository.entity.UserEntity;
import com.store.dev.service.UserInfoService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserInfoControlModule
 * Created By X.
 */
@RestController
@RequestMapping("/user")
public class UserInfoController{
    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserEntityRepository repository;

    @PostMapping("/login")
    public ResultWrapper userLogin(@Validated @RequestBody UserEntity user,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                return ResultWrapper.error(401, error.getDefaultMessage());
            }
        }
        return userInfoService.findUserByUsername(user);
    }

    @PostMapping("/register")
    public ResultWrapper userRegister(@Validated @RequestBody UserEntity user,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                return ResultWrapper.error(400, error.getDefaultMessage());
            }
        }
        return userInfoService.addNewUser(user);
    }

    @GetMapping("test")
    public List<UserEntity> test() {
        return repository.findAll();
    }

}
