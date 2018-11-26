package com.store.dev.cart.controller;

import com.store.dev.cart.controller.params.CartParams;
import com.store.dev.cart.service.CartService;
import com.store.dev.repository.commons.GetUserIp;
import com.store.dev.repository.commons.ResultWrapper;
import com.store.dev.repository.entity.CartEntity;
import com.store.dev.repository.entity.ItemEntity;
import com.store.dev.repository.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author 铭
 * 2018/11/6 19:58
 */
@RestController
@RequestMapping("/cart")
@Api(description = "用户的购物车")
public class CartController {

    @Autowired
    private HttpServletRequest request;

    @Resource
    private CartService cartService;

    private JedisPool pool;

    private Jedis jedis;

    // 查询所有用户的购物车
    @RequestMapping("/findAll")
    public List<UserEntity> findAll() {
        List<UserEntity> all = cartService.findAll();
        return all;
    }

    // 查询当前登录用户的购物车
    @ApiOperation(value = "查询当前登录用户的购物车", notes = "根据token取出用户ID")
    @RequestMapping("/list")
    public Object getOne(@RequestBody UserEntity userEntity) {
        if (userEntity.getUserId() != null && !"".equals(userEntity.getUserId())) {
            Long userId = userEntity.getUserId();
            UserEntity one = cartService.getOne(userId);
            return one;
        } else {
            String userIP = GetUserIp.getIpAddr(request);
            System.out.println(userIP);

            List<String> itemId = jedis.hmget(userIP, "itemId");
            List<String> goodsNumber = jedis.hmget(userIP, "goodsNumber");

            List<Map<String, Object>> redisCartList = new ArrayList<>();

            for (int i = 0; i < itemId.size() && i < goodsNumber.size(); i++) {
                Long id = Long.valueOf(itemId.get(i));
                Long number = Long.valueOf(goodsNumber.get(i));
                ItemEntity itemEntity = cartService.findRedisCart(id);
                Map<String, Object> map = new HashMap<>();
                map.put("itemEntity", itemEntity);
                map.put("goodsNumber", number);
                redisCartList.add(map);
            }
            System.out.println("****************************");
            System.out.println(redisCartList);

            return redisCartList;
        }
    }

    // 根据当前登录用户ID删除指定商品
    @ApiOperation(value = "根据当前登录用户ID删除指定商品", notes = "接收REST风格的参数形式")
    @RequestMapping("/deleteGoods/{itemId}")
    public ResultWrapper deleteGoodsByUserId(@PathVariable Integer itemId) {
        Integer result = cartService.deleteGoodsByUserId(7L, itemId);
        return getResultWrapper(result);
    }

    // 插入用户购买的商品(用户ID,商品ID,商品数量)
//    @ApiOperation(value = "向购物车中插入用户购买的商品", notes = "接收json类型数据")
//    @RequestMapping("/addGoodsTest")
//    public ResultWrapper addUserCartGoods(@RequestBody CartEntity cartEntity) {
//        pool = new JedisPool();
//        jedis = pool.getResource();
//        if (cartEntity.getUserId() != null && !"".equals(cartEntity.getUserId())) {
//            Integer result = cartService.addUserCartGoods(cartEntity);
//            return getResultWrapper(result);
//        } else {
//            String userIP = GetUserIp.getIpAddr(request);
//            System.out.println(userIP);
//            String itemId = String.valueOf(cartEntity.getItemId());
//            String goodsNumber = String.valueOf(cartEntity.getGoodsNumber());
//            System.out.println(itemId);
//            System.out.println(goodsNumber);
////            Boolean exists = jedis.exists(userIP);
//            Map<String, String> map = new HashMap<>();
//            map.put("itemId", itemId);
//            map.put("goodsNumber", goodsNumber);
//            String s = jedis.hmset(userIP, map);
//            return getResultWrapper(s);
//        }
//    }

//    @RequestMapping("/redisCart")
//    public List<Map<String, Object>> getRedisCart() {
//
//    }

    @ApiOperation(value = "根据用户ID和商品ID更新购物车中的商品数量", notes = "接收json数据")
    // 更新购物车中的商品数量
    @RequestMapping("/updateGoodsNumber")
    public ResultWrapper updateGoodsNumber(@RequestBody CartEntity cartEntity) {
        Integer result = cartService.updateGoodsNumber(cartEntity);
        return getResultWrapper(result);
    }

    @ApiOperation(value = "根据商品ID查询商品信息", notes = "接收json数据")
    // 根据商品ID查询商品信息
    @PostMapping("/goodsMessage")
    public ItemEntity findGoodsByItemId(@RequestBody ItemEntity itemEntity) {
        ItemEntity result = cartService.findGoodsByItemId(itemEntity);
        return result;
    }


    @ApiOperation(value = "根据用户ID和商品ID的集合批量删除购物车中的商品", notes = "接收json数据,例子: Map<String, ArrayList<Integer>> itemIds")
    // 批量删除购物车商品
    @RequestMapping("/deleteItems")
    public ResultWrapper deleteGoods(@RequestBody Map<String, ArrayList<Integer>> itemIds) {
        ArrayList<Integer> list = itemIds.get("itemIds");
        ResultWrapper wrapper = cartService.deleteGoods(7L, list);
        return wrapper;
    }

    @ApiOperation(value = "根据用户ID和商品ID查询购物车商品信息,如果有这个商品,就更新数量,否则就添加这个商品", notes = "接收json数据")
    // 根据用户ID和商品ID查询购物车商品信息,如果有这个商品,就更新数量,否则就添加这个商品
    @PostMapping("/addGoods")
    public ResultWrapper findGoodsByItemId(@RequestBody CartEntity cartEntity) {
        pool = new JedisPool();
        jedis = pool.getResource();
        if (cartEntity.getUserId() != null && !"".equals(cartEntity.getUserId())) {
            Integer result = cartService.findGoods(cartEntity);
            return getResultWrapper(result);
        } else {
            String userIP = GetUserIp.getIpAddr(request);
            System.out.println(userIP);
            String itemId = String.valueOf(cartEntity.getItemId());
            String goodsNumber = String.valueOf(cartEntity.getGoodsNumber());
            System.out.println(itemId);
            System.out.println(goodsNumber);
//            Boolean exists = jedis.exists(userIP);
            Map<String, String> map = new HashMap<>();
            map.put("itemId", itemId);
            map.put("goodsNumber", goodsNumber);
            String s = jedis.hmset(userIP, map);
            return getResultWrapper(s);
        }
    }

    // 根据很多商品ID查询很多商品信息
    @GetMapping("/findGoods")
    public CartParams findGoodsByItemIds() {
        CartParams result = cartService.findGoodsByItemIds();
        return result;
    }

    @ApiOperation(value = "根据商品ID集合更新购物车中的许多商品数量", notes = "接收json数据,例子: Map<String, ArrayList<Long>> itemList")
    // 更新购物车中的许多商品数量
    @PostMapping("/updateNumberList")
    public ResultWrapper updateNumberList(@RequestBody Map<String, ArrayList<Long>> itemList) {
        ResultWrapper resultWrapper = cartService.updateNumberList(itemList);
        return resultWrapper;
    }

    // 购物车结算时向redis中缓存数据(商品ID的集合和商品数量的集合)
    @PostMapping("/itemListRedis")
    public void itemListRedis(@RequestBody Map<String, List<Map<String, Integer>>> itemList) {
        cartService.itemListRedis(itemList);
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
