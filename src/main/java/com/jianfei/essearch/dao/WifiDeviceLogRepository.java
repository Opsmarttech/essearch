package com.jianfei.essearch.dao;

import com.jianfei.essearch.model.WifiDeviceLog;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WifiDeviceLogRepository extends ElasticsearchCrudRepository<WifiDeviceLog, String> {

    @Query("{\n" +
            "\"query\": {\n" +
            "\"bool\": {\n" +
            "\"must\": [\n" +
            "{\n" +
            "\"term\": {\n" +
            "\"ApSerial\": \"?0\"\n" +
            "}\n" +
            "}\n" +
            "],\n" +
            "\"must_not\": [ ],\n" +
            "\"should\": [ ]\n" +
            "}\n" +
            "},\n" +
            "\"from\": ?1,\n" +
            "\"size\": ?2,\n" +
            "\"sort\": [{\n" +
            "      \"Timestamp\": {\n" +
            "        \"order\": \"asc\"\n" +
            "      }\n" +
            "    }],\n" +
            "\"aggs\": { }\n" +
            "}")
    public List<WifiDeviceLog> findFoo(String ApSerial, int from, int size);

}
