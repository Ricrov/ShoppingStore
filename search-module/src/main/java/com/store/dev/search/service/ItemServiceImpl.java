package com.store.dev.search.service;

import com.store.dev.repository.dao.ItemRepository;
import com.store.dev.repository.entity.ItemEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ItemRepository itemRepository;

    @Cacheable(value = "itemService")
    @Override
    public List<ItemEntity> findAll() {
        return itemRepository.findAll();
    }

    @Cacheable(value = "itemService")
    @Override
    public List<ItemEntity> findItemEntityByTitle(String title) {
        return itemRepository.findItemEntityByTitle(title);
    }

    @Cacheable(value = "itemService")
    @Override
    public ItemEntity findGoodsByItemId(ItemEntity entity) {
        ItemEntity itemEntity = itemRepository.findById(entity.getItemId()).get();
        return itemEntity;
    }

}
