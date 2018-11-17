package com.store.dev.cart.controller.params;

import com.store.dev.repository.entity.ItemEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 铭
 * 2018/11/17 9:11
 */
@Data
public class CartParams implements Serializable {
    private List<ItemEntity> itemEntities;
    private List<Integer> numberList;
    private float totalPrice;
    private float preferentialPrice;
}
