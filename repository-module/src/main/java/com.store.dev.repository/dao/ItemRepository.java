package com.store.dev.repository.dao;

import com.store.dev.repository.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    @Query(value = "select * from tb_item where title like ?", nativeQuery = true)
    List<ItemEntity> findItemEntityByTitle(String title);

}
