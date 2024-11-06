package com.jim_jam.jim_jam_url_shortener.models;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Model to store data to be returned when an url is mapped to a short url
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(name = "GetShortUrlResponse", description = "Model to store data to be returned when an url is mapped to a short url")
public class GetShortUrlResponse {
    @Schema(name = "shortUrl", description = "Tiny url mapped to actual address", example = "http://jim-jam/abc")
    private String shortUrl;
}
