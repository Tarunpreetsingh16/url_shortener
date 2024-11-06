package com.jim_jam.jim_jam_url_shortener.service.impl;

import com.jim_jam.jim_jam_url_shortener.common.error.ErrorDetail;
import com.jim_jam.jim_jam_url_shortener.common.error.ErrorType;
import com.jim_jam.jim_jam_url_shortener.common.error.ErrorTypeToHttpStatus;
import com.jim_jam.jim_jam_url_shortener.common.error.UrlShortenerServiceException;
import com.jim_jam.jim_jam_url_shortener.models.GetKeyResponse;
import com.jim_jam.jim_jam_url_shortener.service.IKeyGenerationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class KeyGenerationServiceImpl implements IKeyGenerationService {

    private static final String GET_KEY_ENDPOINT = "/";

    private final RestTemplate restTemplate;
    private final String keyGenerationServiceUrl;

    public KeyGenerationServiceImpl(
            RestTemplate restTemplate,
            @Value("${key.generation.service.url}") String keyGenerationServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.keyGenerationServiceUrl = keyGenerationServiceUrl;
    }

    @Override
    public GetKeyResponse getRandomKey() throws UrlShortenerServiceException {
        try {
            ResponseEntity<GetKeyResponse> getKeyResponse = restTemplate.getForEntity(
                    keyGenerationServiceUrl.concat(GET_KEY_ENDPOINT),
                    GetKeyResponse.class);
            return getKeyResponse.getBody();
        }
        catch (RestClientException e) {
            log.error("Failed to get key.", e);
            ErrorDetail errorDetail = ErrorType.KEY_GENERATION_SERVICE_ERROR.getErrorDetail();
            throw new UrlShortenerServiceException(
                    errorDetail,
                    ErrorTypeToHttpStatus.getHttpStatus(errorDetail.getTitleKey()));
        }
    }
}
