package com.jim_jam.jim_jam_url_shortener.helpers;

import com.jim_jam.jim_jam_url_shortener.common.error.ErrorDetail;
import com.jim_jam.jim_jam_url_shortener.common.error.ErrorType;
import com.jim_jam.jim_jam_url_shortener.common.error.ErrorTypeToHttpStatus;
import com.jim_jam.jim_jam_url_shortener.common.error.UrlShortenerServiceException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class to handle URL utilities
 */
public class UrlHelper {
    private static final String URL_REGEX =
            "^(https?://)" +
            "([\\w\\-]+\\.)+[\\w\\-]+" +
            "(:\\d+)?(/.*)?$";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX, Pattern.CASE_INSENSITIVE);

    /**
     * Method to validate if url is valid or not
     * @param url to be validated
     * @return {@code true} if valid; otherwise, {@code false}
     */
    public static boolean isValidUrl(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.matches();
    }

    /**
     * Method to normalize the url
     * @param url to be normalized
     * @return {@link String} normalized url
     * @throws UrlShortenerServiceException when something goes wrong while normalizing
     */
    private static String normalizeUrl(String url) throws UrlShortenerServiceException {
        try {
            URI uri = new URI(url).normalize();

            String normalizedUrl = uri.toString();
            if (normalizedUrl.endsWith("/")) {
                normalizedUrl = normalizedUrl.substring(0, normalizedUrl.length() - 1);
            }
            return normalizedUrl;
        }
        catch (URISyntaxException e) {
            ErrorDetail errorDetail = ErrorType.NORMALIZING_URL_ERROR.getErrorDetail();
            throw new UrlShortenerServiceException(
                    errorDetail,
                    ErrorTypeToHttpStatus.getHttpStatus(errorDetail.getTitleKey()));
        }
    }

    /**
     * Method to encode an url with UTF_8
     * @param url to be encoded
     * @return {@link String} encoded url
     */
    private static String encodeUrl(String url) {
        return URLEncoder.encode(url, StandardCharsets.UTF_8);
    }

    /**
     * Method to decode an url with UTF_8
     * @param url to be decoded
     * @return {@link String} decoded url
     */
    public static String decodeUrl(String url) {
        return URLDecoder.decode(url, StandardCharsets.UTF_8);
    }

    /**
     * Method to get a cleaned url
     * @param url to be cleaned
     * @return {@link String} cleaned url
     * @throws UrlShortenerServiceException when something goes wrong while cleaning up the url
     */
    public static String getCleanedUrl(String url) throws UrlShortenerServiceException {
        String normalizedUrl = normalizeUrl(url);
        return encodeUrl(normalizedUrl);
    }
}
