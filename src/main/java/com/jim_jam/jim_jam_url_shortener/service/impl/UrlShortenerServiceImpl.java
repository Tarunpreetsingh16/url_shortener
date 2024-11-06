package com.jim_jam.jim_jam_url_shortener.service.impl;

import com.jim_jam.jim_jam_url_shortener.common.error.ErrorDetail;
import com.jim_jam.jim_jam_url_shortener.common.error.ErrorType;
import com.jim_jam.jim_jam_url_shortener.common.error.ErrorTypeToHttpStatus;
import com.jim_jam.jim_jam_url_shortener.common.error.UrlShortenerServiceException;
import com.jim_jam.jim_jam_url_shortener.data.ShortUrl;
import com.jim_jam.jim_jam_url_shortener.helpers.UrlHelper;
import com.jim_jam.jim_jam_url_shortener.models.GetKeyResponse;
import com.jim_jam.jim_jam_url_shortener.models.GetShortUrlRequest;
import com.jim_jam.jim_jam_url_shortener.service.IKeyGenerationService;
import com.jim_jam.jim_jam_url_shortener.service.IUrlShortenerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Concrete implementation of {@link IUrlShortenerService}
 */
@Service
@Slf4j
public class UrlShortenerServiceImpl implements IUrlShortenerService {

    private final IKeyGenerationService keyGenerationService;
    private final String domain;
    private final ShortUrlService shortUrlService;
    private final ActualUrlProvider actualUrlProvider;

    /**
     * Primary constructor to set up the bean
     * @param keyGenerationService service layer bean to interact with key generation service
     * @param shortUrlService service layer of this microservice
     * @param actualUrlProvider bean to provide url
     * @param domain hostname of the current microservice
     */
    public UrlShortenerServiceImpl(
            IKeyGenerationService keyGenerationService,
            ShortUrlService shortUrlService,
            ActualUrlProvider actualUrlProvider,
            @Value("${domain}") String domain
    ) {
        this.keyGenerationService = keyGenerationService;
        this.domain = domain;
        this.shortUrlService = shortUrlService;
        this.actualUrlProvider = actualUrlProvider;
    }

    @Override
    public String getShortUrl(GetShortUrlRequest shortUrlRequestBody) throws UrlShortenerServiceException {
        GetKeyResponse keyResponseBody = keyGenerationService.getRandomKey();

        if (keyResponseBody == null) {
            log.error("Empty response when getting a new key.");
            ErrorDetail errorDetail = ErrorType.URL_SHORTENER_SERVICE_ERROR.getErrorDetail();
            throw new UrlShortenerServiceException(
                    errorDetail,
                    ErrorTypeToHttpStatus.getHttpStatus(errorDetail.getTitleKey()));
        }
        String key = keyResponseBody.getKey();
        String actualUrl = shortUrlRequestBody.getActualUrl();
        String cleanedActualUrl = UrlHelper.getCleanedUrl(actualUrl);

        String shortUrl = domain.concat("/").concat(key);
        log.info("Successfully generated short url with id={} for actual url={}",
                shortUrl, actualUrl);

        shortUrlService.saveShortUrl(
                ShortUrl.builder()
                        .id(key)
                        .actualUrl(cleanedActualUrl)
                        .build());

        return shortUrl;
    }

    @Override
    public String getActualUrl(String shortUrlId) throws UrlShortenerServiceException {
        ShortUrl shortUrl = actualUrlProvider.getActualUrl(shortUrlId);

        if (shortUrl == null) {
            ErrorDetail errorDetail = ErrorType.KEY_NOT_FOUND.getErrorDetail();
            log.error("Short url not found for id={}", shortUrlId);
            throw new UrlShortenerServiceException(
                    errorDetail,
                    ErrorTypeToHttpStatus.getHttpStatus(errorDetail.getTitleKey()));
        }
        String decodedUrl = UrlHelper.decodeUrl(shortUrl.getActualUrl());
        log.info("Decoded url={} for key={}", decodedUrl, shortUrlId);
        return decodedUrl;
    }
}
