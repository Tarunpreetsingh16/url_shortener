package com.jim_jam.jim_jam_url_shortener.service;

import com.jim_jam.jim_jam_url_shortener.common.error.UrlShortenerServiceException;
import com.jim_jam.jim_jam_url_shortener.models.GetShortUrlRequest;

/**
 * Service layer for this microservice
 */
public interface IUrlShortenerService {
    /**
     * Method to generate a short URL
     * @param shortUrlRequestBody request body
     * @return {@link String} short URL
     * @throws UrlShortenerServiceException when something goes wrong while getting a short URL
     */
    String getShortUrl(GetShortUrlRequest shortUrlRequestBody) throws UrlShortenerServiceException;

    /**
     * Method to get the mapped url
     * @param shortUrlId key to which actual url is mapped
     * @return {@link String} actual url
     * @throws UrlShortenerServiceException when something goes wrong while getting actual url
     */
    String getActualUrl(String shortUrlId) throws UrlShortenerServiceException;
}
