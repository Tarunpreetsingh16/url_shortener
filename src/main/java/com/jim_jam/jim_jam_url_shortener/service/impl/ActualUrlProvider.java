package com.jim_jam.jim_jam_url_shortener.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.jim_jam.jim_jam_url_shortener.data.ShortUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service layer to provide short url mapping
 */
@Service
@Slf4j
public class ActualUrlProvider {
    private final ShortUrlService shortUrlService;
    private final Cache<String, Object> shortUrlCache;

    public ActualUrlProvider(
            Cache<String, Object> shortUrlCache,
            ShortUrlService shortUrlService
    ) {
        this.shortUrlCache = shortUrlCache;
        this.shortUrlService = shortUrlService;
    }

    /**
     * Method to get the actual url
     * @param shortUrlId key to get the actual url
     * @return {@link ShortUrl}
     */
    public ShortUrl getActualUrl(String shortUrlId) {
        ShortUrl shortUrl = (ShortUrl) shortUrlCache.getIfPresent(shortUrlId);
        if (shortUrl == null || shortUrl.getActualUrl() == null) {
            shortUrl = shortUrlService.findActualUrl(shortUrlId);
            log.info("Returning actual url from database for id={}", shortUrlId);
            shortUrlCache.put(shortUrlId, shortUrl);
        }
        else {
            log.info("Returning actual url from cache for id={}", shortUrlId);
        }
        return shortUrl;
    }
}
