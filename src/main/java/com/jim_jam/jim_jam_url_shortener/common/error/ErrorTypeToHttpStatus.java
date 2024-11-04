package com.jim_jam.jim_jam_url_shortener.common.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to map type of error with the http status
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorTypeToHttpStatus {
    private static final Map<String, HttpStatus> map = new HashMap<>();

    static {
        map.put(ErrorType.KEY_NOT_FOUND.getErrorDetail().getTitleKey(), HttpStatus.NOT_FOUND);
    }

    /**
     * Method to get {@link HttpStatus} based on type of error
     * @param key for which we want the status
     * @return {@link HttpStatus}
     */
    public static HttpStatus getHttpStatus(String key) {
        return map.getOrDefault(key, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
