package com.jim_jam.jim_jam_url_shortener.service;

import com.jim_jam.jim_jam_url_shortener.common.error.UrlShortenerServiceException;
import com.jim_jam.jim_jam_url_shortener.models.GetShortUrlRequest;

public interface IUrlShortenerService {
    String getShortUrl(GetShortUrlRequest shortUrlRequestBody) throws UrlShortenerServiceException;
}
