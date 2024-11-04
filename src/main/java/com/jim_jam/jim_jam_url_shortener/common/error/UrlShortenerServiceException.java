package com.jim_jam.jim_jam_url_shortener.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom exception class for the service
 */
@Getter
public class UrlShortenerServiceException extends Exception{
    ErrorDetail errorDetail;
    HttpStatus httpStatus;

    /**
     * Primary constructor
     * @param errorDetail detail of an error
     * @param httpStatus {@link HttpStatus}
     */
    public UrlShortenerServiceException(ErrorDetail errorDetail, HttpStatus httpStatus) {
        super(errorDetail.getTitleKey());
        this.errorDetail = errorDetail;
        this.httpStatus = httpStatus;
    }
}
