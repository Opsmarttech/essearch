package com.jianfei.essearch.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.NodeClientFactoryBean;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@EnableElasticsearchRepositories
public class ESSearchConfig extends AbstractElasticsearchConfiguration {

    @Override
    public RestHighLevelClient elasticsearchClient() {
        return RestClients.create(ClientConfiguration.create("114.55.117.34:9200")).rest();
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate() throws Exception {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

    @PreDestroy
    public void deleteIndex() {
    }

    @PostConstruct
    public void insertDataSample() {
    }

}
