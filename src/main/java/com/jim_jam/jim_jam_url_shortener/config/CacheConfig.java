package com.jim_jam.jim_jam_url_shortener.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Config to set up caches used by the service
 */
@EnableCaching
@Configuration
public class CacheConfig {
    @Value("${actual.url.cache.max.size:50}")
    private int actualUrlCacheMaxSize;

    @Value("${actual.url.cache.ttl:30000}")
    private int actualUrlCacheTtl;

    /**
     * Bean to set up cache to store keys
     * @return {@link Cache}
     */
    @Bean
    public Cache<String, Object> shortUrlCache() {
        return Caffeine.newBuilder()
                .maximumSize(actualUrlCacheMaxSize)
                .expireAfterWrite(actualUrlCacheTtl, TimeUnit.MILLISECONDS)
                .build();
    }
}
