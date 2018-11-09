package com.store.dev.repository.dao;

import com.store.dev.repository.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 铭
 * 2018/11/6 19:58
 */
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    // 根据当前登录用户ID删除指定商品
    @Transactional
    @Modifying
    @Query(value = "delete from tb_cart where user_id = ? and item_id = ?", nativeQuery = true)
    Integer deleteGoodsByUserId(Long userId, Long itemId);

    // 根据当前登录用户ID向购物车中添加指定商品
    @Transactional
    @Modifying
    @Query(value = "insert into tb_cart (user_id, item_id, goods_number) values (?, ?, ?)", nativeQuery = true)
    Integer addUserCartGoods(Long userId, Long itemId, Long goodsNumber);

    @Transactional
    @Modifying
    @Query(value = "update tb_cart set goods_number = ? where user_id = ? and item_id = ?", nativeQuery = true)
    Integer updateGoodsNumber(Long goodsNumber, Long userId, Long itemId);
}
