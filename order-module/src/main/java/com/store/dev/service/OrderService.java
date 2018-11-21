package com.store.dev.service;

import com.store.dev.repository.commons.MallResult;
import com.store.dev.repository.entity.*;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * @Author: MQ
 * @Date: 2018/11/7 19:37
 */

public interface OrderService {


    TbOrder deleteByID(Long Order);

    List<UserEntity> findAll();

    UserEntity getOne(Long userId);

    int totalCount();

    List<TbOrder> QueryAll(Integer page,Integer count,String searchMsg);

    MallResult createOrder(OrderInfo orderInfo);


//    Page<TbOrder> findAll(Pageable pageable) throws InvocationTargetException, IllegalAccessException;
//
//    Page<TbOrder> findSearch(String query, Pageable pageable) throws InvocationTargetException, IllegalAccessException;
//

}
