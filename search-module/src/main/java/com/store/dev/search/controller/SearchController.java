package com.store.dev.search.controller;

import com.store.dev.search.SearchResult;
import com.store.dev.search.service.SearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private SearchService searchService;


    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public Map<String, Object> search(@RequestParam(value = "q") String keyword,
                                      @RequestParam(value = "page",
                                              defaultValue = "1") Integer page)
            throws Exception {
        SearchResult searchResult = searchService.search(keyword, page);
        Long total = searchResult.getTotal();
        Long totalPage = total % 30 == 0 ? total / 30 : total / 30 + 1;
        Map<String, Object> map = new HashMap();
        map.put("list", searchResult.getData());
        map.put("totalPage", totalPage);
        map.put("currentPage", page);
        return map;
    }

}