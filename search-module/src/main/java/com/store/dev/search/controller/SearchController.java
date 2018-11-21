//package com.store.dev.search.controller;
//
//import com.alibaba.fastjson.JSON;
//import org.apache.solr.client.solrj.SolrClient;
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.common.SolrDocumentList;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@RestController
//@RequestMapping("/search")
//public class SearchController {
//
//    @Resource
//    private SolrClient client;
//
//    @RequestMapping("/solr")
//    public String findByTitlet(@RequestParam String keywords) {
//        try {
//            SolrQuery params = new SolrQuery();
//
//            //查询条件, 这里的 q 对应 下面图片标红的地方
//            params.set("q", keywords);
//
//            //分页
//            params.setStart(0);
//            params.setRows(10);
//
//            //默认域
//            params.set("df", "title");
//
//            //只查询指定域
//            params.set("fl", "title");
//
//            QueryResponse queryResponse = client.query(params);
//
//            SolrDocumentList results = queryResponse.getResults();
//            long numFound = results.getNumFound();
//            String s = JSON.toJSONString(results);
//            System.out.println(s);
//            return s;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }
//
//}
