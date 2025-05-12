package org.harvey.respiratory.server.config;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.harvey.respiratory.server.properties.ConstantsProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 对上下文的设置
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2024-01-21 21:13
 */
@Configuration
@EnableConfigurationProperties(ConstantsProperties.class)
public class ApplicationConfig {
    @Resource
    private ConstantsProperties constantsProperties;

    @Bean
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        // 配置类
        Config config = new Config();
        // 添加Redis地址, 这里添加了单点的地址
        config.useSingleServer()
                .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setPassword(redisProperties.getPassword());
        // 也可以使用config.useClusterServers()添加集群地址
        return Redisson.create(config);
    }

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://" + constantsProperties.getEsHost() + ":" + constantsProperties.getEsPort())
        ));
    }
}
