package com.store.dev.cart.service;

import com.alibaba.fastjson.JSON;
import com.store.dev.cart.controller.params.CartParams;
import com.store.dev.repository.commons.ResultWrapper;
import com.store.dev.repository.dao.CartRepository;
import com.store.dev.repository.dao.ItemRepository;
import com.store.dev.repository.dao.UserEntityRepository;
import com.store.dev.repository.entity.CartEntity;
import com.store.dev.repository.entity.ItemEntity;
import com.store.dev.repository.entity.UserEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 铭
 *
 * 2018/11/6 20:43
 */
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartRepository cartRepository;

    @Resource
    private UserEntityRepository userEntityRepository;

    @Resource
    private ItemRepository itemRepository;

    private JedisPool pool;

    private Jedis jedis;


    @Cacheable(value = "cartService")
    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> all = userEntityRepository.findAll();
        return all;
    }

    @Cacheable(value = "cartService", key = "#userId")
    @Override
    public UserEntity getOne(Long userId) {
        UserEntity one = userEntityRepository.findById(userId).get();
        System.out.println(one);
        return one;
    }

    @Transactional
    @CacheEvict(value = "cartService", allEntries = true)
    @Override
    public Integer deleteGoodsByUserId(Long userId, Integer itemId) {
        Integer result = cartRepository.deleteGoodsByUserId(userId, itemId);
        return result;
    }

    @CacheEvict(value = "cartService", allEntries = true)
    @Override
    public Integer addUserCartGoods(CartEntity cartEntity) {
        Integer result = cartRepository.addUserCartGoods(cartEntity.getUserId(), cartEntity.getItemId(), cartEntity.getGoodsNumber());
        return result;
    }

    @CacheEvict(value = "cartService", allEntries = true)
    @Override
    public Integer updateGoodsNumber(CartEntity cartEntity) {
        return cartRepository.updateGoodsNumber(cartEntity.getGoodsNumber(), cartEntity.getUserId(), cartEntity.getItemId());
    }

    @Override
    public ItemEntity findGoodsByItemId(ItemEntity entity) {
        ItemEntity itemEntity = itemRepository.findById(entity.getItemId()).get();
        return itemEntity;
    }

    @Transactional
    @CacheEvict(value = "cartService", allEntries = true)
    @Override
    public ResultWrapper deleteGoods(Long userId, List<Integer> itemIds) {
        try {
            for (Integer itemId : itemIds) {
                Integer integer = cartRepository.deleteGoodsByUserId(userId, itemId);
            }
            ResultWrapper resultWrapper = new ResultWrapper();
            resultWrapper.setStatus(200);
            resultWrapper.setMessage("操作成功");
            return resultWrapper;
        } catch (Exception e) {
            ResultWrapper resultWrapper = new ResultWrapper();
            resultWrapper.setStatus(303);
            resultWrapper.setMessage("操作失败");
            return resultWrapper;
        }
    }

    // 根据用户ID和商品ID查询购物车商品信息,如果有这个商品,就更新数量,否则就添加这个商品
    @Transactional
    @CacheEvict(value = "cartService", allEntries = true)
    @Override
    public Integer findGoods(CartEntity cartEntity) {
        CartEntity goods = cartRepository.findGoods(cartEntity.getUserId(), cartEntity.getItemId());
        if (goods != null) {
            Integer result = cartRepository.updateGoodsNumber(cartEntity.getGoodsNumber(), cartEntity.getUserId(), cartEntity.getItemId());
            return result;
        } else {
            Integer result = cartRepository.addUserCartGoods(cartEntity.getUserId(), cartEntity.getItemId(), cartEntity.getGoodsNumber());
            return result;
        }
    }

    @Override
    public CartParams findGoodsByItemIds() {

        pool = new JedisPool();
        jedis = pool.getResource();

        CartParams testDemo = new CartParams();

        List<Map<String, Integer>> itemList = null;
        // 先从redis里面去查询数据，看是否能获取到对应json字符串
        String jsonStrLp = jedis.get("itemList");

        // 目的是为了泛型的转换
        itemList = new ArrayList<Map<String, Integer>>();

        // 先把从redis缓存中取出来的json字符串转为List<Map>集合
        List<Map> mapList = JSON.parseArray(jsonStrLp, Map.class);

        // 然后循环遍历这个List集合，得出的结果为Map,然后再强转为Map<String,Object>,
        // 再循环 把 Map<String,Object>添加到List集合中，搞定！！！
        for (Map map : mapList) {
            Map<String, Integer> sObj = (Map<String, Integer>) map;
            itemList.add(sObj);
        }

        List<Integer> itemIdList = new ArrayList<>();
        List<Integer> goodsNumberList = new ArrayList<>();
        for (Map<String, Integer> item : itemList) {
            itemIdList.add(item.get("itemId"));
            goodsNumberList.add(item.get("goodsNumber"));
        }
        List<ItemEntity> itemEntities = new ArrayList<>();
        for (Integer itemId : itemIdList) {
            ItemEntity itemEntity = itemRepository.findById(Long.valueOf(itemId)).get();
            itemEntities.add(itemEntity);
        }


        float price = 0;
        for (int i = 0; i < itemEntities.size() && i < goodsNumberList.size(); i++) {
            price += itemEntities.get(i).getPrice() * goodsNumberList.get(i);
        }
        System.out.println("总价: " + price);

        testDemo.setItemEntities(itemEntities);
        testDemo.setNumberList(goodsNumberList);
        testDemo.setTotalPrice(price);
        testDemo.setPreferentialPrice(price - 10);

        return testDemo;
    }

    @Transactional
    @CacheEvict(value = "cartService", allEntries = true)
    @Override
    public ResultWrapper updateNumberList(Map<String, ArrayList<Long>> itemIdList) {
        ArrayList<Long> itemIdList2 = itemIdList.get("itemIdList");
        ArrayList<Long> goodsNumberList2 = itemIdList.get("goodsNumberList");
        try {
            for (int i = 0; i < itemIdList2.size() && i < goodsNumberList2.size(); i++) {
                cartRepository.updateGoodsNumber(goodsNumberList2.get(i), 7L, itemIdList2.get(i));
            }
            ResultWrapper resultWrapper = new ResultWrapper();
            resultWrapper.setStatus(200);
            resultWrapper.setMessage("操作成功");
            return resultWrapper;
        } catch (Exception e) {
            ResultWrapper resultWrapper = new ResultWrapper();
            resultWrapper.setStatus(303);
            resultWrapper.setMessage("操作失败");
            return resultWrapper;
        }
    }

    /**
     * @TODO 缓存ID待优化: key = "cart:userId:7:itemList"
     * @param itemList
     */
    @Override
    public void itemListRedis(Map<String, List<Map<String, Integer>>> itemList) {
        pool = new JedisPool();
        jedis = pool.getResource();
        List<Map<String, Integer>> maps = itemList.get("itemList");
        String itemListJson = JSON.toJSONString(maps);
        jedis.set("itemList", itemListJson);
        jedis.close();
    }


}
