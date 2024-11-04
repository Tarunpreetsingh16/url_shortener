package com.jim_jam.jim_jam_url_shortener.controller;

import com.jim_jam.jim_jam_url_shortener.common.error.ErrorResponse;
import com.jim_jam.jim_jam_url_shortener.common.error.UrlShortenerServiceException;
import com.jim_jam.jim_jam_url_shortener.models.GetShortUrlRequest;
import com.jim_jam.jim_jam_url_shortener.models.GetShortUrlResponse;
import com.jim_jam.jim_jam_url_shortener.service.IUrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShortenerController {

    private final IUrlShortenerService urlShortenerService;

    public UrlShortenerController(
            IUrlShortenerService urlShortenerService
    ) {
        this.urlShortenerService = urlShortenerService;
    }

    @Tag(name = "GET", description = "GET methods of the APIs.")
    @Operation(summary = "Get a short URL", description = "Method to get a url mapped to actual urly.")
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
        String key = urlShortenerService.getShortUrl(shortUrlRequestBody);
        return ResponseEntity.ok().body(
                GetShortUrlResponse.builder()
                        .shortUrl(key)
                        .build()
        );
    }
}
