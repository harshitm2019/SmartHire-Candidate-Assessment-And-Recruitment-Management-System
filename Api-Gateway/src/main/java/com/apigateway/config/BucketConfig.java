package com.apigateway.config;

import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.redisson.cas.RedissonBasedProxyManager;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.redisson.command.CommandAsyncExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BucketConfig {

    private final RedissonClient redissonClient;

    @Bean
    public ProxyManager<String> proxyManager() {

        CommandAsyncExecutor commandExecutor = ((org.redisson.Redisson) redissonClient).getCommandExecutor();

        return RedissonBasedProxyManager.builderFor(commandExecutor).build();

    }

}
