package com.store.dev.search.service;

import com.store.dev.repository.entity.ItemEntity;
import com.store.dev.search.SearchResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {
    SearchResult search(String keyword, Integer page)throws Exception;

    List<ItemEntity> findAllItems();
}
