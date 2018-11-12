package com.store.dev.cart.service;

import com.store.dev.repository.commons.ResultWrapper;
import com.store.dev.repository.entity.CartEntity;
import com.store.dev.repository.entity.ItemEntity;
import com.store.dev.repository.entity.UserEntity;

import java.util.List;

/**
 * @author 铭
 * 2018/11/6 20:32
 */
public interface CartService {

    // 查询所有用户的购物车
    List<UserEntity> findAll();

    // 根据用户ID查询他的购物车
    UserEntity getOne(Long userId);

    // 根据当前登录用户ID删除指定商品
    Integer deleteGoodsByUserId(Long userId, Integer itemId);

    // 插入用户购买的商品(用户ID,商品ID,商品数量)
    Integer addUserCartGoods(CartEntity cartEntity);

    // 更新购物车中的商品数量
    Integer updateGoodsNumber(CartEntity cartEntity);

    // 根据商品ID查询商品信息
    ItemEntity findGoodsByItemId(ItemEntity itemId);

    // 根据当前登录用户ID删除指定多个商品
    ResultWrapper deleteGoods(Long userId, List<Integer> itemIds);

    // 根据用户ID和商品ID查询购物车商品信息,如果有这个商品,就更新数量,否则就添加这个商品
    Integer findGoods(CartEntity cartEntity);

}
