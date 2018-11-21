package com.store.dev.search.service;

import com.store.dev.repository.entity.ItemEntity;

import java.util.List;

public interface ItemService {

    List<ItemEntity> findAll();

    List<ItemEntity> findItemEntityByTitle(String title);

    ItemEntity findGoodsByItemId(ItemEntity itemId);

}
