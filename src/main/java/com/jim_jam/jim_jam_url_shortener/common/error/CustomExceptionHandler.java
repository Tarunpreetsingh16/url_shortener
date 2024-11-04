package com.jim_jam.jim_jam_url_shortener.common.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Class to handle exceptions that occur in the service
 */
@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Method to handle custom service error, {@link UrlShortenerServiceException}
     * @param ex {@link UrlShortenerServiceException}
     * @param request request for which the exception occurred
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(UrlShortenerServiceException.class)
    public ResponseEntity<ErrorResponse> handleKeyGenerationServiceException(
            UrlShortenerServiceException ex,
            WebRequest request
    ) {
        log.error("Error occurred={}", ex.errorDetail);
        return getGeneralError();
    }

    /**
     * Method to handle general error, {@link Exception}
     * @param ex {@link Exception}
     * @param request request for which the exception occurred
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(
            Exception ex,
            WebRequest request
    ) {
        log.error("Something went wrong={}", ex.getLocalizedMessage());
        return getGeneralError();
    }

    /**
     * Method to return a generic error response
     * @return {@link ResponseEntity}
     */
    private ResponseEntity<ErrorResponse> getGeneralError() {
        ErrorDetail errorDetail = ErrorType.URL_SHORTENER_SERVICE_ERROR.getErrorDetail();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .title(errorDetail.getTitle())
                .titleKey(errorDetail.getTitleKey())
                .build();
        return new ResponseEntity<>(errorResponse, ErrorTypeToHttpStatus.getHttpStatus(errorDetail.getTitleKey()));
    }
}
