package com.store.dev.repository.dao.order;

import com.store.dev.repository.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: MQ
 * @Date: 2018/11/7 11:47
 */

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,String> {

    int totalCount();

    List<OrderEntity> QueryAllOrders();
}
