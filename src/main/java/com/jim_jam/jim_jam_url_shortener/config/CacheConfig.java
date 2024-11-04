package com.jim_jam.jim_jam_url_shortener.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config to set up caches used by the service
 */
@EnableCaching
@Configuration
public class CacheConfig {
    @Value("${url.cache.max.size:50}")
    private int urlCacheMaxSize;

    /**
     * Bean to set up cache to store keys
     * @return {@link Cache}
     */
    @Bean
    public Cache<String, Object> keyCache() {
        return Caffeine.newBuilder()
                .maximumSize(urlCacheMaxSize)
                .build();
    }
}
