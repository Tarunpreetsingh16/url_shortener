package com.jim_jam.jim_jam_url_shortener.service;

import com.jim_jam.jim_jam_url_shortener.common.error.UrlShortenerServiceException;
import com.jim_jam.jim_jam_url_shortener.models.GetKeyResponse;

/**
 * Service layer to interact with Key generation service
 */
public interface IKeyGenerationService {
    /**
     * Method to get the key
     * @return {@link GetKeyResponse}
     * @throws UrlShortenerServiceException when something goes wrong while getting a key
     */
    GetKeyResponse getRandomKey() throws UrlShortenerServiceException;
}
