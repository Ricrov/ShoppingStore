package com.store.dev.search.service;

import com.store.dev.repository.dao.ItemRepository;
import com.store.dev.repository.entity.ItemEntity;
import com.store.dev.search.SearchResult;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;


@Service
public class SearchServiceImpl implements SearchService {
    public static final Integer ROWS = 30;
    @Resource
    private ItemRepository itemRepository;
    private static SolrClient solrClient = new HttpSolrClient.Builder(
            "http://localhost:8983/solr/search").build();

    @Override
    public SearchResult search(String keyword, Integer page) throws Exception {

//        构造搜索条件
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("title:" + keyword);//搜索关键词
        solrQuery.setStart((Math.max(page, 1) - 1) * ROWS);
        solrQuery.setRows(ROWS);
//        执行查询
        QueryResponse queryResponse = solrClient.query(solrQuery);
//        获取信息
        List<ItemEntity> items = queryResponse.getBeans(ItemEntity.class);
        return new SearchResult(queryResponse.getResults().getNumFound(), items);
    }

    @Override
    public List<ItemEntity> findAllItems() {
        try {
            solrClient.addBeans(itemRepository.findAll());
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
