package com.store.dev.cart.service;

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

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 铭
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

//    @Resource
//    private RedisTemplate<Object, Object> redisTemplate;

    @Cacheable(value = "cartService")
    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> all = userEntityRepository.findAll();
        return userEntityRepository.findAll();
    }

    @Cacheable(value = "cartService", key = "#userId")
    @Override
    public UserEntity getOne(Long userId) {
        UserEntity one = userEntityRepository.findById(userId).get();
        return one;
    }

    @Transactional
    @CacheEvict(value = "cartService", allEntries = true)
    @Override
    public Integer deleteGoodsByUserId(Long userId, Integer itemId) {
        Integer result = cartRepository.deleteGoodsByUserId(userId, itemId);
        return result;
    }

    // @CachePut(value = "cartService", key = "#cartEntity")
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


}
