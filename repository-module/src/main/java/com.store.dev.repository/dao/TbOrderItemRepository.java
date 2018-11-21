package com.store.dev.repository.dao;

import com.store.dev.repository.entity.TbItem;
import com.store.dev.repository.entity.TbOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @Author: MQ
 * @Date: 2018/11/7 11:47
 * 订单信息表的DAO层
 */
@Repository
public interface TbOrderItemRepository extends JpaRepository<TbOrderItem, Long>{

}
