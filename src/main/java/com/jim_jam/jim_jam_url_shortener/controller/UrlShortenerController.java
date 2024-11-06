package com.jim_jam.jim_jam_url_shortener.controller;

import com.jim_jam.jim_jam_url_shortener.common.error.*;
import com.jim_jam.jim_jam_url_shortener.helpers.UrlHelper;
import com.jim_jam.jim_jam_url_shortener.models.GetShortUrlRequest;
import com.jim_jam.jim_jam_url_shortener.models.GetShortUrlResponse;
import com.jim_jam.jim_jam_url_shortener.service.IUrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlShortenerController {

    private final IUrlShortenerService urlShortenerService;

    public UrlShortenerController(
            IUrlShortenerService urlShortenerService
    ) {
        this.urlShortenerService = urlShortenerService;
    }

    @Tag(name = "POST", description = "Post methods of the APIs.")
    @Operation(summary = "Get a short URL", description = "Method to get a url mapped to actual url.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetShortUrlResponse.class)
                            )
                    }),
            @ApiResponse(
                    responseCode = "500",
                    description = "Something goes wrong.",
                    content = {
                            @Content (
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    })
    })
    @PostMapping("/")
    public ResponseEntity<GetShortUrlResponse> getShortUrl(
            @RequestBody GetShortUrlRequest shortUrlRequestBody
    ) throws UrlShortenerServiceException {
        if (!UrlHelper.isValidUrl(shortUrlRequestBody.getActualUrl())) {
            ErrorDetail errorDetail = ErrorType.INVALID_URL_ERROR.getErrorDetail();
            throw new UrlShortenerServiceException(
                    errorDetail,
                    ErrorTypeToHttpStatus.getHttpStatus(errorDetail.getTitleKey()));
        }

        String key = urlShortenerService.getShortUrl(shortUrlRequestBody);
        return ResponseEntity.ok().body(
                GetShortUrlResponse.builder()
                        .shortUrl(key)
                        .build()
        );
    }


    @Tag(name = "GET", description = "Get methods of the APIs.")
    @Operation(summary = "Redirect to actual URL", description = "Method to redirect the user to the actual URL.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "301",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found.",
                    content = {
                            @Content (
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    }),
            @ApiResponse(
                    responseCode = "500",
                    description = "Something goes wrong.",
                    content = {
                            @Content (
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    })
    })@GetMapping("/{shortUrl}")
    public ResponseEntity<URI> redirect(
            @PathVariable String shortUrl
    ) throws UrlShortenerServiceException {
        String actualUrl = urlShortenerService.getActualUrl(shortUrl);
        URI uri = URI.create(actualUrl);
        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .location(uri)
                .build();
    }
}
