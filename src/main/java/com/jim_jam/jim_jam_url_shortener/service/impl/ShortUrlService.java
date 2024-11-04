package com.jim_jam.jim_jam_url_shortener.service.impl;

import com.jim_jam.jim_jam_url_shortener.data.ShortUrl;
import com.jim_jam.jim_jam_url_shortener.repositories.ShortUrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ShortUrlService {

    private ShortUrlRepository shortUrlRepository;

    public ShortUrlService(
            ShortUrlRepository shortUrlRepository
    ) {
        this.shortUrlRepository = shortUrlRepository;
    }

    public void saveShortUrl(ShortUrl shortUrl) {
        shortUrlRepository.save(shortUrl);
    }

    public ShortUrl findActualUrl(String shortUrlId) {
        Optional<ShortUrl> shortUrl = shortUrlRepository.findById(shortUrlId);
        return shortUrl.orElse(null);
    }
}
