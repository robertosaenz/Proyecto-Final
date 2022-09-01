package com.bootcamp.walletbankservice.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig
{
//    @Bean
//    public RedisCommands connectionFactory() {
//
//        RedisURI redisURI = RedisURI.create("redis://localhost:6379");
//        RedisClient redisClient = RedisClient.create(redisURI);
//        StatefulRedisConnection<String, String> redisConnection = redisClient.connect();
//        return redisConnection.sync();
//    }
//    @Bean
//    public RedisConnectionFactory connectionFactory()
//    {
//        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
//        configuration.setHostName("localhost");
//        configuration.setPort(6379);
//        return new LettuceConnectionFactory(configuration);
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> template() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory());
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
//        template.setValueSerializer(new JdkSerializationRedisSerializer());
//        template.setEnableTransactionSupport(true);
//        template.afterPropertiesSet();
//        return template;
//    }
}
