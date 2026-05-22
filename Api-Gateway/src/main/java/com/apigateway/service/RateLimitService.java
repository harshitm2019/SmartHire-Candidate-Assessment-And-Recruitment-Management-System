package com.apigateway.service;

import io.github.bucket4j.*;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateLimitService {

    private final ProxyManager<String> proxyManager;

    private BucketConfiguration createBucketConfig(long capacity, long refillTokens, long refillDuration) {


        Bandwidth limit = Bandwidth.builder()
                .capacity(capacity)
                .refillGreedy(refillTokens, Duration.ofMinutes(refillDuration))
                .build();

        return BucketConfiguration.builder()
                .addLimit(limit)
                .build();
    }

    public ConsumptionProbe consume(String key, long capacity, long refillTokens, long refillDuration) {

        Bucket bucket = proxyManager.getProxy(key, () ->
                createBucketConfig(capacity, refillTokens, refillDuration));

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        log.debug("Remaining tokens: {}",
                probe.getRemainingTokens());

        log.debug("Consumed: {}",
                probe.isConsumed());

        return probe;

    }





}
