package com.tonytaotao.springboot.dubbo.order.es.controller;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;

/**
* <p>
*      前端控制器
* </p>
 *
 * @author tonytaotao
 * @since 2019-10-22
*/
@RestController
@RequestMapping("/es/api")
@Slf4j
public class EsController {

    @Autowired
    RestHighLevelClient client;

    /**
    *查询es
     * @return
    */
    @GetMapping("/queryEs")
    public String queryEs() {

        String[] esIndexs = {"filebeat-6.5.4-2019.11.04"};

        LocalDateTime startTime = LocalDateTime.of(2019, 11, 4, 0, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2019, 11, 4, 23, 0, 0);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("@timestamp");
        rangeQueryBuilder.gte(startTime);
        rangeQueryBuilder.lte(endTime);
        BoolQueryBuilder boolRangeQueryBuilder = QueryBuilders.boolQuery();
        boolRangeQueryBuilder.must(rangeQueryBuilder);
        boolQueryBuilder.must(boolRangeQueryBuilder);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().size(0);
        searchSourceBuilder.query(boolQueryBuilder);

        SearchRequest searchRequest = new SearchRequest(esIndexs);
        searchRequest.source(searchSourceBuilder);

        System.out.println(searchRequest);

        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            log.error("query es throw error", e);
        }

        if (searchResponse != null) {

            System.out.println(searchResponse);
        }


        return null;
    }


}
