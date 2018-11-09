package com.store.dev.repository.dao;

import com.store.dev.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    /**
     * 根据用户ID查询购物车
     *
     * @param userId
     * @return
     */
    UserEntity getOne(Long userId);
}
