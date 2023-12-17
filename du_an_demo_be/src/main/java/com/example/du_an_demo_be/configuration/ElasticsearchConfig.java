package com.example.du_an_demo_be.configuration;

import com.example.du_an_demo_be.model.entity.VectorEntity;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.annotation.PostConstruct;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.du_an_demo_be.repository")
public class ElasticsearchConfig {

//    @Autowired
//    private ElasticsearchRestTemplate elasticsearchTemplate;
//
//
//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo("localhost:9200")
//                .build();
//        RestHighLevelClient client = RestClients.create(clientConfiguration).rest();
//        System.out.println(client);
//
//        return RestClients.create(clientConfiguration).rest();
//    }
//
//    @Bean
//    public ElasticsearchRestTemplate elasticsearchTemplate(RestHighLevelClient client) {
//        this.createIndexIfNotExists();
//        return new ElasticsearchRestTemplate(client);
//    }
//
//    private void createIndexIfNotExists() {
//        IndexOperations indexOps = elasticsearchTemplate.indexOps(VectorEntity.class);
//        if (!indexOps.exists()) {
//            indexOps.create();
//            indexOps.putMapping(indexOps.createMapping(VectorEntity.class));
//        }
//    }
@Autowired
private ElasticsearchRestTemplate elasticsearchTemplate;

    @Bean
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate(RestHighLevelClient client) {
        return new ElasticsearchRestTemplate(client);
    }

    @PostConstruct
    private void createIndexIfNotExists() {
        IndexOperations indexOps = elasticsearchTemplate.indexOps(VectorEntity.class);
        if (!indexOps.exists()) {
            indexOps.create();
            indexOps.putMapping(indexOps.createMapping(VectorEntity.class));
        }
    }

}
