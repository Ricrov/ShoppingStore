package com.store.dev.repository.dao;

import com.store.dev.repository.entity.TbOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author: MQ
 * @Date: 2018/11/7 11:47
 * 订单信息表的DAO层
 */
@Repository
public interface TbOrderRepository extends JpaRepository<TbOrder, Long>,
        JpaSpecificationExecutor<TbOrder>{

    @Query(value = "select * from tb_order where order_id = ?",nativeQuery = true)
    TbOrder getOrderId(String orderId);

    @Modifying
    @Transactional
    @Query(value = "delete from tb_order where order_id = ?",nativeQuery = true)
    Integer delOrderId(String orderId);



//
//    int totalCount();
//
//    //根据用户名昵称等信息模糊查询
//    @Query("select orders from tb_order orders where orders.orderId like %?1% ")
//    Page<TbOrder> findSearch(String query, Pageable pageable);


}
