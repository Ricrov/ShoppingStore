package com.store.dev.cart.controller;

import com.store.dev.cart.service.CartService;
import com.store.dev.repository.commons.ResultWrapper;
import com.store.dev.repository.entity.CartEntity;
import com.store.dev.repository.entity.ItemEntity;
import com.store.dev.repository.entity.UserEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 铭
 * 2018/11/6 19:58
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    // 查询所有用户的购物车
    @RequestMapping("/findAll")
    public List<UserEntity> findAll() {
        List<UserEntity> all = cartService.findAll();
        return all;
    }

    // 查询当前登录用户的购物车
    @RequestMapping("/list")
    public UserEntity getOne(@Param("userId") Long userId) {
        UserEntity one = cartService.getOne(userId);
        return one;
    }

    // 根据当前登录用户ID删除指定商品
    @RequestMapping("/deleteGoods/{itemId}")
    public ResultWrapper deleteGoodsByUserId(@PathVariable Integer itemId) {
        System.out.println("*************************");
        System.out.println(itemId);
        Integer result = cartService.deleteGoodsByUserId(7L, itemId);
        return getResultWrapper(result);
    }

    // 插入用户购买的商品(用户ID,商品ID,商品数量)
    @RequestMapping("/addGoodsTest")
    public ResultWrapper addUserCartGoods(@RequestBody CartEntity cartEntity) {
        System.out.println(cartEntity);
        Integer result = cartService.addUserCartGoods(cartEntity);
        return getResultWrapper(result);
    }

    // 更新购物车中的商品数量
    @RequestMapping("/updateGoodsNumber")
    public ResultWrapper updateGoodsNumber(@RequestBody CartEntity cartEntity) {
        Integer result = cartService.updateGoodsNumber(cartEntity);
        return getResultWrapper(result);
    }

    // 根据商品ID查询商品信息
    @PostMapping("/goodsMessage")
    public ItemEntity findGoodsByItemId(@RequestBody ItemEntity itemEntity) {
        ItemEntity result = cartService.findGoodsByItemId(itemEntity);
        return result;
    }

    // 批量删除购物车商品
    @RequestMapping("/deleteItems")
    public ResultWrapper deleteGoods(@RequestBody Map<String, ArrayList<Integer>> itemIds) {
        ArrayList<Integer> list = itemIds.get("itemIds");
        ResultWrapper wrapper = cartService.deleteGoods(7L, list);
        return wrapper;
    }

    // 根据用户ID和商品ID查询购物车商品信息,如果有这个商品,就更新数量,否则就添加这个商品
    @PostMapping("/addGoods")
    public ResultWrapper findGoodsByItemId(@RequestBody CartEntity cartEntity) {
        Integer result = cartService.findGoods(cartEntity);
        return getResultWrapper(result);
    }

    // 根据很多商品ID查询很多商品信息
    @PostMapping("/findGoods")
    public List<ItemEntity> findGoodsByItemId(@RequestBody Map<String, ArrayList<Integer>> map) {
        ArrayList<Integer> itemIds = map.get("itemIds");
        List<ItemEntity> goods = cartService.findGoodsByItemIds(itemIds);
        return goods;
    }

    private ResultWrapper getResultWrapper(Object result) {
        ResultWrapper resultWrapper = new ResultWrapper();
        if (result != null) {
            resultWrapper.setStatus(200);
            resultWrapper.setMessage("操作成功");
            return resultWrapper;
        } else {
            resultWrapper.setStatus(303);
            resultWrapper.setMessage("操作失败");
            return resultWrapper;
        }
    }

}
