package com.jim_jam.jim_jam_url_shortener.service.impl;

import com.jim_jam.jim_jam_url_shortener.data.ShortUrl;
import com.jim_jam.jim_jam_url_shortener.repositories.ShortUrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service layer to interact with short_url repository
 */
@Service
@Slf4j
public class ShortUrlService {

    private ShortUrlRepository shortUrlRepository;

    /**
     * Primary constructor to set up the bean
     * @param shortUrlRepository repository bean to interact with short_url collection
     */
    public ShortUrlService(
            ShortUrlRepository shortUrlRepository
    ) {
        this.shortUrlRepository = shortUrlRepository;
    }

    /**
     * Method to save the url to the collection
     * @param shortUrl short url object
     */
    public void saveShortUrl(ShortUrl shortUrl) {
        shortUrlRepository.save(shortUrl);
    }
}
