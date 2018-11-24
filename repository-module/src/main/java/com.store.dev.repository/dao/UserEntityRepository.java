package com.store.dev.repository.dao;

import com.store.dev.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    @Query(value = "select * from tb_user user2 left join tb_cart cart on user2.user_id = cart.user_id left join tb_item item on item.item_id = cart.item_id where user2.user_id = ?", nativeQuery = true)
    UserEntity findUserCart(Long userId);

}
