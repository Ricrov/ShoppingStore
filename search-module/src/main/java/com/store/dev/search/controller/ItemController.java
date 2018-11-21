package com.store.dev.search.controller;

import com.store.dev.repository.entity.ItemEntity;
import com.store.dev.search.service.ItemService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/searchGoods")
public class ItemController {

    @Resource
    private ItemService itemService;

    @RequestMapping("/findAll")
    public List<ItemEntity> findAll() {
        List<ItemEntity> list = itemService.findAll();
        return list;
    }

    @RequestMapping("/findByTitle")
    public List<ItemEntity> findItemEntityByTitle(@RequestParam String title) {
        System.out.println(title);
        String result = "%" + title + "%";
        System.out.println(result);
        List<ItemEntity> itemList = itemService.findItemEntityByTitle(result);
        itemList.forEach(System.out::println);
        return itemList;
    }

    @PostMapping("/goodsMessage")
    public ItemEntity findGoodsByItemId(@RequestBody ItemEntity itemEntity) {
        ItemEntity result = itemService.findGoodsByItemId(itemEntity);
        return result;
    }

}
