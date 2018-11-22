package com.store.dev.service;

import com.store.dev.repository.commons.MallResult;
import com.store.dev.repository.entity.*;
import org.hibernate.criterion.Order;

import java.util.List;
import java.util.Map;

/**
 * @Author: MQ
 * @Date: 2018/11/7 19:37
 */

public interface OrderService {


    TbOrder deleteByID(Long Order);

    List<TbOrder> findAll();

    UserEntity getOne(Long userId);

    TbOrder submitOrder(Map<String, Object> itemList);

}
