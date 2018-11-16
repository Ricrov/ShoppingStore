package com.store.dev.cart.controller.params;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 铭
 * 2018/11/16 16:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class CartParams implements Serializable {

    private String name;
    private String phone;
    private String address;
    private String paymentType;
    private List<Integer> itemPriceList;
    private List<Integer> itemIdList;
    private List<String> itemTitleList;
    private List<Integer> itemNumberList;

}
